package com.door.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.door.entity.DoorApplyListEntity;
import com.door.model.DoorApplyModel;

import cn.net.cyberway.R;


public class DoorApplyDetailActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    // 当前授权信息
    private DoorApplyListEntity.ContentBean.ListBean authorizationListResp;
    private FrameLayout czyTitleLayout;
    private ImageView user_top_view_back;//返回
    private TextView user_top_view_title;//title

    private TextView txt_detail;//申请详情或者授权详情
    private TextView txt_stutas;//状态
    private TextView txt_mobile;//电话号码
    private TextView txt_memo;//备注

    private Button btn_apply;//再次申请
    private DoorApplyModel applyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_apply_detail);
        prepareView();
        getIntentData();
        prepareDate();
    }

    /**
     * 获取Intent传过来的数据
     */
    private void getIntentData() {
        Intent intent = getIntent();
        authorizationListResp = (DoorApplyListEntity.ContentBean.ListBean) intent
                .getSerializableExtra("applyListResp");
    }

    private void prepareView() {
        czyTitleLayout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_back.setOnClickListener(this);

        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        txt_detail = (TextView) findViewById(R.id.txt_detail);

        txt_stutas = (TextView) findViewById(R.id.txt_stutas);
        txt_mobile = (TextView) findViewById(R.id.txt_mobile);
        txt_memo = (TextView) findViewById(R.id.txt_memo);

        btn_apply = (Button) findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czyTitleLayout, user_top_view_back, user_top_view_title);
    }

    /**
     * 赋值
     */
    private void prepareDate() {

        user_top_view_title.setText(getResources().getString(R.string.title_door_apply_details));
        txt_detail.setText(getResources().getString(R.string.title_door_apply_details));

        String type = authorizationListResp.getType();
        String isdelete = authorizationListResp.getIsdeleted();
        String statuString = "";

        if ("1".equals(type)) {

            // 默认 未批复
            if ("1".equals(isdelete)) {
                txt_stutas.setTextColor(getResources()
                        .getColor(R.color.edit_text_color));
                statuString = getResources().getString(R.string.door_apply_refuse);
                btn_apply.setVisibility(View.VISIBLE);
            } else {
                // 未批复
                txt_stutas.setTextColor(getResources()
                        .getColor(R.color.lightgred_color));
                statuString = getResources().getString(R.string.door_apply_wait);
                btn_apply.setVisibility(View.GONE);
            }
        } else if ("2".equals(type)) {

            if ("1".equals(isdelete)) {
                txt_stutas.setTextColor(getResources()
                        .getColor(R.color.edit_text_color));
                statuString = getResources().getString(R.string.door_apply_timeout);
                btn_apply.setVisibility(View.VISIBLE);
            } else if ("2".equals(type)) {
                // 已批复
                String auType = authorizationListResp.getUsertype();
                // 绿色
                txt_stutas.setTextColor(getResources()
                        .getColor(R.color.lightggray_color));
                btn_apply.setVisibility(View.GONE);
                statuString = getResources().getString(R.string.door_apply_pass);
            }
        }
        txt_stutas.setText(statuString);
        txt_mobile.setText(authorizationListResp.getMobile());
        String memo = authorizationListResp.getMemo();
        if (!TextUtils.isEmpty(memo) && !memo.equalsIgnoreCase("null")) {
            txt_memo.setText(memo);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_apply:
                if (authorizationListResp != null) {
                    aginApply();
                }
                break;
        }
    }


    private void aginApply() {
        applyModel = new DoorApplyModel(this);

        String cid = authorizationListResp.getCid();
        if (cid == null || cid.length() == 0 || cid.equalsIgnoreCase("0")) {
            cid = authorizationListResp.getFromid();
        }
        String memo = authorizationListResp.getMemo();
        if (TextUtils.isEmpty(cid)) {
            cid = "";
        }
        if (TextUtils.isEmpty(memo) || "null".equalsIgnoreCase(memo)) {
            memo = "";
        }
        applyModel.setApplyByCid(0, cid, memo, this);
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}