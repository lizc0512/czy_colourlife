package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.eparking.adapter.ChoiceParkingAddressAdapter;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingApplyModel;
import com.eparking.protocol.ParkingAddressEntity;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  找车位搜索停车场
 */
public class SearchParkingActivity extends BaseActivity implements View.OnClickListener, Inputtips.InputtipsListener, NewHttpResponse {

    public static final String CITYCODE = "citycode";
    public static final String CHOICELAT = "choicelat";
    public static final String CHOICELON = "choieclon";
    public static final String CHOICEADDRESS = "choiceaddress";
    private ImageView imageView_back;//返回
    private EditText ed_search_content;
    private TextView tv_hint;
    private ImageView iv_delete_text;
    private TextView tv_cancel;
    private TextView tv_no_result;
    private RecyclerView lv_parking;
    private String cityCode;
    private ParkingApplyModel parkingApplyModel;
    private String edCommunityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_parking);
        imageView_back = findViewById(R.id.user_top_view_back);
        ed_search_content = findViewById(R.id.ed_search_content);
        tv_hint = findViewById(R.id.tv_hint);
        iv_delete_text = findViewById(R.id.iv_delete_text);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_no_result = findViewById(R.id.tv_no_result);
        lv_parking = findViewById(R.id.lv_parking);
        imageView_back.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        iv_delete_text.setOnClickListener(this);
        Intent intent = getIntent();
        cityCode = intent.getStringExtra(CITYCODE);
        parkingApplyModel = new ParkingApplyModel(SearchParkingActivity.this);
        initTextWatcher();
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private void initTextWatcher() {
        ed_search_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edCommunityName = s.toString().trim();
                if (TextUtils.isEmpty(edCommunityName)) {
                    tv_hint.setVisibility(View.VISIBLE);
                    iv_delete_text.setVisibility(View.GONE);
                    dataSource.clear();
                    if (null != choiceParkingAddressAdapter) {
                        choiceParkingAddressAdapter.notifyDataSetChanged();
                    }
                } else {
                    tv_hint.setVisibility(View.GONE);
                    iv_delete_text.setVisibility(View.VISIBLE);
                    parkingApplyModel.searchStationList(1, edCommunityName, SearchParkingActivity.this);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.iv_delete_text:
                ed_search_content.setText("");
                break;
        }
    }

    private List<String> dataSource = new ArrayList<>();
    private ChoiceParkingAddressAdapter choiceParkingAddressAdapter;

    private void setAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchParkingActivity.this, LinearLayoutManager.VERTICAL, false);
        lv_parking.setLayoutManager(layoutManager);
        if (null == choiceParkingAddressAdapter) {
            choiceParkingAddressAdapter = new ChoiceParkingAddressAdapter(SearchParkingActivity.this, dataSource, -1);
            lv_parking.setAdapter(choiceParkingAddressAdapter);
        } else {
            choiceParkingAddressAdapter.notifyDataSetChanged();
        }
        choiceParkingAddressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                double choiceLat = 0;
                double choiceLon = 0;
                if (parkingAddressList.size() > 0) {
                    ParkingAddressEntity.ContentBean contentBean = parkingAddressList.get(i);
                    choiceLat = contentBean.getLatitude();
                    choiceLon = contentBean.getLongitude();
                } else {
                    Tip tip = tipList.get(i);
                    LatLonPoint latLonPoint = tip.getPoint();
                    choiceLat = latLonPoint.getLatitude();
                    choiceLon = latLonPoint.getLongitude();
                }
                Intent intent = new Intent();
                intent.putExtra(CHOICELAT, choiceLat);
                intent.putExtra(CHOICELON, choiceLon);
                intent.putExtra(CHOICEADDRESS, dataSource.get(i));
                setResult(200, intent);
                finish();
            }
        });
    }


    private void searchPoi(String areaAddress) {
        InputtipsQuery inputquery = new InputtipsQuery(areaAddress, cityCode);
        inputquery.setCityLimit(true);//限制在当前城市
        inputquery.setType("1509");
        Inputtips inputTips = new Inputtips(SearchParkingActivity.this, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();

    }

    private ArrayList<Tip> tipList = new ArrayList<>();

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        parkingAddressList.clear();
        tipList.clear();
        dataSource.clear();
        for (Tip tip : list) {
            String name = tip.getName();
            String typeCode = tip.getTypeCode();
            switch (typeCode) {
                case "150900":
                case "150903":
                case "150904":
                case "150905":
                case "150906":
                    tipList.add(tip);
                    dataSource.add(name);
                    break;
                default:
                    continue;
            }
        }
        if (tipList.size() == 0) {
            tv_no_result.setVisibility(View.VISIBLE);
            lv_parking.setVisibility(View.GONE);
        } else {
            tv_no_result.setVisibility(View.GONE);
            lv_parking.setVisibility(View.VISIBLE);
        }
        setAdapter();
    }

    private List<ParkingAddressEntity.ContentBean> parkingAddressList = new ArrayList<>();

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 1:
                try {
                    ParkingAddressEntity parkingAddressEntity = GsonUtils.gsonToBean(result, ParkingAddressEntity.class);
                    parkingAddressList.clear();
                    dataSource.clear();
                    parkingAddressList.addAll(parkingAddressEntity.getContent());
                    for (ParkingAddressEntity.ContentBean contentBean : parkingAddressList) {
                        dataSource.add(contentBean.getName());
                    }
                    if (parkingAddressList.size() > 0) {
                        setAdapter();
                    } else {
                        searchPoi(edCommunityName);
                    }
                } catch (Exception e) {
                    searchPoi(edCommunityName);
                }
                break;
        }
    }
}
