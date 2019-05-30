package com.eparking.adapter;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.eparking.activity.CarsBindActivity;
import com.eparking.activity.ChoiceParkingUserInforActivity;
import com.eparking.activity.EParkingHistoryRecordActivity;
import com.eparking.activity.EParkingHomeActivity;
import com.eparking.activity.MonthCardRenewalActivity;
import com.eparking.activity.ShareParkingSpaceActivity;
import com.eparking.activity.ShareParkingSpaceRecoActivity;
import com.eparking.activity.SurrenderParkingFeeActivity;
import com.eparking.activity.TemporaryParkingOrderActivity;
import com.eparking.helper.MapNaviUtils;
import com.eparking.helper.OptionsPickerInterface;
import com.eparking.helper.OptionsPickerViewUtils;
import com.eparking.protocol.ParkingHomeEntity;
import com.eparking.view.keyboard.KeyboardInputController;
import com.eparking.view.keyboard.OnInputChangedListener;
import com.eparking.view.keyboard.PopupHelper;
import com.eparking.view.keyboard.PopupKeyboard;
import com.eparking.view.keyboard.view.InputView;
import com.nohttp.utils.GlideImageLoader;
import com.user.UserAppConst;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.CityCustomConst;

import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGCOMMUNITYID;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGCOMMUNITYNAME;
import static com.eparking.activity.InputOpenCodeActivity.STATIONID;
import static com.eparking.activity.MonthCardApplyActivity.CARSTATUS;
import static com.eparking.activity.ShareParkingSpaceActivity.LOCKCODE;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/17 14:56
 * @change
 * @chang time
 * @class describe  e停首页
 */

public class EparkingHomeAdapter extends PagerAdapter {

    private boolean isMultiScr;

    private List<ParkingHomeEntity.ContentBean> homeParkingList;
    private Activity activity;

    public EparkingHomeAdapter(Activity activity, boolean isMultiScr, List<ParkingHomeEntity.ContentBean> homeParkingList) {
        this.activity = activity;
        this.homeParkingList = homeParkingList;
    }

    @Override
    public int getCount() {
        return homeParkingList == null ? 0 : homeParkingList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        final ParkingHomeEntity.ContentBean contentBean = homeParkingList.get(position);
        String templateType = contentBean.getCard_type();
        View homeView = null;
        switch (templateType.trim()) {
            case "my_home":
                homeView = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_temporary_stop_unbind, null);
                initUnBindView(homeView, contentBean);
                break;
            case "my_car": //临停在场
                int isPark = contentBean.getIs_park();
                if (isPark == 1) { //临停在场
                    homeView = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_temporary_stop_bindcar, null);
                    initTemporaryView(homeView, contentBean);
                } else {//临停离场
                    homeView = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_temporary_applymonthcard, null);
                    initTemporayMonthCard(homeView, contentBean);
                }
                PopupHelper.dismissFromActivity(activity);
                break;
            case "my_contract": //月卡
                homeView = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_stop_monthcard, null);
                initMonthCardView(homeView, contentBean);
                PopupHelper.dismissFromActivity(activity);
                break;
            case "my_lock": //车位锁
                homeView = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_share_sparkingspace, null);
                initSparkingSpaceView(homeView, contentBean);
                PopupHelper.dismissFromActivity(activity);
                break;
            default:
                homeView = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_temporary_stop_unbind, null);
                initUnBindView(homeView, contentBean);
                break;
        }
        container.addView(homeView);
        return homeView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }


    private void initUnBindView(View rootView, final ParkingHomeEntity.ContentBean contentBean) {
        TextView tv_bind_history = rootView.findViewById(R.id.tv_bind_history);
        LinearLayout item_bind_layout = rootView.findViewById(R.id.item_bind_layout);
        tv_bind_history.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        Button btn_monthcard_renewal = rootView.findViewById(R.id.btn_monthcard_renewal);

        Button btn_temporary_fee = rootView.findViewById(R.id.btn_temporary_fee);

        TextView tv_bind_car = rootView.findViewById(R.id.tv_bind_car);

        final InputView inputView = rootView.findViewById(R.id.input_view);
        final Button tv_change_cartype = rootView.findViewById(R.id.tv_change_cartype);
        // 创建弹出键盘
        final PopupKeyboard mPopupKeyboard = new PopupKeyboard(activity);
        SharedPreferences sharedPreferences = activity.getSharedPreferences(UserAppConst.USERINFO, 0);
        mPopupKeyboard.getKeyboardEngine().setLocalProvinceName(sharedPreferences.getString(CityCustomConst.LOCATION_PROVINCE, ""));
        mPopupKeyboard.attach(inputView, activity);
        mPopupKeyboard.getController()
                .setDebugEnabled(true)
                .bindLockTypeProxy(new KeyboardInputController.ButtonProxyImpl(tv_change_cartype) {
                    @Override
                    public void onNumberTypeChanged(boolean isNewEnergyType) {
                        super.onNumberTypeChanged(isNewEnergyType);
                        if (isNewEnergyType) {
                            tv_change_cartype.setText(activity.getResources().getString(R.string.switch_normal_car));
                        } else {
                            tv_change_cartype.setText(activity.getResources().getString(R.string.switch_new_energy));
                        }
                    }
                }).addOnInputChangedListener(new OnInputChangedListener() {
            @Override
            public void onChanged(String number, boolean isCompleted) {

            }

            @Override
            public void onCompleted(String number, boolean isAutoCompleted) {
                if (isAutoCompleted) {
                    PopupHelper.dismissFromActivity(activity);
                }
            }
        });
        item_bind_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mPopupKeyboard && mPopupKeyboard.isShown()) {
                    mPopupKeyboard.dismiss(activity);
                }
            }
        });
        tv_bind_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出选择车牌的框
                if (null != mPopupKeyboard && mPopupKeyboard.isShown()) {
                    mPopupKeyboard.dismiss(activity);
                }
                showCarNumberView(activity, inputView);
            }
        });
        btn_monthcard_renewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputView.isCompleted()) {
                    Intent intent = new Intent(activity, MonthCardRenewalActivity.class);
                    intent.putExtra(CARNUMBER, inputView.getNumber());
                    intent.putExtra(STATIONID, "");
                    activity.startActivity(intent);
                } else {
                    ToastUtil.toastShow(activity, activity.getResources().getString(R.string.please_compelete_plate));
                }

            }
        });
        btn_temporary_fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputView.isCompleted()) {
                    Intent intent = new Intent(activity, TemporaryParkingOrderActivity.class);
                    intent.putExtra(CARNUMBER, inputView.getNumber());
                    activity.startActivity(intent);
                } else {
                    ToastUtil.toastShow(activity, activity.getResources().getString(R.string.please_compelete_plate));
                }
            }
        });
        tv_bind_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //绑定车辆  输入车牌号的页面
                Intent intent = new Intent(activity, CarsBindActivity.class);
                activity.startActivity(intent);
            }
        });
        MapNaviUtils.setInputViewData(activity, inputView, false);
    }


    private void showCarNumberView(Activity activity, final InputView inputView) {//
        OptionsPickerViewUtils.showPickerView(activity, R.string.car_brand, 0, null, new OptionsPickerInterface() {
            @Override
            public void choicePickResult(int type, String choiceText, String choiceId) {
                inputView.updateNumber(choiceText);
            }
        });
    }

    private void initTemporaryView(View rootView, final ParkingHomeEntity.ContentBean contentBean) {
        ImageView iv_car_model = rootView.findViewById(R.id.iv_car_model);
        TextView tv_car_number = rootView.findViewById(R.id.tv_car_number);
        TextView tv_parking_space = rootView.findViewById(R.id.tv_parking_space);
        TextView tv_enter_time = rootView.findViewById(R.id.tv_enter_time);
        TextView tv_sparking_time = rootView.findViewById(R.id.tv_sparking_time);
        TextView tv_pay_count = rootView.findViewById(R.id.tv_pay_count);
        Button btn_payoff_the_count = rootView.findViewById(R.id.btn_payoff_the_count);
        GlideImageLoader.loadImageDefaultDisplay(activity, contentBean.getLogo(), iv_car_model, R.drawable.eparking_img_carmodel, R.drawable.eparking_img_carmodel);
        tv_car_number.setText(contentBean.getPlate());
        tv_parking_space.setText(contentBean.getStation_name());
        tv_enter_time.setText(TimeUtil.formatTime(contentBean.getArrival(), "yyyy-MM-dd HH:mm"));
        tv_sparking_time.setText(TimeUtil.dateDiff(contentBean.getArrival(), contentBean.getNow_time()));
        tv_pay_count.setText(contentBean.getTemp_money());
        btn_payoff_the_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TemporaryParkingOrderActivity.class);
                intent.putExtra(CARNUMBER, contentBean.getPlate());
                activity.startActivity(intent);
            }
        });
        TextView tv_help_payoff = rootView.findViewById(R.id.tv_help_payoff);
        tv_help_payoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SurrenderParkingFeeActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    private void initMonthCardView(View rootView, final ParkingHomeEntity.ContentBean contentBean) {
        ImageView iv_car_model = rootView.findViewById(R.id.iv_car_model);
        TextView tv_car_number = rootView.findViewById(R.id.tv_car_number);
        TextView tv_parking_space = rootView.findViewById(R.id.tv_parking_space);
        final ImageView iv_monthcard_sign = rootView.findViewById(R.id.iv_monthcard_sign);
        TextView tv_monthcard_expire = rootView.findViewById(R.id.tv_monthcard_expire);
        TextView tv_parking_status = rootView.findViewById(R.id.tv_parking_status);
        tv_monthcard_expire.setText(TimeUtil.formatTime(contentBean.getEnd_time(), "yyyy-MM-dd"));
        GlideImageLoader.loadImageDefaultDisplay(activity, contentBean.getLogo(), iv_car_model, R.drawable.eparking_img_carmodel, R.drawable.eparking_img_carmodel);
        final String plate = contentBean.getPlate();
        tv_car_number.setText(plate);
        tv_parking_space.setText(contentBean.getStation_name());
        int isPark = contentBean.getIs_park();
        if (isPark == 1) {
            tv_parking_status.setText(activity.getResources().getString(R.string.car_parking_present));
        } else {
            tv_parking_status.setText(activity.getResources().getString(R.string.car_parking_leave));
        }
        Button btn_monthcard_renewal = rootView.findViewById(R.id.btn_monthcard_renewal);
        btn_monthcard_renewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MonthCardRenewalActivity.class);
                intent.putExtra(CARNUMBER, contentBean.getPlate());
                intent.putExtra(STATIONID, contentBean.getStation_id());
                activity.startActivity(intent);
            }
        });
        TextView tv_help_payoff = rootView.findViewById(R.id.tv_help_payoff);
        tv_help_payoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SurrenderParkingFeeActivity.class);
                activity.startActivity(intent);
            }
        });
        final LinearLayout payoff_record_layout = rootView.findViewById(R.id.payoff_record_layout);
        payoff_record_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EParkingHistoryRecordActivity.class);
                activity.startActivity(intent);
            }
        });
        final String carLockStatus = contentBean.getStatus();
        iv_monthcard_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //锁车
                ((EParkingHomeActivity) activity).carLockController(iv_monthcard_sign, contentBean);
            }
        });
        if ("close".equalsIgnoreCase(carLockStatus)) {
            iv_monthcard_sign.setImageResource(R.drawable.eparking_img_lockcar);
        } else {
            iv_monthcard_sign.setImageResource(R.drawable.eparking_img_unlockcar);
        }
    }

    private void initSparkingSpaceView(View rootView, final ParkingHomeEntity.ContentBean contentBean) {
        ImageView iv_car_model = rootView.findViewById(R.id.iv_car_model);
        TextView tv_car_number = rootView.findViewById(R.id.tv_car_number);
        TextView tv_parking_space = rootView.findViewById(R.id.tv_parking_space);
        final TextView tv_status = rootView.findViewById(R.id.tv_status);
        TextView tv_place_number = rootView.findViewById(R.id.tv_place_number);
        TextView tv_place_status = rootView.findViewById(R.id.tv_place_status);
        final String currentStatus = contentBean.getStatus();
        final String etCode = contentBean.getEtcode();
        GlideImageLoader.loadImageDefaultDisplay(activity, contentBean.getLogo(), iv_car_model, R.drawable.eparking_img_canuse, R.drawable.eparking_img_canuse);
        tv_car_number.setText(contentBean.getLock_name());
        tv_parking_space.setText(contentBean.getStation_name());
        tv_place_number.setText(etCode);
        tv_place_status.setText(contentBean.getLock_user_name());
        Button btn_share_place = rootView.findViewById(R.id.btn_share_place);
        btn_share_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ShareParkingSpaceActivity.class);
                intent.putExtra(LOCKCODE, etCode);
                activity.startActivity(intent);
            }
        });
        TextView tv_help_payoff = rootView.findViewById(R.id.tv_help_payoff);
        tv_help_payoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SurrenderParkingFeeActivity.class);
                activity.startActivity(intent);
            }
        });
        LinearLayout share_record_layout = rootView.findViewById(R.id.share_record_layout);
        share_record_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ShareParkingSpaceRecoActivity.class);
//                Intent intent = new Intent(activity, EparkingLockStatusActivity.class);
//                intent.putExtra(ETCODE, etCode);
                activity.startActivity(intent);
            }
        });
        tv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EParkingHomeActivity) activity).openateCarLock(tv_status, contentBean);
            }
        });
        if ("close".equalsIgnoreCase(currentStatus)) {
            tv_status.setText(activity.getResources().getString(R.string.lock_down));
        } else {
            tv_status.setText(activity.getResources().getString(R.string.lock_up));
        }
    }

    private void initTemporayMonthCard(View homeView, final ParkingHomeEntity.ContentBean contentBean) {
        ImageView iv_car_model = homeView.findViewById(R.id.iv_car_model);
        TextView tv_car_number = homeView.findViewById(R.id.tv_car_number);
        TextView tv_parking_space = homeView.findViewById(R.id.tv_parking_space);
        TextView tv_leave_time = homeView.findViewById(R.id.tv_leave_time);
        TextView tv_parking_status = homeView.findViewById(R.id.tv_parking_status);
        GlideImageLoader.loadImageDefaultDisplay(activity, contentBean.getLogo(), iv_car_model, R.drawable.eparking_img_carmodel, R.drawable.eparking_img_carmodel);
        final String carPlate = contentBean.getPlate();
        tv_car_number.setText(carPlate);
        tv_parking_space.setText(contentBean.getStation_name());
        tv_leave_time.setText(TimeUtil.formatTime(contentBean.getLeave(), "yyyy-MM-dd HH:mm"));
        tv_parking_status.setText(activity.getResources().getString(R.string.car_parking_leave));
        Button btn_monthcard_apply = homeView.findViewById(R.id.btn_monthcard_apply);
        btn_monthcard_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ChoiceParkingUserInforActivity.class);
                intent.putExtra(CARNUMBER, carPlate);
                intent.putExtra(PARKINGCOMMUNITYNAME, contentBean.getStation_name());
                intent.putExtra(PARKINGCOMMUNITYID, contentBean.getStation_id());
                intent.putExtra(CARSTATUS, 1);
                activity.startActivity(intent);
            }
        });
        TextView tv_help_payoff = homeView.findViewById(R.id.tv_help_payoff);
        tv_help_payoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SurrenderParkingFeeActivity.class);
                activity.startActivity(intent);
            }
        });
        LinearLayout payoff_record_layout = homeView.findViewById(R.id.payoff_record_layout);
        payoff_record_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EParkingHistoryRecordActivity.class);
                activity.startActivity(intent);
            }
        });
    }

}
