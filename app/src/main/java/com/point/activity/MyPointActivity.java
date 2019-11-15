package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.point.adapter.PointListAdapter;
import com.point.entity.PointAccountListEntity;
import com.point.entity.PointKeywordEntity;
import com.point.model.PointModel;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

import static com.user.UserAppConst.COLOUR_WALLET_ACCOUNT_LIST;
import static com.user.UserAppConst.COLOUR_WALLET_KEYWORD_SIGN;

/***
 * 积分的列表和各种类型
 */
public class MyPointActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView mBack;
    private TextView mTitle;
    private ImageView iv_point_desc;
    private TextView tv_point_title;
    private TextView tv_point_total;
    private RecyclerView rv_point;
    private PointModel pointModel;
    private PointListAdapter pointListAdapter;
    private List<PointAccountListEntity.ContentBean.ListBean> listBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_list);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        iv_point_desc = findViewById(R.id.iv_point_desc);
        tv_point_title = findViewById(R.id.tv_point_title);
        tv_point_total = findViewById(R.id.tv_point_total);
        rv_point = findViewById(R.id.rv_point);
        mBack.setOnClickListener(this);
        iv_point_desc.setOnClickListener(this);
        tv_point_total.setOnClickListener(this);
        pointModel = new PointModel(MyPointActivity.this);
        pointModel.getWalletKeyWord(0, MyPointActivity.this);
        pointModel.getAccountList(1, MyPointActivity.this);
        String keyWordSign = shared.getString(COLOUR_WALLET_KEYWORD_SIGN, "积分");
        String accountList = shared.getString(COLOUR_WALLET_ACCOUNT_LIST, "");
        if (!TextUtils.isEmpty(keyWordSign)) {
            mTitle.setText("彩" + keyWordSign);
        }
        if (!TextUtils.isEmpty(accountList)) {
            showAccountList(accountList);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.iv_point_desc:
                if (null == pointDescDialog) {
                    pointDescDialog = new PointDescDialog(MyPointActivity.this);
                }
                pointDescDialog.show();
                break;
            case R.id.tv_point_total:
                Intent transactionIntent = new Intent(MyPointActivity.this, PointTransactionListActivity.class);
                startActivity(transactionIntent);
                break;
        }
    }

    private PointDescDialog pointDescDialog;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    PointKeywordEntity pointKeywordEntity = GsonUtils.gsonToBean(result, PointKeywordEntity.class);
                    String keywordSign = pointKeywordEntity.getContent().getKeyword();
                    editor.putString(COLOUR_WALLET_KEYWORD_SIGN, keywordSign).apply();
                    mTitle.setText("彩" + keywordSign);
                } catch (Exception e) {

                }
                break;
            case 1:
                showAccountList(result);
                break;
        }
    }


    private void showAccountList(String result) {
        try {
            PointAccountListEntity pointAccountListEntity = GsonUtils.gsonToBean(result, PointAccountListEntity.class);
            PointAccountListEntity.ContentBean contentBean = pointAccountListEntity.getContent();
            PointAccountListEntity.ContentBean.TotalBean totalBean = contentBean.getTotal();
            if (null != totalBean) {
                tv_point_title.setText(totalBean.getName());
                tv_point_total.setText(String.valueOf(totalBean.getBalance() / 100.0f));
            }
            List<PointAccountListEntity.ContentBean.ListBean> contentBeanList = contentBean.getList();
            if (null != contentBeanList) {
                listBeanList.clear();
                listBeanList.addAll(contentBeanList);
            }
            if (null == pointListAdapter) {
                pointListAdapter = new PointListAdapter(listBeanList);
                rv_point.setLayoutManager(new LinearLayoutManager(MyPointActivity.this, LinearLayoutManager.VERTICAL, false));
                rv_point.setAdapter(pointListAdapter);
            } else {
                pointListAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {

        }
    }

}
