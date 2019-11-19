package com.point.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.point.adapter.PointGivenHistoryAdapter;
import com.point.entity.PointTransferListEntity;
import com.point.model.PointModel;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

import static com.point.activity.PointTransactionListActivity.POINTTPANO;

/***
 * 饭票赠送历史记录
 */

public class GivenPointHistoryActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView mBack;
    private TextView mTitle;
    private SwipeMenuRecyclerView rv_given_history;//当前类型的饭票或积分赠送记录
    private TextView tv_no_record;
    private List<PointTransferListEntity.ContentBean.ListBean> totalContentBeanList = new ArrayList<>();
    private PointGivenHistoryAdapter pointGivenHistoryAdapter;
    private PointModel pointModel;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_given_history);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        rv_given_history = findViewById(R.id.rv_given_history);
        tv_no_record = findViewById(R.id.tv_no_record);
        mBack.setOnClickListener(this);
        mTitle.setText("历史记录");
        pointModel = new PointModel(GivenPointHistoryActivity.this);
        String pano = getIntent().getStringExtra(POINTTPANO);
        pointModel.getTransferList(0, pano, page, true, GivenPointHistoryActivity.this);
        rv_given_history.setLoadMoreListener(() -> {
            page++;
            pointModel.getTransferList(0, pano, page, false, GivenPointHistoryActivity.this);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)){
                    try {
                        PointTransferListEntity pointTransferListEntity = GsonUtils.gsonToBean(result, PointTransferListEntity.class);
                        PointTransferListEntity.ContentBean contentBean = pointTransferListEntity.getContent();
                        if (page == 1) {
                            totalContentBeanList.clear();
                        }
                        boolean dataEmpty = false;
                        boolean moreEmpty = false;
                        if (null != contentBean) {
                            List<PointTransferListEntity.ContentBean.ListBean> listBeanList = contentBean.getList();
                            if (null != listBeanList) {
                                dataEmpty = true;
                                totalContentBeanList.addAll(listBeanList);
                                if (listBeanList.size() < 20) {
                                    moreEmpty = false;
                                } else {
                                    moreEmpty = true;
                                }
                            } else {
                                dataEmpty = false;
                                moreEmpty = false;
                            }
                        }
                        if (totalContentBeanList.size() > 0) {
                            rv_given_history.setVisibility(View.VISIBLE);
                            tv_no_record.setVisibility(View.GONE);
                        } else {
                            rv_given_history.setVisibility(View.GONE);
                            tv_no_record.setVisibility(View.VISIBLE);
                        }
                        if (null == pointGivenHistoryAdapter) {
                            pointGivenHistoryAdapter = new PointGivenHistoryAdapter(totalContentBeanList);
                            rv_given_history.setLayoutManager(new LinearLayoutManager(GivenPointHistoryActivity.this, LinearLayoutManager.VERTICAL, false));
                            rv_given_history.setAdapter(pointGivenHistoryAdapter);
                        } else {
                            pointGivenHistoryAdapter.notifyDataSetChanged();
                        }
                        rv_given_history.loadMoreFinish(dataEmpty, moreEmpty);
                    } catch (Exception e) {

                    }
                }else{
                    rv_given_history.setVisibility(View.GONE);
                    tv_no_record.setVisibility(View.VISIBLE);
                }
                break;
        }

    }
}
