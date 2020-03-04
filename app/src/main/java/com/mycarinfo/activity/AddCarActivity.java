package com.mycarinfo.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ReplacementTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.CompressHelper;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.mycarinfo.model.MyCarInfoModel;
import com.mycarinfo.protocol.COLOURTICKETPROVINCEINFOLIST;
import com.mycarinfo.protocol.VehicleAddvehiclePostApi;
import com.mycarinfo.protocol.VehicleGetprovinceGetApi;
import com.myproperty.adapter.ProvinceAdapter;
import com.myproperty.model.PropertyInfoModel;
import com.myproperty.protocol.ImageuploadPostApi;
import com.permission.AndPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.utils.GlideImageLoader;
import cn.csh.colourful.life.view.imagepicker.ImagePicker;
import cn.csh.colourful.life.view.imagepicker.bean.ImageItem;
import cn.csh.colourful.life.view.imagepicker.ui.ImageGridActivity;
import cn.net.cyberway.R;
import cn.net.cyberway.utils.FileUtils;

public class AddCarActivity extends BaseActivity implements View.OnClickListener, HttpApiResponse {
    private ImageView mBack;
    private TextView mTitle;
    private TextView mRightText;
    private RelativeLayout rl_addcar_model;
    private RelativeLayout rl_addcar_carNum;//选择车牌号
    private Button button;//提交按钮
    private ImageView iv_addcar_idfront2;
    private ImageView iv_addcar_idback2;
    private ImageView iv_addcar_dirverfront2;
    private ImageView iv_addcar_dirverback2;
    private String types;
    private PropertyInfoModel propertyInfoModel;
    private EditText carnumber, vehicle_code, engine_code, name, phone;
    private String names, phones, carnumbers, vehicle_codes, engine_codes;//车型
    private String brand_id, model_id, colour_id, card_font_id = "", card_back_id = "", driver_card_font_id = "", driver_card_back_id = "";
    private boolean isCarModel;//是否选择了车型，默认为false
    private MyCarInfoModel myCarInfoModel;
    private LinearLayout llpai;
    private MyListView xListView;
    private TextView tv_addcar_pai, tv_addcar_a;//'省份
    private List<COLOURTICKETPROVINCEINFOLIST> list_proviece = new ArrayList<>();
    private List<COLOURTICKETPROVINCEINFOLIST> list = new ArrayList<COLOURTICKETPROVINCEINFOLIST>();
    boolean nexta = false;
    private TextView tv_addcar_brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        initImagePicker();
        initPublic();
        initView();
    }


    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    private void initPublic() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        mRightText = (TextView) findViewById(R.id.user_top_view_right);
        mTitle.setText(getResources().getString(R.string.car_increase));
        mBack.setOnClickListener(this);
        ThemeStyleHelper.rightTexteFrameLayout(getApplicationContext(), czy_title_layout, mBack, mTitle, mRightText);
        for (int i = 0; i < 26; i++) {
            COLOURTICKETPROVINCEINFOLIST a = new COLOURTICKETPROVINCEINFOLIST();
            a.setShort_name(String.valueOf((char) (65 + i)));
            list.add(a);
        }
    }

    private void initView() {
        tv_addcar_brand = (TextView) findViewById(R.id.tv_addcar_brand);
        tv_addcar_a = (TextView) findViewById(R.id.tv_addcar_a);
        tv_addcar_pai = (TextView) findViewById(R.id.tv_addcar_pai);
        llpai = (LinearLayout) findViewById(R.id.ll_addcar_pai);
        rl_addcar_carNum = (RelativeLayout) findViewById(R.id.rl_addcar_carNum);
        rl_addcar_carNum.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_addcar_commit);
        button.setOnClickListener(this);
        name = (EditText) findViewById(R.id.et_addcar_familyname);
        phone = (EditText) findViewById(R.id.et_addcar_phone);
        carnumber = (EditText) findViewById(R.id.et_addcar_carnumber);
        vehicle_code = (EditText) findViewById(R.id.et_addcar_vehicle_code);
        engine_code = (EditText) findViewById(R.id.et_addcar_engine_code);
        iv_addcar_idfront2 = (ImageView) findViewById(R.id.iv_addcar_idfront2);
        iv_addcar_idback2 = (ImageView) findViewById(R.id.iv_addcar_idback2);
        iv_addcar_dirverfront2 = (ImageView) findViewById(R.id.iv_addcar_driverfront2);
        iv_addcar_dirverback2 = (ImageView) findViewById(R.id.iv_addcar_driverback2);
        rl_addcar_model = (RelativeLayout) findViewById(R.id.rl_addcar_model);
        iv_addcar_idfront2.setOnClickListener(this);
        iv_addcar_idback2.setOnClickListener(this);
        iv_addcar_dirverfront2.setOnClickListener(this);
        iv_addcar_dirverback2.setOnClickListener(this);
        rl_addcar_model.setOnClickListener(this);
        propertyInfoModel = new PropertyInfoModel(this);
        myCarInfoModel = new MyCarInfoModel(this);
        carnumber.setTransformationMethod(new AllCapTransformationMethod());
        xListView = (MyListView) findViewById(R.id.lv_addcar_pai);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < list.size()) {
                    if (nexta == true) {
                        tv_addcar_a.setText(list.get(position).getShort_name());
                        nexta = false;
                        llpai.setVisibility(View.GONE);
                    } else if (nexta == false) {
                        if (!list_proviece.isEmpty()) {
                            tv_addcar_pai.setText(list_proviece.get(position).short_name);
                            notirefrsh();
                        }
                    }
                }
            }
        });
    }

    private void notirefrsh() {
        nexta = true;
        adapter = new ProvinceAdapter(this, list);
        xListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View contentView = this.getLayoutInflater().inflate(R.layout.user_avatar_dialog, null);
        dialog.setContentView(contentView);
        android.view.ViewGroup.LayoutParams cursorParams = contentView.getLayoutParams();
        cursorParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(cursorParams);
        dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
        TextView album = (TextView) dialog.findViewById(R.id.avatar_album);
        TextView photograph = (TextView) dialog.findViewById(R.id.avatar_photograph);
        TextView cancel = (TextView) dialog.findViewById(R.id.avatar_cancel);
        switch (v.getId()) {
            case R.id.user_top_view_back:
                AddCarActivity.this.finish();
                break;
            case R.id.rl_addcar_carNum://选择车牌
                myCarInfoModel.getProvince(this);
                break;
            case R.id.rl_addcar_model:
                Intent intent = new Intent();
                intent.setClass(AddCarActivity.this, CarModelActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.iv_addcar_idfront2:
                dialog.show();
                //从相册选择上传
                album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        picture(501);
                    }
                });
                //拍照上传
                photograph.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openCarcme(501);
                    }
                });
                //取消
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.iv_addcar_idback2:
                dialog.show();
                //从相册选择上传
                album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        picture(502);
                    }
                });
                //拍照上传
                photograph.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openCarcme(502);
                    }
                });
                //取消
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.iv_addcar_driverfront2:
                dialog.show();
                //从相册选择上传
                album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        picture(503);

                    }
                });
                //拍照上传
                photograph.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openCarcme(503);
                    }
                });
                //取消
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.iv_addcar_driverback2:
                dialog.show();
                //从相册选择上传
                album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        picture(504);
                    }
                });
                //拍照上传
                photograph.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openCarcme(504);
                    }
                });
                //取消
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.btn_addcar_commit:
                String owenerName = name.getText().toString().trim();
                String owenerPhone = phone.getText().toString().trim();
                if (!TextUtils.isEmpty(owenerName)) {
                    if (!TextUtils.isEmpty(owenerPhone) && owenerPhone.startsWith("1")) {
                        if (!carnumber.getText().toString().trim().isEmpty()) {
                            if (isCarModel == true) {
                                if (!vehicle_code.getText().toString().trim().isEmpty()) {
                                    if (!engine_code.getText().toString().trim().isEmpty()) {
                                        if (card_font_id != "" && card_back_id != "") {//card_font_id, card_back_id, driver_card_font_id, driver_card_back_id
                                            if (driver_card_font_id != "" && driver_card_back_id != "") {
                                                commit();
                                            } else {
                                                ToastUtil.toastShow(AddCarActivity.this, getResources().getString(R.string.car_choice_vehicle));
                                            }
                                        } else {
                                            ToastUtil.toastShow(AddCarActivity.this, getResources().getString(R.string.car_choice_idcard));
                                        }
                                    } else {
                                        ToastUtil.toastShow(AddCarActivity.this, getResources().getString(R.string.car_input_engine));
                                    }
                                } else {
                                    ToastUtil.toastShow(AddCarActivity.this, getResources().getString(R.string.car_input_vehiclenumber));
                                }
                            } else {
                                ToastUtil.toastShow(AddCarActivity.this, getResources().getString(R.string.car_choice_carmodel));
                            }
                        } else {
                            ToastUtil.toastShow(AddCarActivity.this, getResources().getString(R.string.car_input_number));
                        }
                    } else {
                        ToastUtil.toastShow(AddCarActivity.this, getResources().getString(R.string.property_mobile_error));
                    }
                } else {
                    ToastUtil.toastShow(AddCarActivity.this, getResources().getString(R.string.car_input_name));
                }
                break;

        }
    }

    private void commit() {
        names = name.getText().toString().trim();
        phones = phone.getText().toString().trim();
        vehicle_codes = vehicle_code.getText().toString().trim();
        engine_codes = engine_code.getText().toString().trim();
        carnumbers = tv_addcar_pai.getText().toString() + tv_addcar_a.getText().toString() + carnumber.getText().toString().trim();//车牌号
        myCarInfoModel.postaddVehicle(this, names, phones, carnumbers, vehicle_codes, engine_codes,
                brand_id, model_id, colour_id, card_font_id, card_back_id, driver_card_font_id, driver_card_back_id);

    }


    /**
     * 本地相册选择图片
     */
    private void picture(int requestCode) {
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent = new Intent(AddCarActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 打开照相机
     */
    private void openCarcme(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermission(getApplicationContext(), Manifest.permission.CAMERA)) {
                ImagePicker.getInstance().setSelectLimit(1);
                Intent intent = new Intent(AddCarActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, requestCode);
            } else {
                ToastUtil.toastShow(getApplicationContext(), getResources().getString(R.string.user_camerapermission_notice));
            }
        } else {
            ImagePicker.getInstance().setSelectLimit(1);
            Intent intent = new Intent(AddCarActivity.this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1://获取车型
                if (resultCode == 1) {
                    Bundle b = data.getExtras(); //data为B中回传的Intent
                    String all = b.getString("car") + " " + data.getStringExtra("carmodel") + " " + b.getString("carcolour");
                    tv_addcar_brand.setText(all);
                    tv_addcar_brand.setTextColor(getResources().getColor(R.color.black_text_color));
                    isCarModel = true;
                    brand_id = data.getStringExtra("carid");
                    model_id = data.getStringExtra("carmodelid");
                    colour_id = data.getStringExtra("carcolourid");
                }
                break;
            case 501:
            case 502:
            case 503:
            case 504:
                if (null != data) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    if (null == images || images.size() == 0) {
                        return;
                    }
                    String path = images.get(0).path;
                    Bitmap newBp = FileUtils.decodeSampledBitmapFromFd(path, 100, 100);
                    if (requestCode == 501) {
                        iv_addcar_idfront2.setImageBitmap(newBp);
                        types = "idcard_front";
                    } else if (requestCode == 502) {
                        iv_addcar_idback2.setImageBitmap(newBp);
                        types = "idcard_back";
                    } else if (requestCode == 503) {
                        iv_addcar_dirverfront2.setImageBitmap(newBp);
                        types = "license_front";
                    } else if (requestCode == 504) {
                        iv_addcar_dirverback2.setImageBitmap(newBp);
                        types = "license_back";
                    }
                    File newFile = CompressHelper.getDefault(this).compressToFile(new File(path));
                    propertyInfoModel.postimageUpload(this, newFile.getPath(), types);
                }
                break;
        }
    }

    private ProvinceAdapter adapter;

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(VehicleAddvehiclePostApi.class)) {//新增车辆
            VehicleAddvehiclePostApi vehicleAddvehiclePostApi = (VehicleAddvehiclePostApi) api;
            if (vehicleAddvehiclePostApi.response.code == 0) {
                setResult(120);
                ToastUtil.toastShow(AddCarActivity.this, getResources().getString(R.string.car_add_success));
                AddCarActivity.this.finish();
            }
        }
        if (api.getClass().equals(VehicleGetprovinceGetApi.class)) {//省份简称
            VehicleGetprovinceGetApi vehicleGetprovinceGetApi = (VehicleGetprovinceGetApi) api;
            if (vehicleGetprovinceGetApi.response.code == 0) {
                list_proviece = vehicleGetprovinceGetApi.response.data;
                if (!list_proviece.isEmpty() && list_proviece.size() != 0) {
                    if (adapter == null) {
                        adapter = new ProvinceAdapter(this, list_proviece);
                        xListView.setAdapter(adapter);
                    } else {
                        adapter = new ProvinceAdapter(this, list_proviece);
                        xListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    llpai.setVisibility(View.VISIBLE);
                } else {
                    ToastUtil.toastShow(this, "获取省份简称失败");
                }
            } else {
                ToastUtil.toastShow(this, vehicleGetprovinceGetApi.response.message);
            }
        }
        if (api.getClass().equals(ImageuploadPostApi.class)) {//上传图片
            ImageuploadPostApi imageuploadPostApi = (ImageuploadPostApi) api;
            if (imageuploadPostApi.response.code == 0) {
                ToastUtil.toastShow(AddCarActivity.this, getResources().getString(R.string.property_upload_success));
                if (types.equals("idcard_front")) {
                    card_font_id = imageuploadPostApi.response.content.image_id;
                } else if (types.equals("idcard_back")) {
                    card_back_id = imageuploadPostApi.response.content.image_id;
                } else if (types.equals("license_front")) {
                    driver_card_font_id = imageuploadPostApi.response.content.image_id;
                } else if (types.equals("license_back")) {
                    driver_card_back_id = imageuploadPostApi.response.content.image_id;
                }
                types = "";
            } else {
                ToastUtil.toastShow(AddCarActivity.this, getResources().getString(R.string.property_upload_fail));
            }

        }

    }

    public class AllCapTransformationMethod extends ReplacementTransformationMethod {

        @Override
        protected char[] getOriginal() {
            char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] cc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return cc;
        }

    }
}
