package com.door.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.door.adapter.SelectCommunityAdapter;
import com.door.dialog.SelectCommunityDialog;
import com.door.entity.DoorAuthorListEntity;
import com.door.entity.DoorCommunityEntity;
import com.door.model.DoorAuthorModel;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;


public class DoorAuthorizationApproveActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {


    private FrameLayout czyTitleLayout;
    private ImageView user_top_view_back;//返回
    private TextView user_top_view_title;//title

    private TextView tv_time,//申请时间
            tv_memo,//申请备注
            tv_community;//授权小区
    private Button btn_hour,//2小时
            btn_one_day,//一天
            btn_seven_days,//7天
            btn_years,//一年
            btn_permanent;//永久;
    private Button btn_pass,//通过
            btn_refuse;//拒绝

    private String usertype = "4";
    private DoorAuthorListEntity.ContentBean.ListBean authorizationListResp;

    private boolean refuse;

    // 小区列表
    private List<DoorCommunityEntity.ContentBean.CommunitylistBean> communityList = new ArrayList<DoorCommunityEntity.ContentBean.CommunitylistBean>();
    // 当前小区
    private DoorCommunityEntity.ContentBean.CommunitylistBean communityResp;
    // 保存小区选中状态 哪一个小区被选中
    private int whichCommunitySel = 0;

    private DoorAuthorModel authorModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.door_noauthor_detail);
        prepareView();
        getIntentData();
        prepareDate();
    }

    /**
     * 获取Intent传过来的数据
     */
    private void getIntentData() {

        Intent intent = getIntent();
        authorizationListResp = (DoorAuthorListEntity.ContentBean.ListBean) intent
                .getSerializableExtra("authorListResp");
        refuse = intent.getBooleanExtra("refuse", true);
        String communityObject = shared.getString(UserAppConst.COLOUR_COMMUNITYLIST, "");
        if (!TextUtils.isEmpty(communityObject)) {
            try {
                communityList.clear();
                DoorCommunityEntity doorCommunityEntity = GsonUtils.gsonToBean(communityObject, DoorCommunityEntity.class);
                communityList.addAll(doorCommunityEntity.getContent().getCommunitylist());
            } catch (Exception e) {

            }
        }
    }

    private void prepareView() {

        authorModel = new DoorAuthorModel(this);
        czyTitleLayout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_back.setOnClickListener(this);

        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        user_top_view_title.setText(getResources().getString(R.string.title_door_authorize));

        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_memo = (TextView) findViewById(R.id.tv_memo);
        tv_community = (TextView) findViewById(R.id.tv_community);
        tv_community.setOnClickListener(this);
        btn_hour = (Button) findViewById(R.id.btn_hour);
        btn_hour.setOnClickListener(this);
        btn_hour.setSelected(true);
        btn_one_day = (Button) findViewById(R.id.btn_one_day);
        btn_one_day.setOnClickListener(this);
        btn_seven_days = (Button) findViewById(R.id.btn_seven_days);
        btn_seven_days.setOnClickListener(this);
        btn_years = (Button) findViewById(R.id.btn_years);
        btn_years.setOnClickListener(this);
        btn_permanent = (Button) findViewById(R.id.btn_permanent);
        btn_permanent.setOnClickListener(this);

        btn_pass = (Button) findViewById(R.id.btn_pass);
        btn_pass.setOnClickListener(this);
        btn_refuse = (Button) findViewById(R.id.btn_refuse);
        btn_refuse.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czyTitleLayout, user_top_view_back, user_top_view_title);

    }

    private void prepareDate() {
        if (authorizationListResp != null && communityList != null && communityList.size() > 0) {
            tv_memo.setText(authorizationListResp.getMemo());
            String date = authorizationListResp.getCreationtime() + "000";
            String dateTime = "";
            try {
                Long dateLong = Long.parseLong(date);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                dateTime = df.format(dateLong);
            } catch (Exception e) {
            }
            tv_time.setText(dateTime);
            if (refuse) {
                btn_refuse.setVisibility(View.VISIBLE);
            } else {
                btn_refuse.setVisibility(View.GONE);
            }
            communityResp = communityList.get(0);
            tv_community.setText(communityResp.getName());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_hour:
                usertype = "4";
                setChooseBtnSelector(1);
                break;
            case R.id.btn_one_day:
                usertype = "3";
                setChooseBtnSelector(2);
                break;
            case R.id.btn_seven_days:
                usertype = "2";
                setChooseBtnSelector(3);
                break;
            case R.id.btn_years:
                usertype = "5";
                setChooseBtnSelector(4);
                break;
            case R.id.btn_permanent:
                usertype = "1";
                setChooseBtnSelector(5);
                break;

            case R.id.btn_pass:
                if (refuse) {
                    //通过
                    approve("1");
                } else {
                    //再次授权
                    authorize();
                }
                break;
            //拒绝
            case R.id.btn_refuse:
                approve("2");
                break;
            case R.id.tv_community:
                if (communityList.size() > 1) {
                    selectCommunity();
                }
                break;


        }
    }


    /**
     * 批复
     *
     * @param approve 1表示通过 2表示拒绝
     */
    private void approve(String approve) {

        // 没有授权任何人
        if (authorizationListResp == null) {
            return;
        }
        // 申请编号
        String id = authorizationListResp.getId();

        String bid = "";
        if (communityResp != null) {
            bid = communityResp.getBid();
        } else {
            ToastUtil.toastShow(DoorAuthorizationApproveActivity.this,
                    getResources().getString(R.string.door_choice_community));
            return;
        }
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
        // 备注
        String memo = "";
        authorModel.approveNew(0, id, bid, approve, usertype, autype, granttype, currentTime, stop, memo, this);
    }


    /**
     * 再次授权
     *
     * @param
     */
    private void authorize() {

        // 没有授权任何人
        if (authorizationListResp == null) {
            return;
        }
        String toid = authorizationListResp.getToid();
        String bid = "";
        if (communityResp != null) {
            bid = communityResp.getBid();
        } else {
            ToastUtil.toastShow(DoorAuthorizationApproveActivity.this,
                    getResources().getString(R.string.door_choice_community));
            return;
        }
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

        // 备注
        String memo = "";


        authorModel.reAuthorize(1, toid, bid, usertype, granttype, autype,
                currentTime, stop, memo, this);
    }

    /**
     * 选择小区弹窗
     */
    private void selectCommunity() {

        final SelectCommunityDialog dialog = new SelectCommunityDialog(
                DoorAuthorizationApproveActivity.this,
                R.style.selectorDialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        // 添加选项名称
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.door_choice_community));

        ListView listView = (ListView) dialog.findViewById(R.id.listview);

        final SelectCommunityAdapter adapter = new SelectCommunityAdapter(
                DoorAuthorizationApproveActivity.this, communityList,
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
                    tv_community.setText(communityResp.getName());
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
                btn_hour.setSelected(true);
                break;
            case 2:
                btn_one_day.setSelected(true);
                break;
            case 3:
                btn_seven_days.setSelected(true);
                break;
            case 4:
                btn_years.setSelected(true);
                break;
            case 5:
                btn_permanent.setSelected(true);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                setResult(RESULT_OK);
                finish();
                break;
            case 1:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
