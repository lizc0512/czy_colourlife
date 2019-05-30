package com.eparking.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.helper.PermissionUtils;
import com.eparking.model.ParkingTicketModel;
import com.eparking.protocol.OpenTicketEntity;
import com.eparking.protocol.SingleOpenTicketEntity;
import com.eparking.protocol.TicketDownloadEntity;
import com.nohttp.utils.GsonUtils;

import cn.net.cyberway.R;

import static com.cashier.activity.NewOrderPayActivity.ORDER_SN;
import static com.eparking.activity.CarsLicenseUploadActivity.FROMSOURCE;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  发票详情
 */
public class ParkingOpenTicketDetailsActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String OPENTICKETDETAIL = "openticketdetail";
    public static final String STATIONNAME = "stationname";
    public static final String PLATE = "plate";
    public static final String KPAMOUNT = "kpamount";
    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private TextView tv_parking_name;
    private TextView tv_car_number;
    private TextView tv_supplier_name;
    private TextView tv_purchase_name;
    private TextView tv_openticket_amount;
    private TextView tv_openticket_date;
    private TextView tv_openticket_code;
    private TextView tv_openticket_number;
    private Button btn_download_pdf;
    private LinearLayout custom_service_layout;

    private ParkingTicketModel parkingTicketModel;
    private String downloadUrl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openticket_details);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_ticket_details));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        initView();
        Intent intent = getIntent();
        int source = intent.getIntExtra(FROMSOURCE, 0);
        parkingTicketModel = new ParkingTicketModel(ParkingOpenTicketDetailsActivity.this);
        if (source == 0) {
            OpenTicketEntity.ContentBean.ListsBean listsBean = (OpenTicketEntity.ContentBean.ListsBean) getIntent().getSerializableExtra(OPENTICKETDETAIL);
            tv_parking_name.setText(listsBean.getStation_name());
            tv_car_number.setText(listsBean.getPlate());
            tv_supplier_name.setText(listsBean.getXfmc());
            tv_purchase_name.setText(listsBean.getGfmc());
            tv_openticket_amount.setText(listsBean.getKp_amount());
            tv_openticket_date.setText(listsBean.getKprq());
            tv_openticket_code.setText(listsBean.getFpdm());
            tv_openticket_number.setText(listsBean.getFphm());
            String fileUrl = listsBean.getUrl();
            parkingTicketModel.downloadInvoice(1, fileUrl, this);
        } else {
            String tNum = intent.getStringExtra(ORDER_SN);//通过订单号获取发票信息
            tv_parking_name.setText(intent.getStringExtra(STATIONNAME));
            tv_car_number.setText(intent.getStringExtra(PLATE));
            tv_openticket_amount.setText(intent.getStringExtra(KPAMOUNT));
            parkingTicketModel.getInvoiceInforByTnum(2, tNum, this);
        }
        ParkingActivityUtils.getInstance().addActivity(this);
    }


    private void initView() {
        tv_parking_name = findViewById(R.id.tv_parking_name);
        tv_car_number = findViewById(R.id.tv_car_number);
        tv_supplier_name = findViewById(R.id.tv_supplier_name);
        tv_purchase_name = findViewById(R.id.tv_purchase_name);
        tv_openticket_amount = findViewById(R.id.tv_openticket_amount);
        tv_openticket_date = findViewById(R.id.tv_openticket_date);
        tv_openticket_code = findViewById(R.id.tv_openticket_code);
        tv_openticket_number = findViewById(R.id.tv_openticket_number);
        btn_download_pdf = findViewById(R.id.btn_download_pdf);
        custom_service_layout = findViewById(R.id.custom_service_layout);
        btn_download_pdf.setOnClickListener(this);
        custom_service_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_download_pdf:
                if (!TextUtils.isEmpty(downloadUrl)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                    startActivity(intent);
                    ParkingActivityUtils.getInstance().exit();
                }
                break;
            case R.id.custom_service_layout:
                PermissionUtils.showPhonePermission(ParkingOpenTicketDetailsActivity.this);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 1:
                try {
                    TicketDownloadEntity ticketDownloadEntity = GsonUtils.gsonToBean(result, TicketDownloadEntity.class);
                    downloadUrl = ticketDownloadEntity.getContent().getDownload_url();
                } catch (Exception e) {

                }
                break;
            case 2:
                try {
                    SingleOpenTicketEntity singleOpenTicketEntity = GsonUtils.gsonToBean(result, SingleOpenTicketEntity.class);
                    SingleOpenTicketEntity.ContentBean contentBean = singleOpenTicketEntity.getContent();
                    tv_supplier_name.setText(contentBean.getXfmc());
                    tv_purchase_name.setText(contentBean.getGfmc());
                    tv_openticket_date.setText(contentBean.getKprq());
                    tv_openticket_code.setText(contentBean.getFpdm());
                    tv_openticket_number.setText(contentBean.getFphm());
                    String fileUrl = contentBean.getUrl();
                    parkingTicketModel.downloadInvoice(1, fileUrl, this);
                } catch (Exception e) {

                }
                break;
        }
    }
}
