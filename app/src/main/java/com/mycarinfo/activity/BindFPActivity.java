package com.mycarinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.activity.WebViewActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.mycarinfo.adapter.BindFpAdapter;
import com.mycarinfo.model.MyCarInfoModel;
import com.myproperty.model.VipFpInfoModel;
import com.myproperty.protocol.CTGLORYTICKETMYTICKETLISTDATA;
import com.myproperty.protocol.ColorhouseLocallistGetApi;
import com.myproperty.protocol.VehicleBindmealPostApi;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

public class BindFPActivity extends BaseActivity implements View.OnClickListener, HttpApiResponse {
    private ImageView mBack;
    private TextView mTitle;
    private TextView mRightText;
    private MyCarInfoModel myCarInfoModel;
    private VipFpInfoModel vipFpInfoModel;
    private ListView listView;
    private BindFpAdapter adapter;
    private Button btn;
    private String vehicle_id, pano, url;
    private ImageView img_empty;
    private TextView tv_tips;
    public static int i;
    private boolean isSelect = false;
    private List<CTGLORYTICKETMYTICKETLISTDATA> list = new ArrayList<CTGLORYTICKETMYTICKETLISTDATA>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_fp);
        initPublic();
        initView();
    }

    private void initPublic() {
        Intent intent = this.getIntent();
        vehicle_id = intent.getStringExtra("vehicle_id");
        img_empty = (ImageView) findViewById(R.id.img_empty);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        mRightText = (TextView) findViewById(R.id.user_top_view_right);
        mTitle.setText(getResources().getString(R.string.car_bind_ticket));
        mRightText.setText(getResources().getString(R.string.car_help_desc));
        mBack.setOnClickListener(this);
        mRightText.setOnClickListener(this);
        ThemeStyleHelper.rightTexteFrameLayout(getApplicationContext(), czy_title_layout, mBack, mTitle, mRightText);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.bindfp_listview);
        btn = (Button) findViewById(R.id.btn_bindfp_ok);
        btn.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isSelect = true;
                pano = list.get(position).pano;
                adapter.setCheckId(list.get(position).pano);
            }
        });
        myCarInfoModel = new MyCarInfoModel(this);
        vipFpInfoModel = new VipFpInfoModel(this);
        vipFpInfoModel.getlocalList(this, true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                BindFPActivity.this.finish();
                break;
            case R.id.user_top_view_right:
                Intent intent = new Intent(BindFPActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEBTITLE, "");
                intent.putExtra(WebViewActivity.WEBURL, url);
                startActivity(intent);
                break;
            case R.id.btn_bindfp_ok:
                if (isSelect == true) {
                    myCarInfoModel.postbindMeal(this, vehicle_id, pano);
                } else {
                    ToastUtil.toastShow(BindFPActivity.this, getResources().getString(R.string.car_choice_ticket));
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(VehicleBindmealPostApi.class)) {//绑定饭票回馈
            VehicleBindmealPostApi vehicleBindmealPostApi = (VehicleBindmealPostApi) api;
            if (vehicleBindmealPostApi.response.code == 0) {
                ToastUtil.toastShow(BindFPActivity.this, getResources().getString(R.string.user_bind_success));
                setResult(1);
            } else {
                String a = vehicleBindmealPostApi.response.message;
                ToastUtil.toastShow(BindFPActivity.this, a);
            }
            BindFPActivity.this.finish();
        } else if (api.getClass().equals(ColorhouseLocallistGetApi.class)) {//获得饭票信息
            ColorhouseLocallistGetApi colorhouseLocallistGetApi = (ColorhouseLocallistGetApi) api;
            list = colorhouseLocallistGetApi.response.data.list;
            url = colorhouseLocallistGetApi.response.data.help_url;
            if (!url.isEmpty()) {
                mRightText.setVisibility(View.VISIBLE);
            } else {
                mRightText.setVisibility(View.GONE);
            }
            if (list.isEmpty() || list.size() == 0) {
                img_empty.setVisibility(View.VISIBLE);
                tv_tips.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
            } else {
                img_empty.setVisibility(View.GONE);
                tv_tips.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
                if (adapter == null) {
                    adapter = new BindFpAdapter(this, list);
                    listView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

        }
    }
}
