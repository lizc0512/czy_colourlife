package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.adapter.ChoiceParkingAddressAdapter;
import com.eparking.helper.ConstantKey;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingApplyModel;
import com.eparking.protocol.IsContractEntity;
import com.eparking.protocol.ParkingAddressEntity;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;
import static com.eparking.activity.MonthCardApplyActivity.CARSTATUS;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  e停绑定月卡  选择停车场
 */
public class ChoiceParkingCommunityActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String PARKINGCOMMUNITYID = "parkingcommunityid"; //车场id
    public static final String PARKINGCOMMUNITYUUID = "parkingcommunityuuid"; //车场id
    public static final String PARKINGBUILDID = "parkingbuildid";//楼栋id
    public static final String PARKINGROOMID = "parkingroomid";//房间号 id
    public static final String PARKINGCOMMUNITYNAME = "parkincommunityname"; //车场名称
    public static final String PARKINGBUILDNAME = "parkingbuildname";//楼栋名称
    public static final String PARKINGROOMNAME = "parkingroomname";//房间名称
    public static final String PARKINGROLE = "parkingrole";//用户的角色
    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private EditText ed_parking_name; //搜索的社区名称
    private TextView tv_hint;
    private TextView tv_near_parking;
    private TextView tv_no_result;
    private ImageView iv_delete_text;
    private RecyclerView rv_community;

    private ChoiceParkingAddressAdapter choiceParkingAddressAdapter;
    private List<String> dataSource;
    private String plate;


    private ParkingApplyModel parkingApplyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_eparking);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_choice_parking));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        ed_parking_name = (EditText) findViewById(R.id.ed_parking_name);
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        tv_near_parking = (TextView) findViewById(R.id.tv_near_parking);
        tv_near_parking.setVisibility(View.VISIBLE);
        iv_delete_text = (ImageView) findViewById(R.id.iv_delete_text);
        rv_community = (RecyclerView) findViewById(R.id.rv_community);
        tv_no_result = findViewById(R.id.tv_no_result);
        imageView_back.setOnClickListener(this);
        iv_delete_text.setOnClickListener(this);
        Intent intent = getIntent();
        plate = intent.getStringExtra(CARNUMBER);
        initTextWatcher();
        dataSource = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChoiceParkingCommunityActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_community.setLayoutManager(layoutManager);
        parkingApplyModel = new ParkingApplyModel(ChoiceParkingCommunityActivity.this);
        parkingApplyModel.nearStationList(0, shared.getString(ConstantKey.EPARKINGLAT, ""),
                shared.getString(ConstantKey.EPARKINGLON, ""), 0.15, true, this);
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private void initTextWatcher() {
        ed_parking_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String edCommunityName = ed_parking_name.getText().toString().trim();
                    if (TextUtils.isEmpty(edCommunityName)) {
                        ToastUtil.toastShow(ChoiceParkingCommunityActivity.this, "请输入你要搜索的停车场");
                    } else {
                        tv_hint.setVisibility(View.GONE);
                        rv_community.setVisibility(View.VISIBLE);
                        tv_near_parking.setVisibility(View.INVISIBLE);
                        parkingApplyModel.searchStationList(1, edCommunityName, ChoiceParkingCommunityActivity.this);
                    }
                }
                return false;
            }
        });


        ed_parking_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String edCommunityName = s.toString().trim();
                if (TextUtils.isEmpty(edCommunityName)) {
                    tv_hint.setVisibility(View.VISIBLE);
                    iv_delete_text.setVisibility(View.GONE);
                    tv_near_parking.setVisibility(View.VISIBLE);
                    parkingAddressList.clear();
                    dataSource.clear();
                    parkingAddressList.addAll(saveAddressList);
                    if (parkingAddressList.size() == 0) {
                        tv_no_result.setVisibility(View.VISIBLE);
                        tv_no_result.setText(getResources().getString(R.string.near_noparking_result));
                    }
                    uplateAddresslist();
                } else {
                    tv_hint.setVisibility(View.GONE);
                    iv_delete_text.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.iv_delete_text:
                ed_parking_name.setText("");
                break;
        }
    }

    private List<ParkingAddressEntity.ContentBean> parkingAddressList = new ArrayList<>();
    private List<ParkingAddressEntity.ContentBean> saveAddressList = new ArrayList<>();

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
            case 1:
                try {
                    dataSource.clear();
                    parkingAddressList.clear();
                    if (what == 0) {
                        saveAddressList.clear();
                        saveAddressList.addAll(parkingAddressList);
                    }
                    ParkingAddressEntity parkingAddressEntity = GsonUtils.gsonToBean(result, ParkingAddressEntity.class);
                    parkingAddressList.addAll(parkingAddressEntity.getContent());
                } catch (Exception e) {

                }
                String showResult = "";
                if (what == 0) {
                    showResult = getResources().getString(R.string.near_noparking_result);
                } else {
                    showResult = getResources().getString(R.string.search_noparking_result);
                }
                if (parkingAddressList.size() == 0) {
                    tv_no_result.setVisibility(View.VISIBLE);
                    tv_no_result.setText(showResult);
                } else {
                    tv_no_result.setVisibility(View.GONE);
                }
                uplateAddresslist();
                break;
            case 2:
                int is_accred = 0;
                int is_contract = 0;
                if (!TextUtils.isEmpty(result)) {
                    try {
                        IsContractEntity isContractEntity = GsonUtils.gsonToBean(result, IsContractEntity.class);
                        IsContractEntity.ContentBean contentBean = isContractEntity.getContent();
                        if ("Y".equalsIgnoreCase(contentBean.getIs_accred())) {
                            is_accred = 1;
                        } else {
                            is_accred = 0;
                        }
                        is_contract = contentBean.getIs_contract();
                    } catch (Exception e) {

                    }
                }
                if (is_contract == 1) {
                    ToastUtil.toastShow(ChoiceParkingCommunityActivity.this, getResources().getString(R.string.apply_contract_error));
                } else {
                    Intent intent = new Intent(ChoiceParkingCommunityActivity.this, ChoiceParkingUserInforActivity.class);
                    intent.putExtra(PARKINGCOMMUNITYID, stationId);
                    intent.putExtra(PARKINGCOMMUNITYUUID, stationUUId);
                    intent.putExtra(PARKINGCOMMUNITYNAME, stationName);
                    intent.putExtra(CARNUMBER, plate);
                    intent.putExtra(CARSTATUS, is_accred);
                    startActivity(intent);
                }
                break;
        }
    }

    private String stationId;
    private String stationUUId;
    private String stationName;

    private void uplateAddresslist() {
        for (ParkingAddressEntity.ContentBean contentBean : parkingAddressList) {
            dataSource.add(contentBean.getName());
        }
        if (null != choiceParkingAddressAdapter) {
            choiceParkingAddressAdapter.notifyDataSetChanged();
        } else {
            choiceParkingAddressAdapter = new ChoiceParkingAddressAdapter(ChoiceParkingCommunityActivity.this, dataSource, 0);
            rv_community.setAdapter(choiceParkingAddressAdapter);
        }
        choiceParkingAddressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                choiceParkingAddressAdapter.setSelectPos(i);
                ParkingAddressEntity.ContentBean contentBean = parkingAddressList.get(i);
                stationId = contentBean.getId();
                stationUUId = contentBean.getUuid();
                stationName = contentBean.getName();
                parkingApplyModel.isPlateContract(2, plate, stationId, ChoiceParkingCommunityActivity.this);
            }
        });
    }
}
