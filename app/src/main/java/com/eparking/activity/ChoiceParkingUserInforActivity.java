package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingApplyModel;
import com.eparking.protocol.ParkingBuildingEntity;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGBUILDID;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGBUILDNAME;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGCOMMUNITYID;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGCOMMUNITYNAME;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGCOMMUNITYUUID;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGROLE;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGROOMNAME;
import static com.eparking.activity.MonthCardApplyActivity.CARSTATUS;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  e停绑定月卡  填写用户相关信息
 */
public class ChoiceParkingUserInforActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private Button btn_next;//返回
    private TextView tv_apply_parking;
    private RelativeLayout apply_building_layout;
    private EditText ed_apply_building;
    private ImageView iv_apply_building;

    private RelativeLayout apply_room_layout;
    private EditText ed_apply_room;
    private ImageView iv_apply_room;

    private RelativeLayout apply_userrole_layout;
    private TextView tv_apply_userrole;


    private ClearEditText ed_apply_phone;
    private ClearEditText ed_apply_name;
    private String parkingName;
    private String plate;
    private String parkingUUId;
    private String parkingId;
    private int parkingType = 1;
    private int carStatus = 0;
    private ParkingApplyModel parkingApplyModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_userinfor);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_apply_monthcard));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        btn_next = findViewById(R.id.btn_next);


        tv_apply_parking = findViewById(R.id.tv_apply_parking);
        apply_building_layout = findViewById(R.id.apply_building_layout);
        ed_apply_building = findViewById(R.id.ed_apply_building);
        iv_apply_building = findViewById(R.id.iv_apply_building);
        apply_room_layout = findViewById(R.id.apply_room_layout);
        ed_apply_room = findViewById(R.id.ed_apply_room);
        iv_apply_room = findViewById(R.id.iv_apply_room);
        apply_userrole_layout = findViewById(R.id.apply_userrole_layout);
        tv_apply_userrole = findViewById(R.id.tv_apply_userrole);
        ed_apply_phone = findViewById(R.id.ed_apply_phone);
        ed_apply_name = findViewById(R.id.ed_apply_name);
        imageView_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        Intent intent = getIntent();
        parkingUUId = intent.getStringExtra(PARKINGCOMMUNITYUUID);
        parkingId = intent.getStringExtra(PARKINGCOMMUNITYID);
        parkingName = intent.getStringExtra(PARKINGCOMMUNITYNAME);
        plate = intent.getStringExtra(CARNUMBER);
        carStatus = intent.getIntExtra(CARSTATUS, 0);
        apply_userrole_layout.setOnClickListener(this);
        tv_apply_parking.setText(parkingName);
        if (carStatus == 1) {
            btn_next.setText(getResources().getString(R.string.finish));
        } else {
            btn_next.setText(getResources().getString(R.string.next));
        }
        ParkingActivityUtils.getInstance().addActivity(this);
        parkingApplyModel = new ParkingApplyModel(ChoiceParkingUserInforActivity.this);
        parkingApplyModel.getBuildingList(0, parkingUUId, this);
        String phone = shared.getString(UserAppConst.Colour_login_mobile, "");
        ed_apply_phone.setText(phone);
        ed_apply_phone.setSelection(phone.length());
    }

    private void setBuildRoomLayout() {
        switch (parkingType) {
            case 1:
                ed_apply_building.setHint(getResources().getString(R.string.apply_input_building));
                ed_apply_room.setHint(getResources().getString(R.string.apply_input_room));
                iv_apply_building.setVisibility(View.INVISIBLE);
                iv_apply_room.setVisibility(View.INVISIBLE);
                break;
            default:
                ed_apply_building.setFocusable(false);
                ed_apply_room.setFocusable(false);
                ed_apply_building.setOnClickListener(this);
                ed_apply_room.setOnClickListener(this);
                apply_building_layout.setOnClickListener(this);
                apply_room_layout.setOnClickListener(this);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.apply_building_layout:
            case R.id.ed_apply_building:
                Intent buildIntent = new Intent(ChoiceParkingUserInforActivity.this, ChoiceParkingBuildActivity.class);
                buildIntent.putExtra(PARKINGCOMMUNITYUUID, parkingUUId);
                startActivityForResult(buildIntent, 1000);
                break;
            case R.id.apply_room_layout:
            case R.id.ed_apply_room:
                String buildingName = ed_apply_building.getText().toString();
                if (TextUtils.isEmpty(buildingName)) {
                    ToastUtil.toastShow(ChoiceParkingUserInforActivity.this, getResources().getString(R.string.apply_building_notice));
                } else {
                    Intent roomIntent = new Intent(ChoiceParkingUserInforActivity.this, ChoiceParkingRoomActivity.class);
                    roomIntent.putExtra(PARKINGBUILDID, buildId);
                    startActivityForResult(roomIntent, 2000);
                }
                break;
            case R.id.apply_userrole_layout:
                Intent roleIntent = new Intent(ChoiceParkingUserInforActivity.this, ChoiceParkingUserRoleActivity.class);
                startActivityForResult(roleIntent, 3000);
                break;
            case R.id.btn_next:
                String buildName = "";
                String roomName = "";
                String roleName = "";
                String applyName = ed_apply_name.getText().toString();
                String applyPhone = ed_apply_phone.getText().toString().trim();
                buildName = ed_apply_building.getText().toString().trim();
                if (TextUtils.isEmpty(buildName)) {
                    ToastUtil.toastShow(ChoiceParkingUserInforActivity.this, getResources().getString(R.string.apply_building_notice));
                    return;
                }
                roomName = ed_apply_room.getText().toString().trim();
                if (TextUtils.isEmpty(roomName)) {
                    ToastUtil.toastShow(ChoiceParkingUserInforActivity.this, getResources().getString(R.string.apply_room_notice));
                    return;
                }
                roleName = tv_apply_userrole.getText().toString();
                if (TextUtils.isEmpty(roleName)) {
                    ToastUtil.toastShow(ChoiceParkingUserInforActivity.this, getResources().getString(R.string.apply_role_notice));
                    return;
                }
                if (TextUtils.isEmpty(applyPhone)) {
                    ToastUtil.toastShow(ChoiceParkingUserInforActivity.this, getResources().getString(R.string.apply_phone_emptynotice));
                    return;
                }
                if (applyPhone.length() != 11) {
                    ToastUtil.toastShow(ChoiceParkingUserInforActivity.this, getResources().getString(R.string.apply_phone_lengthnotice));
                    return;
                }
                if (TextUtils.isEmpty(applyName)) {
                    ToastUtil.toastShow(ChoiceParkingUserInforActivity.this, getResources().getString(R.string.apply_name_notice));
                    return;
                }
                if (carStatus == 1) {
                    //临停已认证申请月卡
                    parkingApplyModel.contractApplyOperation(2, applyPhone, applyName, plate, "", "", parkingId, buildName, roomName, roleName, this);
                } else {
                    Intent intent = new Intent(ChoiceParkingUserInforActivity.this, CarsLicenseUploadActivity.class);
                    intent.putExtra(CarsLicenseUploadActivity.FROMSOURCE, 0);
                    intent.putExtra(CarsLicenseUploadActivity.APPLAYNAME, applyName);
                    intent.putExtra(CarsLicenseUploadActivity.APPLAYPHONE, applyPhone);
                    intent.putExtra(CarsLicenseUploadActivity.CARNUMBER, plate);
                    intent.putExtra(ChoiceParkingCommunityActivity.PARKINGCOMMUNITYID, parkingId);
                    intent.putExtra(ChoiceParkingCommunityActivity.PARKINGBUILDNAME, buildName);
                    intent.putExtra(ChoiceParkingCommunityActivity.PARKINGROOMNAME, roomName);
                    intent.putExtra(ChoiceParkingCommunityActivity.PARKINGROLE, roleName);
                    startActivity(intent);
                }
                break;
        }
    }

    private String buildId = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            switch (requestCode) {
                case 1000:
                    ed_apply_building.setText(data.getStringExtra(PARKINGBUILDNAME));
                    ed_apply_room.setText("");
                    buildId = data.getStringExtra(PARKINGBUILDID);
                    break;
                case 2000:
                    ed_apply_room.setText(data.getStringExtra(PARKINGROOMNAME));
                    break;
                case 3000:
                    tv_apply_userrole.setText(data.getStringExtra(PARKINGROLE));
                    break;
            }
        }
    }

    private List<ParkingBuildingEntity.ContentBean> buildingList = new ArrayList<>();

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (TextUtils.isEmpty(result)) {
                    parkingType = 1;
                    setBuildRoomLayout();
                } else {
                    try {
                        ParkingBuildingEntity parkingBuildingEntity = GsonUtils.gsonToBean(result, ParkingBuildingEntity.class);
                        buildingList.clear();
                        buildingList.addAll(parkingBuildingEntity.getContent());
                    } catch (Exception e) {

                    }
                    if (buildingList.size() == 0) {
                        parkingType = 1;
                    } else {
                        parkingType = 0; //彩生活小区
                    }
                    setBuildRoomLayout();
                }
                break;
            case 2:
                BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                ToastUtil.toastShow(ChoiceParkingUserInforActivity.this, baseContentEntity.getMessage());
                ParkingActivityUtils.getInstance().exit();
                break;

        }
    }
}
