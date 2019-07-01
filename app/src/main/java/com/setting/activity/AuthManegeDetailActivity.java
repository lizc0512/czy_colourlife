package com.setting.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.user.entity.AuthManegeDetailEntity;
import com.user.model.NewUserModel;

import java.lang.ref.WeakReference;

import cn.net.cyberway.R;

/**
 * 授权详情
 * hxg 2019.06.14
 */
public class AuthManegeDetailActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String APP_ID = "app_id";

    private ImageView user_top_view_back;
    private TextView user_top_view_title;

    private ImageView iv_icon;
    private TextView tv_auth_name;
    private TextView tv_auth_status;
    private TextView tv_time;
    private TextView tv_valid_time;
    private TextView tv_content;
    private TextView tv_auth_unbind;

    private NewUserModel newUserModel;
    private String id;
    private UnbindAuthDialog unbindAuthDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_manage_detail);
        getWindow().setBackgroundDrawable(null);

        initView();
        initData();
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        iv_icon = findViewById(R.id.iv_icon);
        tv_auth_name = findViewById(R.id.tv_auth_name);
        tv_auth_status = findViewById(R.id.tv_auth_status);
        tv_time = findViewById(R.id.tv_time);
        tv_valid_time = findViewById(R.id.tv_valid_time);
        tv_content = findViewById(R.id.tv_content);
        tv_auth_unbind = findViewById(R.id.tv_auth_unbind);

        user_top_view_back.setOnClickListener(this);
        tv_auth_unbind.setOnClickListener(this);
        unbindAuthDialog = new UnbindAuthDialog(this);
    }

    private void initData() {
        user_top_view_title.setText("授权详情");
        id = getIntent().getStringExtra(APP_ID);
        newUserModel = new NewUserModel(this);
        newUserModel.getAuthDetail(0, id, AuthManegeDetailActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_auth_unbind:
                unbindAuthDialog.show();
                unbindAuthDialog.left_button.setOnClickListener(v1 -> unbindAuthDialog.dismiss());
                unbindAuthDialog.right_button.setOnClickListener(v2 -> {
                    unbindAuthDialog.dismiss();
                    unbind();
                });
                break;
        }
    }

    private void unbind() {
        newUserModel.unbindAuth(1, id, AuthManegeDetailActivity.this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    AuthManegeDetailEntity entity = GsonUtils.gsonToBean(result, AuthManegeDetailEntity.class);
                    AuthManegeDetailEntity.ContentBean bean = entity.getContent();
                    GlideImageLoader.loadImageDefaultDisplay(this, bean.getIcon(), iv_icon, R.drawable.default_image, R.drawable.default_image);
                    tv_auth_name.setText(bean.getName());
                    tv_auth_status.setText(1 == bean.getAuth_state() ? "已授权" : "已过期");
                    tv_time.setText("授权时间：" + bean.getAuth_time());
                    tv_valid_time.setText("有效期至：" + ("-1".equals(bean.getExpires_time()) ? "永久" : bean.getExpires_time()));
                    tv_content.setText(bean.getAuth_content());
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    ToastUtil.toastShow(getApplicationContext(), "解约成功");
                    mHandler.sendEmptyMessageDelayed(0, 600);
                }
                break;
        }
    }

    private AuthManegeDetailActivity.InterHandler mHandler = new AuthManegeDetailActivity.InterHandler(this);

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private static class InterHandler extends Handler {
        private WeakReference<AuthManegeDetailActivity> mActivity;

        InterHandler(AuthManegeDetailActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            AuthManegeDetailActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == 0) {
                    activity.setResult(200);
                    activity.finish();
                }
            } else {
                super.handleMessage(msg);
            }
        }
    }
}
