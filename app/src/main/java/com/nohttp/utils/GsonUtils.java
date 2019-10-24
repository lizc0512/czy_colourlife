package com.nohttp.utils;


import com.BeeFramework.Utils.ToastUtil;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/*
 * @date 创建时间 2017/7/3
 * @author  yuansk
 * @Description   gson的解析
 * @version  1.1
 */
public class GsonUtils {


    private static Gson gson = null;

    static {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
            gson = builder
                    .registerTypeAdapter(new TypeToken<Map<String, Object>>() {
                            }.getType(),
                            new JsonDeserializer<Map<String, String>>() {
                                @Override
                                public Map<String, String> deserialize(
                                        JsonElement json, Type typeOfT,
                                        JsonDeserializationContext context) throws JsonParseException {

                                    Map<String, String> treeMap = new HashMap<>();
                                    JsonObject jsonObject = json.getAsJsonObject();
                                    Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                                    for (Map.Entry<String, JsonElement> entry : entrySet) {
                                        treeMap.put(entry.getKey(), entry.getValue().getAsString());
                                    }
                                    return treeMap;
                                }
                            })
                    .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();

        }
    }


    private GsonUtils() {

    }

    private static TypeAdapter<Number> INTEGER = new TypeAdapter<Number>() {
        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return 0;
            }
            try {
                return in.nextInt();
            } catch (NumberFormatException e) {
                in.nextString();
            } catch (IllegalStateException e) {
                in.nextBoolean();
            }
            return 0;
        }

        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.value(value);
        }
    };

    private static TypeAdapter<Number> LONG = new TypeAdapter<Number>() {
        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return 0;
            }
            //这里同
            try {
                return in.nextLong();
            } catch (Exception e) {
                in.nextString();
            }
            return 0;
        }

        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.value(value);

        }
    };

    private static TypeAdapter<Number> DOUBLE = new TypeAdapter<Number>() {
        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return 0.0;
            }
            //这里同
            try {
                return in.nextDouble();
            } catch (Exception e) {
                in.nextString();
            }
            return 0.0;
        }

        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.value(value);
        }
    };

    /***处理基本数据类型***/
    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType == String.class) {
                return (TypeAdapter<T>) new StringNullAdapter();
            } else if (rawType == int.class) {
                return (TypeAdapter<T>) INTEGER;
            } else if (rawType == long.class) {
                return (TypeAdapter<T>) LONG;
            } else if (rawType == double.class) {
                return (TypeAdapter<T>) DOUBLE;
            } else {
                return null;
            }
        }
    }

    public static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            // TODO Auto-generated method stub
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            // TODO Auto-generated method stub
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }

    }


    public static Gson getSkipIdAndGroupIdGson() {
        Gson gson = new GsonBuilder().setExclusionStrategies(
                new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        //过滤掉字段名包含"id","address"的字段
                        return f.getName().equals("id") | f.getName().equals("groupGuid");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 过滤掉 类名包含 Bean的类
                        return false;
                    }
                }).create();
        return gson;
    }

    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    public static String gsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }


    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }


    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }


    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }


    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {


            }.getType());
        }
        return map;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static Map<String, String> gsonObjectToMaps(String gsonString) {
        Map<String, String> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, Object>>() {


            }.getType());
        }
        return map;
    }

}
