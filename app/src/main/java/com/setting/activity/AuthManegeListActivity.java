package com.setting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.setting.adapter.AuthManegeAdapter;
import com.user.entity.AuthManegeListEntity;
import com.user.model.NewUserModel;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 授权管理列表
 * hxg 2019.06.14
 */
public class AuthManegeListActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_auth;
    private LinearLayout ll_no_auth;
    private SwipeMenuRecyclerView smrv_auth;

    private NewUserModel newUserModel;
    private AuthManegeAdapter authManegeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_manage_list);
        getWindow().setBackgroundDrawable(null);

        initView();
        initData();
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        tv_auth = findViewById(R.id.tv_auth);
        ll_no_auth = findViewById(R.id.ll_no_auth);
        smrv_auth = findViewById(R.id.smrv_auth);
        user_top_view_back.setOnClickListener(this);

        smrv_auth.setLayoutManager(new LinearLayoutManager(this));
        smrv_auth.useDefaultLoadMore();
    }

    private void initData() {
        user_top_view_title.setText("授权管理");
        newUserModel = new NewUserModel(this);
        getList();
    }

    private void getList() {
        newUserModel.getAuthList(0, this);
    }

    public void clickItem(String id) {
        if (!TextUtils.isEmpty(id)) {
            Intent intent = new Intent(this, AuthManegeDetailActivity.class);
            intent.putExtra(AuthManegeDetailActivity.APP_ID, id);
            startActivityForResult(intent, 0);
        } else {
            ToastUtil.toastShow(getApplicationContext(), "找不到授权详情");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        AuthManegeListEntity authManegeListEntity = GsonUtils.gsonToBean(result, AuthManegeListEntity.class);
                        List<AuthManegeListEntity.ContentBean> list = authManegeListEntity.getContent();
                        if (0 == list.size()) {
                            ll_no_auth.setVisibility(View.VISIBLE);
                            tv_auth.setVisibility(View.GONE);
                            smrv_auth.setVisibility(View.GONE);
                        } else {
                            ll_no_auth.setVisibility(View.GONE);
                            tv_auth.setVisibility(View.VISIBLE);
                            smrv_auth.setVisibility(View.VISIBLE);
                            if (null == authManegeAdapter) {
                                authManegeAdapter = new AuthManegeAdapter(this, list);
                                smrv_auth.setAdapter(authManegeAdapter);
                            } else {
                                authManegeAdapter.notifyDataSetChanged();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ll_no_auth.setVisibility(View.VISIBLE);
                        tv_auth.setVisibility(View.GONE);
                        smrv_auth.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (200 == resultCode) {
            getList();
        }
    }

}
