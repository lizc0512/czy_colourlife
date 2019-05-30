package com.eparking.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.adapter.EparkingServiceAdapter;
import com.eparking.helper.MapNaviUtils;
import com.eparking.helper.OptionsPickerInterface;
import com.eparking.helper.OptionsPickerViewUtils;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingHomeModel;
import com.eparking.protocol.EparkingServiceEntity;
import com.eparking.view.keyboard.KeyboardInputController;
import com.eparking.view.keyboard.OnInputChangedListener;
import com.eparking.view.keyboard.PopupHelper;
import com.eparking.view.keyboard.PopupKeyboard;
import com.eparking.view.keyboard.view.InputView;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.csh.colourful.life.view.recycleview.WrapHeightGridLayoutManager;
import cn.net.cyberway.R;
import cn.net.cyberway.utils.CityCustomConst;

import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;
import static com.eparking.activity.InputOpenCodeActivity.STATIONID;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/18 17:53
 * @change
 * @chang time
 * @class describe  代缴停车费
 */
public class SurrenderParkingFeeActivity extends BaseActivity implements View.OnClickListener, OptionsPickerInterface, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private PopupKeyboard mPopupKeyboard;
    private InputView inputView;
    private List<EparkingServiceEntity.ContentBean> serviceEntityList = new ArrayList<>();
    private RecyclerView rv_service;
    private EparkingServiceAdapter serviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surrender_parkingfee);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_surrend_parkfee));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        rv_service = (RecyclerView) findViewById(R.id.rv_service);
        initView();
        initServiceView();
        ParkingActivityUtils.getInstance().addActivity(this);
        ParkingHomeModel parkingHomeModel = new ParkingHomeModel(SurrenderParkingFeeActivity.this);
        parkingHomeModel.getHomeAvertData(1, this);
    }


    private void initServiceView() {
        GridLayoutManager gridLayoutManager = new WrapHeightGridLayoutManager(SurrenderParkingFeeActivity.this, 2);
        //设置布局管理器
        rv_service.setLayoutManager(gridLayoutManager);
        serviceAdapter = new EparkingServiceAdapter(serviceEntityList);
        rv_service.setAdapter(serviceAdapter);
        serviceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Intent intent = new Intent(SurrenderParkingFeeActivity.this, EParkingCardHolderActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initView() {
        TextView tv_bind_history = findViewById(R.id.tv_bind_history);
        LinearLayout item_bind_layout = findViewById(R.id.item_bind_layout);
        item_bind_layout.setOnClickListener(this);
        tv_bind_history.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_bind_history.setOnClickListener(this);
        Button btn_monthcard_renewal = findViewById(R.id.btn_monthcard_renewal);
        btn_monthcard_renewal.setOnClickListener(this);
        Button btn_temporary_fee = findViewById(R.id.btn_temporary_fee);
        btn_temporary_fee.setOnClickListener(this);
        inputView = findViewById(R.id.input_view);
        final Button tv_change_cartype = findViewById(R.id.tv_change_cartype);
        // 创建弹出键盘
        mPopupKeyboard = new PopupKeyboard(SurrenderParkingFeeActivity.this);
        mPopupKeyboard.getKeyboardEngine().setLocalProvinceName(shared.getString(CityCustomConst.LOCATION_PROVINCE, ""));
        mPopupKeyboard.attach(inputView, SurrenderParkingFeeActivity.this);
        mPopupKeyboard.getController()
                .setDebugEnabled(true)
                .bindLockTypeProxy(new KeyboardInputController.ButtonProxyImpl(tv_change_cartype) {
                    @Override
                    public void onNumberTypeChanged(boolean isNewEnergyType) {
                        super.onNumberTypeChanged(isNewEnergyType);
                        if (isNewEnergyType) {
                            tv_change_cartype.setText(getResources().getString(R.string.switch_normal_car));
                        } else {
                            tv_change_cartype.setText(getResources().getString(R.string.switch_new_energy));
                        }
                    }
                }).addOnInputChangedListener(new OnInputChangedListener() {
            @Override
            public void onChanged(String number, boolean isCompleted) {

            }

            @Override
            public void onCompleted(String number, boolean isAutoCompleted) {
                if (isAutoCompleted) {
                    PopupHelper.dismissFromActivity(SurrenderParkingFeeActivity.this);
                }
            }
        });
        MapNaviUtils.setInputViewData(SurrenderParkingFeeActivity.this, inputView, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_bind_history: //关于车牌号的选择
                OptionsPickerViewUtils.showPickerView(SurrenderParkingFeeActivity.this, R.string.car_brand, 0, null, this);
                break;
            case R.id.btn_monthcard_renewal:
                if (inputView.isCompleted()) {
                    Intent monthcardRenewalIntent = new Intent(SurrenderParkingFeeActivity.this, MonthCardRenewalActivity.class);
                    monthcardRenewalIntent.putExtra(CARNUMBER, inputView.getNumber());
                    monthcardRenewalIntent.putExtra(STATIONID, "");
                    startActivity(monthcardRenewalIntent);
                } else {
                    ToastUtil.toastShow(SurrenderParkingFeeActivity.this, getResources().getString(R.string.please_compelete_plate));
                }
                break;
            case R.id.btn_temporary_fee:
                if (inputView.isCompleted()) {
                    Intent temporaryFeeIntent = new Intent(SurrenderParkingFeeActivity.this, TemporaryParkingOrderActivity.class);
                    temporaryFeeIntent.putExtra(CARNUMBER, inputView.getNumber());
                    startActivity(temporaryFeeIntent);
                } else {
                    ToastUtil.toastShow(SurrenderParkingFeeActivity.this, getResources().getString(R.string.please_compelete_plate));
                }
                break;
            case R.id.item_bind_layout:
                if (null != mPopupKeyboard && mPopupKeyboard.isShown()) {
                    mPopupKeyboard.dismiss(SurrenderParkingFeeActivity.this);
                }
                break;
        }
    }

    @Override
    public void choicePickResult(int type, String choiceText, String choiceId) {
        inputView.updateNumber(choiceText);
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 1:
                try {
                    serviceEntityList.clear();
                    EparkingServiceEntity eparkingServiceEntity = GsonUtils.gsonToBean(result, EparkingServiceEntity.class);
                    serviceEntityList.addAll(eparkingServiceEntity.getContent());
                } catch (Exception e) {

                }
                serviceAdapter.notifyDataSetChanged();
                break;
        }
    }
}