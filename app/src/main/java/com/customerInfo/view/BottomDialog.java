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
import com.customerInfo.model.NewCustomerInfoModel;
import com.customerInfo.protocol.RegionEntity;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

public class BottomDialog extends Dialog implements NewHttpResponse, View.OnClickListener {

    private static final int INDEX_TAB_PROVINCE = 0;
    private static final int INDEX_TAB_CITY = 1;
    private static final int INDEX_TAB_COUNTY = 2;
    private TextView textViewProvince;
    private TextView textViewCity;
    private TextView textViewCounty;
    private View indicator;
    private ListView listView;
    private int tabIndex = INDEX_TAB_PROVINCE;
    private NewCustomerInfoModel newCustomerInfoModel;

    private String provincName;
    private String provinceId;
    private String cityName;
    private String cityId;
    private String countyName;
    private String countyId;
    private Context mContext;
    private OnAddressSelectedListener onAddressSelectedListener;

    public BottomDialog(Context context, OnAddressSelectedListener onAddressSelectedListener) {
        super(context, R.style.bottom_dialog);
        mContext = context;
        this.onAddressSelectedListener = onAddressSelectedListener;
        init(context);
    }

    public BottomDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public BottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
        indicator = view.findViewById(R.id.indicator);
        listView = (ListView) view.findViewById(R.id.listView);
        textViewProvince.setOnClickListener(this);
        textViewCity.setOnClickListener(this);
        textViewCounty.setOnClickListener(this);
        updateIndicator();
        countyAdapter = new CountyAdapter();
        listView.setAdapter(countyAdapter);
        newCustomerInfoModel = new NewCustomerInfoModel(mContext);
        newCustomerInfoModel.getRegionData(0, "", this);
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
                        countyAdapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(0);
                        newCustomerInfoModel.getRegionData(1, provinceId, BottomDialog.this);
                        break;
                    case INDEX_TAB_CITY:
                        item = cities.get(position);
                        cityId = item.getId();
                        cityName = item.getName();
                        textViewCity.setText(cityName);
                        textViewCounty.setText("请选择");
                        countyAdapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(0);
                        newCustomerInfoModel.getRegionData(2, cityId, BottomDialog.this);
                        break;
                    case INDEX_TAB_COUNTY:
                        item = counties.get(position);
                        countyName = item.getName();
                        countyId = item.getId();
                        textViewCounty.setText(countyName);
                        onAddressSelectedListener.onAddressSelected(provincName, cityName, countyName,"","");
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
                    try {
                        RegionEntity regionEntity = GsonUtils.gsonToBean(result, RegionEntity.class);
                        provinces.clear();
                        provinces.addAll(regionEntity.getContent());
                    } catch (Exception e) {

                    }
                    countyAdapter.setResourceData(provinces);
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    tabIndex = INDEX_TAB_CITY;
                    try {
                        RegionEntity regionEntity = GsonUtils.gsonToBean(result, RegionEntity.class);
                        cities.clear();
                        cities.addAll(regionEntity.getContent());
                    } catch (Exception e) {

                    }
                    countyAdapter.setResourceData(cities);
                }

                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    tabIndex = INDEX_TAB_COUNTY;
                    try {
                        RegionEntity regionEntity = GsonUtils.gsonToBean(result, RegionEntity.class);
                        counties.clear();
                        counties.addAll(regionEntity.getContent());
                    } catch (Exception e) {

                    }
                    countyAdapter.setResourceData(counties);
                }
                break;
        }
        updateTabsVisibility();
        updateIndicator();
    }

    private List<RegionEntity.ContentBean> provinces = new ArrayList<>();
    private List<RegionEntity.ContentBean> cities = new ArrayList<>();
    private List<RegionEntity.ContentBean> counties = new ArrayList<>();
    private CountyAdapter countyAdapter;

    private void updateTabsVisibility() {
        textViewProvince.setVisibility(provinces.size() > 0 ? View.VISIBLE : View.INVISIBLE);
        textViewCity.setVisibility(cities.size() > 0 ? View.VISIBLE : View.INVISIBLE);
        textViewCounty.setVisibility(counties.size() > 0 ? View.VISIBLE : View.INVISIBLE);
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
