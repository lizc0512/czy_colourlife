package com.customerInfo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.adapter.SwitchAddressAdapter;
import com.customerInfo.model.NewCustomerInfoModel;
import com.customerInfo.protocol.AddressListEntity;
import com.external.eventbus.EventBus;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/2 15:28
 * @change
 * @chang time
 * @class describe
 */

public class CustomerSwitchAddressActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String ADDRESSNAME = "addressname"; //
    public static final String ADDRESSID = "addressid"; //
    public static final String COMMUNITYNAME = "communityname"; //
    public static final String BUILDNAME = "buildname"; //
    public static final String UNITNAME = "unitname"; //
    public static final String ROOMNAME = "roomname"; //
    private ImageView mback;
    private TextView mtitle;
    private TextView mSave;
    private Button btn_add_community;
    private SwipeMenuRecyclerView address_rv;
    private SwitchAddressAdapter switchAddressAdapter;
    private List<AddressListEntity.ContentBean.DataBean> communityBeanList = new ArrayList<>();
    private int page = 1;
    private NewCustomerInfoModel newCustomerInfoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_switch_address);
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mback = (ImageView) findViewById(R.id.user_top_view_back);
        mtitle = (TextView) findViewById(R.id.user_top_view_title);
        mSave = (TextView) findViewById(R.id.user_top_view_right);
        address_rv = (SwipeMenuRecyclerView) findViewById(R.id.address_rv);
        btn_add_community = (Button) findViewById(R.id.btn_add_community);
        mback.setOnClickListener(this);
        btn_add_community.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mtitle.setText(getResources().getString(R.string.title_choice_address));
        ThemeStyleHelper.onlyFrameTitileBar(CustomerSwitchAddressActivity.this, czy_title_layout, mback, mtitle);
        newCustomerInfoModel = new NewCustomerInfoModel(this);
        address_rv.setLayoutManager(new LinearLayoutManager(CustomerSwitchAddressActivity.this, LinearLayoutManager.VERTICAL, false));// 布局管理器。
        address_rv.setSwipeMenuCreator(swipeMenuCreator);
        address_rv.setSwipeMenuItemClickListener(mMenuItemClickListener);
        String addressListCache = shared.getString(UserAppConst.ADDRESSLISTCACHE, "");
        switchAddressAdapter = new SwitchAddressAdapter(CustomerSwitchAddressActivity.this, communityBeanList);
        address_rv.setAdapter(switchAddressAdapter);
        if (!TextUtils.isEmpty(addressListCache)) {
            communityBeanList.addAll(GsonUtils.jsonToList(addressListCache, AddressListEntity.ContentBean.DataBean.class));
            for (AddressListEntity.ContentBean.DataBean communityBean : communityBeanList) {
                if (communityBean.getIs_default() == 1) {
                    defaultAddressId = communityBean.getId();
                    break;
                }
            }
            switchAddressAdapter.setDefaultAddressId(defaultAddressId);
            getCommunityList(false);
        } else {
            getCommunityList(true);
        }
        address_rv.useDefaultLoadMore();
        address_rv.setLoadMoreListener(new SwipeMenuRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                getCommunityList(false);
            }
        });
    }

    private void getCommunityList(boolean isLoading) {
        newCustomerInfoModel.getCustomerAddress(0, page, isLoading, this);
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = (int) (90 * getResources().getDisplayMetrics().density + 0.5f);
            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(CustomerSwitchAddressActivity.this)
                    .setBackground(new ColorDrawable(Color.rgb(0xff, 0x00, 0x00)))
                    .setText(getResources().getString(R.string.message_del)) // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(18) // 文字大小。
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };

    /**
     * 菜单点击监听。
     */

    private AddressListEntity.ContentBean.DataBean operationCommunityBean;
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            operationCommunityBean = communityBeanList.get(adapterPosition);
            String currentId = operationCommunityBean.getId();
            switch (menuPosition) {
                case 0:
                    if (currentId.equals(defaultAddressId)) {
                        ToastUtil.toastShow(CustomerSwitchAddressActivity.this, "默认地址不能删除");
                    } else {
                        newCustomerInfoModel.deleteCustomerAddress(1, currentId, CustomerSwitchAddressActivity.this);
                    }
                    break;
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_add_community:
                Intent intent = new Intent(CustomerSwitchAddressActivity.this, CustomerAddCommunityActivity.class);
                startActivityForResult(intent, 1000);
                break;
            case R.id.user_top_view_right:
                defaultAddressId = switchAddressAdapter.getDefaultId();
                newCustomerInfoModel.customerAddressChange(2, defaultAddressId, CustomerSwitchAddressActivity.this);
                break;
        }
    }

    private int totalRecord;
    private String defaultAddressId = "";

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
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
                            mSave.setText("保存");
                        }
                        for (AddressListEntity.ContentBean.DataBean communityBean : communityBeanList) {
                            if (communityBean.getIs_default() == 1) {
                                defaultAddressId = communityBean.getId();
                                break;
                            }
                        }
                        switchAddressAdapter.setDefaultAddressId(defaultAddressId);
                        boolean dataEmpty = addDataList == null || addDataList.size() == 0;
                        boolean hasMore = totalRecord > communityBeanList.size();
                        address_rv.loadMoreFinish(dataEmpty, hasMore);
                    } catch (Exception e) {

                    }
                }
                break;
            case 1: //删除
                ToastUtil.toastShow(CustomerSwitchAddressActivity.this, "地址删除成功!");
                communityBeanList.remove(operationCommunityBean);
                switchAddressAdapter.notifyDataSetChanged();
                String addressCache = GsonUtils.gsonString(communityBeanList);
                editor.putString(UserAppConst.ADDRESSLISTCACHE, addressCache).apply();
                break;
            case 2: //设为默认
                ToastUtil.toastShow(CustomerSwitchAddressActivity.this, "设为默认地址成功!");
                for (AddressListEntity.ContentBean.DataBean communityBean : communityBeanList) {
                    if (defaultAddressId.equals(communityBean.getId())) {
                        operationCommunityBean = communityBean;
                        break;
                    }
                }
                String addressUUId = operationCommunityBean.getCommunity_uuid();
                String addressName = operationCommunityBean.getCommunity_name();
                editor.putString(UserAppConst.Colour_login_community_uuid, addressUUId);
                editor.putString(UserAppConst.Colour_login_community_name, addressName);
                editor.putString(UserAppConst.Colour_Build_name, operationCommunityBean.getBuild_name());
                editor.putString(UserAppConst.Colour_Room_name, operationCommunityBean.getRoom_name());
                editor.putString(UserAppConst.Colour_Unit_name, operationCommunityBean.getUnit_name());
                editor.putString(UserAppConst.Colour_authentication, operationCommunityBean.getAuthentication());
                editor.commit();
                Message msg = new Message();
                msg.what = UserMessageConstant.CHANGE_COMMUNITY;
                msg.obj = defaultAddressId;
                EventBus.getDefault().post(msg);
                Intent intent = new Intent();
                intent.putExtra("community", addressName);
                setResult(1, intent);
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 200) {
            finish();
        }
    }
}
