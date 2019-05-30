package com.customerInfo.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.model.NewCustomerInfoModel;
import com.customerInfo.protocol.RegionEntity;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/3 10:40
 * @change
 * @chang time
 * @class describe
 */

public class CustomerChoiceRegionActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private static final int INDEX_TAB_PROVINCE = 0;
    private static final int INDEX_TAB_CITY = 1;
    private static final int INDEX_TAB_COUNTY = 2;
    private ImageView mback;
    private TextView mtitle;
    private TextView user_top_view_right;
    private TextView textViewProvince;
    private TextView textViewCity;
    private TextView textViewCounty;
    private View indicator;
    private ListView listView;
    private int tabIndex = INDEX_TAB_PROVINCE;
    private NewCustomerInfoModel newCustomerInfoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_seletor);
        mback = (ImageView) findViewById(R.id.user_top_view_back);
        mtitle = (TextView) findViewById(R.id.user_top_view_title);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        textViewProvince = (TextView) findViewById(R.id.textViewProvince);
        textViewCity = (TextView) findViewById(R.id.textViewCity);
        textViewCounty = (TextView) findViewById(R.id.textViewCounty);
        indicator = findViewById(R.id.indicator);
        listView = (ListView) findViewById(R.id.listView);
        mtitle.setText(getResources().getString(R.string.title_choice_address));
        user_top_view_right.setText(getResources().getString(R.string.cashier_finish));
        user_top_view_right.setVisibility(View.VISIBLE);
        mback.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        textViewProvince.setOnClickListener(this);
        textViewCity.setOnClickListener(this);
        textViewCounty.setOnClickListener(this);
        updateIndicator();
        countyAdapter = new CountyAdapter();
        listView.setAdapter(countyAdapter);
        newCustomerInfoModel = new NewCustomerInfoModel(CustomerChoiceRegionActivity.this);
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
                        newCustomerInfoModel.getRegionData(1, provinceId, CustomerChoiceRegionActivity.this);
                        break;
                    case INDEX_TAB_CITY:
                        item = cities.get(position);
                        cityId = item.getId();
                        cityName = item.getName();
                        textViewCity.setText(cityName);
                        textViewCounty.setText("请选择");
                        countyAdapter.notifyDataSetChanged();
                        newCustomerInfoModel.getRegionData(2, cityId, CustomerChoiceRegionActivity.this);
                        break;
                    case INDEX_TAB_COUNTY:
                        item = counties.get(position);
                        countyName = item.getName();
                        countyId = item.getId();
                        textViewCounty.setText(countyName);
                        countyAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }

    private String provincName;
    private String provinceId;
    private String cityName;
    private String cityId;
    private String countyName;
    private String countyId;


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
            case R.id.user_top_view_back:
                finish();
                break;
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
            case R.id.user_top_view_right:
                if (TextUtils.isEmpty(provincName)) {
                    ToastUtil.toastShow(CustomerChoiceRegionActivity.this, "请选择省");
                    return;
                }
                if (TextUtils.isEmpty(cityName)) {
                    ToastUtil.toastShow(CustomerChoiceRegionActivity.this, "请选择市");
                    return;
                }
                if (TextUtils.isEmpty(countyName)) {
                    ToastUtil.toastShow(CustomerChoiceRegionActivity.this, "请选择区");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(CustomerChoiceCommunityActivity.PROVINCENAME, provincName);
                intent.putExtra(CustomerChoiceCommunityActivity.PROVINCEID, provinceId);
                intent.putExtra(CustomerChoiceCommunityActivity.CITYNAME, cityName);
                intent.putExtra(CustomerChoiceCommunityActivity.CITYID, cityId);
                intent.putExtra(CustomerChoiceCommunityActivity.REGIONID, countyId);
                intent.putExtra(CustomerChoiceCommunityActivity.REGIONIDNAME, countyName);
                setResult(200, intent);
                finish();
                break;
        }
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
                    if (cities.size() > 0) {
                        listView.setSelection(0);
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
                    if (counties.size() > 0) {
                        listView.setSelection(0);
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
        textViewProvince.setVisibility(provinces.size() > 0 ? View.VISIBLE : View.GONE);
        textViewCity.setVisibility(cities.size() > 0 ? View.VISIBLE : View.GONE);
        textViewCounty.setVisibility(counties.size() > 0 ? View.VISIBLE : View.GONE);
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
            Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);
                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
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
