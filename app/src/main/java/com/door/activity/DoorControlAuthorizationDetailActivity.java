package com.door.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.door.entity.DoorAuthorListEntity;
import com.door.model.DoorAuthorModel;

import cn.net.cyberway.R;


public class DoorControlAuthorizationDetailActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    // 跳转到再次授权
    private final static int INTENT_TO_ACTION_AUTHORIZATION_APPROVE_PASS = 1;
    private FrameLayout czyTitleLayout;
    private ImageView user_top_view_back;//返回
    private TextView user_top_view_title;//title

    private TextView txt_stutas;//状态
    private TextView txt_autor_time;//授权时间
    private TextView txt_apply_time;//申请时间
    private TextView txt_memo;//备注
    private Button btn_autor;//取消或者再次授权
    private DoorAuthorListEntity.ContentBean.ListBean authorizationListResp;

    private DoorAuthorModel authorModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_autor_detail);
        prepareView();
        getIntentData();
        prepareData();
    }

    private void prepareView() {
        czyTitleLayout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_back.setOnClickListener(this);

        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        user_top_view_title.setText(getResources().getString(R.string.title_door_authorize_details));

        txt_stutas = (TextView) findViewById(R.id.txt_stutas);
        txt_autor_time = (TextView) findViewById(R.id.txt_autor_time);
        txt_apply_time = (TextView) findViewById(R.id.txt_apply_time);
        txt_memo = (TextView) findViewById(R.id.txt_memo);

        btn_autor = (Button) findViewById(R.id.btn_autor);
        btn_autor.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czyTitleLayout, user_top_view_back, user_top_view_title);
    }

    /**
     * 获取Intent传过来的数据
     */
    private void getIntentData() {

        Intent intent = getIntent();
        authorizationListResp = (DoorAuthorListEntity.ContentBean.ListBean) intent
                .getSerializableExtra("authorizationListResp");
    }

    private String idDeleted;

    private void prepareData() {

        if (authorizationListResp != null) {

            idDeleted = authorizationListResp.getIsdeleted();
            // 小区
            if ("0".equals(idDeleted)) {
                txt_stutas.setTextColor(getResources()
                        .getColor(R.color.lightggray_color));
                txt_stutas.setText(getResources().getString(R.string.door_apply_pass));
                btn_autor.setText(getResources().getString(R.string.door_author_cancel));
            } else if ("1".equals(idDeleted)) {
                txt_stutas.setText(getResources().getString(R.string.door_apply_timeout));
                txt_stutas.setTextColor(getResources()
                        .getColor(R.color.edit_text_color));
                btn_autor.setText(getResources().getString(R.string.door_again_author));
            }

            // 授权类型
            String type = authorizationListResp.getUsertype();
            if ("1".equals(type)) {
                txt_autor_time.setText(getResources().getString(R.string.door_author_forver));
            } else if ("2".equals(type)) {
                txt_autor_time.setText(7 + getResources().getString(R.string.door_author_day));
            } else if ("3".equals(type)) {
                txt_autor_time.setText(1 + getResources().getString(R.string.door_author_day));
            } else if ("4".equals(type)) {
                txt_autor_time.setText(2 + getResources().getString(R.string.door_author_hour));
            } else if ("5".equals(type)) {
                txt_autor_time.setText(1 + "年");
            }

            // 授权时间
            txt_apply_time.setText(TimeUtil.getDateToString(authorizationListResp.getCreationtime()));
            txt_memo.setText(authorizationListResp.getMemo());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_autor:
                if (authorizationListResp != null) {
                    if ("0".equals(idDeleted)) {
                        cancelAutor();
                    } else if ("1".equals(idDeleted)) {
                        Intent intent = new Intent(
                                DoorControlAuthorizationDetailActivity.this,
                                DoorAuthorizationApproveActivity.class);
                        intent.putExtra("authorListResp",
                                authorizationListResp);
                        intent.putExtra("refuse", false);
                        startActivityForResult(intent,
                                INTENT_TO_ACTION_AUTHORIZATION_APPROVE_PASS);
                    }
                }
                break;
        }
    }

    //取消授权
    private void cancelAutor() {
        if (authorModel == null) {
            authorModel = new DoorAuthorModel(this);
        }
        String aid = authorizationListResp.getId();
        authorModel.cancelAutor(0, aid, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case INTENT_TO_ACTION_AUTHORIZATION_APPROVE_PASS:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK);
                    finish();
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        if (what == 0) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
