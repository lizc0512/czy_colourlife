package com.door.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.door.entity.ApplyAuthorizeRecordEntity;
import com.door.model.NewDoorAuthorModel;

import cn.net.cyberway.R;
/*
 * 授权通过或拒绝的页面
 *
 * */

public class NewDoorAuthorizePassActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_apply_name;
    private TextView tv_apply_identify;
    private TextView tv_apply_room;
    private TextView tv_apply_time;
    private TextView tv_apply_status;
    private TextView tv_apply_title;
    private TextView tv_apply_duration;
    private LinearLayout layout_pass_time;
    private TextView tv_pass_time;
    private Button btn_cancel_authorize;
    private ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean applyListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_authorize_pass);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        tv_apply_name = findViewById(R.id.tv_apply_name);
        tv_apply_identify = findViewById(R.id.tv_apply_identify);
        tv_apply_room = findViewById(R.id.tv_apply_room);
        tv_apply_time = findViewById(R.id.tv_apply_time);
        tv_apply_status = findViewById(R.id.tv_apply_status);
        tv_apply_title = findViewById(R.id.tv_apply_title);
        tv_apply_duration = findViewById(R.id.tv_apply_duration);
        layout_pass_time = findViewById(R.id.layout_pass_time);
        tv_pass_time = findViewById(R.id.tv_pass_time);
        btn_cancel_authorize = findViewById(R.id.btn_cancel_authorize);
        user_top_view_back.setOnClickListener(this::onClick);
        btn_cancel_authorize.setOnClickListener(this::onClick);
        user_top_view_title.setText("申请信息");
        applyListBean = (ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean) getIntent().getSerializableExtra("applyListBean");
        tv_apply_name.setText(applyListBean.getName());
        String isDeleted = applyListBean.getIsdeleted();
        String identify_name;
        switch (applyListBean.getUsertype()) {
            case "1":
                identify_name = "业主  ";
                break;
            case "3":
                identify_name = "租客  ";
                break;
            case "4":
                identify_name = "访客  ";
                break;
            default:
                identify_name = "家属  ";
                break;
        }
        tv_apply_name.setText(applyListBean.getToname());
        tv_apply_identify.setText(identify_name);
        tv_apply_room.setText(applyListBean.getName());
        tv_apply_time.setText(TimeUtil.getDateToString(applyListBean.getCreationtime()));
        if ("1".equals(isDeleted)){  //拒绝
            tv_apply_status.setText("已拒绝");
            tv_apply_title.setText("拒绝时间");
            tv_apply_duration.setText(TimeUtil.getDateToString(applyListBean.getModifiedtime()));
            layout_pass_time.setVisibility(View.GONE);
            btn_cancel_authorize.setVisibility(View.GONE);
        }else{
            tv_apply_status.setText("已通过");
            if (applyListBean.getStoptime()==0){
                tv_apply_duration.setText("永久");
            }else{
                tv_apply_duration.setText(TimeUtil.getDateToString(applyListBean.getStarttime())+"至"+
                        TimeUtil.getDateToString(applyListBean.getStoptime())  );
            }
            layout_pass_time.setVisibility(View.VISIBLE);
            btn_cancel_authorize.setVisibility(View.VISIBLE);
            tv_pass_time.setText(TimeUtil.getDateToString(applyListBean.getModifiedtime()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_cancel_authorize:
                NewDoorAuthorModel newDoorAuthorModel=new NewDoorAuthorModel(NewDoorAuthorizePassActivity.this);
                newDoorAuthorModel.cancelUserAutor(0,applyListBean.getId(),NewDoorAuthorizePassActivity.this);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        ToastUtil.toastShow(NewDoorAuthorizePassActivity.this, "操作成功!");
       setResult(200);
       finish();
    }
}
