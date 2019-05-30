package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.adapter.ChoiceParkingAddressAdapter;
import com.eparking.helper.MyRecyclerViewScrollListener;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingApplyModel;
import com.eparking.protocol.ParkingUnitEntity;
import com.eparking.view.AddBuildRoomDialog;
import com.im.utils.CharacterParser;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGBUILDID;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGROOMID;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGROOMNAME;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  e停绑定月卡  选择房间号
 */
public class ChoiceParkingRoomActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private EditText ed_parking_name;
    private TextView tv_hint;
    private ImageView iv_delete_text;
    private RecyclerView rv_community;
    private Button btn_increase;
    private List<String> dataSource;
    private List<String> saveData = new ArrayList<>();
    private ChoiceParkingAddressAdapter choiceParkingAddressAdapter;
    private String buildId; //获取房间号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_eparking);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText("选择房号");
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        ed_parking_name = (EditText) findViewById(R.id.ed_parking_name);
        ed_parking_name.setImeOptions(EditorInfo.IME_ACTION_DONE);
        TextView tv_near_parking = findViewById(R.id.tv_near_parking);
        tv_near_parking.setVisibility(View.VISIBLE);
        tv_near_parking.setText(getResources().getString(R.string.apply_room));
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        tv_hint.setHint("搜索房号");
        iv_delete_text = (ImageView) findViewById(R.id.iv_delete_text);
        rv_community = findViewById(R.id.rv_community);
        btn_increase = findViewById(R.id.btn_increase);
        btn_increase.setVisibility(View.VISIBLE);
        btn_increase.setOnClickListener(this);
        imageView_back.setOnClickListener(this);
        iv_delete_text.setOnClickListener(this);
        Intent intent = getIntent();
        buildId = intent.getStringExtra(PARKINGBUILDID);
        initTextWatcher();
        dataSource = new ArrayList<>();
        if (TextUtils.isEmpty(buildId)) {
            btn_increase.setVisibility(View.GONE);
            addRoomDialog();
        } else {
            btn_increase.setVisibility(View.VISIBLE);
            ParkingApplyModel parkingApplyModel = new ParkingApplyModel(ChoiceParkingRoomActivity.this);
            parkingApplyModel.getUnitList(0, buildId, this);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChoiceParkingRoomActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_community.setLayoutManager(layoutManager);
        choiceParkingAddressAdapter = new ChoiceParkingAddressAdapter(ChoiceParkingRoomActivity.this, dataSource, 1);
        rv_community.setAdapter(choiceParkingAddressAdapter);
        choiceParkingAddressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                choiceParkingAddressAdapter.setSelectPos(i);
                ParkingUnitEntity.ContentBean contentBean = unitList.get(i);
                Intent intent = new Intent();
                intent.putExtra(PARKINGROOMNAME, contentBean.getUnitname() + contentBean.getName() + contentBean.getFloor());
                intent.putExtra(PARKINGROOMID, contentBean.getId());
                setResult(200, intent);
                finish();
            }
        });
        rv_community.addOnScrollListener(new MyRecyclerViewScrollListener(btn_increase));
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private void initTextWatcher() {
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
                    //切换为原来的数据源
                    filterData("");
                } else {
                    tv_hint.setVisibility(View.GONE);
                    iv_delete_text.setVisibility(View.VISIBLE);
                    filterData(edCommunityName);
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
            case R.id.btn_increase:
                addRoomDialog();
                break;
        }
    }

    private AddBuildRoomDialog addBuildRoomDialog;

    private void addRoomDialog() {
        addBuildRoomDialog = new AddBuildRoomDialog(ChoiceParkingRoomActivity.this, R.style.dialog);
        addBuildRoomDialog.show();
        addBuildRoomDialog.btn_define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = addBuildRoomDialog.ed_add_content.getText().toString().trim();
                if (!TextUtils.isEmpty(roomName)) {
                    addBuildRoomDialog.dismiss();
                    Intent intent = new Intent();
                    intent.putExtra(PARKINGROOMID, "");
                    intent.putExtra(PARKINGROOMNAME, roomName);
                    setResult(200, intent);
                    finish();
                } else {
                    ToastUtil.toastShow(ChoiceParkingRoomActivity.this, "房间号不能为空");
                }
            }
        });
        final int size = dataSource.size();
        addBuildRoomDialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBuildRoomDialog.dismiss();
                if (size == 0) {
                    finish();
                }
            }
        });
        addBuildRoomDialog.dialog_title.setText(getResources().getString(R.string.apply_room));
        addBuildRoomDialog.ed_add_content.setHint(getResources().getString(R.string.apply_input_room));
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        dataSource.clear();
        CharacterParser characterParser = CharacterParser.getInstance();
        List<String> filterDateList = new ArrayList<String>();
        if (TextUtils.isEmpty(filterStr)) {
            dataSource.addAll(saveData);
        } else {
            filterDateList.clear();
            for (String str : saveData) {
                if (str.indexOf(filterStr) != -1
                        || characterParser.getSelling(str).startsWith(filterStr)
                        || str.startsWith(filterStr)) {
                    filterDateList.add(str);
                }
            }
        }
        dataSource.addAll(filterDateList);
        uplateAddresslist();
    }

    private List<ParkingUnitEntity.ContentBean> unitList = new ArrayList<>();

    @Override
    public void OnHttpResponse(int what, String result) {
        if (what == 0) {
            try {
                ParkingUnitEntity parkingUnitEntity = GsonUtils.gsonToBean(result, ParkingUnitEntity.class);
                unitList.clear();
                dataSource.clear();
                saveData.clear();
                unitList.addAll(parkingUnitEntity.getContent());
                if (unitList.size() > 0) {
                    for (ParkingUnitEntity.ContentBean contentBean : unitList) {
                        dataSource.add(contentBean.getUnitname() + contentBean.getName() + contentBean.getFloor());
                    }
                    saveData.addAll(dataSource);
                } else {
                    addRoomDialog();
                }
            } catch (Exception e) {

            }
            uplateAddresslist();
        }
    }

    private void uplateAddresslist() {
        if (null != choiceParkingAddressAdapter) {
            choiceParkingAddressAdapter.notifyDataSetChanged();
        }
    }

}
