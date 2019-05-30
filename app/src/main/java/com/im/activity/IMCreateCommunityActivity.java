package com.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.im.model.IMCommunityModel;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/15 9:55
 * @change
 * @chang time
 * @class describe   创建社群填写资料的页面
 */

public class IMCreateCommunityActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_top_view_right;
    private EditText ed_community_name;
    private EditText ed_community_households;
    private EditText ed_community_area;
    private EditText ed_group_name;
    private EditText ed_mobile;
    public static final String COMMUNITYTYPE = "communitytype";
    public static final String COMMUNITYID = "communityid";
    public static final String IMID = "imid";
    public static final String OWNER = "owner";
    public static final String AREA = "area";
    public static final String MOBILE = "mobile";
    public static final String TOTALNAME = "totalname";
    public static final String GROUPNAME = "groupname";
    private String imId;
    private int communityId;
    private int communityType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_community);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_top_view_right = findViewById(R.id.user_top_view_right);
        ed_community_name = findViewById(R.id.ed_community_name);
        ed_community_households = findViewById(R.id.ed_community_households);
        ed_community_area = findViewById(R.id.ed_community_area);
        ed_group_name = findViewById(R.id.ed_group_name);
        ed_mobile = findViewById(R.id.ed_mobile);
        user_top_view_title.setText("创建社群");
        user_top_view_right.setText("确定");
        user_top_view_right.setTextColor(getResources().getColor(R.color.color_bbc0cb));
        user_top_view_back.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        Intent intent = getIntent();
        communityType = intent.getIntExtra(COMMUNITYTYPE, 0);
        if (communityType == 1) {
            imId = intent.getStringExtra(IMID);
            communityId = intent.getIntExtra(COMMUNITYID, 0);
            groupName = intent.getStringExtra(OWNER);
            communityArea = intent.getStringExtra(AREA);
            mobile = intent.getStringExtra(MOBILE);
            communityHouseholds = intent.getStringExtra(TOTALNAME);
            communityName = intent.getStringExtra(GROUPNAME);
            ed_community_name.setText(communityName);
            ed_community_households.setText(communityHouseholds);
            ed_community_area.setText(communityArea);
            ed_mobile.setText(mobile);
            ed_group_name.setText(groupName);
        }
        setButtonStatus();
        ed_community_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                communityName = s.toString().trim();
                setButtonStatus();
            }
        });
        ed_community_households.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                communityHouseholds = s.toString().trim();
                setButtonStatus();
            }
        });
        ed_community_area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                communityArea = s.toString().trim();
                setButtonStatus();
            }
        });
        ed_group_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                groupName = s.toString().trim();
                setButtonStatus();
            }
        });
        ed_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mobile = s.toString().trim();
                setButtonStatus();
            }
        });
    }

    private void setButtonStatus() {
        if (!TextUtils.isEmpty(communityName) && !TextUtils.isEmpty(communityHouseholds)
                && !TextUtils.isEmpty(communityArea) && !TextUtils.isEmpty(groupName)
                && !TextUtils.isEmpty(mobile) && mobile.length() == 11) {
            user_top_view_right.setTextColor(getResources().getColor(R.color.color_45adff));
            user_top_view_right.setEnabled(true);
        } else {
            user_top_view_right.setTextColor(getResources().getColor(R.color.color_bbc0cb));
            user_top_view_right.setEnabled(false);
        }
    }

    private String communityName;
    private String communityHouseholds;
    private String communityArea;
    private String groupName;
    private String mobile;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                IMCommunityModel imCommunityModel = new IMCommunityModel(IMCreateCommunityActivity.this);
                if (communityType == 1) {
                    imCommunityModel.repeatCommitApply(0, String.valueOf(communityId), "2", imId, communityName, communityHouseholds,
                            communityArea, groupName, mobile, true, this);
                } else {
                    imCommunityModel.createOrModifyCommunity(0, communityName, communityHouseholds, communityArea, groupName, mobile, this);
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    ToastUtil.toastShow(IMCreateCommunityActivity.this, "已提交");
                    Intent intent=new Intent(IMCreateCommunityActivity.this,IMCommunityListActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
