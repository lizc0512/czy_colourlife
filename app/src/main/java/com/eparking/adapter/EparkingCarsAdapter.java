package com.eparking.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eparking.activity.CarsLicenseUploadActivity;
import com.eparking.protocol.CarInforEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.eparking.activity.CarsLicenseUploadActivity.CARLOGO;
import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;
import static com.eparking.activity.CarsLicenseUploadActivity.FROMSOURCE;

/**
 * @name ${yuansk}
 * @class name：com.eparking.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/15 17:47
 * @change
 * @chang time
 * @class describe  我的卡包  车辆
 */
public class EparkingCarsAdapter extends RecyclerView.Adapter<EparkingCarsAdapter.PaymentViewHolder> {

    private List<CarInforEntity.ContentBean> carInforEntityList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public EparkingCarsAdapter(Context mContext, List<CarInforEntity.ContentBean> carInforEntityList) {
        this.carInforEntityList = carInforEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public EparkingCarsAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eparking_carinfor_item, parent, false);
        PaymentViewHolder paymentViewHolder = new PaymentViewHolder(v);
        paymentViewHolder.onClickListener = onClickListener;
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final EparkingCarsAdapter.PaymentViewHolder holder, int position) {
        CarInforEntity.ContentBean contentBean = carInforEntityList.get(position);
        final String carLogo = contentBean.getLogo();
        final String carNumber = contentBean.getPlate();
        GlideImageLoader.loadImageDefaultDisplay(mContext, carLogo, holder.iv_car_logo, R.drawable.eparking_img_carmodel, R.drawable.eparking_img_carmodel);
        holder.tv_car_brand.setText(carNumber);
        if (carNumber.trim().length() == 8) {
            holder.tv_eparking_place.setText(mContext.getResources().getString(R.string.energy_car_model));
        } else {
            holder.tv_eparking_place.setText(mContext.getResources().getString(R.string.normal_car_model));
        }
        String status = contentBean.getIs_accred();
        int contract_state = contentBean.getContract_state();
        if (contract_state == 0) {
            holder.unbind_car_layout.setVisibility(View.GONE);
            holder.unbind_line.setVisibility(View.GONE);
            holder.tv_car_bindstatus.setText(mContext.getResources().getString(R.string.car_waiting_authorize));
            holder.tv_car_bindstatus.setBackgroundResource(R.drawable.shape_applyrecord_model);
            holder.tv_car_bindstatus.setVisibility(View.VISIBLE);
        } else {
            if ("Y".equalsIgnoreCase(status) || contract_state == 1) {
                holder.unbind_car_layout.setVisibility(View.GONE);
                holder.unbind_line.setVisibility(View.GONE);
                holder.tv_car_bindstatus.setText(mContext.getResources().getString(R.string.car_finish_authorize));
                holder.tv_car_bindstatus.setVisibility(View.VISIBLE);
            } else {
                holder.unbind_car_layout.setVisibility(View.VISIBLE);
                holder.unbind_line.setVisibility(View.VISIBLE);
                holder.tv_car_bindstatus.setVisibility(View.GONE);
            }
        }

        holder.unbind_car_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CarsLicenseUploadActivity.class);
                intent.putExtra(FROMSOURCE, 1);
                intent.putExtra(CARNUMBER, carNumber);
                intent.putExtra(CARLOGO, carLogo);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return carInforEntityList == null ? 0 : carInforEntityList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_car_logo;
        TextView tv_car_brand;
        TextView tv_eparking_place;
        TextView tv_car_bindstatus;
        View unbind_line;
        LinearLayout unbind_car_layout;

        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            iv_car_logo = (ImageView) itemView.findViewById(R.id.iv_car_logo);
            tv_car_brand = (TextView) itemView.findViewById(R.id.tv_car_brand);
            tv_eparking_place = (TextView) itemView.findViewById(R.id.tv_eparking_place);
            tv_car_bindstatus = (TextView) itemView.findViewById(R.id.tv_car_bindstatus);
            unbind_line = itemView.findViewById(R.id.unbind_line);
            unbind_car_layout = (LinearLayout) itemView.findViewById(R.id.unbind_car_layout);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
