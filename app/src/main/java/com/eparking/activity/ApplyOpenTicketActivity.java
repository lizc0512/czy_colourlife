package com.eparking.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.eparking.helper.OptionsPickerInterface;
import com.eparking.helper.OptionsPickerViewUtils;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingTicketModel;
import com.eparking.protocol.OrderInvoiceInforEntity;
import com.eparking.protocol.ParkingCompanyEntity;
import com.eparking.protocol.ParkingTaxEntity;
import com.nohttp.utils.GsonUtils;

import java.util.List;

import cn.csh.colourful.life.view.pickview.OptionsPickerView;
import cn.net.cyberway.R;

import static com.cashier.activity.NewOrderPayActivity.ORDER_SN;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  用户申请开发票的
 */
public class ApplyOpenTicketActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, OptionsPickerInterface {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private LinearLayout all_ticketinfor_layout;
    private RadioButton rb_ticket_personal;
    private RadioButton rb_ticket_enterprise;
    private EditText tv_ticket_header;
    private RelativeLayout tax_number_layout;
    private EditText ed_tax_number;
    private Button btn_car_authorization;
    private ParkingTicketModel parkingTicketModel = null;
    private String tNum = "";
    private int currentType = 1;
    private boolean personlaRequest = true; //个人只请求一次

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openticket_infors);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_openticket_details));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        all_ticketinfor_layout = findViewById(R.id.all_ticketinfor_layout);

        rb_ticket_personal = findViewById(R.id.rb_ticket_personal);
        rb_ticket_enterprise = findViewById(R.id.rb_ticket_enterprise);
        tv_ticket_header = findViewById(R.id.tv_ticket_header);
        tax_number_layout = findViewById(R.id.tax_number_layout);
        ed_tax_number = findViewById(R.id.ed_tax_number);
        btn_car_authorization = findViewById(R.id.btn_car_authorization);
        btn_car_authorization.setOnClickListener(this);

        rb_ticket_personal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    tv_ticket_header.setHint(getResources().getString(R.string.input_company_header));
                    tv_ticket_header.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                    tv_ticket_header.setText("");
                    tax_number_layout.setVisibility(View.VISIBLE);
                    currentType = 1;
                } else {
                    tv_ticket_header.setHint(getResources().getString(R.string.input_ticket_header));
                    tv_ticket_header.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    tv_ticket_header.setText("");
                    ed_tax_number.setText("");
                    tax_number_layout.setVisibility(View.GONE);
                    currentType = 2;
                    if (null != pickerView) {
                        pickerView.dismiss();
                    }
                    if (personlaRequest) {
                        personlaRequest = false;
                        parkingTicketModel.getInvoiceInfor(2, 2, ApplyOpenTicketActivity.this);
                    }
                }
            }
        });
        tv_ticket_header.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String companyName = tv_ticket_header.getText().toString().trim();
                    if (TextUtils.isEmpty(companyName)) {
                        ToastUtil.toastShow(ApplyOpenTicketActivity.this, getResources().getString(R.string.input_company_header));
                    } else {
                        parkingTicketModel.getInvoiceTax(1, companyName, ApplyOpenTicketActivity.this);
                    }
                }
                return false;
            }
        });
        tNum = getIntent().getStringExtra(ORDER_SN);
        parkingTicketModel = new ParkingTicketModel(this);
        parkingTicketModel.getInvoiceInfor(2, 1, ApplyOpenTicketActivity.this);
        parkingTicketModel.getOrderInvoiceInfor(3, tNum, ApplyOpenTicketActivity.this);
        ParkingActivityUtils.getInstance().addActivity(this);
        tv_ticket_header.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setBtnClick();
            }
        });

        ed_tax_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setBtnClick();
            }
        });
    }

    private void setBtnClick() {
        String ticket_header = tv_ticket_header.getText().toString();
        String tax_number = ed_tax_number.getText().toString();
        switch (currentType) {
            case 1:
                if (TextUtils.isEmpty(ticket_header) || TextUtils.isEmpty(tax_number)) {
                    btn_car_authorization.setBackgroundResource(R.drawable.shape_openticket_default);
                    btn_car_authorization.setEnabled(false);
                } else {
                    btn_car_authorization.setBackgroundResource(R.drawable.shape_authorizeation_btn);
                    btn_car_authorization.setEnabled(true);
                }
                break;
            case 2:
                if (TextUtils.isEmpty(ticket_header)) {
                    btn_car_authorization.setBackgroundResource(R.drawable.shape_openticket_default);
                    btn_car_authorization.setEnabled(false);
                } else {
                    btn_car_authorization.setBackgroundResource(R.drawable.shape_authorizeation_btn);
                    btn_car_authorization.setEnabled(true);
                }
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_car_authorization:
                String ticketHeader = tv_ticket_header.getText().toString().trim();
                String taxNumber = ed_tax_number.getText().toString().trim();
                parkingTicketModel.openTicketOperation(0, tNum, currentType, ticketHeader, taxNumber, this);
                break;
        }
    }

    private OptionsPickerView pickerView;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                ToastUtil.toastShow(ApplyOpenTicketActivity.this, getResources().getString(R.string.apply_ticket_success));
                ParkingActivityUtils.getInstance().exit();
                break;
            case 1:
                try {
                    ParkingTaxEntity parkingTaxEntity = GsonUtils.gsonToBean(result, ParkingTaxEntity.class);
                    List<ParkingTaxEntity.ContentBean.ListsBean> listsBeanList = parkingTaxEntity.getContent().getLists();
                    pickerView = OptionsPickerViewUtils.showPickerView(ApplyOpenTicketActivity.this, R.string.apply_compmany, 0, listsBeanList, this);
                } catch (Exception e) {

                }
                break;
            case 2:
                try {
                    ParkingCompanyEntity parkingCompanyEntity = GsonUtils.gsonToBean(result, ParkingCompanyEntity.class);
                    ParkingCompanyEntity.ContentBean contentBean = parkingCompanyEntity.getContent();
                    tv_ticket_header.setText(contentBean.getCompany_name());
                    ed_tax_number.setText(contentBean.getCompany_code());
                } catch (Exception e) {

                }
                break;
            case 3:
                try {
                    OrderInvoiceInforEntity orderInvoiceInforEntity = GsonUtils.gsonToBean(result, OrderInvoiceInforEntity.class);
                    List<OrderInvoiceInforEntity.ContentBean> contentBeanList = orderInvoiceInforEntity.getContent();
                    for (OrderInvoiceInforEntity.ContentBean contentBean : contentBeanList) {
                        View view = LayoutInflater.from(ApplyOpenTicketActivity.this).inflate(R.layout.single_openticket_layout, null);
                        TextView tv_ticket_name = view.findViewById(R.id.tv_ticket_name);
                        TextView tv_ticket_amount = view.findViewById(R.id.tv_ticket_amount);
                        TextView tv_ticket_rate = view.findViewById(R.id.tv_ticket_rate);
                        tv_ticket_name.setText(contentBean.getSpmc());
                        tv_ticket_amount.setText("¥" + contentBean.getJe());
                        tv_ticket_rate.setText(contentBean.getSlv() / 100.0f + "");
                        all_ticketinfor_layout.addView(view);
                    }
                } catch (Exception e) {

                }
                break;
        }
    }

    @Override
    public void choicePickResult(int type, String choiceText, String choiceId) {
        if (type == 0) {
            tv_ticket_header.setText(choiceText);
            ed_tax_number.setText(choiceId);
        }
    }
}
