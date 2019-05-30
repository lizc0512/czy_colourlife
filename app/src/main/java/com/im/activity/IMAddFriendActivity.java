package com.im.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.NetworkUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.im.entity.MobileInforEntity;
import com.im.helper.CacheMobileInforHelper;
import com.scanCode.activity.CaptureActivity;
import com.setting.activity.EditDialog;
import com.setting.activity.SettingActivity;
import com.setting.activity.UserConcealActivity;
import com.user.UserAppConst;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.home.view.LeadGestureDialog;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/15 9:55
 * @change
 * @chang time
 * @class describe   添加好友方式的页面
 */

public class IMAddFriendActivity extends BaseActivity implements View.OnClickListener {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private RelativeLayout search_layout;
    private RelativeLayout phone_layout;
    private RelativeLayout community_layout;
    private RelativeLayout person_layout;
    private RelativeLayout scanner_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initView();
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        search_layout = findViewById(R.id.search_layout);
        phone_layout = findViewById(R.id.phone_layout);
        community_layout = findViewById(R.id.community_layout);
        person_layout = findViewById(R.id.person_layout);
        scanner_layout = findViewById(R.id.scanner_layout);
        user_top_view_back.setOnClickListener(this);
        search_layout.setOnClickListener(this);
        phone_layout.setOnClickListener(this);
        community_layout.setOnClickListener(this);
        person_layout.setOnClickListener(this);
        scanner_layout.setOnClickListener(this);
        user_top_view_title.setText(getResources().getString(R.string.title_add_friend));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.search_layout:
                Intent searchIntent = new Intent(IMAddFriendActivity.this, IMSearchConditionActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.phone_layout:
                List<MobileInforEntity> listBeanList = CacheMobileInforHelper.instance().toQueryMobileList(IMAddFriendActivity.this);
                if (null == listBeanList || listBeanList.size() == 0) {
                    Intent uploadIntent = new Intent(IMAddFriendActivity.this, IMUploadPhoneBook.class);
                    startActivity(uploadIntent);
                } else {
                    Intent phoneIntent = new Intent(IMAddFriendActivity.this, IMMobileBookActivity.class);
                    phoneIntent.putExtra(IMMobileBookActivity.FRISTLOAD, false);
                    startActivity(phoneIntent);
                }
                break;
            case R.id.community_layout:
                Intent communityIntent = new Intent(IMAddFriendActivity.this, IMNearCommunityPersonActivity.class);
                communityIntent.putExtra("type", 1);
                startActivity(communityIntent);
                break;
            case R.id.person_layout:
                String permissionOpen = shared.getString(UserAppConst.Colour_Permit_position, "");
                if ("1".equals(permissionOpen)) {
                    Intent personIntent = new Intent(IMAddFriendActivity.this, IMNearCommunityPersonActivity.class);
                    personIntent.putExtra("type", 2);
                    startActivity(personIntent);
                } else {  //给出提示框 让用户去开启隐私
                    showOpenPermissionDialog();
                }
                break;
            case R.id.scanner_layout:
                Intent intent = new Intent(IMAddFriendActivity.this, CaptureActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void showOpenPermissionDialog() {
        final LeadGestureDialog mDialog = new LeadGestureDialog(IMAddFriendActivity.this, R.style.custom_dialog_theme);
        mDialog.show();
        mDialog.setContent(getResources().getString(R.string.instant_open_conceal));
        mDialog.setTitle(getResources().getString(R.string.instant_notice));
        mDialog.btn_cancel.setTextColor(Color.parseColor("#c7c7c7"));
        mDialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Intent intent = new Intent(IMAddFriendActivity.this, UserConcealActivity.class);
                intent.putExtra(UserConcealActivity.OPENNEARPERSON, 1);
                startActivity(intent);
            }
        });
    }
}
