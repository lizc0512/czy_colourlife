package com.door.activity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import com.BeeFramework.activity.BaseFragmentActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.cashier.adapter.ViewPagerAdapter;
import com.door.adapter.DoorViewPagerAdapter;
import com.door.adapter.NewDoorRVAdapter;
import com.door.dialog.ClickPwdDialog;
import com.door.dialog.PwdDialog;
import com.door.entity.DoorAllEntity;
import com.door.entity.DoorCommunityListEntity;
import com.door.entity.DoorGrantedEntity;
import com.door.entity.DoorOftenCallBack;
import com.door.entity.DoorOftenLongCallBack;
import com.door.entity.DoorRecordEntity;
import com.door.entity.DoorRightEntity;
import com.door.fragment.IntelligenceDoorFragment;
import com.door.model.NewDoorModel;
import com.door.view.ShortCutTipsDialog;
import com.door.view.ShowShortCutDialog;
import com.nohttp.utils.GridSpacingItemDecoration;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import cn.net.cyberway.home.service.LekaiParkLockController;
import cn.net.cyberway.utils.LekaiHelper;
import cn.net.cyberway.utils.TableLayoutUtils;

/**
 * 智能门禁
 * hxg 2019.8.6
 */
public class IntelligenceDoorActivity extends BaseFragmentActivity implements NewHttpResponse, View.OnClickListener, LekaiParkLockController.OnScanParkLockChangeListener {
    private static String err = "操作失败，请重试";

    public static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static int INTENT_UPDATEFIXEDDOOR = 2;//编辑常用门禁
    public SharedPreferences.Editor editor;
    private SharedPreferences shared;
    private NewDoorModel newDoorModel;
    //    private List<DoorCommunityListEntity.ContentBean> list = new ArrayList<>();
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
    //    private ViewPager homedoor_viewpager;
    private List<View> viewList = new ArrayList<>();
    private LinearLayout linearLayout;
    private TextView tv_homedoor_communityname;
    private int position;
    private DoorViewPagerAdapter viewPagerAdapter;

    private ImageView iv_editor;
    private View v_status_bar;
    private TabLayout tl_door;
    private ViewPager vp_door;
    private ImageView iv_close;
    private List<Fragment> fragmentList = new ArrayList<>();
    //    private List<DoorAllEntity.ContentBean.DataBean> contentList = new ArrayList<>();
    private String device;
    private ClickPwdDialog clickPwdDialog;
    private int pwdNum = 0;
    private String pwdTime = "";
    private PwdDialog pwdDialog;
    private List<String> communityUuidList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intelligent_door);
        shared = getSharedPreferences(UserAppConst.USERINFO, 0);
        editor = shared.edit();
        newDoorModel = new NewDoorModel(this);
        Intent intent = getIntent();
        if (null != intent) {
            String doorid = intent.getStringExtra("shortcut");//快捷方式开门
            String qrcode = intent.getStringExtra("qrcode");//扫码开门
            if (!TextUtils.isEmpty(qrcode)) {
                newDoorModel.openDoor(2, qrcode, true, IntelligenceDoorActivity.this);
            }
            if (!TextUtils.isEmpty(doorid)) {
                newDoorModel.openDoor(2, doorid, true, IntelligenceDoorActivity.this);
            }
        }
        LekaiHelper.setScanParkLockChangeListener(this);

        initView();
        initData();
    }

    private void initView() {
//        homedoor_viewpager = findViewById(R.id.homedoor_viewpager);

        tintManager.setStatusBarTintColor(Color.parseColor("#00000000"));
        iv_editor = findViewById(R.id.iv_editor);
        v_status_bar = findViewById(R.id.v_status_bar);
        setStatusBarHeight(v_status_bar);
        tl_door = findViewById(R.id.tl_door);
        vp_door = findViewById(R.id.vp_door);
        iv_close = findViewById(R.id.iv_close);

        iv_editor.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }

    private void initTab(String[] tabTitleArray, String result) {
        if (1 == tabTitleArray.length) {
            if (TextUtils.isEmpty(tabTitleArray[0])) {
                return;
            }
        }
        try {
            for (int i = 0; i < tabTitleArray.length; i++) {
                tl_door.addTab(tl_door.newTab().setText(tabTitleArray[i]));
                fragmentList.add(IntelligenceDoorFragment.newInstance(i, result/*contentList.get(i))*/));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tl_door.setTabTextColors(getResources().getColor(R.color.color_b3ffffff), getResources().getColor(R.color.white));
        tl_door.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        tl_door.setSelectedTabIndicatorHeight(3);
        tl_door.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this, fragmentList, tabTitleArray);
        vp_door.setAdapter(adapter);
        vp_door.setOffscreenPageLimit(fragmentList.size());
        tl_door.setupWithViewPager(vp_door);
        TableLayoutUtils.dynamicSetTabLayoutMode(this, tl_door);//tab可滑动
    }

    private void setStatusBarHeight(View tabBarView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        tabBarView.setLayoutParams(layoutParams);
    }

    private void initData() {
//        String dooroftenCache = shared.getString(UserAppConst.HOMEDOOROFTEN, "");
//        if (!TextUtils.isEmpty(dooroftenCache)) {
//            dataAdapter(dooroftenCache, null);
//            isloading = false;
//        }
        boolean isGranted = shared.getBoolean(UserAppConst.HAVADOORGRANTED, false);//true 已授权
        if (isGranted) {
            isgranted = "1";
        }
        String intelligenceDoorCache = shared.getString(UserAppConst.COLOUR_INTELLIGENCE_DOOR, "");
        setData(intelligenceDoorCache);

//        newDoorModel.getDoorRight(3, isloading, this);
//        newDoorModel.getDoorCommunityList(0, isloading, this);//弃用
        newDoorModel.getHaveDoorRight(1, false, this);//授权管理
        newDoorModel.getCommunityKey(5, this);//获取小区门禁
    }

    /**
     * 获取设备密码
     */
    public void getDevicePwd(String device) {
        this.device = device;
        newDoorModel.devicePasswordLog(7, device, 1, this);
    }

    /**
     * 远程开门
     */
    public void remoteDoor(String device) {
        newDoorModel.deviceRemoteUnlock(6, device, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_editor:
                showPopWindow(v);
                break;
            case R.id.iv_close:
                finish();
                this.overridePendingTransition(0, R.anim.door_push_bottom_out);
                break;
        }
    }

    /**
     * 显示popwindow
     *
     * @param parent
     */
    private void showPopWindow(View parent) {
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popWindowView = LayoutInflater.from(this).inflate(R.layout.homedoor_pop, null);
            popupWindow = new PopupWindow(popWindowView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);
            //申请
            ll_homedoorpop_apply = popWindowView.findViewById(R.id.ll_homedoorpop_apply);
            ll_homedoorpop_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent applyIntent = new Intent(IntelligenceDoorActivity.this, DoorApplyActivity.class);
                    startActivity(applyIntent);
                    popupWindow.dismiss();
                }
            });
            //门禁授权
            ll_homedoorpop_authorization = popWindowView.findViewById(R.id.ll_homedoorpop_authorization);
            if ("1".equals(isgranted)) {
                ll_homedoorpop_authorization.setVisibility(View.VISIBLE);
            } else {
                ll_homedoorpop_authorization.setVisibility(View.GONE);
            }
            ll_homedoorpop_authorization.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!"1".equals(isgranted)) {
                        ToastUtil.toastShow(IntelligenceDoorActivity.this, getResources().getString(R.string.door_no_grantauthor));
                        return;
                    }
                    Intent authorIntent = new Intent(IntelligenceDoorActivity.this, DoorAuthorizationActivity.class);
                    startActivity(authorIntent);
                    popupWindow.dismiss();
                }
            });
            //编辑门禁
            ll_homedoorpop_compile = popWindowView.findViewById(R.id.ll_homedoorpop_compile);
            ll_homedoorpop_compile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (communityUuidList.size() > 0) {
                        Intent intent = new Intent(IntelligenceDoorActivity.this, NewDoorEditActivity.class);
                        position = vp_door.getCurrentItem();
                        intent.putExtra(UserAppConst.EDITDOORCOMMUNITYUUID, communityUuidList.get(position));
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

    /**
     * 生成viewpager
     *
     * @param contentBeans
     * @param numPage
     * @param arrayList
     */
    private void addPager(final List<DoorCommunityListEntity.ContentBean> contentBeans, final int numPage, DoorCommunityListEntity.ContentBean[] arrayList) {
        LayoutInflater inflater = LayoutInflater.from(IntelligenceDoorActivity.this);
        View view = inflater.inflate(R.layout.page_new_door_open, null);
        RecyclerView recyclerView = view.findViewById(R.id.page_homedoor_recyclerview);
        tv_homedoor_communityname = view.findViewById(R.id.tv_homedoor_communityname);
        tv_homedoor_communityname.setText(contentBeans.get(numPage).getCommunity_name());
        linearLayout = findViewById(R.id.ll_homedoor_page);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(IntelligenceDoorActivity.this, 3);
        recyclerView.setLayoutManager(gridLayoutManager1);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, Utils.dip2px(IntelligenceDoorActivity.this, 5), false));
        List<DoorCommunityListEntity.ContentBean.DoorListBean> list_door = new ArrayList<>();
        list_door.addAll(contentBeans.get(numPage).getDoor_list());
        if (arrayList[numPage].getDoor_list().size() > 6) {
            list_door = list_door.subList(0, 6);
        }
        NewDoorRVAdapter newDoorRVAdapter = new NewDoorRVAdapter(IntelligenceDoorActivity.this, list_door);
        recyclerView.setAdapter(newDoorRVAdapter);
        viewList.add(view);
        newDoorRVAdapter.setDoorOftenCallBack(new DoorOftenCallBack() {
            @Override
            public void getData(String result, int positon) {
                door_code = contentBeans.get(numPage).getDoor_list().get(positon).getQr_code();
                newDoorModel.openDoor(2, door_code, true, IntelligenceDoorActivity.this);
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

    public void setIntelligenceDoorCallBack(String code) {
        newDoorModel.openDoor(2, code, true, IntelligenceDoorActivity.this);
    }

    public void setIntelligenceDoorLongCallBack(String qrcode, String name, int community_type) {
        ispotion = position;
        ShowShortcuts(qrcode, 0, name, community_type);
    }

    /**
     * 点击获取密码
     */
    private void clickPwdDialog(String num, String time, String result) {
        if (null == clickPwdDialog) {
            clickPwdDialog = new ClickPwdDialog(this);
        }
        clickPwdDialog.show();
        clickPwdDialog.tv_pwd_num.setText(num);
        clickPwdDialog.tv_pwd_time.setText(time);
        clickPwdDialog.iv_close.setOnClickListener(v -> {
            if (null != clickPwdDialog) {
                clickPwdDialog.dismiss();
            }
        });
        clickPwdDialog.tv_pwd_record.setOnClickListener(v -> {
            if (null != clickPwdDialog) {
                clickPwdDialog.dismiss();
            }
            Intent intent = new Intent(this, IntelligenceDoorRecordActivity.class);
            intent.putExtra(IntelligenceDoorRecordActivity.DEVICE, device);
            intent.putExtra(IntelligenceDoorRecordActivity.RESULT, result);
            startActivity(intent);
        });
        clickPwdDialog.tv_pwd_get.setOnClickListener(v -> {
            if (null != clickPwdDialog) {
                clickPwdDialog.dismiss();
            }
            newDoorModel.getDevicePwd(4, device, this);
        });
    }

    /**
     * 分享密码
     */
    @SuppressLint("SetTextI18n")
    private void pwdDialog(String password) {
        if (null == pwdDialog) {
            pwdDialog = new PwdDialog(this);
        }
        pwdDialog.show();
        pwdNum = pwdNum - 1;
        pwdDialog.tv_pwd_num.setText(pwdNum + "");
        pwdDialog.tv_pwd_time.setText(pwdTime);
        pwdDialog.iv_close.setOnClickListener(v1 -> {
            if (null != pwdDialog) {
                pwdDialog.dismiss();
            }
        });
        pwdDialog.tv_share.setOnClickListener(v2 -> {
            if (null != pwdDialog) {
                pwdDialog.dismiss();
            }
            android.content.ClipboardManager cmb = (android.content.ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(password);
            cmb.getText();
            ToastUtil.toastShow(this, "开门密码已复制");
        });
    }

    /**
     * 数据适配
     */
    private void dataAdapter(String json, List<DoorCommunityListEntity.ContentBean.DoorListBean> list_temp) {
//        list.clear();
//        viewList.clear();
//        try {
//            DoorCommunityListEntity doorCommunityListEntity = GsonUtils.gsonToBean(json, DoorCommunityListEntity.class);
//            if (null != doorCommunityListEntity.getContent() && doorCommunityListEntity.getContent().size() > 0) {
//                if (null != list_temp && list_temp.size() > 0) {
//                    doorCommunityListEntity.getContent().get(position).setDoor_list(list_temp);
//                }
//                list.addAll(doorCommunityListEntity.getContent());
//                DoorCommunityListEntity.ContentBean[] arrayList = list.toArray(new DoorCommunityListEntity.ContentBean[list.size()]);
//                for (int i = 0; i < list.size(); i++) {
//                    addPager(list, i, arrayList);
//                }
//                if (null == viewPagerAdapter) {
//                    viewPagerAdapter = new DoorViewPagerAdapter(IntelligenceDoorActivity.this, viewList);
//                    homedoor_viewpager.setAdapter(viewPagerAdapter);
//                } else {
//                    viewPagerAdapter.setData(viewList);
//                }
//                homedoor_viewpager.setAdapter(viewPagerAdapter);
//                homedoor_viewpager.addOnPageChangeListener(new ViewPagerIndicator(IntelligenceDoorActivity.this, linearLayout, list.size()));
//                homedoor_viewpager.setCurrentItem(position);
//            }
//        } catch (Exception e) {
//            System.out.print(e);
//        }
    }

    /**
     * 显示快捷方式Diaog
     */
    private void ShowShortcuts(final List<DoorCommunityListEntity.ContentBean> contentBeans, final int numPage, String name, final int community_type) {
        showShortCutDialog = new ShowShortCutDialog(IntelligenceDoorActivity.this, R.style.custom_dialog_theme);
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
                    ToastUtil.toastShow(IntelligenceDoorActivity.this, getResources().getString(R.string.input_door_name));
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
     * 显示快捷方式Diaog...
     */
    private void ShowShortcuts(String qrcode, final int numPage, String name, final int community_type) {
        showShortCutDialog = new ShowShortCutDialog(IntelligenceDoorActivity.this, R.style.custom_dialog_theme);
        showShortCutDialog.show();
        showShortCutDialog.setCanceledOnTouchOutside(true);
        showShortCutDialog.dialog_content.setText(name);
        showShortCutDialog.dialog_content.setSelection(showShortCutDialog.dialog_content.getText().length());
        showShortCutDialog.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(showShortCutDialog.dialog_content.getText().toString().trim())) {
                    creatShortCut(qrcode, numPage, showShortCutDialog.dialog_content.getText().toString().trim(), community_type);
                    showShortCutDialog.dismiss();
                    if (shared.getBoolean(UserAppConst.SHOWSHORTCUTTIPS, false) == false) {
                        showTips();
                    }
                } else {
                    ToastUtil.toastShow(IntelligenceDoorActivity.this, getResources().getString(R.string.input_door_name));
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
        shortCutTipsDialog = new ShortCutTipsDialog(IntelligenceDoorActivity.this, R.style.custom_dialog_theme);
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
                icon = Icon.createWithResource(IntelligenceDoorActivity.this, R.drawable.icon_shortcut);
            } else {
                icon = Icon.createWithResource(IntelligenceDoorActivity.this, R.drawable.icon_shortcut_xzl);
            }
            ShortcutManager shortcutManager = (ShortcutManager) getSystemService(Context.SHORTCUT_SERVICE);
            if (shortcutManager.isRequestPinShortcutSupported()) {
                Intent shortcutInfoIntent = new Intent(IntelligenceDoorActivity.this, MainActivity.class);
                shortcutInfoIntent.setAction(Intent.ACTION_VIEW); //action必须设置，不然报错
                shortcutInfoIntent.putExtra("shortcut", contentBeans.get(numPage).getDoor_list().get(ispotion).getQr_code());
                ShortcutInfo info = new ShortcutInfo.Builder(IntelligenceDoorActivity.this, doorName)
                        .setIcon(icon)
                        .setShortLabel(doorName)
                        .setIntent(shortcutInfoIntent)
                        .build();
                //当添加快捷方式的确认弹框弹出来时，将被回调
                PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(IntelligenceDoorActivity.this, 0, new Intent(IntelligenceDoorActivity.this, MainActivity.MyReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
                shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.getIntentSender());
            }
        } else {
            Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
            shortcutIntent.setClass(IntelligenceDoorActivity.this, MainActivity.class);
            shortcutIntent.putExtra("shortcut", contentBeans.get(numPage).getDoor_list().get(ispotion).getQr_code());
            Intent shortcut = new Intent(ACTION_ADD_SHORTCUT);
            shortcut.putExtra("duplicate", true);
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, doorName);
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            if (community_type == 1) {//1:住宅，2:写字楼
                shortcutIconResource = Intent.ShortcutIconResource.fromContext(IntelligenceDoorActivity.this, R.drawable.icon_shortcut);
            } else {
                shortcutIconResource = Intent.ShortcutIconResource.fromContext(IntelligenceDoorActivity.this, R.drawable.icon_shortcut_xzl);
            }
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, shortcutIconResource);
            this.sendBroadcast(shortcut);
        }

    }

    /**
     * 添加快捷方式..
     */
    private void creatShortCut(String qrcode, int numPage, String doorName, int community_type) {
        if (Build.VERSION.SDK_INT > 25) {
            if (community_type == 1) {//1:住宅，2:写字楼
                icon = Icon.createWithResource(IntelligenceDoorActivity.this, R.drawable.icon_shortcut);
            } else {
                icon = Icon.createWithResource(IntelligenceDoorActivity.this, R.drawable.icon_shortcut_xzl);
            }
            ShortcutManager shortcutManager = (ShortcutManager) getSystemService(Context.SHORTCUT_SERVICE);
            if (shortcutManager.isRequestPinShortcutSupported()) {
                Intent shortcutInfoIntent = new Intent(IntelligenceDoorActivity.this, MainActivity.class);
                shortcutInfoIntent.setAction(Intent.ACTION_VIEW); //action必须设置，不然报错
                shortcutInfoIntent.putExtra("shortcut", qrcode);
                ShortcutInfo info = new ShortcutInfo.Builder(IntelligenceDoorActivity.this, doorName)
                        .setIcon(icon)
                        .setShortLabel(doorName)
                        .setIntent(shortcutInfoIntent)
                        .build();
                //当添加快捷方式的确认弹框弹出来时，将被回调
                PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(IntelligenceDoorActivity.this, 0, new Intent(IntelligenceDoorActivity.this, MainActivity.MyReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
                shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.getIntentSender());
            }
        } else {
            Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
            shortcutIntent.setClass(IntelligenceDoorActivity.this, MainActivity.class);
            shortcutIntent.putExtra("shortcut", qrcode);
            Intent shortcut = new Intent(ACTION_ADD_SHORTCUT);
            shortcut.putExtra("duplicate", true);
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, doorName);
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            if (community_type == 1) {//1:住宅，2:写字楼
                shortcutIconResource = Intent.ShortcutIconResource.fromContext(IntelligenceDoorActivity.this, R.drawable.icon_shortcut);
            } else {
                shortcutIconResource = Intent.ShortcutIconResource.fromContext(IntelligenceDoorActivity.this, R.drawable.icon_shortcut_xzl);
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
                initData();

//                List<SingleCommunityEntity.ContentBean.CommonUseBean> list_item = new ArrayList<>();
//                List<DoorCommunityListEntity.ContentBean.DoorListBean> list_temp = new ArrayList<>();
//                list_item = (List<SingleCommunityEntity.ContentBean.CommonUseBean>) data.getSerializableExtra("itemlist");
//                for (int i = 0; i < list_item.size(); i++) {
//                    DoorCommunityListEntity.ContentBean.DoorListBean doorListBean = new DoorCommunityListEntity.ContentBean.DoorListBean();
//                    doorListBean.setCommunity_type(list_item.get(i).getCommunity_type());
//                    doorListBean.setConnection_type(list_item.get(i).getConnection_type());
//                    doorListBean.setDoor_id(list_item.get(i).getDoor_id());
//                    doorListBean.setDoor_img(list_item.get(i).getDoor_img());
//                    doorListBean.setDoor_name(list_item.get(i).getDoor_name());
//                    doorListBean.setDoor_type(list_item.get(i).getDoor_type());
//                    doorListBean.setPosition(list_item.get(i).getPosition());
//                    doorListBean.setQr_code(list_item.get(i).getQr_code());
//                    list_temp.add(doorListBean);
//                }
//                String dooroftenCache = shared.getString(UserAppConst.HOMEDOOROFTEN, "");
//                dataAdapter(dooroftenCache, list_temp);
//                try {
//                    String stringData = GsonUtils.gsonString(list);
//                    JSONObject jsonObject = new JSONObject();
//                    JSONArray jsonArray = new JSONArray(stringData);
//                    jsonObject.put("code", 0);
//                    jsonObject.put("message", "success");
//                    jsonObject.put("content", jsonArray);
//                    jsonObject.put("contentEncrypt", "");
//                    editor.putString(UserAppConst.HOMEDOOROFTEN, jsonObject.toString());
//                    editor.commit();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
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
//                    dataAdapter(result, null);
                }
                break;
            case 1://授权管理
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
            case 2://开门
                if (!TextUtils.isEmpty(result)) {
                    try {
                        Intent intent = new Intent();
                        intent.putExtra("doorData", result);
                        intent.putExtra("door_code", door_code);
                        setResult(2001, intent);
                        IntelligenceDoorActivity.this.finish();
                        this.overridePendingTransition(R.anim.push_right_in, R.anim.door_push_bottom_out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3://获取权限
                if (!TextUtils.isEmpty(result)) {
                    if ("1".equals(result)) {//默认显示数据
//                        homedoor_viewpager.setVisibility(View.VISIBLE);
                        vp_door.setVisibility(View.VISIBLE);
                    } else {
                        try {
                            DoorRightEntity doorRightEntity = GsonUtils.gsonToBean(result, DoorRightEntity.class);
                            if (doorRightEntity.getContent().getAuthority() == 1) {//有权限
//                                homedoor_viewpager.setVisibility(View.VISIBLE);
                                vp_door.setVisibility(View.VISIBLE);
                            } else if (doorRightEntity.getContent().getAuthority() == 2) {//无权限
                                Intent intent = new Intent(IntelligenceDoorActivity.this, NoRightDoorActivity.class);
                                startActivity(intent);
                                IntelligenceDoorActivity.this.finish();
                                this.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
                break;
            case 4://获取密码
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String content = jsonObject.getString("content");
                        JSONObject data = new JSONObject(content);
                        String password = data.getString("password");
                        if (!TextUtils.isEmpty(password)) {
                            pwdDialog(password);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            case 5://小区钥匙列表
                editor.putString(UserAppConst.COLOUR_INTELLIGENCE_DOOR, result);
                editor.commit();
                setData(result);
                break;
            case 6://远程开门
                if (!TextUtils.isEmpty(result)) {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 7://获取密码
                if (!TextUtils.isEmpty(result)) {
                    try {
                        DoorRecordEntity doorRecordEntity = GsonUtils.gsonToBean(result, DoorRecordEntity.class);
                        DoorRecordEntity.ContentBean.PsdDataBean psdDataBean = doorRecordEntity.getContent().getPsd_data();
                        List<DoorRecordEntity.ContentBean.DataBean> dataBean = doorRecordEntity.getContent().getData();
                        pwdNum = psdDataBean.getRemain();
                        pwdTime = psdDataBean.getTime();
                        clickPwdDialog(psdDataBean.getRemain() + "", psdDataBean.getTime(), result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void setData(String result) {
        try {
            if (!TextUtils.isEmpty(result)) {
                DoorAllEntity doorAllEntity = GsonUtils.gsonToBean(result, DoorAllEntity.class);
                List<DoorAllEntity.ContentBean.CommunityBean> titleList = doorAllEntity.getContent().getCommunity();
                String titleString = "";
                if (1 == titleList.size()) {
                    communityUuidList.add(titleList.get(0).getCommunity_uuid());
                    titleString = titleList.get(0).getCommunity_name();
                } else {
                    for (int i = 0; i < titleList.size(); i++) {
                        communityUuidList.add(titleList.get(i).getCommunity_uuid());
                        if (i == titleList.size() - 1) {
                            titleString += titleList.get(i).getCommunity_name();
                        } else {
                            titleString += titleList.get(i).getCommunity_name() + ",";
                        }
                    }
                }
//                contentList.addAll(doorAllEntity.getContent().getData());
                if (!TextUtils.isEmpty(titleString)) {
                    initTab(titleString.split(","), result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * 停车 下降
     */
    public void parkDown(String mac) {
        if (LekaiHelper.isFastClick()) {
            boolean openBluetooth = false;
            BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
            if (null != blueAdapter) {
                openBluetooth = blueAdapter.isEnabled();
            }
            //支持蓝牙模块
            if (openBluetooth) {
                ToastUtil.toastShow(getApplicationContext(), "正在下降车位锁");

                LekaiHelper.parkUnlock(mac, (status, message, battery) -> {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> ToastUtil.toastShow(getApplicationContext(), 0 == status ? ("操作成功,电量：" + battery) : err));
                });
            } else {
                ToastUtil.toastShow(getApplicationContext(), "请打开蓝牙");
            }
        }
    }

    /**
     * 停车 抬起
     */
    public void parkUp(String mac) {
        if (LekaiHelper.isFastClick()) {
            boolean openBluetooth = false;
            BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
            if (null != blueAdapter) {
                openBluetooth = blueAdapter.isEnabled();
            }
            //支持蓝牙模块
            if (openBluetooth) {
                ToastUtil.toastShow(getApplicationContext(), "正在抬升起位锁");

                LekaiHelper.parkLock(mac, (status, message, battery) -> {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> ToastUtil.toastShow(getApplicationContext(), 0 == status ? ("操作成功,电量：" + battery) : err));
                });
            } else {
                ToastUtil.toastShow(getApplicationContext(), "请打开蓝牙");
            }
        }
    }

    @Override
    public void onScanParkLockChanged(String mac) {
    }
}
