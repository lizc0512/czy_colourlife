package com.customerInfo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.Util;
import com.customerInfo.adapter.AddPropertyAdapter;
import com.customerInfo.adapter.AreaPropertyAdapter;
import com.customerInfo.adapter.CityPropertyAdapter;
import com.customerInfo.model.NewCustomerInfoModel;
import com.customerInfo.protocol.AddListEntity;
import com.customerInfo.protocol.AreaListEntity;
import com.customerInfo.protocol.CityListEntity;
import com.customerInfo.protocol.CityListsEntity;
import com.door.activity.NewDoorApplyActivity;
import com.door.entity.DoorSupportTypeEntity;
import com.door.model.NewDoorAuthorModel;
import com.external.eventbus.EventBus;
import com.im.view.SideBar;
import com.myproperty.activity.PropertyChangeActivity;
import com.myproperty.activity.PropertyRealNameActivity;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.CityCustomConst;

import static com.door.activity.NewDoorApplyActivity.DOORSUPPORTTYPE;

/**
 * 新增房产 编辑房产
 * hxg 2019/04/02
 */
public class CustomerAddPropertyActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public final static String ID = "id";
    public final static String IDENTITY_ID = "identity_id";
    public final static String IDENTITY_NAME = "identity_name";
    public final static String COMMUNITY_UUID = "community_uuid";
    public final static String COMMUNITY_NAME = "community_name";
    public final static String BUILD_UUID = "build_uuid";
    public final static String BUILD_NAME = "build_name";
    public final static String UNIT_UUID = "unit_uuid";
    public final static String UNIT_NAME = "unit_name";
    public final static String ROOM_UUID = "room_uuid";
    public final static String ROOM_NAME = "room_name";
    public final static String CITY = "city";
    public final static String DEFAULT = "default";
    public final static String IDENTITY = "identity";

    private String id = "";
    private String community_uuid = "";
    private String community_name = "";
    private String building_uuid = "";
    private String building_name = "";
    private String unit_uuid = "";
    private String unit_name = "";
    private String room_uuid = "";
    private String room_name = "";
    private boolean identity = false;

    private ImageView mback;
    private ImageView iv_clean;
    private TextView mtitle;
    private TextView tv_choose;
    private TextView tv_city;
    private ImageView iv_city;
    private LinearLayout ll_address;
    private EditText et_search_area;
    private RelativeLayout rl_city;
    private RelativeLayout rl_address_choose;

    private TextView tv_garden;
    private TextView tv_block;
    private TextView tv_dong;
    private TextView tv_unit;
    private View v_block;
    private View v_dong;
    private View v_unit;

    private NewCustomerInfoModel newCustomerInfoModel;
    private int choiceType = 0;
    private RelativeLayout rl_no_content;
    private SwipeMenuRecyclerView rv_address;
    private SwipeMenuRecyclerView rv_area;
    private List<AreaListEntity.ContentBean.DataBean> areaBeanList = new ArrayList<>();
    private List<AddListEntity.ContentBean.DataBean> addBeanList = new ArrayList<>();
    private AreaPropertyAdapter areaAdapter;
    private AddPropertyAdapter addAdapter;

    private TextView tv_toast;
    private SideBar sideBar;
    private FrameLayout fl_city;
    private CityPropertyAdapter cityAdapter;
    private ListView lv_city;
    private List<CityListsEntity> cityList = new ArrayList<>();

    private int totalRecord;
    private String city_name = "";
    private int page = 1;
    private int type = 1;
    private int pageSize = 100;
    private String keyword = "";
    private String identifyId = "";
    private String identifyName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_address);
        getWindow().setBackgroundDrawable(null);
        newCustomerInfoModel = new NewCustomerInfoModel(CustomerAddPropertyActivity.this);
        initView();
        initData();
    }

    private void initView() {
        mback = findViewById(R.id.user_top_view_back);
        iv_clean = findViewById(R.id.iv_clean);
        mtitle = findViewById(R.id.user_top_view_title);
        tv_choose = findViewById(R.id.tv_choose);
        tv_city = findViewById(R.id.tv_city);
        iv_city = findViewById(R.id.iv_city);
        ll_address = findViewById(R.id.ll_address);
        et_search_area = findViewById(R.id.et_search_area);
        rl_city = findViewById(R.id.rl_city);

        rl_address_choose = findViewById(R.id.rl_address_choose);
        tv_garden = findViewById(R.id.tv_garden);
        v_block = findViewById(R.id.v_block);
        tv_block = findViewById(R.id.tv_block);
        v_dong = findViewById(R.id.v_dong);
        tv_dong = findViewById(R.id.tv_dong);
        v_unit = findViewById(R.id.v_unit);
        tv_unit = findViewById(R.id.tv_unit);
        rl_no_content = findViewById(R.id.rl_no_content);

        mback.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        iv_city.setOnClickListener(this);
        iv_clean.setOnClickListener(this);
        tv_garden.setOnClickListener(this);
        tv_block.setOnClickListener(this);
        tv_dong.setOnClickListener(this);
        tv_unit.setOnClickListener(this);
        et_search_area.setCursorVisible(false);//隐藏光标
        et_search_area.setOnClickListener(this);
        et_search_area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (View.VISIBLE == rl_no_content.getVisibility()) {
                    rl_no_content.setVisibility(View.GONE);
                }
                if (View.VISIBLE == rv_area.getVisibility()) {
                    rv_area.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                et_search_area.setCursorVisible(!TextUtils.isEmpty(s.toString().trim()));
                iv_clean.setVisibility(TextUtils.isEmpty(s.toString().trim()) ? View.GONE : View.VISIBLE);
                ll_address.setVisibility(TextUtils.isEmpty(s.toString().trim()) ? View.VISIBLE : View.GONE);
            }
        });

        et_search_area.setOnEditorActionListener((v, actionId, event) -> {
            keyword = et_search_area.getText().toString().trim();
            if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(keyword)) {
                choiceType = 0;
                //模糊搜索所有小区
                dismissSoftKeyboard(et_search_area);
                page = 1;
                if (null != addAdapter && 0 < addBeanList.size()) {
                    rv_address.setVisibility(View.GONE);
                    addBeanList.clear();
                    addAdapter.notifyDataSetChanged();
                }
                newCustomerInfoModel.addressSelect(3, "", keyword, page, pageSize, CustomerAddPropertyActivity.this);
            }
            return false;
        });

        rv_area = findViewById(R.id.rv_area);
        rv_area.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        areaAdapter = new AreaPropertyAdapter(this, areaBeanList);
        rv_area.setAdapter(areaAdapter);
        rv_area.useDefaultLoadMore();
        rv_area.setLoadMoreListener(() -> {
            page++;
            newCustomerInfoModel.addressSelect(3, "", keyword, page, pageSize, this);
        });

        rv_address = findViewById(R.id.rv_address);
        rv_address.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        addAdapter = new AddPropertyAdapter(this, addBeanList);
        rv_address.setAdapter(addAdapter);
        rv_address.useDefaultLoadMore();
        rv_address.setLoadMoreListener(() -> {
            page++;
            switch (type) {
                case 1:
                    newCustomerInfoModel.addressSelect(6, city_name, keyword, page, pageSize, this);
                    break;
                case 2:
                    newCustomerInfoModel.getBuildingData(7, community_uuid, page, pageSize, false, this);
                    break;
                case 3:
                    newCustomerInfoModel.getUnitData(8, building_uuid, page, pageSize, false, this);
                    break;
                case 4:
                    newCustomerInfoModel.getRoomData(9, unit_uuid, page, pageSize, false, this);
                    break;
            }
        });

        sideBar = findViewById(R.id.sidebar);
        tv_toast = findViewById(R.id.tv_toast);
        fl_city = findViewById(R.id.fl_city);
        sideBar.setTextView(tv_toast);
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(s -> {
            //该字母首次出现的位置
            int position = cityAdapter.getPositionForSection(s.charAt(0));
            if (position != -1) {
                lv_city.setSelection(position);
            }
        });

        lv_city = findViewById(R.id.lv_city);
        lv_city.setOnItemClickListener((parent, view, position, id) -> {
            fl_city.setVisibility(View.GONE);
            city_name = ((CityListsEntity) cityAdapter.getItem(position)).getCity();
            tv_city.setText(city_name);
            iv_city.setVisibility(View.VISIBLE);
            dismissSoftKeyboard(et_search_area);
            page = 1;
            keyword = "";
            if (null != addAdapter && 0 < addBeanList.size()) {
                rv_address.setVisibility(View.GONE);
                addBeanList.clear();
                addAdapter.notifyDataSetChanged();
            }
            newCustomerInfoModel.addressSelect(6, city_name, keyword, page, pageSize, this);
        });

        cityAdapter = new CityPropertyAdapter(this, cityList);
        lv_city.setAdapter(cityAdapter);
    }

    private void initData() {
        Intent intent = getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra(ID))) {
            mtitle.setText("编辑房产");
            rl_city.setVisibility(View.GONE);
            rl_address_choose.setVisibility(View.VISIBLE);
            if (View.VISIBLE == fl_city.getVisibility()) {
                fl_city.setVisibility(View.GONE);
            }
            id = intent.getStringExtra(ID);
            community_uuid = intent.getStringExtra(COMMUNITY_UUID) == null ? "" : intent.getStringExtra(COMMUNITY_UUID);
            if (!TextUtils.isEmpty(intent.getStringExtra(COMMUNITY_NAME))) {
                community_name = intent.getStringExtra(COMMUNITY_NAME);
                tv_garden.setVisibility(View.VISIBLE);
                tv_garden.setText(community_name);
            }
            building_uuid = intent.getStringExtra(BUILD_UUID) == null ? "" : intent.getStringExtra(BUILD_UUID);
            if (!TextUtils.isEmpty(intent.getStringExtra(BUILD_NAME))) {
                building_name = intent.getStringExtra(BUILD_NAME);
                v_block.setVisibility(View.VISIBLE);
                tv_block.setVisibility(View.VISIBLE);
                tv_block.setText(building_name);
            }
            unit_uuid = intent.getStringExtra(UNIT_UUID) == null ? "" : intent.getStringExtra(UNIT_UUID);
            if (!TextUtils.isEmpty(intent.getStringExtra(UNIT_NAME))) {
                unit_name = intent.getStringExtra(UNIT_NAME);
                v_dong.setVisibility(View.VISIBLE);
                tv_dong.setVisibility(View.VISIBLE);
                tv_dong.setText(unit_name);
            }
            room_uuid = intent.getStringExtra(ROOM_UUID) == null ? "" : intent.getStringExtra(ROOM_UUID);
            room_name = intent.getStringExtra(ROOM_NAME) == null ? "" : intent.getStringExtra(ROOM_NAME);
            city_name = intent.getStringExtra(CITY) == null ? "" : intent.getStringExtra(CITY);
            tv_city.setText(city_name);
            if (!TextUtils.isEmpty(room_name)) {//有房号
                dismissSoftKeyboard(et_search_area);
                page = 1;
                newCustomerInfoModel.getRoomData(9, unit_uuid, page, pageSize, false, CustomerAddPropertyActivity.this);
            } else if (!TextUtils.isEmpty(unit_name)) {//有单元
                dismissSoftKeyboard(et_search_area);
                page = 1;
                newCustomerInfoModel.getUnitData(8, building_uuid, page, pageSize, false, CustomerAddPropertyActivity.this);
            } else if (!TextUtils.isEmpty(building_name)) {//有楼栋
                dismissSoftKeyboard(et_search_area);
                page = 1;
                newCustomerInfoModel.getBuildingData(7, community_uuid, page, pageSize, false, CustomerAddPropertyActivity.this);
            } else {//只有小区，显示城市下的小区
                dismissSoftKeyboard(et_search_area);
                page = 1;
                keyword = "";
                newCustomerInfoModel.addressSelect(6, city_name, keyword, page, pageSize, this);
            }
            identity = intent.getBooleanExtra(IDENTITY, false);//是否跳转选中身份
        } else {
            identifyId = getIntent().getStringExtra(IDENTITY_ID);
            identifyName = getIntent().getStringExtra(IDENTITY_NAME);
            if (TextUtils.isEmpty(identifyId)) {
                mtitle.setText(getResources().getString(R.string.title_increase_property));
            } else {
                mtitle.setText(getResources().getString(R.string.title_choice_community));
            }
            SharedPreferences mShared = getSharedPreferences(UserAppConst.USERINFO, 0);
            city_name = mShared.getString(CityCustomConst.LOCATION_CITY, "");
            if (!Util.isGps(Objects.requireNonNull(this)) || TextUtils.isEmpty(city_name)) {
                ToastUtil.toastShow(this, "未能获取当前定位");
            } else {
                tv_city.setText(city_name);
                dismissSoftKeyboard(et_search_area);
                page = 1;
                keyword = "";
                newCustomerInfoModel.addressSelect(6, city_name, keyword, page, pageSize, this);
            }
        }
    }

    /**
     * 选择
     *
     * @param position position
     */
    public void select(int position) {
        if (addBeanList.size() > 0) {
            switch (choiceType) {
                case -1: //回调
                    room_uuid = addBeanList.get(position).getUuid();
                    room_name = addBeanList.get(position).getName();
                    addOrUpdateAddress();
                    break;
                case 0:
                    rl_city.setVisibility(View.GONE);
                    rl_address_choose.setVisibility(View.VISIBLE);
                    if (View.VISIBLE == fl_city.getVisibility()) {
                        fl_city.setVisibility(View.GONE);
                    }
                    tv_garden.setText(addBeanList.get(position).getName());
                    dismissSoftKeyboard(et_search_area);
                    page = 1;
                    keyword = addBeanList.get(position).getName();
                    if (null != addAdapter && 0 < addBeanList.size()) {
                        rv_address.setVisibility(View.GONE);
                        addBeanList.clear();
                        addAdapter.notifyDataSetChanged();
                    }
                    newCustomerInfoModel.addressSelect(6, city_name, keyword, page, pageSize, this);
                    break;
                case 1://请求楼栋
                    community_uuid = addBeanList.get(position).getUuid();
                    community_name = addBeanList.get(position).getName();
                    tv_garden.setText(community_name);
                    dismissSoftKeyboard(et_search_area);
                    page = 1;
                    if (null != addAdapter && 0 < addBeanList.size()) {
                        rv_address.setVisibility(View.GONE);
                        addBeanList.clear();
                        addAdapter.notifyDataSetChanged();
                    }
                    newCustomerInfoModel.getBuildingData(7, community_uuid, page, pageSize, false, CustomerAddPropertyActivity.this);
                    break;
                case 2:
                    building_uuid = addBeanList.get(position).getUuid();
                    building_name = addBeanList.get(position).getName();
                    tv_block.setText(building_name);
                    dismissSoftKeyboard(et_search_area);
                    page = 1;
                    if (null != addAdapter && 0 < addBeanList.size()) {
                        rv_address.setVisibility(View.GONE);
                        addBeanList.clear();
                        addAdapter.notifyDataSetChanged();
                    }
                    newCustomerInfoModel.getUnitData(8, building_uuid, page, pageSize, false, CustomerAddPropertyActivity.this);
                    break;
                case 3:
                    unit_uuid = addBeanList.get(position).getUuid();
                    unit_name = addBeanList.get(position).getName();
                    tv_dong.setText(unit_name);
                    dismissSoftKeyboard(et_search_area);
                    page = 1;
                    if (null != addAdapter && 0 < addBeanList.size()) {
                        rv_address.setVisibility(View.GONE);
                        addBeanList.clear();
                        addAdapter.notifyDataSetChanged();
                    }
                    newCustomerInfoModel.getRoomData(9, unit_uuid, page, pageSize, false, CustomerAddPropertyActivity.this);
                    break;
            }
        }
    }

    /**
     * 搜索选择进入
     *
     * @param position position
     */
    public void searchSelect(int position) {
        if (areaBeanList.size() > 0) {
            et_search_area.setText("");
            choiceType = 1;
            type = 1;
            setBackground(1);
            rv_area.setVisibility(View.GONE);
            community_uuid = areaBeanList.get(position).getUuid();
            community_name = areaBeanList.get(position).getName();
            tv_garden.setText(community_name);
            dismissSoftKeyboard(et_search_area);
            page = 1;
            newCustomerInfoModel.getBuildingData(7, community_uuid, page, pageSize, false, CustomerAddPropertyActivity.this);
        }
    }

    /**
     * 新增或编辑
     */
    private void addOrUpdateAddress() {
        newCustomerInfoModel.isNeedRealName(13, community_uuid, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    try {
                        if ("新增房产".equals(mtitle.getText().toString().trim())) {
                            String identity = data.getStringExtra(PropertyChangeActivity.IDENTITY_TYPE);
                            newCustomerInfoModel.postCustomerAddress(10, community_uuid, community_name, building_uuid, building_name
                                    , unit_uuid, unit_name, room_uuid, room_name, identity, this);
                        } else if ("选择小区".equals(mtitle.getText().toString().trim())) {
                            NewDoorAuthorModel newDoorAuthorModel = new NewDoorAuthorModel(CustomerAddPropertyActivity.this);
                            newDoorAuthorModel.getSupportDoorType(14, community_uuid, CustomerAddPropertyActivity.this);
                        } else {//修改
                            newCustomerInfoModel.postCustomerUpdateAddress(12, id, community_uuid, community_name, building_uuid, building_name
                                    , unit_uuid, unit_name, room_uuid, room_name, this);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 100:
                if (resultCode == 200) {
                    setResult(200);
                    finish();
                }
                break;
        }
    }

    private void setBackground(int i) {
        switch (i) {
            case 1://花园
                tv_choose.setText("选择花园");
                tv_garden.setVisibility(View.VISIBLE);
                tv_garden.setTextColor(getResources().getColor(R.color.color_8eafea));
                tv_garden.setBackgroundResource(R.drawable.select_text);
                v_block.setVisibility(View.GONE);
                tv_block.setVisibility(View.GONE);
                v_dong.setVisibility(View.GONE);
                tv_dong.setVisibility(View.GONE);
                v_unit.setVisibility(View.GONE);
                tv_unit.setVisibility(View.GONE);
                break;
            case 2://小区
                tv_choose.setText("选择小区");
                tv_garden.setVisibility(View.VISIBLE);
                tv_garden.setBackgroundColor(Color.WHITE);
                tv_garden.setTextColor(getResources().getColor(R.color.black_text_color));
                v_block.setVisibility(View.VISIBLE);
                tv_block.setVisibility(View.VISIBLE);
                tv_block.setTextColor(getResources().getColor(R.color.color_8eafea));
                tv_block.setBackgroundResource(R.drawable.select_text);
                v_dong.setVisibility(View.GONE);
                tv_dong.setVisibility(View.GONE);
                v_unit.setVisibility(View.GONE);
                tv_unit.setVisibility(View.GONE);
                break;
            case 3://栋
                tv_choose.setText("选择楼栋");
                tv_garden.setVisibility(View.VISIBLE);
                v_block.setVisibility(View.VISIBLE);
                tv_block.setVisibility(View.VISIBLE);
                tv_block.setTextColor(getResources().getColor(R.color.black_text_color));
                tv_block.setBackgroundColor(Color.WHITE);
                v_dong.setVisibility(View.VISIBLE);
                tv_dong.setVisibility(View.VISIBLE);
                tv_dong.setTextColor(getResources().getColor(R.color.color_8eafea));
                tv_dong.setBackgroundResource(R.drawable.select_text);
                v_unit.setVisibility(View.GONE);
                tv_unit.setVisibility(View.GONE);
                break;
            case 4://单元
                tv_choose.setText("选择单元");
                tv_garden.setVisibility(View.VISIBLE);
                v_block.setVisibility(View.VISIBLE);
                tv_block.setVisibility(View.VISIBLE);
                v_dong.setVisibility(View.VISIBLE);
                tv_dong.setVisibility(View.VISIBLE);
                tv_dong.setTextColor(getResources().getColor(R.color.black_text_color));
                tv_dong.setBackgroundColor(Color.WHITE);
                v_unit.setVisibility(View.VISIBLE);
                tv_unit.setVisibility(View.VISIBLE);
                tv_unit.setTextColor(getResources().getColor(R.color.color_8eafea));
                tv_unit.setBackgroundResource(R.drawable.select_text);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.iv_clean:
                et_search_area.setText("");
                dismissSoftKeyboard(et_search_area);
                break;
            case R.id.et_search_area:
                if (View.VISIBLE == rl_no_content.getVisibility()) {
                    rl_no_content.setVisibility(View.GONE);
                }
                if (View.VISIBLE == rv_area.getVisibility()) {
                    rv_area.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_city://城市列表
            case R.id.iv_city://城市列表
                if (null != addAdapter && 0 < addBeanList.size()) {
                    rv_address.setVisibility(View.GONE);
                    addBeanList.clear();
                    addAdapter.notifyDataSetChanged();
                }
                tv_choose.setText("选择城市");
                iv_city.setVisibility(View.GONE);
                fl_city.setVisibility(View.VISIBLE);
                dismissSoftKeyboard(et_search_area);
                newCustomerInfoModel.cityListSelect(11, this);
            case R.id.tv_garden://点击花园
                if (null != addAdapter && 0 < addBeanList.size()) {
                    rv_address.setVisibility(View.GONE);
                    addBeanList.clear();
                    addAdapter.notifyDataSetChanged();
                }
                choiceType = 0;
                community_uuid = "";
                community_name = "";
                building_uuid = "";
                building_name = "";
                unit_uuid = "";
                unit_name = "";
                rl_city.setVisibility(View.VISIBLE);
                rl_address_choose.setVisibility(View.GONE);
                dismissSoftKeyboard(et_search_area);
                page = 1;
                keyword = "";
                newCustomerInfoModel.addressSelect(6, city_name, keyword, page, pageSize, this);
                break;
            case R.id.tv_block:
                if (null != addAdapter && 0 < addBeanList.size()) {
                    rv_address.setVisibility(View.GONE);
                    addBeanList.clear();
                    addAdapter.notifyDataSetChanged();
                }
                choiceType = 1;
                building_uuid = "";
                building_name = "";
                unit_uuid = "";
                unit_name = "";
                type = 2;
                setBackground(2);
                dismissSoftKeyboard(et_search_area);
                page = 1;
                newCustomerInfoModel.getBuildingData(7, community_uuid, page, pageSize, false, CustomerAddPropertyActivity.this);
                break;
            case R.id.tv_dong:
                if (null != addAdapter && 0 < addBeanList.size()) {
                    rv_address.setVisibility(View.GONE);
                    addBeanList.clear();
                    addAdapter.notifyDataSetChanged();
                }
                choiceType = 2;
                unit_uuid = "";
                unit_name = "";
                type = 3;
                setBackground(3);
                dismissSoftKeyboard(et_search_area);
                page = 1;
                newCustomerInfoModel.getUnitData(8, building_uuid, page, pageSize, false, CustomerAddPropertyActivity.this);
                break;
            case R.id.tv_unit:
                if (null != addAdapter && 0 < addBeanList.size()) {
                    rv_address.setVisibility(View.GONE);
                    addBeanList.clear();
                    addAdapter.notifyDataSetChanged();
                }
                choiceType = 3;
                type = 4;
                setBackground(4);
                dismissSoftKeyboard(et_search_area);
                page = 1;
                newCustomerInfoModel.getRoomData(9, unit_uuid, page, pageSize, false, CustomerAddPropertyActivity.this);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        if (View.GONE == rv_address.getVisibility()) {
            rv_address.setVisibility(View.VISIBLE);
        }
        switch (what) {
            case 3:
                if (!TextUtils.isEmpty(result)) {
                    AreaListEntity areaListEntity = GsonUtils.gsonToBean(result, AreaListEntity.class);
                    AreaListEntity.ContentBean contentBean = areaListEntity.getContent();
                    AreaListEntity.ContentBean.PagingBean pagingBean;
                    if (page == 1) {
                        areaBeanList.clear();
                    }
                    List<AreaListEntity.ContentBean.DataBean> addDataList;
                    addDataList = contentBean.getData();
                    areaBeanList.addAll(addDataList);
                    pagingBean = contentBean.getPaging();
                    totalRecord = pagingBean.getTotal_record();
                    boolean dataEmpty = addDataList.size() == 0;
                    boolean hasMore = totalRecord > areaBeanList.size();
                    rv_area.loadMoreFinish(dataEmpty, hasMore);
                    if (1 == page && 0 == addDataList.size()) {
                        rv_area.setVisibility(View.GONE);
                        rl_no_content.setVisibility(View.VISIBLE);
                    } else {
                        rl_no_content.setVisibility(View.GONE);
                        rv_area.setVisibility(View.VISIBLE);
                    }
                    areaAdapter.notifyDataSetChanged();
                }
                break;
            case 6:
                if (!TextUtils.isEmpty(result)) {
                    type = 1;
                    rl_city.setVisibility(View.VISIBLE);
                    rl_address_choose.setVisibility(View.GONE);
                    choiceType = 1;
                    AddListEntity addListEntity = GsonUtils.gsonToBean(result, AddListEntity.class);
                    AddListEntity.ContentBean contentBean = addListEntity.getContent();
                    AddListEntity.ContentBean.PagingBean pagingBean;
                    if (page == 1) {
                        addBeanList.clear();
                        setBackground(1);
                    }
                    List<AddListEntity.ContentBean.DataBean> addDataList;
                    addDataList = contentBean.getData();
                    int size = addDataList.size();
                    for (int i = 0; i < size; i++) {
                        if (!TextUtils.isEmpty(addDataList.get(i).getFloor())) {
                            AddListEntity.ContentBean.DataBean item = new AddListEntity.ContentBean.DataBean();
                            item.setFloor("");
                            item.setName(addDataList.get(i).getFloor() + "楼" + addDataList.get(i).getName());
                            addDataList.set(i, item);
                        }
                    }
                    addBeanList.addAll(addDataList);
                    pagingBean = contentBean.getPaging();
                    totalRecord = pagingBean.getTotal_record();
                    boolean dataEmpty = addDataList.size() == 0;
                    boolean hasMore = totalRecord > addBeanList.size();
                    rv_address.loadMoreFinish(dataEmpty, hasMore);
                    if (1 == page && 0 == addDataList.size()) {
                        rv_address.setVisibility(View.GONE);
                        rl_no_content.setVisibility(View.VISIBLE);
                    } else {
                        rv_address.setVisibility(View.VISIBLE);
                        addAdapter.notifyDataSetChanged();
                        rl_no_content.setVisibility(View.GONE);
                    }
                }
                break;
            case 7:
                if (!TextUtils.isEmpty(result)) {
                    rl_city.setVisibility(View.GONE);
                    rl_address_choose.setVisibility(View.VISIBLE);
                    if (View.VISIBLE == fl_city.getVisibility()) {
                        fl_city.setVisibility(View.GONE);
                    }
                    choiceType = 2;
                    type = 2;
                    AddListEntity addListEntity = GsonUtils.gsonToBean(result, AddListEntity.class);
                    AddListEntity.ContentBean contentBean = addListEntity.getContent();
                    AddListEntity.ContentBean.PagingBean pagingBean;
                    if (page == 1) {
                        addBeanList.clear();
                    }
                    List<AddListEntity.ContentBean.DataBean> addDataList;
                    addDataList = contentBean.getData();
                    int size = addDataList.size();
                    for (int i = 0; i < size; i++) {
                        if (!TextUtils.isEmpty(addDataList.get(i).getFloor())) {
                            AddListEntity.ContentBean.DataBean item = new AddListEntity.ContentBean.DataBean();
                            item.setFloor("");
                            item.setName(addDataList.get(i).getFloor() + "楼" + addDataList.get(i).getName());
                            addDataList.set(i, item);
                        }
                    }
                    addBeanList.addAll(addDataList);
                    pagingBean = contentBean.getPaging();
                    totalRecord = pagingBean.getTotal_record();
                    boolean dataEmpty = addDataList.size() == 0;
                    boolean hasMore = totalRecord > addBeanList.size();
                    rv_address.loadMoreFinish(dataEmpty, hasMore);
                    if (1 == page && 0 == addDataList.size()) {
                        rv_address.setVisibility(View.GONE);
                    } else {
                        rv_address.setVisibility(View.VISIBLE);
                        addAdapter.notifyDataSetChanged();
                    }
                    if (!hasMore && dataEmpty && 0 == addBeanList.size()) {
                        addOrUpdateAddress();
                    }
                }
                break;
            case 8:
                if (!TextUtils.isEmpty(result)) {
                    choiceType = 3;
                    type = 3;
                    AddListEntity addListEntity = GsonUtils.gsonToBean(result, AddListEntity.class);
                    AddListEntity.ContentBean contentBean = addListEntity.getContent();
                    AddListEntity.ContentBean.PagingBean pagingBean;
                    List<AddListEntity.ContentBean.DataBean> addDataList;
                    addDataList = contentBean.getData();
                    if (page == 1) {
                        addBeanList.clear();
                        setBackground(2);
                    }
                    int size = addDataList.size();
                    for (int i = 0; i < size; i++) {
                        if (!TextUtils.isEmpty(addDataList.get(i).getFloor())) {
                            AddListEntity.ContentBean.DataBean item = new AddListEntity.ContentBean.DataBean();
                            item.setFloor("");
                            item.setName(addDataList.get(i).getFloor() + "楼" + addDataList.get(i).getName());
                            addDataList.set(i, item);
                        }
                    }
                    addBeanList.addAll(addDataList);
                    pagingBean = contentBean.getPaging();
                    totalRecord = pagingBean.getTotal_record();
                    boolean dataEmpty = addDataList.size() == 0;
                    boolean hasMore = totalRecord > addBeanList.size();
                    rv_address.loadMoreFinish(dataEmpty, hasMore);
                    if (1 == page && 0 == addDataList.size()) {
                        rv_address.setVisibility(View.GONE);
                    } else {
                        rv_address.setVisibility(View.VISIBLE);
                        addAdapter.notifyDataSetChanged();
                    }
                    if (!hasMore && dataEmpty && 0 == addBeanList.size()) {
                        addOrUpdateAddress();
                    }
                }
                break;
            case 9:
                if (!TextUtils.isEmpty(result)) {
                    choiceType = -1;
                    type = 4;
                    AddListEntity addListEntity = GsonUtils.gsonToBean(result, AddListEntity.class);
                    AddListEntity.ContentBean contentBean = addListEntity.getContent();
                    AddListEntity.ContentBean.PagingBean pagingBean;
                    if (page == 1) {
                        addBeanList.clear();
                        setBackground(3);
                    }
                    List<AddListEntity.ContentBean.DataBean> addDataList;
                    addDataList = contentBean.getData();
                    int size = addDataList.size();
                    for (int i = 0; i < size; i++) {
                        if (!TextUtils.isEmpty(addDataList.get(i).getFloor())) {
                            AddListEntity.ContentBean.DataBean item = new AddListEntity.ContentBean.DataBean();
                            item.setFloor("");
                            item.setUuid(addDataList.get(i).getUuid());
                            item.setName(addDataList.get(i).getFloor() + "楼" + addDataList.get(i).getName());
                            addDataList.set(i, item);
                        }
                    }
                    addBeanList.addAll(addDataList);
                    pagingBean = contentBean.getPaging();
                    totalRecord = pagingBean.getTotal_record();
                    boolean dataEmpty = addDataList.size() == 0;
                    boolean hasMore = totalRecord > addBeanList.size();
                    rv_address.loadMoreFinish(dataEmpty, hasMore);
                    if (1 == page && 0 == addDataList.size()) {
                        rv_address.setVisibility(View.GONE);
                    } else {
                        rv_address.setVisibility(View.VISIBLE);
                        addAdapter.notifyDataSetChanged();
                    }
                    if (!hasMore && dataEmpty && 0 == addBeanList.size()) {
                        addOrUpdateAddress();
                    }
                }
                break;
            case 10:
                if (!TextUtils.isEmpty(result)) {
                    ToastUtil.toastShow(this, "添加地址成功!");
                    setResult(1);
                    finish();
                }
                break;
            case 11:
                if (!TextUtils.isEmpty(result)) {
                    CityListEntity cityListEntity = GsonUtils.gsonToBean(result, CityListEntity.class);
                    List<CityListEntity.ContentBean> contentBean = cityListEntity.getContent();
                    CityListsEntity item;
                    for (CityListEntity.ContentBean i : contentBean) {
                        for (String j : i.getCity_list()) {
                            item = new CityListsEntity();
                            item.setCode(i.getCodeX());//城市字母
                            item.setCity(j);//城市名称
                            cityList.add(item);
                        }
                    }
                    cityAdapter.updateListView(cityList);
                }
                break;
            case 12:
                if (!TextUtils.isEmpty(result)) {
                    ToastUtil.toastShow(this, "修改成功!");
                    try {
                        if (getIntent().getBooleanExtra(DEFAULT, false)) {
                            JSONObject jsonObject = new JSONObject(result);
                            String content = jsonObject.getString("content");
                            JSONObject data = new JSONObject(content);
                            String id = data.getString("id");
                            editor.putString(UserAppConst.Colour_login_community_uuid, community_uuid);
                            editor.putString(UserAppConst.Colour_login_community_name, community_name);
                            editor.putString(UserAppConst.Colour_Build_name, building_name);
                            editor.putString(UserAppConst.Colour_Unit_name, unit_name);
                            editor.putString(UserAppConst.Colour_Room_name, room_name);
                            editor.commit();
                            Message msg = new Message();
                            msg.what = UserMessageConstant.CHANGE_COMMUNITY;
                            msg.obj = id;
                            EventBus.getDefault().post(msg);
                        }
                        setResult(1);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 13:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String content = jsonObject.getString("content");
                        JSONObject data = new JSONObject(content);
                        int isVerify = data.getInt("is_verify");
                        if (1 == isVerify) {//1 需要，2 不需要
                            Intent intent = new Intent(this, PropertyRealNameActivity.class);
                            intent.putExtra(PropertyRealNameActivity.COMMUNITY_UUID, community_uuid);
                            intent.putExtra(PropertyRealNameActivity.BUILD_NAME, building_name);
                            intent.putExtra(PropertyRealNameActivity.UNIT_NAME, unit_name);
                            intent.putExtra(PropertyRealNameActivity.ROOM_NAME, room_name);
                            intent.putExtra(PropertyRealNameActivity.IS_ADD, "新增房产".equals(mtitle.getText().toString().trim()));
                            startActivityForResult(intent, 1);
                        } else {
                            if ("新增房产".equals(mtitle.getText().toString().trim())) {
                                Intent intent = new Intent(this, PropertyChangeActivity.class);
                                startActivityForResult(intent, 1);
                            } else if ("选择小区".equals(mtitle.getText().toString().trim())) {
                                NewDoorAuthorModel newDoorAuthorModel = new NewDoorAuthorModel(CustomerAddPropertyActivity.this);
                                newDoorAuthorModel.getSupportDoorType(14, community_uuid, CustomerAddPropertyActivity.this);
                            } else {//修改
                                newCustomerInfoModel.postCustomerUpdateAddress(12, id, community_uuid, community_name, building_uuid, building_name
                                        , unit_uuid, unit_name, room_uuid, room_name, this);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 14:
                try {
                    DoorSupportTypeEntity doorSupportTypeEntity = GsonUtils.gsonToBean(result, DoorSupportTypeEntity.class);
                    Intent intent = new Intent(CustomerAddPropertyActivity.this, NewDoorApplyActivity.class);
                    intent.putExtra(COMMUNITY_UUID, community_uuid);
                    intent.putExtra(COMMUNITY_NAME, community_name);
                    intent.putExtra(BUILD_UUID, building_uuid);
                    intent.putExtra(BUILD_NAME, building_name);
                    intent.putExtra(UNIT_UUID, unit_uuid);
                    intent.putExtra(UNIT_NAME, unit_name);
                    intent.putExtra(ROOM_UUID, room_uuid);
                    intent.putExtra(ROOM_NAME, room_name);
                    intent.putExtra(DOORSUPPORTTYPE, doorSupportTypeEntity.getContent());
                    intent.putExtra(IDENTITY_ID, identifyId);
                    intent.putExtra(IDENTITY_NAME, identifyName);
                    startActivityForResult(intent, 100);
                } catch (Exception e) {

                }

                break;
        }
    }

}