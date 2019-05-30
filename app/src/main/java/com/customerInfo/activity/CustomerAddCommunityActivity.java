package com.customerInfo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.model.NewCustomerInfoModel;
import com.customerInfo.protocol.AddCommuntiyEntity;
import com.customerInfo.protocol.AddHouseEntity;
import com.customerInfo.protocol.ComBuildUnitRoomEntity;
import com.customerInfo.view.BottomDialog;
import com.customerInfo.view.OnAddressSelectedListener;
import com.myproperty.activity.NameAuthActivity;
import com.myproperty.activity.NameAuthDialog;
import com.myproperty.model.AddressInfoModel;
import com.nohttp.utils.GsonUtils;

import java.util.List;

import cn.csh.colourful.life.utils.ToastUtils;
import cn.csh.colourful.life.view.pickview.OptionsPickerView;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/3 9:28
 * @change
 * @chang time
 * @class describe
 */

public class CustomerAddCommunityActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, OnAddressSelectedListener {
    private ImageView mback;
    private TextView mtitle;
    private RelativeLayout select_address_rl;
    private TextView address_tv;
    private RelativeLayout select_community_rl;
    private TextView community_tv;
    private RelativeLayout select_build_rl;
    private TextView build_tv;
    private RelativeLayout select_unit_rl;
    private TextView unit_tv;
    private RelativeLayout select_room_rl;
    private TextView room_tv;
    private Button complete_btn;
    private NewCustomerInfoModel newCustomerInfoModel;
    private int choiceType = 0;
    private int enterType = 0;
    public static final String ENTERTYPE = "entertype";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_address);
        enterType = getIntent().getIntExtra(ENTERTYPE, 0);
        newCustomerInfoModel = new NewCustomerInfoModel(CustomerAddCommunityActivity.this);
        initView();
    }

    private void initView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mback = (ImageView) findViewById(R.id.user_top_view_back);
        mtitle = (TextView) findViewById(R.id.user_top_view_title);
        select_address_rl = (RelativeLayout) findViewById(R.id.select_address_rl);
        address_tv = (TextView) findViewById(R.id.address_tv);
        select_community_rl = (RelativeLayout) findViewById(R.id.select_community_rl);
        community_tv = (TextView) findViewById(R.id.community_tv);
        select_build_rl = (RelativeLayout) findViewById(R.id.select_build_rl);
        build_tv = (TextView) findViewById(R.id.build_tv);
        select_unit_rl = (RelativeLayout) findViewById(R.id.select_unit_rl);
        unit_tv = (TextView) findViewById(R.id.unit_tv);
        select_room_rl = (RelativeLayout) findViewById(R.id.select_room_rl);
        room_tv = (TextView) findViewById(R.id.room_tv);
        complete_btn = (Button) findViewById(R.id.complete_btn);
        mback.setOnClickListener(this);
        select_address_rl.setOnClickListener(this);
        select_community_rl.setOnClickListener(this);
        select_build_rl.setOnClickListener(this);
        select_unit_rl.setOnClickListener(this);
        select_room_rl.setOnClickListener(this);
        complete_btn.setOnClickListener(this);
        if (enterType == 1) {
            mtitle.setText(getResources().getString(R.string.title_increase_property));
        } else {
            mtitle.setText(getResources().getString(R.string.title_choice_community));
        }
        ThemeStyleHelper.onlyFrameTitileBar(CustomerAddCommunityActivity.this, czy_title_layout, mback, mtitle);
    }

    private int page = 1;

    private void getCommunityData() {
        newCustomerInfoModel.getCommunityData(1, province_name, city_name, region_name, page, 200, true, CustomerAddCommunityActivity.this);
    }


    private void getBuildingData() {
        newCustomerInfoModel.getBuildingData(1, community_uuid, page, 200, true, CustomerAddCommunityActivity.this);
    }


    private void getUnitData() {
        newCustomerInfoModel.getUnitData(1, building_uuid, page, 200, true, CustomerAddCommunityActivity.this);
    }


    private void getRoomData() {
        newCustomerInfoModel.getRoomData(1, unit_uuid, page, 200, true, CustomerAddCommunityActivity.this);
    }

    private BottomDialog bottomDialog = null;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.select_address_rl:
                if (null == bottomDialog) {
                    bottomDialog = new BottomDialog(CustomerAddCommunityActivity.this, this);
                }
                bottomDialog.show();
                break;
            case R.id.select_community_rl:
                if (TextUtils.isEmpty(province_name) || TextUtils.isEmpty(province_name) || TextUtils.isEmpty(province_name)) {
                    ToastUtil.toastShow(CustomerAddCommunityActivity.this, "请选择省市区");
                    return;
                }
                choiceType = 0;
                getCommunityData();
                break;
            case R.id.select_build_rl:
                if (TextUtils.isEmpty(province_name) || TextUtils.isEmpty(province_name) || TextUtils.isEmpty(province_name)) {
                    ToastUtil.toastShow(CustomerAddCommunityActivity.this, "请选择省市区");
                    return;
                }
                if (TextUtils.isEmpty(community_name)) {
                    ToastUtil.toastShow(CustomerAddCommunityActivity.this, "请选择小区");
                    return;
                }
                choiceType = 1;
                getBuildingData();
                break;
            case R.id.select_unit_rl:
                if (TextUtils.isEmpty(province_name) || TextUtils.isEmpty(province_name) || TextUtils.isEmpty(province_name)) {
                    ToastUtil.toastShow(CustomerAddCommunityActivity.this, "请选择省市区");
                    return;
                }
                if (TextUtils.isEmpty(community_name)) {
                    ToastUtil.toastShow(CustomerAddCommunityActivity.this, "请选择小区");
                    return;
                }
                if (TextUtils.isEmpty(building_name)) {
                    ToastUtil.toastShow(CustomerAddCommunityActivity.this, "请选择楼栋");
                    return;
                }
                choiceType = 2;
                getUnitData();
                break;
            case R.id.select_room_rl:
                if (TextUtils.isEmpty(province_name) || TextUtils.isEmpty(province_name) || TextUtils.isEmpty(province_name)) {
                    ToastUtil.toastShow(CustomerAddCommunityActivity.this, "请选择省市区");
                    return;
                }
                if (TextUtils.isEmpty(community_name)) {
                    ToastUtil.toastShow(CustomerAddCommunityActivity.this, "请选择小区");
                    return;
                }
                if (TextUtils.isEmpty(building_name)) {
                    ToastUtil.toastShow(CustomerAddCommunityActivity.this, "请选择楼栋");
                    return;
                }
                if (TextUtils.isEmpty(unit_name)) {
                    ToastUtil.toastShow(CustomerAddCommunityActivity.this, "请选择单元");
                    return;
                }
                choiceType = 3;
                getRoomData();
                break;
            case R.id.complete_btn:
                NewCustomerInfoModel newCustomerInfoModel = new NewCustomerInfoModel(CustomerAddCommunityActivity.this);
                if (TextUtils.isEmpty(province_name)) {
                    ToastUtil.toastShow(this, "请选择省市区");
                    return;
                }
                if (TextUtils.isEmpty(community_name)) {
                    ToastUtil.toastShow(this, "请选择小区");
                    return;
                }
                if (enterType == 1) {
                    if (TextUtils.isEmpty(building_uuid)) {
                        ToastUtil.toastShow(this, "请选择楼栋");
                        return;
                    }
                    if (TextUtils.isEmpty(unit_uuid)) {
                        ToastUtil.toastShow(this, "请选择单元");
                        return;
                    }
                    if (TextUtils.isEmpty(room_uuid)) {
                        ToastUtil.toastShow(this, "请选择房间号");
                        return;
                    }
                    AddressInfoModel addressInfoModel = new AddressInfoModel(CustomerAddCommunityActivity.this);
                    addressInfoModel.postPropertyList(2, community_uuid, building_uuid, unit_uuid, room_uuid, building_name, unit_name, room_name, CustomerAddCommunityActivity.this);
                } else {
                    newCustomerInfoModel.postCustomerAddress(0, community_uuid, community_name, building_uuid, building_name
                            , unit_uuid, unit_name, room_uuid, room_name, "", this);
                }
                break;
        }
    }


    private void ShowCommunityPickerView(final List lists) {// 弹出选择器
        String title = "";
        boolean isEmpty = false;
        if (lists == null || lists.size() == 0) {
            isEmpty = true;
        }
        switch (choiceType) {
            case 0:
                if (isEmpty) {
                    ToastUtils.showMessage(CustomerAddCommunityActivity.this, "该地址暂无小区数据,请稍后重试");
                    return;
                }
                title = "请选择小区";
                break;
            case 1:
                if (isEmpty) {
                    ToastUtils.showMessage(CustomerAddCommunityActivity.this, "该小区暂无楼栋数据,请稍后重试");
                    return;
                }
                title = "请选择楼栋";
                break;
            case 2:
                if (isEmpty) {
                    ToastUtils.showMessage(CustomerAddCommunityActivity.this, "该楼栋暂无单元数据,请稍后重试");
                    return;
                }
                title = "请选择单元";
                break;
            case 3:
                if (isEmpty) {
                    ToastUtils.showMessage(CustomerAddCommunityActivity.this, "该单元暂无房间数据,请稍后重试");
                    return;
                }
                title = "请选择房间号";
                break;
        }


        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                ComBuildUnitRoomEntity.ContentBean.DataBean dataBean = (ComBuildUnitRoomEntity.ContentBean.DataBean) lists.get(options1);
                String choiceText = dataBean.getName();
                String choiceUUId = dataBean.getUuid();
                if (choiceType == 0) {
                    community_uuid = choiceUUId;
                    community_name = choiceText;
                    community_tv.setText(community_name);
                    build_tv.setText("请选择楼栋");
                    building_uuid = "";
                    unit_tv.setText("请选择单元");
                    unit_uuid = "";
                    room_tv.setText("请选择房间号");
                    room_uuid = "";
                    complete_btn.setEnabled(true);
                    complete_btn.setBackgroundResource(R.drawable.rect_round_blue);
                } else if (choiceType == 1) {
                    building_uuid = choiceUUId;
                    building_name = choiceText;
                    build_tv.setText(building_name);
                    unit_tv.setText("请选择单元");
                    unit_uuid = "";
                    room_tv.setText("请选择房间号");
                    room_uuid = "";
                } else if (choiceType == 2) {
                    unit_uuid = choiceUUId;
                    unit_name = choiceText;
                    unit_tv.setText(unit_name);
                    room_tv.setText("请选择房间号");
                    room_uuid = "";
                    room_name = "";
                } else if (choiceType == 3) {
                    room_name = choiceText;
                    room_uuid = choiceUUId;
                    room_tv.setText(room_name);
                }
            }
        })
                .setTitleText(title)
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setCancelColor(Color.parseColor("#629ef0"))
                .setSubmitColor(Color.parseColor("#629ef0"))
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(lists);//一级选择器
        pvOptions.show();
    }

    @Override
    public void onAddressSelected(String provinceName, String cityName, String countyName, String streeName, String communityName) {
        province_name = provinceName;
        city_name = cityName;
        region_name = countyName;
        address_tv.setText(province_name + city_name + region_name);
        community_tv.setText("请选择小区");
        community_uuid = "";
        build_tv.setText("请选择楼栋");
        building_uuid = "";
        unit_tv.setText("请选择单元");
        unit_uuid = "";
        room_tv.setText("请选择房间号");
        room_uuid = "";
        complete_btn.setEnabled(false);
        complete_btn.setBackgroundResource(R.drawable.rect_round_gray);
    }


    private String province_name = "";
    private String city_name = "";
    private String region_name = "";


    private String community_uuid = "";
    private String community_name = "";

    private String building_uuid = "";
    private String building_name = "";

    private String unit_uuid = "";
    private String unit_name = "";

    private String room_uuid = "";
    private String room_name = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            switch (requestCode) {
                case 100:
                    province_name = data.getStringExtra(CustomerChoiceCommunityActivity.PROVINCENAME);
                    city_name = data.getStringExtra(CustomerChoiceCommunityActivity.CITYNAME);
                    region_name = data.getStringExtra(CustomerChoiceCommunityActivity.REGIONIDNAME);
                    address_tv.setText(province_name + city_name + region_name);
                    community_tv.setText("请选择小区");
                    community_uuid = "";
                    build_tv.setText("请选择楼栋");
                    building_uuid = "";
                    unit_tv.setText("请选择单元");
                    unit_uuid = "";
                    room_tv.setText("请选择房间号");
                    room_uuid = "";
                    break;
                case 200:
                    community_uuid = data.getStringExtra(CustomerChoiceCommunityActivity.UUID);
                    community_name = data.getStringExtra(CustomerChoiceCommunityActivity.NAME);
                    community_tv.setText(community_name);
                    build_tv.setText("请选择楼栋");
                    building_uuid = "";
                    unit_tv.setText("请选择单元");
                    unit_uuid = "";
                    room_tv.setText("请选择房间号");
                    room_uuid = "";
                    complete_btn.setEnabled(true);
                    complete_btn.setBackgroundResource(R.drawable.rect_round_blue);
                    break;
                case 300:
                    building_uuid = data.getStringExtra(CustomerChoiceCommunityActivity.UUID);
                    building_name = data.getStringExtra(CustomerChoiceCommunityActivity.NAME);
                    build_tv.setText(building_name);
                    unit_tv.setText("请选择单元");
                    unit_uuid = "";
                    room_tv.setText("请选择房间号");
                    room_uuid = "";
                    break;
                case 400:
                    unit_uuid = data.getStringExtra(CustomerChoiceCommunityActivity.UUID);
                    unit_name = data.getStringExtra(CustomerChoiceCommunityActivity.NAME);
                    unit_tv.setText(unit_name);
                    room_tv.setText("请选择房间号");
                    room_uuid = "";
                    break;
                case 500:
                    room_uuid = data.getStringExtra(CustomerChoiceCommunityActivity.UUID);
                    room_name = data.getStringExtra(CustomerChoiceCommunityActivity.NAME);
                    room_tv.setText(room_name);
                    break;
            }
        }
    }

    private NameAuthDialog nameAuthDialog;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        ToastUtil.toastShow(this, "新增成功");
                        setResult(200);
                        finish();
                    } catch (Exception e) {

                    }
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        ComBuildUnitRoomEntity comBuildUnitRoomEntity = GsonUtils.gsonToBean(result, ComBuildUnitRoomEntity.class);
                        ComBuildUnitRoomEntity.ContentBean contentBean = comBuildUnitRoomEntity.getContent();
                        if (null != contentBean) {
                            ComBuildUnitRoomEntity.ContentBean.PagingBean pagingBean = contentBean.getPaging();
                            List<ComBuildUnitRoomEntity.ContentBean.DataBean> dataBeanList = contentBean.getData();
                            if (pagingBean.getTotal_record() > 200) {
                                jumpPage();
                            } else {
                                ShowCommunityPickerView(dataBeanList);
                            }
                        }
                    } catch (Exception e) {

                    }
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        AddHouseEntity addHouseEntity = GsonUtils.gsonToBean(result, AddHouseEntity.class);
                        final String house_id = addHouseEntity.getContent().getHouse_id();
                        nameAuthDialog = new NameAuthDialog(this);
                        nameAuthDialog.show();
                        nameAuthDialog.left_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                nameAuthDialog.dismiss();
                                Intent intent = new Intent();
                                setResult(1, intent);
                                finish();
                            }
                        });
                        nameAuthDialog.right_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                nameAuthDialog.dismiss();
                                Intent intent = new Intent();
                                intent.setClass(CustomerAddCommunityActivity.this, NameAuthActivity.class);
                                intent.putExtra("property_id", house_id);//房产id
                                intent.putExtra("house_uuid", room_uuid);//rms系统返回的house_uuid
                                startActivity(intent);
                                finish();
                            }
                        });
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }

    private void jumpPage() {
        switch (choiceType) {
            case 0:
                Intent comIntent = new Intent(CustomerAddCommunityActivity.this, CustomerChoiceCommunityActivity.class);
                comIntent.putExtra(CustomerChoiceCommunityActivity.CHOICETYPE, 0);
                comIntent.putExtra(CustomerChoiceCommunityActivity.PROVINCENAME, province_name);
                comIntent.putExtra(CustomerChoiceCommunityActivity.CITYNAME, city_name);
                comIntent.putExtra(CustomerChoiceCommunityActivity.REGIONIDNAME, region_name);
                startActivityForResult(comIntent, 200);
                break;
            case 1:
                Intent bulidIntent = new Intent(CustomerAddCommunityActivity.this, CustomerChoiceCommunityActivity.class);
                bulidIntent.putExtra(CustomerChoiceCommunityActivity.CHOICETYPE, 1);
                bulidIntent.putExtra(CustomerChoiceCommunityActivity.COMMUNITYUUID, community_uuid);
                startActivityForResult(bulidIntent, 300);
                break;
            case 2:
                Intent unitIntent = new Intent(CustomerAddCommunityActivity.this, CustomerChoiceCommunityActivity.class);
                unitIntent.putExtra(CustomerChoiceCommunityActivity.CHOICETYPE, 2);
                unitIntent.putExtra(CustomerChoiceCommunityActivity.BUILDINGUUID, building_uuid);
                startActivityForResult(unitIntent, 400);
                break;
            case 3:
                Intent roomIntent = new Intent(CustomerAddCommunityActivity.this, CustomerChoiceCommunityActivity.class);
                roomIntent.putExtra(CustomerChoiceCommunityActivity.CHOICETYPE, 3);
                roomIntent.putExtra(CustomerChoiceCommunityActivity.UNITUUID, unit_uuid);
                startActivityForResult(roomIntent, 500);
                break;
        }
    }
}
