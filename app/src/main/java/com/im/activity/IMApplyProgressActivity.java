package com.im.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.external.maxwin.view.XListView;
import com.im.entity.CommunityInforEntity;
import com.im.model.IMCommunityModel;
import com.im.view.CommunityOperationDialog;
import com.im.view.UploadPhoneDialog;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;

import cn.csh.colourful.life.utils.ToastUtils;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/29 20:55
 * @change
 * @chang time
 * @class describe
 */
public class IMApplyProgressActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String COMMMUNITYID = "commmunityid";
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_top_view_right;
    private XListView community_listview;
    private int communityId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_community);
        communityId = getIntent().getIntExtra(COMMMUNITYID, 0);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_top_view_right = findViewById(R.id.user_top_view_right);
        user_top_view_title.setText("社群申请进度");
        user_top_view_right.setVisibility(View.GONE);
        user_top_view_right.setText("更多");
        user_top_view_right.setTextColor(getResources().getColor(R.color.color_45adff));
        user_top_view_right.setOnClickListener(this);
        community_listview = findViewById(R.id.community_listview);
        user_top_view_back.setOnClickListener(this);
        community_listview.setPullLoadEnable(false);
        community_listview.setPullRefreshEnable(false);
        community_listview.setAdapter(null);
        initHeaderView();
        IMCommunityModel imCommunityModel = new IMCommunityModel(IMApplyProgressActivity.this);
        imCommunityModel.getSingleCommunityInfor(0, String.valueOf(communityId), this);
    }

    private TextView tv_verify_status;
    private TextView tv_remain_time;
    private TextView tv_community_name;
    private TextView tv_community_households;
    private TextView tv_community_area;
    private TextView tv_group_name;
    private TextView tv_mobile;
    private TextView tv_apply_time;
    private TextView tv_apply_number;
    private TextView tv_copy_number;

    private void initHeaderView() {
        View headView = LayoutInflater.from(IMApplyProgressActivity.this).inflate(R.layout.header_community_progress, null);
        tv_verify_status = headView.findViewById(R.id.tv_verify_status);
        tv_remain_time = headView.findViewById(R.id.tv_remain_time);
        tv_community_name = headView.findViewById(R.id.tv_community_name);
        tv_community_households = headView.findViewById(R.id.tv_community_households);
        tv_community_area = headView.findViewById(R.id.tv_community_area);
        tv_group_name = headView.findViewById(R.id.tv_group_name);
        tv_mobile = headView.findViewById(R.id.tv_mobile);
        tv_apply_time = headView.findViewById(R.id.tv_apply_time);
        tv_apply_number = headView.findViewById(R.id.tv_apply_number);
        tv_copy_number = headView.findViewById(R.id.tv_copy_number);
        community_listview.addHeaderView(headView);
        tv_copy_number.setOnClickListener(this);
    }

    private CommunityOperationDialog communityOperationDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                if (null == communityOperationDialog) {
                    communityOperationDialog = new CommunityOperationDialog(IMApplyProgressActivity.this, R.style.custom_dialog_theme);
                }
                communityOperationDialog.show();
                communityOperationDialog.setShowHide(status);
                communityOperationDialog.tv_cancel.setOnClickListener(this);
                communityOperationDialog.tv_again_submit.setOnClickListener(this);
                communityOperationDialog.tv_revoke.setOnClickListener(this);
                break;
            case R.id.tv_copy_number:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", tv_apply_number.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtils.showMessage(IMApplyProgressActivity.this, "申请编号复制成功!");
                break;
            case R.id.tv_cancel:
                communityOperationDialog.dismiss();
                break;
            case R.id.tv_again_submit:
                Intent intent = new Intent(IMApplyProgressActivity.this, IMCreateCommunityActivity.class);
                intent.putExtra(IMCreateCommunityActivity.COMMUNITYTYPE, 1);
                intent.putExtra(IMCreateCommunityActivity.COMMUNITYID, id);
                intent.putExtra(IMCreateCommunityActivity.IMID, imId);
                intent.putExtra(IMCreateCommunityActivity.OWNER, owner);
                intent.putExtra(IMCreateCommunityActivity.AREA, area);
                intent.putExtra(IMCreateCommunityActivity.MOBILE, mobile);
                intent.putExtra(IMCreateCommunityActivity.TOTALNAME, totalName);
                intent.putExtra(IMCreateCommunityActivity.GROUPNAME, groupName);
                startActivity(intent);
                finish();
                communityOperationDialog.dismiss();
                break;
            case R.id.tv_revoke:
                if (null == mRevokeDialog) {
                    mRevokeDialog = new UploadPhoneDialog(IMApplyProgressActivity.this, R.style.custom_dialog_theme);
                }
                mRevokeDialog.show();
                mRevokeDialog.dialog_title.setVisibility(View.GONE);
                mRevokeDialog.dialog_content.setText("确定要撤销申请吗?");
                mRevokeDialog.btn_open.setOnClickListener(this);
                mRevokeDialog.btn_cancel.setOnClickListener(this);
                break;
            case R.id.btn_cancel:
                if (null != mRevokeDialog) {
                    mRevokeDialog.dismiss();
                }
                break;
            case R.id.btn_open:
                if (null != mRevokeDialog) {
                    mRevokeDialog.dismiss();
                    IMCommunityModel imCommunityModel = new IMCommunityModel(IMApplyProgressActivity.this);
                    imCommunityModel.cancelApplyCommunity(1, String.valueOf(id), IMApplyProgressActivity.this);
                }
                break;
        }
    }

    private UploadPhoneDialog mRevokeDialog = null;

    private int status;
    private int id;
    private String imId;
    private String owner;
    private String area;
    private String mobile;
    private String totalName;
    private String groupName;


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        CommunityInforEntity communityInforEntity = GsonUtils.gsonToBean(result, CommunityInforEntity.class);
                        CommunityInforEntity.ContentBean contentBean = communityInforEntity.getContent();
                        imId = contentBean.getIm_id();
                        id = contentBean.getId();
                        groupName = contentBean.getGroup_name();
                        owner = contentBean.getOwner();
                        area = contentBean.getArea();
                        mobile = contentBean.getMobile();
                        totalName = contentBean.getTotal_num();
                        tv_community_name.setText(groupName);
                        tv_group_name.setText(owner);
                        tv_community_area.setText(area);
                        tv_mobile.setText(mobile);
                        tv_community_households.setText(totalName);
                        tv_apply_number.setText(contentBean.getApply_num());
                        status = contentBean.getState();
                        if (4 == status) {
                            tv_verify_status.setText("已撤销");
                            user_top_view_right.setVisibility(View.VISIBLE);
                        } else if (2 == status) {
                            tv_verify_status.setText("审核通过");
                            user_top_view_right.setVisibility(View.GONE);
                        } else if (3 == status) {
                            tv_verify_status.setText("审核不通过");
                            user_top_view_right.setVisibility(View.VISIBLE);
                        } else {
                            tv_verify_status.setText("审核中");
                            user_top_view_right.setVisibility(View.VISIBLE);
                        }
                        long updateTime = contentBean.getUpdated_at() * 1000;
                        long nowTime = contentBean.getNow_time() * 1000;
                        tv_apply_time.setText(TimeUtil.getYearTime(updateTime, "yyyy年MM月dd日 HH:mm"));
                        tv_remain_time.setText("(已等待" + TimeUtil.dateDiff(updateTime, nowTime) + ")");
                    } catch (Exception e) {

                    }
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                    ToastUtil.toastShow(IMApplyProgressActivity.this, "已撤销");
                    setResult(200);
                    finish();
                }
                break;
        }
    }
}
