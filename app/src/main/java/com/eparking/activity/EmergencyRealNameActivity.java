package com.eparking.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingApplyModel;
import com.eparking.view.cardcamera.camera.CameraActivity;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/11 16:27
 * @change
 * @chang time
 * @class describe 紧急开闸实名认证 上传证件信息
 */
public class EmergencyRealNameActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private Button btn_next;//返回
    private TextView tv_user_infor;
    private ImageView iv_bg_idfront;
    private ImageView iv_upload_idfront;
    private ImageView iv_front_delete;
    private ImageView iv_bg_idback;
    private ImageView iv_upload_idback;
    private ImageView iv_back_delete;
    private ParkingApplyModel parkingApplyModel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_realname);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_identify_authorize));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        tv_user_infor = findViewById(R.id.tv_user_infor);
        iv_bg_idfront = findViewById(R.id.iv_bg_idfront);
        iv_upload_idfront = findViewById(R.id.iv_upload_idfront);
        iv_front_delete = findViewById(R.id.iv_front_delete);
        iv_bg_idback = findViewById(R.id.iv_bg_idback);
        iv_upload_idback = findViewById(R.id.iv_upload_idback);
        iv_back_delete = findViewById(R.id.iv_back_delete);
        btn_next = findViewById(R.id.btn_next);
        imageView_back.setOnClickListener(this);
        iv_bg_idfront.setOnClickListener(this);
        iv_front_delete.setOnClickListener(this);
        iv_bg_idback.setOnClickListener(this);
        iv_back_delete.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        parkingApplyModel = new ParkingApplyModel(this);
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private int CAMERATYPE = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.iv_bg_idfront:
                CAMERATYPE = CameraActivity.TYPE_IDCARD_BACK;
                CameraActivity.toCameraActivity(this, CAMERATYPE);
                break;
            case R.id.iv_front_delete:
                idFrontPath = "";
                iv_front_delete.setVisibility(View.GONE);
                iv_upload_idfront.setVisibility(View.GONE);
                iv_bg_idfront.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_bg_idback:
                CAMERATYPE = CameraActivity.TYPE_IDCARD_FRONT;
                CameraActivity.toCameraActivity(this, CAMERATYPE);
                break;
            case R.id.iv_back_delete:
                idBackPath = "";
                iv_back_delete.setVisibility(View.GONE);
                iv_upload_idback.setVisibility(View.GONE);
                iv_bg_idback.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_next:
                Intent intent = new Intent(EmergencyRealNameActivity.this, SubmitIDCardInforActivity.class);
                startActivity(intent);
                break;
        }
    }

    private String idFrontPath;
    private String idBackPath;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CameraActivity.REQUEST_CODE && resultCode == CameraActivity.RESULT_CODE) {
            //获取图片路径，显示图片
            final String path = CameraActivity.getImagePath(data);
            if (CAMERATYPE == CameraActivity.TYPE_IDCARD_BACK) {
                if (!TextUtils.isEmpty(path)) {
                    idFrontPath = path;
                    iv_upload_idfront.setVisibility(View.VISIBLE);
                    iv_upload_idfront.setImageBitmap(BitmapFactory.decodeFile(path));
                    iv_front_delete.setVisibility(View.VISIBLE);
                    iv_bg_idfront.setVisibility(View.GONE);
                    parkingApplyModel.contractUploadOperation(0, path, this);
                }
            } else {
                if (!TextUtils.isEmpty(path)) {
                    idBackPath = path;
                    iv_upload_idback.setVisibility(View.VISIBLE);
                    iv_back_delete.setVisibility(View.VISIBLE);
                    iv_bg_idback.setVisibility(View.GONE);
                    iv_upload_idback.setImageBitmap(BitmapFactory.decodeFile(path));
                    parkingApplyModel.contractUploadOperation(1, path, this);
                }
            }
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:


                break;
            case 1:

                break;
        }
    }
}
