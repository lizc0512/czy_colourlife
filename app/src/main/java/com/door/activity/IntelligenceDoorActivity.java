package com.door.activity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.door.adapter.DoorFragmentAdapter;
import com.door.dialog.ClickPwdDialog;
import com.door.dialog.PwdDialog;
import com.door.entity.DoorAllEntity;
import com.door.entity.DoorGrantedEntity;
import com.door.entity.DoorRecordEntity;
import com.door.entity.DoorRightEntity;
import com.door.entity.OpenDoorResultEntity;
import com.door.fragment.IntelligenceDoorFragment;
import com.door.model.NewDoorAuthorModel;
import com.door.model.NewDoorModel;
import com.door.view.DoorRenameDialog;
import com.door.view.ShowOpenDoorDialog;
import com.external.eventbus.EventBus;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.youmai.hxsdk.utils.DisplayUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.home.service.LekaiParkLockController;
import cn.net.cyberway.utils.LekaiHelper;

import static cn.net.cyberway.utils.TableLayoutUtils.showOpenDoorResultDialog;
import static com.BeeFramework.Utils.Utils.dip2px;
import static com.customerInfo.activity.CustomerAddPropertyActivity.COMMUNITY_NAME;
import static com.customerInfo.activity.CustomerAddPropertyActivity.COMMUNITY_UUID;
import static com.customerInfo.activity.CustomerAddPropertyActivity.IDENTITY_ID;
import static com.door.activity.NewDoorRenewalActivity.DOOR_TYPE;
import static com.door.activity.NewDoorRenewalActivity.UNIT_NAME_LIST;
import static com.door.activity.NewDoorRenewalActivity.UNIT_UUID_LIST;
import static com.user.UserAppConst.COLOUR_BLUETOOTH_ADVISE;

/**
 * 智能门禁
 * hxg 2019.8.6
 */
public class IntelligenceDoorActivity extends BaseFragmentActivity implements NewHttpResponse, View.OnClickListener, LekaiParkLockController.OnScanParkLockChangeListener {

    private static int INTENT_UPDATEFIXEDDOOR = 2;//编辑常用门禁
    public SharedPreferences.Editor editor;
    private SharedPreferences shared;
    private NewDoorModel newDoorModel;
    private PopupWindow popupWindow;
    private RelativeLayout ll_homedoorpop_apply;
    private RelativeLayout ll_homedoorpop_authorization;
    private RelativeLayout ll_homedoorpop_compile;
    private RelativeLayout ll_homedoorpop_record;
    private RelativeLayout ll_homedoorpop_myapply;
    private String isgranted = "0";
    private String door_code = "";
    private int position = 0;

    private ImageView iv_editor;
    private View v_status_bar;
    private TabLayout tl_door;
    private ViewPager vp_door;
    private ImageView iv_close;
    private String device;
    private ClickPwdDialog clickPwdDialog;
    private PwdDialog pwdDialog;
    private List<String> communityUuidList = new ArrayList<>();
    private DoorFragmentAdapter fragmentAdapter;
    private int userId;
    private String intelligenceDoorCache;
    private String resultData = "";
    private String userCommunityUuid = "";
    private String changeCommunityUuid = "";

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
        userId = shared.getInt(UserAppConst.Colour_User_id, 0);
        userCommunityUuid = shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
        initView();
        initData();

//        showDoorOverdueDialog();
        if (!EventBus.getDefault().isregister(IntelligenceDoorActivity.this)) {
            EventBus.getDefault().register(IntelligenceDoorActivity.this);
        }
    }

    private void initView() {
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

    private void initTab(List<String> tabTitleArray, String result) {
        List<Fragment> fragmentList = new ArrayList<>();
        try {
            DoorAllEntity doorAllEntity = GsonUtils.gsonToBean(result, DoorAllEntity.class);
            for (int i = 0; i < tabTitleArray.size(); i++) {
                List<DoorAllEntity.ContentBean.DataBean.ListBean> list = doorAllEntity.getContent().getData().get(i).getList();
                tl_door.addTab(tl_door.newTab().setText(tabTitleArray.get(i)));
                fragmentList.add(IntelligenceDoorFragment.newInstance(userId, GsonUtils.gsonString(list)));
            }
            if (null == fragmentAdapter) {
                tl_door.setTabTextColors(getResources().getColor(R.color.color_b3ffffff), getResources().getColor(R.color.white));
                tl_door.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
                tl_door.setSelectedTabIndicatorHeight(dip2px(this, 3));
                tl_door.setTabIndicatorFullWidth(false);
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius(dip2px(this, 2));
                tl_door.setSelectedTabIndicator(gradientDrawable);//设置圆角
                tl_door.setTabGravity(TabLayout.GRAVITY_FILL);
                fragmentAdapter = new DoorFragmentAdapter(getSupportFragmentManager(), fragmentList, tabTitleArray);
            } else {
                fragmentAdapter.setFragments(getSupportFragmentManager(), fragmentList, tabTitleArray);
            }
            vp_door.setAdapter(fragmentAdapter);
            vp_door.setOffscreenPageLimit(fragmentList.size());
            tl_door.setupWithViewPager(vp_door);
            tl_door.setTabMode(TabLayout.MODE_SCROLLABLE);
            TabLayout.Tab tab = tl_door.getTabAt(position);
            if (null != tab) {
                tab.select();
            }
            resultData = result;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStatusBarHeight(View tabBarView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        tabBarView.setLayoutParams(layoutParams);
    }

    private void initData() {
        boolean isLoading = false;
        intelligenceDoorCache = shared.getString(UserAppConst.COLOUR_INTELLIGENCE_DOOR + userId, "");
        if (TextUtils.isEmpty(intelligenceDoorCache)) {
            isLoading = true;
        }
        setData(true, intelligenceDoorCache);

        boolean isGranted = shared.getBoolean(UserAppConst.HAVADOORGRANTED, false);//true 已授权
        if (isGranted) {
            isgranted = "1";
        }

        newDoorModel.getCommunityKey(5, isLoading, this);//获取门禁列表
        NewDoorAuthorModel newDoorAuthorModel = new NewDoorAuthorModel(IntelligenceDoorActivity.this);
        newDoorAuthorModel.getHaveDoorRight(1, false, this);//授权管理
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
    public void remoteDoor(String qrcode) {
        door_code = qrcode;
        newDoorModel.openDoor(2, qrcode, true, IntelligenceDoorActivity.this);
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
        try {
            if (popupWindow == null) {
                View popWindowView = LayoutInflater.from(this).inflate(R.layout.homedoor_pop, null);
                popupWindow = new PopupWindow(popWindowView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        true);
                //申请
                ll_homedoorpop_apply = popWindowView.findViewById(R.id.ll_homedoorpop_apply);
                //门禁授权
                ll_homedoorpop_authorization = popWindowView.findViewById(R.id.ll_homedoorpop_authorization);
                if ("1".equals(isgranted)) {
                    ll_homedoorpop_authorization.setVisibility(View.VISIBLE);
                } else {
                    ll_homedoorpop_authorization.setVisibility(View.GONE);
                }
                //编辑门禁
                ll_homedoorpop_compile = popWindowView.findViewById(R.id.ll_homedoorpop_compile);
                //门禁记录
                ll_homedoorpop_record = popWindowView.findViewById(R.id.ll_homedoorpop_record);
                ll_homedoorpop_myapply = popWindowView.findViewById(R.id.ll_homedoorpop_myapply);
            }

            ll_homedoorpop_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent applyIntent = new Intent(IntelligenceDoorActivity.this, DoorApplyActivity.class);
                    Intent applyIntent = new Intent(IntelligenceDoorActivity.this, NewDoorIndetifyActivity.class);
                    startActivity(applyIntent);
                    popupWindow.dismiss();
                }
            });
            ll_homedoorpop_authorization.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (!"1".equals(isgranted)) {
                            ToastUtil.toastShow(IntelligenceDoorActivity.this, getResources().getString(R.string.door_no_grantauthor));
                            return;
                        }
                        Intent authorIntent = new Intent(IntelligenceDoorActivity.this, NewDoorAuthorizeActivity.class);
//                        Intent authorIntent = new Intent(IntelligenceDoorActivity.this, DoorAuthorizationActivity.class);
                        startActivity(authorIntent);
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                    popupWindow.dismiss();
                }
            });
            ll_homedoorpop_compile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(resultData)) {
                        try {
                            position = vp_door.getCurrentItem();
                            boolean canEdit = false;

                            DoorAllEntity doorAllEntity = GsonUtils.gsonToBean(resultData, DoorAllEntity.class);
                            List<DoorAllEntity.ContentBean.DataBean.ListBean> list = doorAllEntity.getContent().getData().get(position).getList();
                            if (0 < list.size()) {
                                for (int i = 0; i < list.size(); i++) {
                                    //远程开门
                                    if ("caihuijuDoor".equals(list.get(i).getType())) {
                                        if (0 < list.get(i).getKeyList().size()) {
                                            canEdit = true;
                                        }
                                    }
                                }
                            }
                            if (communityUuidList.size() > 0 && canEdit) {
                                Intent intent = new Intent(IntelligenceDoorActivity.this, NewDoorEditActivity.class);
                                intent.putExtra(UserAppConst.EDITDOORCOMMUNITYUUID, communityUuidList.get(position));
                                startActivityForResult(intent, INTENT_UPDATEFIXEDDOOR);
                            } else {
                                ToastUtil.toastShow(getApplicationContext(), "您当前小区没有可编辑门禁");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        popupWindow.dismiss();
                    }
                }
            });
            ll_homedoorpop_record.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(IntelligenceDoorActivity.this, NewDoorOpenRecordActivity.class);
                    startActivity(intent);
                    popupWindow.dismiss();
                }
            });
            ll_homedoorpop_myapply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(IntelligenceDoorActivity.this, NewDoorApplyRecordActivity.class);
                    startActivity(intent);
                    popupWindow.dismiss();
                }
            });
            // 使其聚集
            popupWindow.setFocusable(true);
            // 设置允许在外点击消失
            popupWindow.setOutsideTouchable(true);
            // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.showAsDropDown(parent, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击获取密码
     */
    private void clickPwdDialog(String num, String time, String result) {
        try {
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
                try {
                    Intent intent = new Intent(this, IntelligenceDoorRecordActivity.class);
                    intent.putExtra(IntelligenceDoorRecordActivity.DEVICE, device);
                    intent.putExtra(IntelligenceDoorRecordActivity.RESULT, result);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            clickPwdDialog.tv_pwd_get.setOnClickListener(v -> {
                if (null != clickPwdDialog) {
                    clickPwdDialog.dismiss();
                }
                newDoorModel.getDevicePwd(4, device, this);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享密码
     */
    @SuppressLint("SetTextI18n")
    private void pwdDialog(String password, String time, String num) {
        if (null == pwdDialog) {
            pwdDialog = new PwdDialog(this);
        }
        pwdDialog.show();
        pwdDialog.tv_pwd.setText(password);
        pwdDialog.tv_pwd_num.setText(num);
        pwdDialog.tv_pwd_time.setText(time);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_UPDATEFIXEDDOOR) {
            if (resultCode == RESULT_OK) {
                initData();
            }
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
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
                        vp_door.setVisibility(View.VISIBLE);
                    } else {
                        try {
                            DoorRightEntity doorRightEntity = GsonUtils.gsonToBean(result, DoorRightEntity.class);
                            if (doorRightEntity.getContent().getAuthority() == 1) {//有权限
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
                        String next_time = data.getString("next_time");
                        String remain = data.getString("remain");
                        if (!TextUtils.isEmpty(password)) {
                            pwdDialog(password, next_time, remain);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            case 5://小区钥匙列表
                if (!intelligenceDoorCache.equals(result)) {
                    editor.putString(UserAppConst.COLOUR_INTELLIGENCE_DOOR + userId, result);
                    editor.commit();
                }
                setData(false, result);
                break;
            case 7://获取密码
                if (!TextUtils.isEmpty(result)) {
                    try {
                        DoorRecordEntity doorRecordEntity = GsonUtils.gsonToBean(result, DoorRecordEntity.class);
                        DoorRecordEntity.ContentBean.PsdDataBean psdDataBean = doorRecordEntity.getContent().getPsd_data();
                        clickPwdDialog(psdDataBean.getRemain() + "", psdDataBean.getTime(), result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 8://添加、移除常用门禁
                if (!TextUtils.isEmpty(result)) {
                    intelligenceDoorCache = shared.getString(UserAppConst.COLOUR_INTELLIGENCE_DOOR + userId, "");
                    newDoorModel.getCommunityKey(5, true, this);//获取门禁列表

                    if (userCommunityUuid.equals(changeCommunityUuid)) {
                        Message msg = new Message();
                        msg.what = UserMessageConstant.UPDATE_DOOR;
                        EventBus.getDefault().post(msg);
                    }
                    changeCommunityUuid = "";
                }
                break;
            case 9://门禁重命名
                if (!TextUtils.isEmpty(result)) {
                    ToastUtil.toastShow(IntelligenceDoorActivity.this, getResources().getString(R.string.door_rename_success));
                    intelligenceDoorCache = shared.getString(UserAppConst.COLOUR_INTELLIGENCE_DOOR + userId, "");
                    newDoorModel.getCommunityKey(5, true, this);//获取门禁列表
                    if (userCommunityUuid.equals(changeCommunityUuid)) {
                        Message msg = new Message();
                        msg.what = UserMessageConstant.UPDATE_DOOR;
                        EventBus.getDefault().post(msg);
                    }
                    changeCommunityUuid = "";
                }
                break;
        }
    }

    private void setData(boolean isCache, String result) {
        try {
            if (!TextUtils.isEmpty(result)) {
                DoorAllEntity doorAllEntity = GsonUtils.gsonToBean(result, DoorAllEntity.class);
                List<DoorAllEntity.ContentBean.CommunityBean> titleList = doorAllEntity.getContent().getCommunity();
                List<String> titleLists = new ArrayList<>();
                communityUuidList.clear();
                for (int i = 0; i < titleList.size(); i++) {
                    communityUuidList.add(titleList.get(i).getCommunity_uuid());
                    titleLists.add(titleList.get(i).getCommunity_name());
                }
                if (!isCache && 0 == titleList.size()) {
                    Intent intent = new Intent(IntelligenceDoorActivity.this, NoRightDoorActivity.class);
                    startActivity(intent);
                    IntelligenceDoorActivity.this.finish();
                    this.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                } else if ((isCache && 0 != titleList.size()) || (!isCache && !intelligenceDoorCache.equals(result))) {
                    initTab(titleLists, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.toastShow(IntelligenceDoorActivity.this, e.getMessage());
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
            if (openBluetooth) {
                ToastUtil.toastShow(getApplicationContext(), "正在下降车位锁");
                LekaiHelper.parkUnlock(mac);
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
            if (openBluetooth) {
                ToastUtil.toastShow(getApplicationContext(), "正在升起位锁");
                LekaiHelper.parkLock(mac);
            } else {
                ToastUtil.toastShow(getApplicationContext(), "请打开蓝牙");
            }
        }
    }

    @Override
    public void onScanParkLockChanged(String mac) {
    }

    /**
     * 重命名操作
     */
    public void renameHandle(int position, String community_uuid, String door_id, String door_name) {
        this.position = vp_door.getCurrentItem();
        changeCommunityUuid = community_uuid;
        showRenameDialog(position, community_uuid, door_id, door_name);
    }

    private DoorRenameDialog doorRenameDialog;

    /**
     * 门禁重命名
     */
    private void showRenameDialog(int position, String community_uuid, String door_id, String door_name) {
        doorRenameDialog = new DoorRenameDialog(this, R.style.custom_dialog_theme);
        doorRenameDialog.show();
        doorRenameDialog.setCanceledOnTouchOutside(true);
        doorRenameDialog.dialog_content.setText(door_name);
        doorRenameDialog.dialog_content.setSelection(doorRenameDialog.dialog_content.getText().length());
        doorRenameDialog.dialog_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > start || s.toString().length() <= start) {
                    doorRenameDialog.dialog_content.setTextColor(getResources().getColor(R.color.black_text_color));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        doorRenameDialog.btn_open.setOnClickListener(v -> {
            String name = doorRenameDialog.dialog_content.getText().toString().trim();
            if (!TextUtils.isEmpty(name)) {
                if (name.length() > 20) {
                    ToastUtil.toastShow(IntelligenceDoorActivity.this, getResources().getString(R.string.door_most_length));
                } else {
                    doorRenameDialog.dismiss();
                    try {
                        DisplayUtil.showInput(false, this);
                        newDoorModel.doorRename(9, community_uuid, door_id, name, this);
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                ToastUtil.toastShow(IntelligenceDoorActivity.this, getResources().getString(R.string.input_door_name));
            }
        });
        doorRenameDialog.btn_cancel.setOnClickListener(v -> doorRenameDialog.dismiss());
    }

    /**
     * 添加，移除名操作
     *
     * @param position 位置
     * @param add      true 添加，false 移除
     */
    public void addOrRemoveHandle(int position, String community_uuid, String door_name, String door_id, boolean add) {
        this.position = vp_door.getCurrentItem();
        changeCommunityUuid = community_uuid;
        newDoorModel.removeDoor(8, community_uuid, door_name, door_id, add ? "1" : "2", this);//添加、移除常用门禁
    }

    /**
     * 申请续期
     */
    public void apply(String community_infor, String identify_id, String door_type, List<DoorAllEntity.ContentBean.DataBean.ListBean.InvalidUnitBean> invalidUnitBeanList) {
        Intent intent = new Intent(IntelligenceDoorActivity.this, NewDoorRenewalActivity.class);
        String community_uuid = "";
        String community_name = "";
        if (community_infor.contains(",")) {
            String[] communityArr = community_infor.split(",");
            if (communityArr.length > 1) {
                community_uuid = communityArr[0];
                community_name = communityArr[1];
            } else {
                community_uuid = communityArr[0];
            }
        }
        intent.putExtra(COMMUNITY_UUID, community_uuid);
        intent.putExtra(COMMUNITY_NAME, community_name);
        intent.putExtra(IDENTITY_ID, identify_id);
        intent.putExtra(DOOR_TYPE, door_type);
        if (null != invalidUnitBeanList) {
            ArrayList<String> unitNameList = new ArrayList<>();
            ArrayList<String> unitIdList = new ArrayList<>();
            for (DoorAllEntity.ContentBean.DataBean.ListBean.InvalidUnitBean invalidUnitBean : invalidUnitBeanList) {
                unitNameList.add(invalidUnitBean.getUnit_name());
                unitIdList.add(invalidUnitBean.getUnit_uuid());
            }
            intent.putStringArrayListExtra(UNIT_NAME_LIST, unitNameList);
            intent.putStringArrayListExtra(UNIT_UUID_LIST, unitIdList);
        }
        startActivity(intent);
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.BLUETOOTH_OPEN_DOOR:
                String result = shared.getString(COLOUR_BLUETOOTH_ADVISE, "");
                if (TextUtils.isEmpty(result)) {
                    ToastUtil.toastShow(IntelligenceDoorActivity.this, "开门成功!");
                } else {
                    showOpenDoorDialog(result);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(IntelligenceDoorActivity.this)) {
            EventBus.getDefault().unregister(IntelligenceDoorActivity.this);
        }
    }

    private ShowOpenDoorDialog showOpenDoorDialog;

    public void showOpenDoorDialog(String json) {
        try {
            if (null == showOpenDoorDialog) {
                showOpenDoorDialog = new ShowOpenDoorDialog(IntelligenceDoorActivity.this, R.style.opendoor_dialog_theme);
            }
            if (null != showOpenDoorDialog && showOpenDoorDialog.isShowing()) {
                showOpenDoorDialog.dismiss();
            }
            showOpenDoorDialog.show();
            final OpenDoorResultEntity openDoorResultEntity = GsonUtils.gsonToBean(json, OpenDoorResultEntity.class);
            OpenDoorResultEntity.ContentBean contentBean = openDoorResultEntity.getContent();
            showOpenDoorResultDialog(IntelligenceDoorActivity.this, showOpenDoorDialog, contentBean);
            showOpenDoorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Message msg = Message.obtain();
                    msg.what = UserMessageConstant.BLUETOOTH_CLOSE_DIALOG;
                    EventBus.getDefault().post(msg);
                }
            });
        } catch (Exception e) {

        }
    }
}
