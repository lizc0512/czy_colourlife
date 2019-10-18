package com.door.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.door.adapter.DoorViewPagerAdapter;
import com.door.adapter.NewDoorRVAdapter;
import com.door.entity.DoorCommunityListEntity;
import com.door.entity.DoorGrantedEntity;
import com.door.entity.DoorOftenCallBack;
import com.door.entity.DoorOftenLongCallBack;
import com.door.entity.DoorRightEntity;
import com.door.entity.SingleCommunityEntity;
import com.door.model.NewDoorModel;
import com.door.view.ShortCutTipsDialog;
import com.door.view.ShowShortCutDialog;
import com.nohttp.utils.GridSpacingItemDecoration;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;


public class NewDoorActivity extends BaseActivity implements NewHttpResponse, View.OnClickListener {
    private static int INTENT_UPDATEFIXEDDOOR = 2;//编辑常用门禁
    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private SharedPreferences shared;
    public SharedPreferences.Editor editor;
    private ImageView iv_homedoor_editor;
    private ImageView iv_homedoor_close;
    private NewDoorModel openModel;
    private List<DoorCommunityListEntity.ContentBean> list = new ArrayList<>();
    private PopupWindow popupWindow;
    private RelativeLayout ll_homedoorpop_apply;
    private RelativeLayout ll_homedoorpop_authorization;
    private RelativeLayout ll_homedoorpop_compile;
    private String isgranted = "0";
    private ShowShortCutDialog showShortCutDialog;
    private ShortCutTipsDialog shortCutTipsDialog;
    private Intent.ShortcutIconResource shortcutIconResource;
    private int ispotion;
    private Icon icon;
    private String door_code;
    private boolean isloading = true;
    private boolean isshow = false;
    private ViewPager homedoor_viewpager;
    private List<View> viewList = new ArrayList<>();
    private LinearLayout linearLayout;
    private TextView tv_homedoor_communityname;
    private int position;
    private DoorViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_door_open);
        shared = getSharedPreferences(UserAppConst.USERINFO, 0);
        editor = shared.edit();
        openModel = new NewDoorModel(this);
        Intent intent = getIntent();
        if (null != intent) {
            String doorid = intent.getStringExtra("shortcut");
            String qrcode = intent.getStringExtra("qrcode");
            if (!TextUtils.isEmpty(qrcode)) {
                openModel.openDoor(2, qrcode, true, NewDoorActivity.this);
            }
            if (!TextUtils.isEmpty(doorid)) {
                openModel.openDoor(2, doorid, true, NewDoorActivity.this);
            }
        }
        initView();
        initData();
    }

    private void initView() {
        homedoor_viewpager = findViewById(R.id.homedoor_viewpager);
        iv_homedoor_editor = (ImageView) findViewById(R.id.iv_homedoor_editor);
        iv_homedoor_close = (ImageView) findViewById(R.id.iv_homedoor_close);
        iv_homedoor_close.setOnClickListener(this);
        iv_homedoor_editor.setOnClickListener(this);


    }

    /**
     * 显示popwindow
     *
     * @param parent
     */
    private void showPopWindow(View parent) {
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popWindowView = layoutInflater.from(this).inflate(R.layout.homedoor_pop, null);
            popupWindow = new PopupWindow(popWindowView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);
            //申请
            ll_homedoorpop_apply = (RelativeLayout) popWindowView.findViewById(R.id.ll_homedoorpop_apply);
            ll_homedoorpop_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent applyIntent = new Intent(NewDoorActivity.this, DoorApplyActivity.class);
                    Intent applyIntent = new Intent(NewDoorActivity.this, NewDoorIndetifyActivity.class);
                    startActivity(applyIntent);
                    popupWindow.dismiss();
                }
            });
            //门禁授权
            ll_homedoorpop_authorization = (RelativeLayout) popWindowView.findViewById(R.id.ll_homedoorpop_authorization);
            if ("1".equals(isgranted)) {
                ll_homedoorpop_authorization.setVisibility(View.VISIBLE);
            } else {
                ll_homedoorpop_authorization.setVisibility(View.GONE);
            }
            ll_homedoorpop_authorization.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!"1".equals(isgranted)) {
                        ToastUtil.toastShow(NewDoorActivity.this, getResources().getString(R.string.door_no_grantauthor));
                        return;
                    }
                    Intent authorIntent = new Intent(NewDoorActivity.this, DoorAuthorizationActivity.class);
                    startActivity(authorIntent);
                    popupWindow.dismiss();
                }
            });
            //编辑门禁
            ll_homedoorpop_compile = (RelativeLayout) popWindowView.findViewById(R.id.ll_homedoorpop_compile);
            ll_homedoorpop_compile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.size() > 0) {
                        Intent intent = new Intent(NewDoorActivity.this, NewDoorEditActivity.class);
                        position = homedoor_viewpager.getCurrentItem();
                        intent.putExtra(UserAppConst.EDITDOORCOMMUNITYUUID, list.get(position).getCommunity_uuid());
                        intent.putExtra(UserAppConst.EDITDOORCOMMUNITYNAME, list.get(position).getCommunity_name());
                        startActivityForResult(intent, INTENT_UPDATEFIXEDDOOR);
                    }
                    popupWindow.dismiss();
                }
            });
        }
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent, 0, 0);
    }

    private void initData() {
        String dooroftenCache = shared.getString(UserAppConst.HOMEDOOROFTEN, "");
        if (!TextUtils.isEmpty(dooroftenCache)) {
            dataAdapter(dooroftenCache, null);
            isloading = false;
        }
        Boolean isGranted = shared.getBoolean(UserAppConst.HAVADOORGRANTED, false);
        if (isGranted == true) {
            isgranted = "1";
        }
        openModel.getDoorRight(3, isloading, this);
        openModel.getDoorCommunityList(0, isloading, this);
        openModel.getHaveDoorRight(1, false, this);
    }


    /**
     * 生成viewpager
     *
     * @param contentBeans
     * @param numPage
     * @param arrayList
     */
    private void addPager(final List<DoorCommunityListEntity.ContentBean> contentBeans, final int numPage, DoorCommunityListEntity.ContentBean[] arrayList) {
        LayoutInflater inflater = LayoutInflater.from(NewDoorActivity.this);
        View view = inflater.inflate(R.layout.page_new_door_open, null);
        RecyclerView recyclerView = view.findViewById(R.id.page_homedoor_recyclerview);
        tv_homedoor_communityname = view.findViewById(R.id.tv_homedoor_communityname);
        tv_homedoor_communityname.setText(contentBeans.get(numPage).getCommunity_name());
        linearLayout = findViewById(R.id.ll_homedoor_page);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(NewDoorActivity.this, 3);
        recyclerView.setLayoutManager(gridLayoutManager1);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, Utils.dip2px(NewDoorActivity.this, 5), false));
        List<DoorCommunityListEntity.ContentBean.DoorListBean> list_door = new ArrayList<>();
        list_door.addAll(contentBeans.get(numPage).getDoor_list());
        if (arrayList[numPage].getDoor_list().size() > 6) {
            list_door = list_door.subList(0, 6);
        }
        NewDoorRVAdapter newDoorRVAdapter = new NewDoorRVAdapter(NewDoorActivity.this, list_door);
        recyclerView.setAdapter(newDoorRVAdapter);
        viewList.add(view);
        newDoorRVAdapter.setDoorOftenCallBack(new DoorOftenCallBack() {
            @Override
            public void getData(String result, int positon) {
                door_code = contentBeans.get(numPage).getDoor_list().get(positon).getQr_code();
                openModel.openDoor(2, door_code, true, NewDoorActivity.this);
            }
        });
        newDoorRVAdapter.setDoorOftenLongCallBack(new DoorOftenLongCallBack() {
            @Override
            public void getLongData(String result, int positon) {
                ispotion = positon;
                ShowShortcuts(contentBeans, numPage, contentBeans.get(numPage).getDoor_list().get(positon).getDoor_name(), contentBeans.get(numPage).getDoor_list().get(positon).getCommunity_type());
            }
        });


    }

    /**
     * 数据适配
     */
    private void dataAdapter(String json, List<DoorCommunityListEntity.ContentBean.DoorListBean> list_temp) {
        list.clear();
        viewList.clear();
        try {
            DoorCommunityListEntity doorCommunityListEntity = GsonUtils.gsonToBean(json, DoorCommunityListEntity.class);
            if (null != doorCommunityListEntity.getContent() && doorCommunityListEntity.getContent().size() > 0) {
                if (null != list_temp && list_temp.size() > 0) {
                    doorCommunityListEntity.getContent().get(position).setDoor_list(list_temp);
                }
                list.addAll(doorCommunityListEntity.getContent());
                DoorCommunityListEntity.ContentBean[] arrayList = list.toArray(new DoorCommunityListEntity.ContentBean[list.size()]);
                for (int i = 0; i < list.size(); i++) {
                    addPager(list, i, arrayList);
                }
                if (null == viewPagerAdapter) {
                    viewPagerAdapter = new DoorViewPagerAdapter(NewDoorActivity.this, viewList);
                    homedoor_viewpager.setAdapter(viewPagerAdapter);
                } else {
                    viewPagerAdapter.setData(viewList);
                }
                homedoor_viewpager.setAdapter(viewPagerAdapter);
                homedoor_viewpager.addOnPageChangeListener(new ViewPagerIndicator(NewDoorActivity.this, linearLayout, list.size()));
                homedoor_viewpager.setCurrentItem(position);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    /**
     * 显示快捷方式Diaog
     */
    private void ShowShortcuts(final List<DoorCommunityListEntity.ContentBean> contentBeans, final int numPage, String name, final int community_type) {
        showShortCutDialog = new ShowShortCutDialog(NewDoorActivity.this, R.style.custom_dialog_theme);
        showShortCutDialog.show();
        showShortCutDialog.setCanceledOnTouchOutside(true);
        showShortCutDialog.dialog_content.setText(name);
        showShortCutDialog.dialog_content.setSelection(showShortCutDialog.dialog_content.getText().length());
        showShortCutDialog.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(showShortCutDialog.dialog_content.getText().toString().trim())) {
                    creatShortCut(contentBeans, numPage, showShortCutDialog.dialog_content.getText().toString().trim(), community_type);
                    showShortCutDialog.dismiss();
                    if (shared.getBoolean(UserAppConst.SHOWSHORTCUTTIPS, false) == false) {
                        showTips();
                    }
                } else {
                    ToastUtil.toastShow(NewDoorActivity.this, getResources().getString(R.string.input_door_name));
                }
            }
        });
        showShortCutDialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShortCutDialog.dismiss();
            }
        });
    }

    /**
     * 添加创建快捷方式提示语
     */
    private void showTips() {
        shortCutTipsDialog = new ShortCutTipsDialog(NewDoorActivity.this, R.style.custom_dialog_theme);
        shortCutTipsDialog.show();
        shortCutTipsDialog.setCanceledOnTouchOutside(true);
        shortCutTipsDialog.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shortCutTipsDialog.dismiss();
                editor.putBoolean(UserAppConst.SHOWSHORTCUTTIPS, true);
                editor.commit();
            }
        });
        shortCutTipsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                editor.putBoolean(UserAppConst.SHOWSHORTCUTTIPS, true);
                editor.commit();
            }
        });
    }

    /**
     * 添加快捷方式
     */
    private void creatShortCut(List<DoorCommunityListEntity.ContentBean> contentBeans, int numPage, String doorName, int community_type) {
        if (Build.VERSION.SDK_INT > 25) {
            if (community_type == 1) {//1:住宅，2:写字楼
                icon = Icon.createWithResource(NewDoorActivity.this, R.drawable.icon_shortcut);
            } else {
                icon = Icon.createWithResource(NewDoorActivity.this, R.drawable.icon_shortcut_xzl);
            }
            ShortcutManager shortcutManager = (ShortcutManager) getSystemService(Context.SHORTCUT_SERVICE);
            if (shortcutManager.isRequestPinShortcutSupported()) {
                Intent shortcutInfoIntent = new Intent(NewDoorActivity.this, MainActivity.class);
                shortcutInfoIntent.setAction(Intent.ACTION_VIEW); //action必须设置，不然报错
                shortcutInfoIntent.putExtra("shortcut", contentBeans.get(numPage).getDoor_list().get(ispotion).getQr_code());
                ShortcutInfo info = new ShortcutInfo.Builder(NewDoorActivity.this, doorName)
                        .setIcon(icon)
                        .setShortLabel(doorName)
                        .setIntent(shortcutInfoIntent)
                        .build();
                //当添加快捷方式的确认弹框弹出来时，将被回调
                PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(NewDoorActivity.this, 0, new Intent(NewDoorActivity.this, MainActivity.MyReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
                shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.getIntentSender());
            }
        } else {
            Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
            shortcutIntent.setClass(NewDoorActivity.this, MainActivity.class);
            shortcutIntent.putExtra("shortcut", contentBeans.get(numPage).getDoor_list().get(ispotion).getQr_code());
            Intent shortcut = new Intent(ACTION_ADD_SHORTCUT);
            shortcut.putExtra("duplicate", true);
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, doorName);
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            if (community_type == 1) {//1:住宅，2:写字楼
                shortcutIconResource = Intent.ShortcutIconResource.fromContext(NewDoorActivity.this, R.drawable.icon_shortcut);
            } else {
                shortcutIconResource = Intent.ShortcutIconResource.fromContext(NewDoorActivity.this, R.drawable.icon_shortcut_xzl);
            }
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, shortcutIconResource);
            this.sendBroadcast(shortcut);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_UPDATEFIXEDDOOR) {
            if (resultCode == RESULT_OK) {
                List<SingleCommunityEntity.ContentBean.CommonUseBean> list_item = new ArrayList<>();
                List<DoorCommunityListEntity.ContentBean.DoorListBean> list_temp = new ArrayList<>();
                list_item = (List<SingleCommunityEntity.ContentBean.CommonUseBean>) data.getSerializableExtra("itemlist");
                for (int i = 0; i < list_item.size(); i++) {
                    DoorCommunityListEntity.ContentBean.DoorListBean doorListBean = new DoorCommunityListEntity.ContentBean.DoorListBean();
                    doorListBean.setCommunity_type(list_item.get(i).getCommunity_type());
                    doorListBean.setConnection_type(list_item.get(i).getConnection_type());
                    doorListBean.setDoor_id(list_item.get(i).getDoor_id());
                    doorListBean.setDoor_img(list_item.get(i).getDoor_img());
                    doorListBean.setDoor_name(list_item.get(i).getDoor_name());
                    doorListBean.setDoor_type(list_item.get(i).getDoor_type());
                    doorListBean.setPosition(list_item.get(i).getPosition());
                    doorListBean.setQr_code(list_item.get(i).getQr_code());
                    list_temp.add(doorListBean);
                }
                String dooroftenCache = shared.getString(UserAppConst.HOMEDOOROFTEN, "");
                dataAdapter(dooroftenCache, list_temp);
                try {
                    String stringData = GsonUtils.gsonString(list);
                    JSONObject jsonObject = new JSONObject();
                    JSONArray jsonArray = new JSONArray(stringData);
                    jsonObject.put("code", 0);
                    jsonObject.put("message", "success");
                    jsonObject.put("content", jsonArray);
                    jsonObject.put("contentEncrypt", "");
                    editor.putString(UserAppConst.HOMEDOOROFTEN, jsonObject.toString());
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    editor.putString(UserAppConst.HOMEDOOROFTEN, result);
                    editor.commit();
                    dataAdapter(result, null);
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    DoorGrantedEntity doorGrantedEntity = GsonUtils.gsonToBean(result, DoorGrantedEntity.class);
                    try {
                        isgranted = doorGrantedEntity.getContent().getIsgranted();
                        if ("1".equals(isgranted)) {
                            editor.putBoolean(UserAppConst.HAVADOORGRANTED, true);
                        } else {
                            editor.putBoolean(UserAppConst.HAVADOORGRANTED, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    Intent intent = new Intent();
                    intent.putExtra("doorData", result);
                    intent.putExtra("door_code", door_code);
                    setResult(2001, intent);
                    NewDoorActivity.this.finish();
                    this.overridePendingTransition(R.anim.push_right_in, R.anim.door_push_bottom_out);
                }
                break;
            case 3:
                if (!TextUtils.isEmpty(result)) {
                    if ("1".equals(result)) {//默认显示数据
                        homedoor_viewpager.setVisibility(View.VISIBLE);
                    } else {
                        try {
                            DoorRightEntity doorRightEntity = GsonUtils.gsonToBean(result, DoorRightEntity.class);
                            if (doorRightEntity.getContent().getAuthority() == 1) {//有权限
                                homedoor_viewpager.setVisibility(View.VISIBLE);
                            } else if (doorRightEntity.getContent().getAuthority() == 2) {//无权限
                                Intent intent = new Intent(NewDoorActivity.this, NoRightDoorActivity.class);
                                startActivity(intent);
                                NewDoorActivity.this.finish();
                                this.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_homedoor_close:
                NewDoorActivity.this.finish();
                this.overridePendingTransition(0, R.anim.door_push_bottom_out);
                break;
            case R.id.iv_homedoor_editor:
                showPopWindow(v);
                break;

        }
    }

    public class ViewPagerIndicator implements ViewPager.OnPageChangeListener {
        private int mPageCount;//页数
        private List<ImageView> mImgList;//保存img总个数
        private int img_select;
        private int img_unSelect;

        public ViewPagerIndicator(Context context, LinearLayout linearLayout, int pageCount) {
            this.mPageCount = pageCount;
            mImgList = new ArrayList<>();
            img_select = R.drawable.icon_point_n;
            img_unSelect = R.drawable.icon_point_d;
            final int imgSize = 25;
            mImgList.clear();
            linearLayout.removeAllViews();
            for (int i = 0; i < mPageCount; i++) {
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                //为小圆点左右添加间距
                params.leftMargin = 10;
                params.rightMargin = 10;
                //给小圆点一个默认大小
                params.height = imgSize;
                params.width = imgSize;
                if (i == 0) {
                    imageView.setBackgroundResource(img_select);
                } else {
                    imageView.setBackgroundResource(img_unSelect);
                }
                //为LinearLayout添加ImageView
                linearLayout.addView(imageView, params);
                mImgList.add(imageView);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < mPageCount; i++) {
                //选中的页面改变小圆点为选中状态，反之为未选中
                if ((position % mPageCount) == i) {
                    (mImgList.get(i)).setBackgroundResource(img_select);
                } else {
                    (mImgList.get(i)).setBackgroundResource(img_unSelect);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
