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
import com.external.eventbus.EventBus;
import com.invite.adapter.SideBar;
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

/**
 * 新增房产
 * hxg 2019/04/02
 */
public class CustomerAddPropertyActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public final static String ID = "id";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_address);
        getWindow().setBackgroundDrawable(null);//减少GPU绘制 布局要设为match_parent
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
                iv_clean.setVisibility(TextUtils.isEmpty(s.toString().trim()) ? View.GONE : View.VISIBLE);
                ll_address.setVisibility(TextUtils.isEmpty(s.toString().trim()) ? View.VISIBLE : View.GONE);
            }
        });

        et_search_area.setOnEditorActionListener((v, actionId, event) -> {
            String keyword = et_search_area.getText().toString().trim();
            if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(keyword)) {
                choiceType = 0;
                //模糊搜索所有小区
                newCustomerInfoModel.addressSelect(3, "", keyword, page, 400, CustomerAddPropertyActivity.this);
            }
            return false;
        });

        rv_area = findViewById(R.id.rv_area);
        rv_area.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        areaAdapter = new AreaPropertyAdapter(this, areaBeanList);
        rv_area.setAdapter(areaAdapter);
        rv_area.useDefaultLoadMore();

        rv_address = findViewById(R.id.rv_address);
        rv_address.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        addAdapter = new AddPropertyAdapter(this, addBeanList);
        rv_address.setAdapter(addAdapter);
        rv_address.useDefaultLoadMore();

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
            newCustomerInfoModel.addressSelect(6, city_name, "", page, 400, this);
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
                newCustomerInfoModel.getRoomData(9, unit_uuid, page, 400, true, CustomerAddPropertyActivity.this);
            } else if (!TextUtils.isEmpty(unit_name)) {//有单元
                newCustomerInfoModel.getUnitData(8, building_uuid, page, 400, true, CustomerAddPropertyActivity.this);
            } else if (!TextUtils.isEmpty(building_name)) {//有楼栋
                newCustomerInfoModel.getBuildingData(7, community_uuid, page, 400, true, CustomerAddPropertyActivity.this);
            } else {//只有小区，显示城市下的小区
                newCustomerInfoModel.addressSelect(6, city_name, "", page, 400, this);
            }
            identity = intent.getBooleanExtra(IDENTITY, false);//是否跳转选中身份
        } else {
            mtitle.setText(getResources().getString(R.string.title_increase_property));
            SharedPreferences mShared = getSharedPreferences(UserAppConst.USERINFO, 0);
            city_name = mShared.getString(CityCustomConst.LOCATION_CITY, "");
            if (!Util.isGps(Objects.requireNonNull(this)) || TextUtils.isEmpty(city_name)) {
                ToastUtil.toastShow(this, "未能获取当前定位");
            } else {
                tv_city.setText(city_name);
                newCustomerInfoModel.addressSelect(6, city_name, "", page, 400, this);
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
                    newCustomerInfoModel.addressSelect(6, city_name, addBeanList.get(position).getName(), page, 400, this);
                    break;
                case 1://请求楼栋
                    community_uuid = addBeanList.get(position).getUuid();
                    community_name = addBeanList.get(position).getName();
                    tv_garden.setText(community_name);
                    newCustomerInfoModel.getBuildingData(7, community_uuid, page, 400, true, CustomerAddPropertyActivity.this);
                    break;
                case 2:
                    building_uuid = addBeanList.get(position).getUuid();
                    building_name = addBeanList.get(position).getName();
                    tv_block.setText(building_name);
                    newCustomerInfoModel.getUnitData(8, building_uuid, page, 400, true, CustomerAddPropertyActivity.this);
                    break;
                case 3:
                    unit_uuid = addBeanList.get(position).getUuid();
                    unit_name = addBeanList.get(position).getName();
                    tv_dong.setText(unit_name);
                    newCustomerInfoModel.getRoomData(9, unit_uuid, page, 400, true, CustomerAddPropertyActivity.this);
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
            setBackground(1);
            rv_area.setVisibility(View.GONE);
            community_uuid = areaBeanList.get(position).getUuid();
            community_name = areaBeanList.get(position).getName();
            tv_garden.setText(community_name);
            newCustomerInfoModel.getBuildingData(7, community_uuid, page, 400, true, CustomerAddPropertyActivity.this);
        }
    }

    /**
     * 新增或更新
     */
    private void addOrUpdateAddress() {
        if ("新增房产".equals(mtitle.getText().toString().trim())) {
            newCustomerInfoModel.isNeedRealName(13, community_uuid, this);
        } else {//修改
            newCustomerInfoModel.postCustomerUpdateAddress(12, id, community_uuid, community_name, building_uuid, building_name
                    , unit_uuid, unit_name, room_uuid, room_name, this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String identity = data.getStringExtra(PropertyChangeActivity.IDENTITY_TYPE);
                    newCustomerInfoModel.postCustomerAddress(10, community_uuid, community_name, building_uuid, building_name
                            , unit_uuid, unit_name, room_uuid, room_name, identity, this);
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
                dismissSoftKeyboard(this);//隐藏软键盘
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
                tv_choose.setText("选择城市");
                iv_city.setVisibility(View.GONE);
                fl_city.setVisibility(View.VISIBLE);
                newCustomerInfoModel.cityListSelect(11, this);
                break;
            case R.id.iv_city://城市列表 按钮
                tv_choose.setText("选择城市");
                iv_city.setVisibility(View.GONE);
                fl_city.setVisibility(View.VISIBLE);
                newCustomerInfoModel.cityListSelect(11, this);
                break;
            case R.id.tv_garden://点击花园
                choiceType = 0;
                community_uuid = "";
                community_name = "";
                building_uuid = "";
                building_name = "";
                unit_uuid = "";
                unit_name = "";
                rl_city.setVisibility(View.VISIBLE);
                rl_address_choose.setVisibility(View.GONE);
                newCustomerInfoModel.addressSelect(6, city_name, "", page, 400, this);
                break;
            case R.id.tv_block:
                choiceType = 1;
                building_uuid = "";
                building_name = "";
                unit_uuid = "";
                unit_name = "";
                setBackground(2);
                newCustomerInfoModel.getBuildingData(7, community_uuid, page, 400, true, CustomerAddPropertyActivity.this);
                break;
            case R.id.tv_dong:
                choiceType = 2;
                unit_uuid = "";
                unit_name = "";
                setBackground(3);
                newCustomerInfoModel.getUnitData(8, building_uuid, page, 400, true, CustomerAddPropertyActivity.this);
                break;
            case R.id.tv_unit:
                choiceType = 3;
                setBackground(4);
                newCustomerInfoModel.getRoomData(9, unit_uuid, page, 400, true, CustomerAddPropertyActivity.this);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
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
                    setBackground(1);
                    rl_city.setVisibility(View.VISIBLE);
                    rl_address_choose.setVisibility(View.GONE);
                    choiceType = 1;
                    AddListEntity addListEntity = GsonUtils.gsonToBean(result, AddListEntity.class);
                    AddListEntity.ContentBean contentBean = addListEntity.getContent();
                    AddListEntity.ContentBean.PagingBean pagingBean;
                    addBeanList.clear();
                    List<AddListEntity.ContentBean.DataBean> addDataList;
                    addDataList = contentBean.getData();
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
                    AddListEntity addListEntity = GsonUtils.gsonToBean(result, AddListEntity.class);
                    AddListEntity.ContentBean contentBean = addListEntity.getContent();
                    AddListEntity.ContentBean.PagingBean pagingBean;
                    addBeanList.clear();
                    List<AddListEntity.ContentBean.DataBean> addDataList;
                    addDataList = contentBean.getData();
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
                    if (!hasMore && dataEmpty) {
                        addOrUpdateAddress();
                    }
                }
                break;
            case 8:
                if (!TextUtils.isEmpty(result)) {
                    setBackground(2);
                    choiceType = 3;
                    AddListEntity addListEntity = GsonUtils.gsonToBean(result, AddListEntity.class);
                    AddListEntity.ContentBean contentBean = addListEntity.getContent();
                    AddListEntity.ContentBean.PagingBean pagingBean;
                    addBeanList.clear();
                    List<AddListEntity.ContentBean.DataBean> addDataList;
                    addDataList = contentBean.getData();
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
                    if (!hasMore && dataEmpty) {
                        addOrUpdateAddress();
                    }
                }
                break;
            case 9:
                if (!TextUtils.isEmpty(result)) {
                    setBackground(3);
                    choiceType = -1;
                    AddListEntity addListEntity = GsonUtils.gsonToBean(result, AddListEntity.class);
                    AddListEntity.ContentBean contentBean = addListEntity.getContent();
                    AddListEntity.ContentBean.PagingBean pagingBean;
                    addBeanList.clear();
                    List<AddListEntity.ContentBean.DataBean> addDataList;
                    addDataList = contentBean.getData();
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
                    if (!hasMore && dataEmpty) {
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
                        int is_verify = data.getInt("is_verify");
                        if (1 == is_verify) {//1 需要，2 不需要
                            Intent intent = new Intent(this, PropertyRealNameActivity.class);
                            intent.putExtra(PropertyRealNameActivity.COMMUNITY_UUID, community_uuid);
                            intent.putExtra(PropertyRealNameActivity.BUILD_NAME, building_name);
                            intent.putExtra(PropertyRealNameActivity.UNIT_NAME, unit_name);
                            intent.putExtra(PropertyRealNameActivity.ROOM_NAME, room_name);
                            startActivityForResult(intent, 1);
                        } else {
                            Intent intent = new Intent(this, PropertyChangeActivity.class);
                            startActivityForResult(intent, 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

}