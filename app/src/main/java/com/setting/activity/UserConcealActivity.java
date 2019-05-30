package com.setting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.im.activity.IMAddFriendActivity;
import com.im.activity.IMNearCommunityPersonActivity;
import com.im.model.IMCommunityModel;
import com.setting.switchButton.SwitchButton;
import com.user.UserAppConst;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.setting.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/13 10:23
 * @change
 * @chang time
 * @class describe
 */

public class UserConcealActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private SwitchButton sb_position;
    private IMCommunityModel imCommunityModel;
    public static final  String OPENNEARPERSON="opennearperson";
    private int openNearPerson=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_conceal);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        sb_position = findViewById(R.id.sb_position);
        user_top_view_back.setOnClickListener(this);
        user_top_view_title.setText(getResources().getString(R.string.user_conceal));
        imCommunityModel = new IMCommunityModel(this);
        String permissionOpen = shared.getString(UserAppConst.Colour_Permit_position, "");
        openNearPerson=getIntent().getIntExtra(OPENNEARPERSON,0);
        if ("1".equals(permissionOpen)) {
            sb_position.setOpened(true);
        } else {
            sb_position.setOpened(false);
        }
        sb_position.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                sb_position.setOpened(true);
                editor.putString(UserAppConst.Colour_Permit_position, "1").apply();
                imCommunityModel.setPermitPosition(0, "1", UserConcealActivity.this);
                if (openNearPerson==1){
                    Intent personIntent = new Intent(UserConcealActivity.this, IMNearCommunityPersonActivity.class);
                    personIntent.putExtra("type", 2);
                    startActivity(personIntent);
                    finish();
                }
            }

            @Override
            public void toggleToOff(View view) {
                sb_position.setOpened(false);
                editor.putString(UserAppConst.Colour_Permit_position, "2").apply();
                imCommunityModel.setPermitPosition(0, "2", UserConcealActivity.this);
            }
        });
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

    }
}
