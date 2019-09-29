package com.door.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.door.adapter.DoorApplyAdapter;
import com.door.dialog.CustomDialog;

import com.door.entity.DoorApplyListEntity;
import com.door.model.DoorApplyModel;
import com.jpush.Constant;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;


import cn.net.cyberway.R;


public class DoorApplyActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private final static int INTENT_ACTION_APPLY_FOR_DETAIL = 3;
    private FrameLayout czyTitleLayout;
    private ImageView user_top_view_back;//返回
    private TextView user_top_view_title;//title
    private EditText edit_mobile;//电话号码
    private EditText edit_memo;//备注
    private Button btn_commit;//确定
    private ListView listview;

    private DoorApplyModel doorModel;

    private DoorApplyAdapter adapter;
    private List<DoorApplyListEntity.ContentBean.ListBean> applyList = new ArrayList<>();
    LayoutInflater mInflater = null;

    // 倒数计时器
    private CountDownTimer countDownTimer;
    private long time;
    private TextView txt_time;

    private CustomDialog dialog;


    // app内通知
    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_list);
        prepareView();
        getApplyList();
        registerNotice();
    }

    private void prepareView() {
        czyTitleLayout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_back.setOnClickListener(this);

        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        user_top_view_title.setText(getResources().getString(R.string.title_door_apply));


        mInflater = LayoutInflater.from(DoorApplyActivity.this);
        View view = mInflater.inflate(R.layout.door_apply_header, null);
        edit_mobile = (EditText) view.findViewById(R.id.edit_mobile);
        edit_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() >= 11) {
                    btn_commit.setBackgroundResource(R.drawable.btn_shape_pass);
                } else {
                    btn_commit.setBackgroundResource(R.drawable.btn_shape_gray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edit_memo = (EditText) view.findViewById(R.id.edit_memo);

        btn_commit = (Button) view.findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(this);

        doorModel = new DoorApplyModel(DoorApplyActivity.this);

        adapter = new DoorApplyAdapter(DoorApplyActivity.this, applyList);
        listview = (ListView) findViewById(R.id.listview);
        listview.addHeaderView(view);
        listview.setAdapter(adapter);
        listview.setVerticalScrollBarEnabled(false);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (applyList == null || applyList.size() == 0) {
                    return;
                }
                Intent intent = new Intent(DoorApplyActivity.this, DoorApplyDetailActivity.class);
                intent.putExtra("applyListResp", applyList.get(position - 1));
                startActivityForResult(intent, INTENT_ACTION_APPLY_FOR_DETAIL);
            }
        });
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                //滑动隐藏键盘
                if (AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    dismissSoftKeyboard(edit_memo);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        countDownTimer = new CountDownTimer(6000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                time = millisUntilFinished / 1000;

                if (txt_time != null) {
                    String date = "(提示：此页面将在" + time + "秒内直接关闭，并返回到门禁首页)";
                    SpannableStringBuilder builder = new SpannableStringBuilder(date);
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.lightgred_color));
                    builder.setSpan(redSpan, 9, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    txt_time.setText(builder);
                }
            }

            @Override
            public void onFinish() {
                finish();
            }
        };
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(),czyTitleLayout,user_top_view_back,user_top_view_title);
    }

    private void setApply() {
        String mobile = edit_mobile.getText().toString();
        String memo = edit_memo.getText().toString();

        if (mobile == null || mobile.length() < 11) {
            ToastUtil.toastShow(DoorApplyActivity.this, "请输入至少11位电话号码查询");
            return;
        }
        doorModel.setApplyByAccount(0, mobile, memo, DoorApplyActivity.this);

    }

    /**
     * 注册通知
     */
    private void registerNotice() {

        mLocalBroadcastManager = LocalBroadcastManager
                .getInstance(DoorApplyActivity.this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_W91);// 申请的推送指令
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constant.ACTION_W91)) {
                    doorModel.getApplyList(1, false, DoorApplyActivity.this);
                }
            }

        };
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);
    }

    private void getApplyList() {

        doorModel.getApplyList(1, true, DoorApplyActivity.this);
    }

    /**
     * 申请返回成功结束页面
     */
    private void successApplyBack(String reason) {
        dialog = new CustomDialog(DoorApplyActivity.this,
                R.style.qr_dialog);
        dialog.show();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getRepeatCount() == 0) {

                    countDownTimer.cancel();
                    finish();

                }
                return false;
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
                countDownTimer.cancel();
                finish();
            }
        });
        countDownTimer.start();
        txt_time = (TextView) dialog.findViewById(R.id.dialog_content);


        TextView dialog_title = (TextView) dialog.findViewById(R.id.dialog_title);
        dialog_title.setText(reason);
        LinearLayout rl_dialog = (LinearLayout) dialog.findViewById(R.id.rl_dialog);
        rl_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                countDownTimer.cancel();
                finish();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_commit:
                setApply();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_ACTION_APPLY_FOR_DETAIL) {
            if (resultCode == RESULT_OK) {
                getApplyList();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        if (mLocalBroadcastManager != null && mReceiver != null) {

            mLocalBroadcastManager.unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                successApplyBack("申请成功");
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        DoorApplyListEntity doorApplyListEntity = GsonUtils.gsonToBean(result, DoorApplyListEntity.class);
                        applyList.clear();
                        applyList.addAll(doorApplyListEntity.getContent().getList());
                        if (applyList.size() > 0) {
                            listview.setDividerHeight(1);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }
}
