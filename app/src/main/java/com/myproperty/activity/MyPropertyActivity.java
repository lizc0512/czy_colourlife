package com.myproperty.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.Util;
import com.customerInfo.activity.CustomerAddPropertyActivity;
import com.customerInfo.model.NewCustomerInfoModel;
import com.customerInfo.protocol.AddressListEntity;
import com.myproperty.adapter.MyPropertyAdapter;
import com.myproperty.adapter.MyPropertyAuthAdapter;
import com.myproperty.protocol.AddressAuthListEntity;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.net.cyberway.R;
import cn.net.cyberway.home.view.GuideView;
import cn.net.cyberway.utils.CityManager;

/**
 * 我的房产列表
 * Created by liusw on 2017/3/27.
 */
public class MyPropertyActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public static final String FROM_CUSTOMER_INFO = "from_customer_info";

    private ImageView mBack;
    private TextView mTitle;
    private ImageView img_right;
    private Button btnAddProperty;
    private RelativeLayout rl_content;
    private View v_guide;
    private TextView tv_enter;
    private GuideView guideView;

    private int page = 1;
    private NewCustomerInfoModel newCustomerInfoModel;
    private SwipeMenuRecyclerView address_rv;
    private List<AddressListEntity.ContentBean.DataBean> communityBeanList = new ArrayList<>();
    private MyPropertyAdapter mAdapter;
    private MyPropertyAuthAdapter mAuthAdapter;
    private String guide = "";
    private int totalRecord;
    private String defaultAddressId = "";
    private SharedPreferences mShared;
    public SharedPreferences.Editor mEditor;
    public int customer_id;
    public boolean fromCustomerInfo = false;
    private String id = "";//房产唯一id

    //认证列表
    private RelativeLayout rl_auth;
    private SwipeMenuRecyclerView address_auth_rv;
    private TextView tv_check_property;
    private List<AddressAuthListEntity.ContentBean> authBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_property);
        getWindow().setBackgroundDrawable(null);
        mShared = this.getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        newCustomerInfoModel = new NewCustomerInfoModel(this);
        Intent intent = getIntent();
        fromCustomerInfo = intent.getBooleanExtra(FROM_CUSTOMER_INFO, false);
        initView();
        initLoad();
    }

    /**
     * 1从房产进入
     */
    private void initLoad() {
        customer_id = mShared.getInt(UserAppConst.Colour_User_id, 0);
        guide = mShared.getString(UserAppConst.COLOR_HOME_GUIDE_STEP + customer_id, "");
        if (!"hide".equals(guide)) {
            mEditor.putString(UserAppConst.COLOR_HOME_GUIDE_STEP + customer_id, "property").commit();//我的房产
        }

        String auth = mShared.getString(UserAppConst.Colour_authentication, "2");//是否认证房产 1：是，2：否
        if ("1".equals(auth)) {
            initData();
        } else {
            newCustomerInfoModel.isDialog(3, this);//是否需要选择列表框
        }
    }

    private void initView() {
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        img_right = findViewById(R.id.img_right);//保留
        img_right.setImageResource(R.drawable.ic_question);
        img_right.setVisibility(View.VISIBLE);
        mTitle.setText(getResources().getString(R.string.title_property_list));
        mBack.setOnClickListener(this);
        img_right.setOnClickListener(this);

        rl_auth = findViewById(R.id.rl_auth);

        rl_content = findViewById(R.id.rl_content);
        v_guide = findViewById(R.id.v_guide);
        btnAddProperty = findViewById(R.id.btn_addproperty);
        btnAddProperty.setOnClickListener(this);

        address_rv = findViewById(R.id.address_rv);
        address_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MyPropertyAdapter(this, communityBeanList);
        address_rv.setAdapter(mAdapter);
        address_rv.useDefaultLoadMore();
        address_rv.setLoadMoreListener(() -> {
            page++;
            getCommunityList();
        });
    }

    /**
     * 房产认证
     */
    private void initAuth() {
        rl_content.setVisibility(View.VISIBLE);
        rl_auth.setVisibility(View.VISIBLE);
        tv_check_property = findViewById(R.id.tv_check_property);

        btnAddProperty.setText(getResources().getString(R.string.ssdk_oks_confirm));
        btnAddProperty.setBackgroundColor(getResources().getColor(R.color.color_d2d2d2));
        btnAddProperty.setOnClickListener(null);

        address_auth_rv = findViewById(R.id.address_auth_rv);
        address_auth_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        mAuthAdapter = new MyPropertyAuthAdapter(this, authBeanList);
        address_auth_rv.setAdapter(mAuthAdapter);
        address_auth_rv.useDefaultLoadMore();
        getCanAuthList();
    }

    /**
     * 点击选择
     */
    public void choose(int position, String select) {
        authBeanList.get(position).setSelect(select);

        int count = 0;
        for (AddressAuthListEntity.ContentBean bean : authBeanList) {
            if ("1".equals(bean.getSelect())) {
                count++;
            }
        }

        if (count == 0) {
            btnAddProperty.setOnClickListener(null);
            btnAddProperty.setBackgroundColor(getResources().getColor(R.color.color_d2d2d2));
            tv_check_property.setVisibility(View.GONE);
        } else if (count > 0 && count < 4) {
            btnAddProperty.setOnClickListener(this);
            btnAddProperty.setBackgroundColor(getResources().getColor(R.color.color_8eafea));
            tv_check_property.setVisibility(View.GONE);
        } else if (count > 3) {
            btnAddProperty.setOnClickListener(null);
            btnAddProperty.setBackgroundColor(getResources().getColor(R.color.color_d2d2d2));
            tv_check_property.setVisibility(View.VISIBLE);
        }
        mAuthAdapter.notifyDataSetChanged();
    }

    private void initData() {
        rl_auth.setVisibility(View.GONE);
        btnAddProperty.setText(getResources().getString(R.string.increase_property));

        rl_content.setVisibility(View.VISIBLE);
        String addressListCache = shared.getString(UserAppConst.ADDRESSLISTCACHE, "");
        if (!TextUtils.isEmpty(addressListCache)) {
            communityBeanList.addAll(GsonUtils.jsonToList(addressListCache, AddressListEntity.ContentBean.DataBean.class));
            for (AddressListEntity.ContentBean.DataBean communityBean : communityBeanList) {
                if (communityBean.getIs_default() == 1) {
                    defaultAddressId = communityBean.getId();
                    break;
                }
            }
            mAdapter.setDefaultAddressId(defaultAddressId);
            getCommunityList();
        } else {
            getCommunityList();
        }
    }

    /**
     * 遮罩引导
     */
    private void guideView() {
        v_guide.setVisibility(View.VISIBLE);
        v_guide.post(this::guide);
    }

    /**
     * 遮罩引导
     */
    private void guide() {
        View inflate = View.inflate(this, R.layout.view_property_guide, null);
        tv_enter = inflate.findViewById(R.id.tv_enter);
        tv_enter.setOnClickListener(this);
        if (null == guideView) {
            guideView = new GuideView.Builder(this)
                    .setTargetView(v_guide)
                    .setHintView(inflate)
                    .setHintViewDirection(GuideView.Direction.BOTTOM)
                    .setmForm(GuideView.Form.REACTANGLE)
                    .create();
        }
        if (!guideView.show()) {
            v_guide.setVisibility(View.GONE);
        }
    }

    /**
     * 用户房产列表
     */
    private void getCommunityList() {
        newCustomerInfoModel.getCustomerAddress(0, page, false, this);
    }

    /**
     * 可认证房产列表
     */
    private void getCanAuthList() {
        newCustomerInfoModel.getCanAuthList(4, this);
    }

    /**
     * 确定认证房产
     */
    private void submit() {
        try {
            String propertyList = "";
            for (AddressAuthListEntity.ContentBean bean : authBeanList) {
                if ("1".equals(bean.getSelect())) {
                    propertyList += bean.getId() + ",";
                }
            }
            propertyList = propertyList.substring(0, propertyList.length() - 1);
            newCustomerInfoModel.submitAuthList(5, propertyList, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击项
     */
    public void select(int position) {
        AddressListEntity.ContentBean.DataBean data = communityBeanList.get(position);
        Intent intent = new Intent();
        intent.setClass(MyPropertyActivity.this, PropertyDetailActivity.class);
        intent.putExtra(PropertyDetailActivity.ID, data.getId());
        intent.putExtra(PropertyDetailActivity.COMMUNITY_UUID, data.getCommunity_uuid());
        intent.putExtra(PropertyDetailActivity.COMMUNITY_NAME, data.getCommunity_name());
        intent.putExtra(PropertyDetailActivity.BUILD_UUID, data.getBuild_uuid());
        intent.putExtra(PropertyDetailActivity.BUILD_NAME, data.getBuild_name());
        intent.putExtra(PropertyDetailActivity.UNIT_UUID, data.getUnit_uuid());
        intent.putExtra(PropertyDetailActivity.UNIT_NAME, data.getUnit_name());
        intent.putExtra(PropertyDetailActivity.ROOM_UUID, data.getRoom_uuid());
        intent.putExtra(PropertyDetailActivity.ROOM_NAME, data.getRoom_name());
        intent.putExtra(PropertyDetailActivity.CITY, data.getCity_name());
        intent.putExtra(PropertyDetailActivity.DEFAULT, defaultAddressId.equals(data.getId()));
        intent.putExtra(PropertyDetailActivity.ADDRESS, data.getAddress());
        intent.putExtra(PropertyDetailActivity.AUTHENTICATION, data.getAuthentication());
        intent.putExtra(PropertyDetailActivity.IDENTITY_STATE_NAME, data.getIdentity_state_name());
        intent.putExtra(PropertyDetailActivity.IDENTITY_NAME, data.getIdentity_name());
        intent.putExtra(PropertyDetailActivity.EMPLOYEE, data.getEmployee());

        startActivityForResult(intent, 1);
    }

    private void refreshData() {
        if (newCustomerInfoModel == null) {
            newCustomerInfoModel = new NewCustomerInfoModel(this);
        }
        getCommunityList();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.user_top_view_back:
                if (!"hide".equals(guide)) {
                    setResult(1);
                }
                finish();
                break;
            case R.id.img_right:
                intent = new Intent();
                intent.setClass(MyPropertyActivity.this, PropertyQuestionActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_addproperty:
                if (getResources().getString(R.string.user_define).equals(btnAddProperty.getText().toString().trim())) {
                    submit();
                } else {
                    intent = new Intent();
                    intent.setClass(MyPropertyActivity.this, CustomerAddPropertyActivity.class);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.tv_enter:
                guide = "hide";
                guideView.hide();
                guideView = null;
                v_guide.setVisibility(View.GONE);
                mEditor.putString(UserAppConst.COLOR_HOME_GUIDE_STEP + customer_id, "hide").commit();//隐藏
                AddressListEntity.ContentBean.DataBean data = communityBeanList.get(0);
                id = data.getId();
                intent = new Intent(this, PropertyChangeActivity.class);
                intent.putExtra(PropertyChangeActivity.CANT_BACK, true);//不可点击返回
                startActivityForResult(intent, 2);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //新增页返回时定位
        if (Util.isGps(Objects.requireNonNull(this))) {
            CityManager.getInstance(this).initLocation();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    if (fromCustomerInfo) {
                        refreshData();
                    } else {
                        finish();
                    }
                }
                break;
            case 2:
                if (resultCode == 1) {
                    try {
                        String identity = data.getStringExtra(PropertyChangeActivity.IDENTITY_TYPE);
                        newCustomerInfoModel.changeIdentity(6, identity, id, this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0://获取列表
                if (!TextUtils.isEmpty(result)) {
                    try {
                        AddressListEntity addressListEntity = GsonUtils.gsonToBean(result, AddressListEntity.class);
                        AddressListEntity.ContentBean contentBean = addressListEntity.getContent();
                        AddressListEntity.ContentBean.PagingBean pagingBean = null;
                        if (page == 1) {
                            communityBeanList.clear();
                        }
                        List<AddressListEntity.ContentBean.DataBean> addDataList = contentBean.getData();
                        if (null != contentBean) {
                            addDataList = contentBean.getData();
                            communityBeanList.addAll(addDataList);
                            pagingBean = contentBean.getPaging();
                            totalRecord = pagingBean.getTotal_record();
                        }
                        if (communityBeanList.size() > 0) {
                            String addressCache = GsonUtils.gsonString(communityBeanList);
                            editor.putString(UserAppConst.ADDRESSLISTCACHE, addressCache).apply();
                        }
                        for (AddressListEntity.ContentBean.DataBean communityBean : communityBeanList) {
                            if (communityBean.getIs_default() == 1) {
                                defaultAddressId = communityBean.getId();
                                if (fromCustomerInfo) {
                                    Intent intent = new Intent();
                                    intent.putExtra("community", communityBean.getCommunity_name());
                                    setResult(1, intent);
                                }
                                break;
                            }
                        }
                        mAdapter.setDefaultAddressId(defaultAddressId);
                        boolean dataEmpty = addDataList == null || addDataList.size() == 0;
                        boolean hasMore = totalRecord > communityBeanList.size();
                        address_rv.loadMoreFinish(dataEmpty, hasMore);

                        if (!"hide".equals(guide)) {
                            if (communityBeanList.size() > 0) {
                                if (!"0".equals(communityBeanList.get(0).getIdentity_id()) || "1".equals(communityBeanList.get(0).getEmployee()) || 1 != communityBeanList.get(0).getIs_default()) {
                                    mEditor.putString(UserAppConst.COLOR_HOME_GUIDE_STEP + customer_id, "hide").commit();
                                } else {
                                    v_guide.setVisibility(View.VISIBLE);
                                    mHandler.sendEmptyMessageDelayed(0, 200);
                                }
                            } else {
                                mEditor.putString(UserAppConst.COLOR_HOME_GUIDE_STEP + customer_id, "hide").commit();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3: //是否需要选择列表框
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String content = jsonObject.getString("content");
                        JSONObject data = new JSONObject(content);
                        String isDialog = data.getString("is_pop");
                        if ("1".equals(isDialog)) {//是否弹过 1：是，2：否
                            initData();
                        } else {
                            initAuth();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 4: //可认证房产列表
                if (!TextUtils.isEmpty(result)) {
                    AddressAuthListEntity entity = GsonUtils.gsonToBean(result, AddressAuthListEntity.class);
                    List<AddressAuthListEntity.ContentBean> list = entity.getContent();
                    for (AddressAuthListEntity.ContentBean bean : list) {
                        bean.setSelect("0");//设置默认没选
                        authBeanList.add(bean);
                    }
                    mAuthAdapter.notifyDataSetChanged();
                }
                break;
            case 5: //提交验证
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String content = jsonObject.getString("content");
                        if (!TextUtils.isEmpty(content)) {//content:2  2个已认证的房产数
                            ToastUtil.toastShow(this, getResources().getString(R.string.property_verify_success));
                            initData();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 6: //修改身份
                if (!TextUtils.isEmpty(result)) {
                    ToastUtil.toastShow(this, "身份修改成功");
                    refreshData();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!"hide".equals(guide)) {
            setResult(1);
        }
        super.onBackPressed();
    }

    private MyPropertyActivity.InterHandler mHandler = new MyPropertyActivity.InterHandler(this);

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private static class InterHandler extends Handler {
        private WeakReference<MyPropertyActivity> mActivity;

        InterHandler(MyPropertyActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MyPropertyActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        activity.guideView();
                        break;
                }
            } else {
                super.handleMessage(msg);
            }
        }
    }

}