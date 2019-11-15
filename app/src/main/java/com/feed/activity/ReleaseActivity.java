package com.feed.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.external.eventbus.EventBus;
import com.feed.model.ActivityPublishModel;
import com.feed.protocol.ACTIVITY_CATEGORY;
import com.feed.protocol.VerFeedPublishActivityApi;
import com.gem.GemConstant;
import com.gem.util.GemDialogUtil;
import com.nohttp.utils.GlideImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tendcloud.tenddata.TCAgent;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.csh.colourful.life.view.pickview.TimePickerView;
import cn.net.cyberway.R;

/**
 * 发活动
 */
public class ReleaseActivity extends BaseActivity implements View.OnClickListener, HttpApiResponse {

    public static final String ACTIVITY_TYPE = "activity_type";
    public static final int REQUEST_CODE = 0;
    private FrameLayout czy_title_layout;
    private LinearLayout mType;
    private ImageView mImageViewType;
    private EditText mLocation;
    private EditText mDescribe;
    private RelativeLayout mTimeLayout;
    private TextView mBack;
    private TextView mTextView;
    private TextView mTime;
    private FrameLayout mActivityTypeLayout;
    private TextView mActivityName;
    private ACTIVITY_CATEGORY mActivityCategory;
    private TextView mSend;
    private ActivityPublishModel activityPublishModel;
    private Long mStartTime;            //选择了的时间
    private String mCurrentCommunityId;
    private long mSelectTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        activityPublishModel = new ActivityPublishModel(this);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (TextView) findViewById(R.id.user_top_view_back);
        TextView user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        mSend = (TextView) findViewById(R.id.user_top_view_right);
        mCurrentCommunityId = shared.getString(UserAppConst.Colour_login_community_uuid, "");
        mType = (LinearLayout) findViewById(R.id.release_select_activitytype);

        mImageViewType = (ImageView) findViewById(R.id.item_select_type_imageview);

        mTimeLayout = (RelativeLayout) findViewById(R.id.release_select_activitytime);

        mTime = (TextView) findViewById(R.id.release_select_time);

        mLocation = (EditText) findViewById(R.id.release_input_activitylocation);

        mDescribe = (EditText) findViewById(R.id.release_input_activitydes);

        //请选择活动类型那句话
        mTextView = (TextView) findViewById(R.id.release_select_activitytype_tv);
        mActivityTypeLayout = (FrameLayout) findViewById(R.id.activity_type_layout);
        mActivityName = (TextView) findViewById(R.id.activity_name);
        mType.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mTimeLayout.setOnClickListener(this);
        mSend.setOnClickListener(this);

        mSelectTime = 0;

        ImageView ivGem = (ImageView) findViewById(R.id.iv_gem);
        GemDialogUtil.showGemDialog(ivGem, this, GemConstant.neighborActivity, "");
        ThemeStyleHelper.backTexteFrameLayout(getApplicationContext(), czy_title_layout, mBack, user_top_view_title, mSend);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.release_select_activitytype:
                Intent intent = new Intent(this, SelectTypeActivity.class);
                startActivityForResult(intent, ReleaseActivity.REQUEST_CODE);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.release_select_activitytime:
                //调用展示选择时间的dialog方法
                showTimeDialog();
                break;
            case R.id.user_top_view_right:
                String location = mLocation.getText().toString();
                String content = mDescribe.getText().toString();
                if (null == mActivityCategory) {
                    ToastUtil.toastShow(ReleaseActivity.this, "请选择活动类型");
                } else if (mStartTime == null) {
                    ToastUtil.toastShow(ReleaseActivity.this, "请选择活动时间");
                } else if (TextUtils.isEmpty(location)) {
                    ToastUtil.toastShow(ReleaseActivity.this, "请输入活动地址");
                    mLocation.requestFocus();
                } else if (mSelectTime < System.currentTimeMillis()) {
                    ToastUtil.toastShow(ReleaseActivity.this, "活动时间不能早于当前时间");
                } else {
                    if (!TextUtils.isEmpty(location)) {
                        Pattern pattern = Pattern.compile("^\\s{1,}$");
                        Matcher match = pattern.matcher(location);
                        if (match.matches()) {
                            ToastUtil.toastShow(ReleaseActivity.this, "请输入正确的活动地址");
                        } else {
                            if (!TextUtils.isEmpty(content)) {
                                Pattern pattern1 = Pattern.compile("^\\s{1,}$");
                                Matcher match1 = pattern.matcher(content);
                                if (match1.matches()) {
                                    ToastUtil.toastShow(ReleaseActivity.this, "请输入正确的活动描述");
                                } else {
                                    int userId = shared.getInt(UserAppConst.Colour_User_id, 0);
                                    String mCommunityId = shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
                                    Map<String, String> paramsMap = new HashMap<>();
                                    paramsMap.put("customer_id", userId + "");
                                    paramsMap.put("community_id", mCommunityId);
                                    TCAgent.onEvent(getApplicationContext(), "207004", "", paramsMap);
                                    activityPublishModel.publish(this, mCurrentCommunityId, mActivityCategory.id, mStartTime + "", content, location);
                                }
                            } else {
                                int userId = shared.getInt(UserAppConst.Colour_User_id, 0);
                                String mCommunityId = shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
                                Map<String, String> paramsMap = new HashMap<>();
                                paramsMap.put("customer_id", userId + "");
                                paramsMap.put("community_id", mCommunityId);
                                TCAgent.onEvent(getApplicationContext(), "207004", "", paramsMap);
                                activityPublishModel.publish(this, mCurrentCommunityId, mActivityCategory.id, mStartTime + "", content, location);
                            }

                        }
                    }
                }

                break;
        }
    }

    private TimePickerView pvTime;

    //展示选择时间的dialog的方法
    private void showTimeDialog() {
        Calendar selectedDate = Calendar.getInstance();
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                mSelectTime = date.getTime();
                mStartTime = mSelectTime / 1000;
                mTime.setText(TimeUtil.getDateToString(date));
                mTime.setTextColor(getResources().getColorStateList(R.color.black_text_color));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(16)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择活动时间")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.parseColor("#333b46"))//标题文字颜色
                .setSubmitColor(Color.parseColor("#27a2f0"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#27a2f0"))//取消按钮文字颜色
                .setTitleBgColor(Color.parseColor("#f5f5f5"))//标题背景颜色 Night mode
                .setBgColor(Color.parseColor("#ffffff"))//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 默认是系统时间*/
                .setRange(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.YEAR) + 1)//默认是1900-2100年
                .setLabel("年", "月", "日", "时", "分", "")
                .build();
        pvTime.show();
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy年MM月dd日 HH:mm
     */
    public static String getStringDate(Long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String dateString = formatter.format(date);
        return dateString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {

            if (requestCode == REQUEST_CODE) {

                //隐藏请选择活动类型那句话
                mTextView.setVisibility(View.GONE);
                mActivityTypeLayout.setVisibility(View.VISIBLE);

                mActivityCategory = (ACTIVITY_CATEGORY) data.getSerializableExtra(ACTIVITY_TYPE);
                if (null != mActivityCategory) {
                    ImageLoader.getInstance().displayImage(mActivityCategory.photo, mImageViewType, GlideImageLoader.optionsImage );
                    mActivityName.setText(mActivityCategory.name);
                }
            }
        }
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(VerFeedPublishActivityApi.class)) {
            ToastUtil.toastShow(ReleaseActivity.this, "发布成功");
            Message msg = new Message();
            msg.what = UserMessageConstant.CREATE_FEED;
            EventBus.getDefault().post(msg);
            finish();
        }
    }
}
