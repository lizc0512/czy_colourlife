package com.customerInfo.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.Util;
import com.customerInfo.model.DeliveryAddressModel;
import com.customerInfo.protocol.RegionEntity;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

public class DeliveryAddressDialog extends Dialog implements NewHttpResponse, View.OnClickListener {

    private static final int INDEX_TAB_PROVINCE = 0;
    private static final int INDEX_TAB_CITY = 1;
    private static final int INDEX_TAB_COUNTY = 2;
    private static final int INDEX_TAB_STREET = 3;
    private static final int INDEX_TAB_COMMUNITY = 4;
    private TextView textViewProvince;
    private TextView textViewCity;
    private TextView textViewCounty;
    private TextView textViewStreet;
    private TextView textViewCommunity;
    private View indicator;
    private ListView listView;
    private int tabIndex = INDEX_TAB_PROVINCE;
    private DeliveryAddressModel deliveryAddressModel;

    private String provincName;
    private String provinceId;
    private String cityName;
    private String cityId;
    private String countyName;
    private String countyId;
    private String streetId;
    private String streetName = "";
    private String communityId;
    private String communityName = "";
    private Context mContext;
    private OnAddressSelectedListener onAddressSelectedListener;

    public DeliveryAddressDialog(Context context, OnAddressSelectedListener onAddressSelectedListener) {
        super(context, R.style.bottom_dialog);
        mContext = context;
        this.onAddressSelectedListener = onAddressSelectedListener;
        init(context);
    }

    public DeliveryAddressDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public DeliveryAddressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_address_selector, null);
        setContentView(view);
        initView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = Util.DensityUtil.dip2px(context, 360);
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
    }

    private void initView(View view) {
        textViewProvince = (TextView) view.findViewById(R.id.textViewProvince);
        textViewCity = (TextView) view.findViewById(R.id.textViewCity);
        textViewCounty = (TextView) view.findViewById(R.id.textViewCounty);
        textViewStreet = (TextView) view.findViewById(R.id.textViewStreet);
        textViewCommunity = (TextView) view.findViewById(R.id.textViewCommunity);
        indicator = view.findViewById(R.id.indicator);
        listView = (ListView) view.findViewById(R.id.listView);
        textViewProvince.setOnClickListener(this);
        textViewCity.setOnClickListener(this);
        textViewCounty.setOnClickListener(this);
        textViewStreet.setOnClickListener(this);
        textViewCommunity.setOnClickListener(this);
        updateIndicator();
        countyAdapter = new CountyAdapter();
        listView.setAdapter(countyAdapter);
        deliveryAddressModel = new DeliveryAddressModel(mContext);
        deliveryAddressModel.getDeliveryRegionData(0, "", this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RegionEntity.ContentBean item = null;
                switch (tabIndex) {
                    case INDEX_TAB_PROVINCE:
                        item = provinces.get(position);
                        provinceId = item.getId();
                        provincName = item.getName();
                        textViewProvince.setText(provincName);
                        textViewCity.setText("请选择");
                        textViewCounty.setText("请选择");
                        textViewStreet.setText("请选择");
                        textViewCommunity.setText("请选择");
                        countyAdapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(0);
                        deliveryAddressModel.getDeliveryRegionData(1, provinceId, DeliveryAddressDialog.this);
                        break;
                    case INDEX_TAB_CITY:
                        item = cities.get(position);
                        cityId = item.getId();
                        cityName = item.getName();
                        textViewCity.setText(cityName);
                        textViewCounty.setText("请选择");
                        textViewStreet.setText("请选择");
                        textViewCommunity.setText("请选择");
                        countyAdapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(0);
                        deliveryAddressModel.getDeliveryRegionData(2, cityId, DeliveryAddressDialog.this);
                        break;
                    case INDEX_TAB_COUNTY:
                        item = counties.get(position);
                        countyName = item.getName();
                        countyId = item.getId();
                        textViewCounty.setText(countyName);
                        textViewStreet.setText("请选择");
                        textViewCommunity.setText("请选择");
                        listView.smoothScrollToPosition(0);
                        deliveryAddressModel.getDeliveryRegionData(3, countyId, DeliveryAddressDialog.this);
                        break;
                    case INDEX_TAB_STREET:
                        item = streets.get(position);
                        streetName = item.getName();
                        streetId = item.getId();
                        textViewStreet.setText(streetName);
                        textViewCommunity.setText("请选择");
                        listView.smoothScrollToPosition(0);
                        deliveryAddressModel.getDeliveryRegionData(4, streetId, DeliveryAddressDialog.this);
                        break;
                    case INDEX_TAB_COMMUNITY:
                        item = communitys.get(position);
                        communityName = item.getName();
                        communityId = item.getId();
                        textViewStreet.setText(communityName);
                        onAddressSelectedListener.onAddressSelected(provincName + "," + provinceId, cityName + "," + cityId, countyName + "," + countyId, streetName + "," + streetId, communityName + "," + communityId);
                        dismiss();
                        break;
                }
            }
        });
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    provinces.clear();
                    try {
                        RegionEntity regionEntity = GsonUtils.gsonToBean(result, RegionEntity.class);
                        provinces.addAll(regionEntity.getContent());
                    } catch (Exception e) {

                    }
                    countyAdapter.setResourceData(provinces);
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    tabIndex = INDEX_TAB_CITY;
                    cities.clear();
                    try {
                        RegionEntity regionEntity = GsonUtils.gsonToBean(result, RegionEntity.class);
                        cities.addAll(regionEntity.getContent());
                    } catch (Exception e) {

                    }
                    countyAdapter.setResourceData(cities);
                }

                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    counties.clear();
                    try {
                        RegionEntity regionEntity = GsonUtils.gsonToBean(result, RegionEntity.class);
                        counties.addAll(regionEntity.getContent());
                    } catch (Exception e) {

                    }
                    if (counties.size() == 0) {
                        onAddressSelectedListener.onAddressSelected(provincName + "," + provinceId, cityName + "," + cityId, "", "", "");
                        dismiss();
                    } else {
                        tabIndex = INDEX_TAB_COUNTY;
                        countyAdapter.setResourceData(counties);
                    }
                }
                break;
            case 3:
                if (!TextUtils.isEmpty(result)) {
                    streets.clear();
                    try {
                        RegionEntity regionEntity = GsonUtils.gsonToBean(result, RegionEntity.class);
                        streets.addAll(regionEntity.getContent());
                    } catch (Exception e) {

                    }
                    if (streets.size() == 0) {
                        onAddressSelectedListener.onAddressSelected(provincName + "," + provinceId, cityName + "," + cityId, countyName + "," + countyId, "", "");
                        dismiss();
                    } else {
                        tabIndex = INDEX_TAB_STREET;
                        countyAdapter.setResourceData(streets);
                    }
                }
                break;

            case 4:
                if (!TextUtils.isEmpty(result)) {
                    communitys.clear();
                    try {
                        RegionEntity regionEntity = GsonUtils.gsonToBean(result, RegionEntity.class);
                        communitys.addAll(regionEntity.getContent());
                    } catch (Exception e) {

                    }
                    if (communitys.size() == 0) {
                        onAddressSelectedListener.onAddressSelected(provincName + "," + provinceId, cityName + "," + cityId, countyName + "," + countyId, streetName + "," + streetId, "");
                        dismiss();
                    } else {
                        tabIndex = INDEX_TAB_COMMUNITY;
                        countyAdapter.setResourceData(communitys);
                    }
                }
                break;
        }
        updateTabsVisibility();
        updateIndicator();
    }

    private List<RegionEntity.ContentBean> provinces = new ArrayList<>();
    private List<RegionEntity.ContentBean> cities = new ArrayList<>();
    private List<RegionEntity.ContentBean> counties = new ArrayList<>();
    private List<RegionEntity.ContentBean> streets = new ArrayList<>();
    private List<RegionEntity.ContentBean> communitys = new ArrayList<>();
    private CountyAdapter countyAdapter;

    private void updateTabsVisibility() {
        textViewProvince.setVisibility(provinces.size() > 0 ? View.VISIBLE : View.INVISIBLE);
        textViewCity.setVisibility(cities.size() > 0 ? View.VISIBLE : View.INVISIBLE);
        textViewCounty.setVisibility(counties.size() > 0 ? View.VISIBLE : View.INVISIBLE);
        textViewStreet.setVisibility(streets.size() > 0 ? View.VISIBLE : View.INVISIBLE);
        textViewCommunity.setVisibility(communitys.size() > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    private void updateIndicator() {
        switch (tabIndex) {
            case INDEX_TAB_PROVINCE:
                buildIndicatorAnimatorTowards(textViewProvince).start();
                break;
            case INDEX_TAB_CITY:
                buildIndicatorAnimatorTowards(textViewCity).start();
                break;
            case INDEX_TAB_COUNTY:
                buildIndicatorAnimatorTowards(textViewCounty).start();
                break;
            case INDEX_TAB_STREET:
                buildIndicatorAnimatorTowards(textViewStreet).start();
                break;
            case INDEX_TAB_COMMUNITY:
                buildIndicatorAnimatorTowards(textViewCommunity).start();
                break;
        }
    }

    private AnimatorSet buildIndicatorAnimatorTowards(TextView tab) {
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(indicator, "X", indicator.getX(), tab.getX());

        final ViewGroup.LayoutParams params = indicator.getLayoutParams();
        ValueAnimator widthAnimator = ValueAnimator.ofInt(params.width, tab.getMeasuredWidth());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (int) animation.getAnimatedValue();
                indicator.setLayoutParams(params);
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.playTogether(xAnimator, widthAnimator);
        return set;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewProvince:
                tabIndex = INDEX_TAB_PROVINCE;
                updateIndicator();
                countyAdapter.setResourceData(provinces);
                break;
            case R.id.textViewCity:
                tabIndex = INDEX_TAB_CITY;
                updateIndicator();
                countyAdapter.setResourceData(cities);
                break;
            case R.id.textViewCounty:
                tabIndex = INDEX_TAB_COUNTY;
                updateIndicator();
                countyAdapter.setResourceData(counties);
                break;
            case R.id.textViewStreet:
                tabIndex = INDEX_TAB_STREET;
                updateIndicator();
                countyAdapter.setResourceData(streets);
                break;
            case R.id.textViewCommunity:
                tabIndex = INDEX_TAB_COMMUNITY;
                updateIndicator();
                countyAdapter.setResourceData(communitys);
                break;
        }
    }


    private class CountyAdapter extends BaseAdapter {

        private List<RegionEntity.ContentBean> showList;


        public void setResourceData(List<RegionEntity.ContentBean> showList) {
            this.showList = showList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return showList == null ? 0 : showList.size();
        }

        @Override
        public RegionEntity.ContentBean getItem(int position) {
            return showList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CountyAdapter.Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);
                holder = new CountyAdapter.Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);
                convertView.setTag(holder);
            } else {
                holder = (CountyAdapter.Holder) convertView.getTag();
            }

            RegionEntity.ContentBean item = showList.get(position);
            holder.textView.setText(item.getName());
            boolean checked = false;
            switch (tabIndex) {
                case INDEX_TAB_PROVINCE:
                    checked = item.getId().equals(provinceId) ? true : false;
                    break;
                case INDEX_TAB_CITY:
                    checked = item.getId().equals(cityId) ? true : false;
                    break;
                case INDEX_TAB_COUNTY:
                    checked = item.getId().equals(countyId) ? true : false;
                    break;
                case INDEX_TAB_STREET:
                    checked = item.getId().equals(streetId) ? true : false;
                    break;
                case INDEX_TAB_COMMUNITY:
                    checked = item.getId().equals(communityId) ? true : false;
                    break;
            }
            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);
            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }
}
