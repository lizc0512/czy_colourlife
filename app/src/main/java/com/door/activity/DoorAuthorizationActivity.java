package com.door.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.door.adapter.DoorAutorAdapter;
import com.door.adapter.SelectCommunityAdapter;
import com.door.dialog.CustomDialog;
import com.door.dialog.SelectCommunityDialog;
import com.door.entity.DoorAuthorListEntity;
import com.door.entity.DoorCommunityEntity;
import com.door.model.CommunityModel;
import com.door.model.DoorAuthorModel;
import com.jpush.Constant;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;


public class DoorAuthorizationActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czyTitleLayout;
    private ImageView user_top_view_back;//返回
    private TextView user_top_view_title;//title

    private ListView listview;

    private DoorAutorAdapter adapter;
    private List<DoorAuthorListEntity.ContentBean.ListBean> authorList = new ArrayList<>();
    LayoutInflater mInflater = null;
    // 倒数计时器
    private CountDownTimer countDownTimer;
    private long time;
    private TextView txt_time;

    private CustomDialog dialog;

    private Button btn_hour,//2小时
            btn_one_day,//一天
            btn_seven_days,//7天
            btn_years,//一年
            btn_permanent,//永久;
            btn_commit;//授权确定
    private EditText edit_mobile,//电话号码
            edit_memo;//备注
    private TextView edit_community;//小区名称
    private DoorAuthorModel authorModel;

    private CommunityModel communityModel;
    // 小区列表
    private List<DoorCommunityEntity.ContentBean.CommunitylistBean> communityList = new ArrayList<DoorCommunityEntity.ContentBean.CommunitylistBean>();
    // 当前小区
    private DoorCommunityEntity.ContentBean.CommunitylistBean communityResp;
    // 保存小区选中状态 哪一个小区被选中
    private int whichCommunitySel = 0;

    private String usertype = "4";
    // app内通知
    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mReceiver;
    // 跳转到授权详情（批复授权和取消授权）
    private final static int INTENT_ACTION_OPEN_AUTHORIZATION_DETAIL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_list);
        prepareView();
        getAuthorandCommunityList();
        registerNotice();
    }

    private void prepareView() {

        try {
            authorModel = new DoorAuthorModel(this);
            communityModel = new CommunityModel(this);
            czyTitleLayout = (FrameLayout) findViewById(R.id.czy_title_layout);
            user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
            user_top_view_back.setOnClickListener(this);

            user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
            user_top_view_title.setText(getResources().getString(R.string.title_door_authorize));

            mInflater = LayoutInflater.from(DoorAuthorizationActivity.this);
            View view = mInflater.inflate(R.layout.activity_door_authorization_header, null);

            btn_hour = (Button) view.findViewById(R.id.btn_hour);
            btn_hour.setOnClickListener(this);
            btn_hour.setSelected(true);
            btn_one_day = (Button) view.findViewById(R.id.btn_one_day);
            btn_one_day.setOnClickListener(this);
            btn_seven_days = (Button) view.findViewById(R.id.btn_seven_days);
            btn_seven_days.setOnClickListener(this);
            btn_years = (Button) view.findViewById(R.id.btn_years);
            btn_years.setOnClickListener(this);
            btn_permanent = (Button) view.findViewById(R.id.btn_permanent);
            btn_permanent.setOnClickListener(this);

            edit_mobile = (EditText) view.findViewById(R.id.edit_mobile);
            edit_community = (TextView) view.findViewById(R.id.edit_community);
            edit_community.setOnClickListener(this);
            edit_memo = (EditText) view.findViewById(R.id.edit_memo);

            btn_commit = (Button) view.findViewById(R.id.btn_autor);
            btn_commit.setOnClickListener(this);
            adapter = new DoorAutorAdapter(DoorAuthorizationActivity.this, authorList);
            listview = (ListView) findViewById(R.id.listview);
            listview.addHeaderView(view);
            listview.setAdapter(adapter);
            listview.setVerticalScrollBarEnabled(false);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DoorAuthorListEntity.ContentBean.ListBean authorizationListResp = authorList.get(position - 1);
                    if (authorizationListResp == null) {
                        return;
                    }
                    if ("1".equals(authorizationListResp.getType())) {
                        //批复界面
                        Intent intent = new Intent(DoorAuthorizationActivity.this, DoorAuthorizationApproveActivity.class);
                        intent.putExtra("authorListResp", authorizationListResp);
                        intent.putExtra("refuse", true);
                        startActivityForResult(intent,
                                INTENT_ACTION_OPEN_AUTHORIZATION_DETAIL);
                    } else {
                        //通过 已失效
                        Intent authorizerIntent = new Intent(
                                DoorAuthorizationActivity.this,
                                DoorControlAuthorizationDetailActivity.class);
                        authorizerIntent.putExtra("authorizationListResp",
                                authorizationListResp);
                        startActivityForResult(authorizerIntent,
                                INTENT_ACTION_OPEN_AUTHORIZATION_DETAIL);
                    }
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
            ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czyTitleLayout, user_top_view_back, user_top_view_title);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取授权,小区列表
     */
    private void getAuthorandCommunityList() {
        authorModel.getAuthorList(0, true, this);
        communityModel.get(1, this);
    }

    /**
     * 注册通知
     */
    private void registerNotice() {
        try {
            mLocalBroadcastManager = LocalBroadcastManager
                    .getInstance(DoorAuthorizationActivity.this);
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constant.ACTION_W90);// 授权的推送指令
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(Constant.ACTION_W90)) {
                        authorModel.getAuthorList(0, false, DoorAuthorizationActivity.this);
                    }
                }
            };
            mLocalBroadcastManager.registerReceiver(mReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void OnHttpResponse(int what, String result) {

        switch (what) {
            case 0:  //授权列表
                try {
                    DoorAuthorListEntity doorAuthorListEntity = GsonUtils.gsonToBean(result, DoorAuthorListEntity.class);
                    authorList.clear();
                    authorList.addAll(doorAuthorListEntity.getContent().getList());
                    if (authorList.size() > 0) {
                        listview.setDividerHeight(1);
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
                break;
            case 1://小区列表
                try {
                    communityList.clear();
                    DoorCommunityEntity doorCommunityEntity = GsonUtils.gsonToBean(result, DoorCommunityEntity.class);
                    communityList.addAll(doorCommunityEntity.getContent().getCommunitylist());
                    if (communityList.size() > 0) {
                        communityResp = communityList.get(0);
                        edit_community.setText(communityResp.getName());
                    }
                } catch (Exception e) {

                }
                break;
            case 2://授权
                successAuthorBack(getResources().getString(R.string.door_author_success));
                break;
        }
    }

    /**
     * 授权返回成功结束页面
     */
    private void successAuthorBack(String reason) {
        try {
            dialog = new CustomDialog(DoorAuthorizationActivity.this,
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择小区弹窗
     */
    private void selectCommunity() {

        final SelectCommunityDialog dialog = new SelectCommunityDialog(
                DoorAuthorizationActivity.this,
                R.style.selectorDialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        // 添加选项名称
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.door_choice_community));

        ListView listView = (ListView) dialog.findViewById(R.id.listview);

        final SelectCommunityAdapter adapter = new SelectCommunityAdapter(
                DoorAuthorizationActivity.this, communityList,
                whichCommunitySel);

        listView.setAdapter(adapter);

        if (communityList != null && communityList.size() > 3) {
            setListViewHeightBasedOnChildren(listView);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                DoorCommunityEntity.ContentBean.CommunitylistBean data = communityList.get(position);

                if (data != null) {
                    communityResp = data;
                    whichCommunitySel = position;
                    adapter.setWhichCommunitySel(whichCommunitySel);
                    edit_community.setText(communityResp.getName());
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 动态计算设置AbsListView高度
     *
     * @param absListView
     * @函数名 setAbsListViewHeightBasedOnChildren
     * @功能 TODO
     * @备注 <其它说明>
     */
    private void setListViewHeightBasedOnChildren(ListView absListView) {

        ListAdapter listAdapter = absListView.getAdapter();
        if (listAdapter != null && listAdapter.getCount() > 0) {

            View view = listAdapter.getView(0, null, absListView);
            view.measure(0, 0);
            int totalHeight = 0;

            totalHeight = view.getMeasuredHeight() * 3;


            ViewGroup.LayoutParams params = absListView.getLayoutParams();
            params.height = totalHeight;
            absListView.setLayoutParams(params);
        }
    }

    /**
     * 选中状态设置
     */
    private void setChooseBtnSelector(int index) {
        btn_hour.setSelected(false);
        btn_one_day.setSelected(false);
        btn_seven_days.setSelected(false);
        btn_years.setSelected(false);
        btn_permanent.setSelected(false);
        switch (index) {
            case 1:
                usertype = "4";
                btn_hour.setSelected(true);
                break;
            case 2:
                usertype = "3";
                btn_one_day.setSelected(true);
                break;
            case 3:
                usertype = "2";
                btn_seven_days.setSelected(true);
                break;
            case 4:
                usertype = "5";
                btn_years.setSelected(true);
                break;
            case 5:
                usertype = "1";
                btn_permanent.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_hour:
                setChooseBtnSelector(1);
                break;
            case R.id.btn_one_day:
                setChooseBtnSelector(2);
                break;
            case R.id.btn_seven_days:
                setChooseBtnSelector(3);
                break;
            case R.id.btn_years:
                setChooseBtnSelector(4);
                break;
            case R.id.btn_permanent:
                setChooseBtnSelector(5);
                break;
            case R.id.edit_community:
                //选择小区
                if (communityList.size() > 1) {
                    selectCommunity();
                }
                break;
            case R.id.btn_autor:
                dismissSoftKeyboard(edit_memo);
                setAutor();
                break;
        }
    }

    //主动授权
    private void setAutor() {
        String mobile = edit_mobile.getText().toString();
        long currentTime = System.currentTimeMillis() / 1000;
        long stop = 0;
        // 二次授权，0没有，1有
        String granttype = "";
        // 授权类型，0临时，1限时，2永久
        String autype = "";
        if ("1".equals(usertype)) {
            granttype = "1";
            autype = "2";
        } else {
            autype = "1";
            granttype = "0";
            if ("2".equals(usertype)) {
                stop = currentTime + 3600 * 24 * 7;
            } else if ("3".equals(usertype)) {
                stop = currentTime + 3600 * 24;
            } else if ("4".equals(usertype)) {
                stop = currentTime + 3600 * 2;
            } else if ("5".equals(usertype)) {
                stop = currentTime + 3600 * 24 * 365;
            }
        }
        String memo = edit_memo.getText().toString();

        if (mobile == null || mobile.length() < 11) {
            ToastUtil.toastShow(DoorAuthorizationActivity.this, getResources().getString(R.string.door_phone_query));
            return;
        }
        String bid = "";
        if (communityResp != null) {
            bid = communityResp.getBid();
        } else {
            ToastUtil.toastShow(DoorAuthorizationActivity.this, getResources().getString(R.string.door_choice_community));
            return;
        }
        authorModel.setAutor(2, mobile, bid, usertype, granttype, autype, currentTime, stop, memo, this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (null != mLocalBroadcastManager && null != mReceiver) {
            mLocalBroadcastManager.unregisterReceiver(mReceiver);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_ACTION_OPEN_AUTHORIZATION_DETAIL) {
            // 跳转到授权详情（批复授权和取消授权）
            if (resultCode == RESULT_OK) {
                // 需要刷新授权列表
                authorModel.getAuthorList(0, true, this);
            }
        }
    }
}
