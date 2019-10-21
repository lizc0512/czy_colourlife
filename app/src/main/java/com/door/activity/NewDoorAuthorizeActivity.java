package com.door.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.cashier.adapter.ViewPagerAdapter;
import com.door.adapter.DoorDateAdapter;
import com.door.entity.ApplyAuthorizeRecordEntity;
import com.door.entity.DoorCommunityEntity;
import com.door.entity.IdentityListEntity;
import com.door.fragment.ApplyRecordFragment;
import com.door.fragment.AuthorizeRecordFragment;
import com.door.model.NewDoorAuthorModel;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.csh.colourful.life.view.pickview.OptionsPickerView;
import cn.net.cyberway.R;
/*
 * 新的授权和授权记录
 *
 * */

public class NewDoorAuthorizeActivity extends BaseFragmentActivity implements View.OnClickListener, NewHttpResponse, TextWatcher {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private AppBarLayout appbar;
    private LinearLayout choice_room_layout;
    private TextView tv_community_name;
    private ImageView iv_more_community;
    private RecyclerView rv_authorize_time;
    private ClearEditText ed_real_name;
    private ClearEditText ed_authorize_phone;
    private LinearLayout choice_identify_layout;
    private TextView tv_identify;
    private Button btn_define_authorize;
    private TabLayout record_tabs;
    private ViewPager record_viewpager;
    private String[] tabTitleArray = null;
    private List<Fragment> fragmentList = new ArrayList<>();
    private NewDoorAuthorModel newDoorAuthorModel;
    private List<DoorCommunityEntity.ContentBean.CommunitylistBean> communityList = new ArrayList<>();
    private List<IdentityListEntity.ContentBean> identityList = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();
    private int choicePos = 0;
    private String authorName;
    private String authorPhone;
    private String autype;
    private String granttype;
    private long starttime;
    private long stoptime;
    private String community_uuid;
    private String community_name;
    private String bid;
    private String user_type;

    private ApplyRecordFragment applyRecordFragment;
    private AuthorizeRecordFragment authorizeRecordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_authorization_list);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        appbar = findViewById(R.id.appbar);
        choice_room_layout = findViewById(R.id.choice_room_layout);
        record_tabs = findViewById(R.id.record_tabs);
        tv_community_name = findViewById(R.id.tv_community_name);
        iv_more_community = findViewById(R.id.iv_more_community);
        rv_authorize_time = findViewById(R.id.rv_authorize_time);
        ed_real_name = findViewById(R.id.ed_real_name);
        ed_authorize_phone = findViewById(R.id.ed_authorize_phone);
        choice_identify_layout = findViewById(R.id.choice_identify_layout);
        tv_identify = findViewById(R.id.tv_identify);
        btn_define_authorize = findViewById(R.id.btn_define_authorize);
        record_viewpager = findViewById(R.id.record_viewpager);
        user_top_view_back.setOnClickListener(this);
        choice_room_layout.setOnClickListener(this);
        choice_identify_layout.setOnClickListener(this);
        btn_define_authorize.setOnClickListener(this);
        ed_real_name.addTextChangedListener(this);
        ed_authorize_phone.addTextChangedListener(this);
        user_top_view_title.setText("授权");
        appbar.post(new Runnable() {
            @Override
            public void run() {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
                behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                    @Override
                    public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                        return true;
                    }
                });
            }
        });
        dateList.add("7天");
        dateList.add("1个月");
        dateList.add("6个月");
        dateList.add("1年");
        dateList.add("永久");
        starttime = System.currentTimeMillis() / 1000;
        stoptime = starttime + 3600 * 24 * 7;
        autype = "1";
        granttype = "0";
        applyRecordFragment = new ApplyRecordFragment();
        fragmentList.add(applyRecordFragment);
        authorizeRecordFragment = new AuthorizeRecordFragment();
        fragmentList.add(authorizeRecordFragment);
        tabTitleArray = getResources().getStringArray(R.array.door_authorize_menu);
        for (int i = 0; i < tabTitleArray.length; i++) {
            record_tabs.addTab(record_tabs.newTab().setText(tabTitleArray[i]));
        }
        record_tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        record_tabs.setSelectedTabIndicatorHeight(4);
        record_tabs.setTabIndicatorFullWidth(false);
        record_tabs.setSelectedTabIndicatorColor(Color.parseColor("#3385FF"));
        record_tabs.setTabTextColors(Color.parseColor("#25282E"), Color.parseColor("#3385FF"));
        record_tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(), this, fragmentList, tabTitleArray);
        record_viewpager.setAdapter(adapter);
        record_viewpager.setOffscreenPageLimit(fragmentList.size());
        record_tabs.setupWithViewPager(record_viewpager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(NewDoorAuthorizeActivity.this, 5);
        rv_authorize_time.setLayoutManager(gridLayoutManager);
        DoorDateAdapter doorDateAdapter = new DoorDateAdapter(dateList);
        doorDateAdapter.setChoicePos(0);
        rv_authorize_time.setAdapter(doorDateAdapter);
        newDoorAuthorModel = new NewDoorAuthorModel(NewDoorAuthorizeActivity.this);
        newDoorAuthorModel.getAuthorCommunityList(0, NewDoorAuthorizeActivity.this);
        newDoorAuthorModel.getIdentifyList(1, false, NewDoorAuthorizeActivity.this);
        newDoorAuthorModel.getAuthorizeAndApplyList(2, false, NewDoorAuthorizeActivity.this);
        doorDateAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                choicePos = i;
                doorDateAdapter.setChoicePos(i);
                setAuthorizeBtn();
                switch (choicePos) {
                    case 1:
                        autype = "1";
                        granttype = "0";
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = starttime + 3600 * 24 * 30;
                        break;
                    case 2:
                        autype = "1";
                        granttype = "0";
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = starttime + 3600 * 24 * 30 * 6;
                        break;
                    case 3:
                        autype = "1";
                        granttype = "0";
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = starttime + 3600 * 24 * 365;
                        break;
                    case 4:
                        granttype = "1";
                        autype = "2";
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = 0;
                        break;
                    default:
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = starttime + 3600 * 24 * 7;
                        autype = "1";
                        granttype = "0";
                        break;
                }
            }
        });
    }


    private void setAuthorizeBtn() {
        authorName = ed_real_name.getText().toString().trim();
        authorPhone = ed_authorize_phone.getText().toString().trim();
        if (TextUtils.isEmpty(community_uuid) || choicePos == -1 || TextUtils.isEmpty(user_type) || TextUtils.isEmpty(authorName) || TextUtils.isEmpty(authorPhone) || 11 != authorPhone.length()) {
            btn_define_authorize.setEnabled(false);
            btn_define_authorize.setBackgroundResource(R.drawable.onekey_login_default_bg);
        } else {
            btn_define_authorize.setEnabled(true);
            btn_define_authorize.setBackgroundResource(R.drawable.onekey_login_bg);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.choice_identify_layout:
                KeyBoardUtils.hideSoftKeyboard(NewDoorAuthorizeActivity.this);
                if (identityList.size() > 0) {
                    showPickerView(identityList, "选择身份");
                }
                break;
            case R.id.choice_room_layout:
                KeyBoardUtils.hideSoftKeyboard(NewDoorAuthorizeActivity.this);
                if (communityList.size() > 1) {
                    showPickerView(communityList, "授权小区");
                }
                break;
            case R.id.btn_define_authorize:
                newDoorAuthorModel.setAuthorizeByMobile(3, community_uuid, bid, user_type, autype, granttype, starttime, stoptime, authorPhone,
                        authorName, "", NewDoorAuthorizeActivity.this);
                break;
        }
    }

    private void showPickerView(List list, String title) {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                Object object = list.get(options1);
                if (object instanceof DoorCommunityEntity.ContentBean.CommunitylistBean) {
                    DoorCommunityEntity.ContentBean.CommunitylistBean communitylistBean = (DoorCommunityEntity.ContentBean.CommunitylistBean) object;
                    community_name = communitylistBean.getName();
                    community_uuid = communitylistBean.getUuid();
                    bid = communitylistBean.getBid();
                    tv_community_name.setText(community_name);
                    setAuthorizeBtn();
                } else if (object instanceof IdentityListEntity.ContentBean) {
                    IdentityListEntity.ContentBean contentBean = (IdentityListEntity.ContentBean) object;
                    user_type = contentBean.getId();
                    tv_identify.setText(contentBean.getName());
                    setAuthorizeBtn();
                }
            }
        })
                .setTitleText(title)
                .setTitleColor(Color.parseColor("#81868F"))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.parseColor("#333333")) //设置选中项文字颜色
                .setCancelColor(Color.parseColor("#81868F"))
                .setSubmitColor(Color.parseColor("#0567FA"))
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(list);//一级选择器
        pvOptions.show();
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    communityList.clear();
                    DoorCommunityEntity doorCommunityEntity = GsonUtils.gsonToBean(result, DoorCommunityEntity.class);
                    communityList.addAll(doorCommunityEntity.getContent().getCommunitylist());
                    int size = communityList.size();
                    if (size > 0) {
                        DoorCommunityEntity.ContentBean.CommunitylistBean communitylistBean = communityList.get(0);
                        community_uuid = communitylistBean.getUuid();
                        community_name = communitylistBean.getName();
                        bid = communitylistBean.getBid();
                        tv_community_name.setText(community_name);
                        setAuthorizeBtn();
                    }
                    if (size == 1) {
                        choice_room_layout.setEnabled(false);
                        iv_more_community.setVisibility(View.INVISIBLE);
                    } else {
                        choice_room_layout.setEnabled(true);
                        iv_more_community.setVisibility(View.VISIBLE);
                    }
                    applyRecordFragment.setCommmunityData(result);
                    String applyAuthorRecordCache = shared.getString(UserAppConst.COLOUR_DOOR_AUTHOUR_APPLY, "");
                    if (!TextUtils.isEmpty(applyAuthorRecordCache)) {
                        setApplyAuthorData(applyAuthorRecordCache);
                    }
                } catch (Exception e) {

                }
                break;
            case 1:
                try {
                    IdentityListEntity identityListEntity = GsonUtils.gsonToBean(result, IdentityListEntity.class);
                    identityList.clear();
                    identityList.addAll(identityListEntity.getContent());
                } catch (Exception e) {

                }
                break;
            case 2:
                setApplyAuthorData(result);
                break;
            case 3:
                ToastUtil.toastShow(NewDoorAuthorizeActivity.this, "授权成功");
                newDoorAuthorModel.getAuthorizeAndApplyList(2, false, NewDoorAuthorizeActivity.this);
                break;
        }

    }

    private void setApplyAuthorData(String result) {
        try {
            ApplyAuthorizeRecordEntity applyAuthorizeRecordEntity = GsonUtils.gsonToBean(result, ApplyAuthorizeRecordEntity.class);
            ApplyAuthorizeRecordEntity.ContentBean contentBean = applyAuthorizeRecordEntity.getContent();
            List<ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean> applyListBeanList = contentBean.getApply_list();
            List<ApplyAuthorizeRecordEntity.ContentBean.AuthorizationListBean> authorizationList = contentBean.getAuthorization_list();
            authorizeRecordFragment.setAuthorData(authorizationList);
            applyRecordFragment.setApplyData(applyListBeanList);
        } catch (Exception e) {

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        setAuthorizeBtn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            newDoorAuthorModel.getAuthorizeAndApplyList(2, false, NewDoorAuthorizeActivity.this);
        }
    }
}
