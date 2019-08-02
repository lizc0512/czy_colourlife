package com.cashier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.cashier.adapter.OrderDetailsAdapter;
import com.cashier.modelnew.NewOrderPayModel;
import com.cashier.protocolchang.OrderDetailsEntity;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;
import com.setting.activity.EditDialog;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;
import cn.net.cyberway.utils.WrapLinearLayoutManager;

import static com.cashier.activity.NewOrderPayActivity.ORDER_SN;

/**
 * @name ${yuansk}
 * @class name：com.cashier.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/22 11:14
 * @change
 * @chang time
 * @class describe  订单的详情和结果页面
 */

public class OrderResultAndDetailActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public static final String FROMRESULT = "fromresult";//判断显示的标题是详情还是结果
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ImageView iv_order_staus;
    private TextView tv_order_staus;
    private RecyclerView rv_order_details;
    private TextView btn_goPay;
    private TextView tv_order_fee;
    private TextView btn_return;
    private String color_sn;
    boolean isResult;
    private NewOrderPayModel newOrderPayModel;
    private List<String> titleList = new ArrayList<>();
    private List<String> contentList = new ArrayList<>();
    private OrderDetailsAdapter orderDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_result);
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        iv_order_staus = (ImageView) findViewById(R.id.iv_order_staus);
        tv_order_staus = (TextView) findViewById(R.id.tv_order_staus);
        rv_order_details = (RecyclerView) findViewById(R.id.rv_order_details);
        btn_goPay = (TextView) findViewById(R.id.btn_goPay);
        tv_order_fee = findViewById(R.id.tv_order_fee);
        btn_return = (TextView) findViewById(R.id.btn_return);
        WrapLinearLayoutManager wrapLayoutManager = new WrapLinearLayoutManager(OrderResultAndDetailActivity.this);
        rv_order_details.setNestedScrollingEnabled(false);
        rv_order_details.setLayoutManager(wrapLayoutManager);
        user_top_view_back.setOnClickListener(this);
        btn_goPay.setOnClickListener(this);
        btn_return.setOnClickListener(this);
        Intent intent = getIntent();
        isResult = intent.getBooleanExtra(FROMRESULT, false);
        if (isResult) {
            user_top_view_title.setText(getResources().getString(R.string.order_result));
        } else {
            user_top_view_title.setText(getResources().getString(R.string.order_details));
        }
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, user_top_view_back, user_top_view_title);
        color_sn = intent.getStringExtra(ORDER_SN);
        newOrderPayModel = new NewOrderPayModel(this);
        newOrderPayModel.getSingleOrderInfor(0, color_sn, this);
    }

    private void showOrderResult(int orderStatus, String stateName) {
        tv_order_staus.setText(stateName);
        if (orderStatus == 1) {
//            iv_order_staus.setImageResource(R.drawable.icon_paywaiting);
            iv_order_staus.setImageResource(R.drawable.img_trade_todo);
            btn_goPay.setVisibility(View.VISIBLE);
        } else if (orderStatus == 2) {
//            iv_order_staus.setImageResource(R.drawable.icon_pay_success);
            iv_order_staus.setImageResource(R.drawable.img_trade_success);
            if (stages_support == 1) {
                btn_goPay.setVisibility(View.VISIBLE);
                btn_goPay.setText("申请分期");
            }
        } else if (orderStatus == 3) {
//            iv_order_staus.setImageResource(R.drawable.icon_pay_fail);
            iv_order_staus.setImageResource(R.drawable.img_trade_close);
        } else if (orderStatus == 4) {
//            iv_order_staus.setImageResource(R.drawable.icon_paywaiting);
            iv_order_staus.setImageResource(R.drawable.img_trade_todo);
        } else {
//            iv_order_staus.setImageResource(R.drawable.icon_pay_fail);
            iv_order_staus.setImageResource(R.drawable.img_trade_close);
        }
        if (orderStatus != 1 && orderStatus != 4) {
            if (TextUtils.isEmpty(shopUrl) || !shopUrl.startsWith("http")) {
                btn_return.setVisibility(View.GONE);
            } else {
                btn_return.setText(getResources().getString(R.string.cashier_return_business));
                btn_return.setVisibility(View.VISIBLE);
            }
        }
    }

    private EditDialog applyDialog = null;

    private void showApplyDialog() {
        if (applyDialog == null) {
            applyDialog = new EditDialog(OrderResultAndDetailActivity.this);
        }
        applyDialog.setContent("你已支付完定金,立即申请分期或前往“我的分期购”申请分期");
        applyDialog.show();
        applyDialog.left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (applyDialog != null) {
                    applyDialog.dismiss();
                }
            }
        });

        applyDialog.right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (applyDialog != null) {
                    applyDialog.dismiss();
                }
                LinkParseUtil.parse(OrderResultAndDetailActivity.this, stages_url, "");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_goPay://去支付
                if (isResult) {
                    finish();
                } else {
                    if (stages_support == 1) {
                        showApplyDialog();
                    } else {
                        Intent intent = new Intent(OrderResultAndDetailActivity.this, NewOrderPayActivity.class);
                        intent.putExtra(ORDER_SN, color_sn);
                        startActivity(intent);
                    }

                }
                break;
            case R.id.btn_return: //返回首页
                if (TextUtils.isEmpty(shopUrl) || !shopUrl.startsWith("http")) {
                    finish();
                } else {
                    LinkParseUtil.parse(OrderResultAndDetailActivity.this, shopUrl, "");
                }
                break;
        }
    }

    private String shopUrl;
    private String stages_url;
    private int stages_support;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                    if (baseContentEntity.getCode() == 0) {
                        OrderDetailsEntity orderDetailsEntity = GsonUtils.gsonToBean(result, OrderDetailsEntity.class);
                        OrderDetailsEntity.ContentBean contentBean = orderDetailsEntity.getContent();
                        if (null != contentBean) {
                            int tradeState = contentBean.getTrade_state();
                            shopUrl = contentBean.getShop_url();
                            stages_support = contentBean.getStages_support();
                            stages_url = contentBean.getStages_url();
                            showOrderResult(tradeState, contentBean.getTrade_state_name());
                            if (!TextUtils.isEmpty(contentBean.getTotal_fee())) {
                                String format = getResources().getString(R.string.cashier_order_amounts);
                                tv_order_fee.setText(String.format(format, contentBean.getTotal_fee()));
                            }
                            if (tradeState == 1) {
                                titleList.add(getResources().getString(R.string.cashier_order_time));
                                contentList.add(TimeUtil.getDateToString(contentBean.getTime_create()));
                            } else {
                                titleList.add(getResources().getString(R.string.cashier_pay_style));
                                titleList.add(getResources().getString(R.string.cashier_order_time));
                                int timePay = contentBean.getTime_pay();
                                contentList.add(contentBean.getPayment_name());
                                contentList.add(TimeUtil.getDateToString(contentBean.getTime_create()));
                                if (timePay != -1) {  //例:取消订单时不显示
                                    titleList.add(getResources().getString(R.string.cashier_pay_time));
                                    contentList.add(TimeUtil.getDateToString(contentBean.getTime_pay()));
                                }
                            }
                            titleList.add(getResources().getString(R.string.cashier_order_no));
                            titleList.add(getResources().getString(R.string.order_details));
                            contentList.add(contentBean.getColour_sn());
                            contentList.add(contentBean.getBody());
                            if (orderDetailsAdapter == null) {
                                orderDetailsAdapter = new OrderDetailsAdapter(titleList, contentList);
                                rv_order_details.setAdapter(orderDetailsAdapter);
                            } else {
                                orderDetailsAdapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        ToastUtil.toastShow(getApplicationContext(), baseContentEntity.getMessage());
                    }
                }
                break;
        }
    }
}
