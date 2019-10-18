package com.door.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.activity.CustomerAddPropertyActivity;
import com.door.adapter.NewDoorIdentifyAdapter;
import com.door.entity.IdentityListEntity;
import com.door.model.NewDoorAuthorModel;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.customerInfo.activity.CustomerAddPropertyActivity.IDENTITY_ID;
import static com.customerInfo.activity.CustomerAddPropertyActivity.IDENTITY_NAME;

public class NewDoorIndetifyActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_top_view_right;
    private TextView btn_next_step;
    private TextView tv_identify_title;
    private RecyclerView rv_open_record;
    private List<IdentityListEntity.ContentBean> identityList = new ArrayList<>();
    private String identityId;
    private String identityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_openrecord);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_top_view_right = findViewById(R.id.user_top_view_right);
        rv_open_record = findViewById(R.id.rv_open_record);
        btn_next_step = findViewById(R.id.btn_next_step);
        tv_identify_title = findViewById(R.id.btn_next_step);
        user_top_view_back.setOnClickListener(this::onClick);
        user_top_view_right.setOnClickListener(this::onClick);
        btn_next_step.setVisibility(View.VISIBLE);
        tv_identify_title.setVisibility(View.VISIBLE);
        btn_next_step.setOnClickListener(this::onClick);
        user_top_view_title.setText("申请权限");
        user_top_view_right.setText("我的申请");
        user_top_view_right.setVisibility(View.INVISIBLE);
        NewDoorAuthorModel newDoorAuthorModel = new NewDoorAuthorModel(NewDoorIndetifyActivity.this);
        newDoorAuthorModel.getIdentifyList(0, true, NewDoorIndetifyActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                Intent intent = new Intent(NewDoorIndetifyActivity.this, NewDoorApplyRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_next_step:
                if (-1==choicePos){
                    ToastUtil.toastShow(NewDoorIndetifyActivity.this,"请选择您的身份！");
                }else{
                    Intent nextIntent=new Intent(NewDoorIndetifyActivity.this, CustomerAddPropertyActivity.class);
                    nextIntent.putExtra(IDENTITY_ID,identityId);
                    nextIntent.putExtra(IDENTITY_NAME,identityName);
                    startActivityForResult(nextIntent,100);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==200){
            setResult(200);
            finish();
        }
    }

    private int choicePos=-1;

    @Override
    public void OnHttpResponse(int what, String result) {
        if (what == 0) {
            try {
                IdentityListEntity identityListEntity = GsonUtils.gsonToBean(result, IdentityListEntity.class);
                identityList.clear();
                identityList.addAll(identityListEntity.getContent());
                NewDoorIdentifyAdapter newDoorIdentifyAdapter = new NewDoorIdentifyAdapter(identityList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewDoorIndetifyActivity.this, LinearLayoutManager.VERTICAL, false);
                rv_open_record.setLayoutManager(linearLayoutManager);
                rv_open_record.setAdapter(newDoorIdentifyAdapter);
                newDoorIdentifyAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int i) {
                        choicePos=i;
                        newDoorIdentifyAdapter.setChoicePos(i);
                        IdentityListEntity.ContentBean contentBean=identityList.get(i);
                        identityId = contentBean.getId();
                        identityName =contentBean.getName();
                        btn_next_step.setBackground(getResources().getDrawable(R.drawable.shape_text_property_select));
                        btn_next_step.setEnabled(true);
                    }
                });
            } catch (Exception e) {

            }
        }
    }
}
