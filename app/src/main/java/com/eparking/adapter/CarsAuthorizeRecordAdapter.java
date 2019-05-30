package com.eparking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eparking.activity.CarsAuthorizeRecordActivity;
import com.eparking.protocol.AuthorizeRecordEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/22 15:43
 * @change
 * @chang time
 * @class describe  授权记录的
 */
public class CarsAuthorizeRecordAdapter extends RecyclerView.Adapter<CarsAuthorizeRecordAdapter.PaymentViewHolder> {

    private List<AuthorizeRecordEntity.ContentBean.ListsBean> authorizeRecordEntityList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public CarsAuthorizeRecordAdapter(Context mContext, List<AuthorizeRecordEntity.ContentBean.ListsBean> authorizeRecordEntityList) {
        this.authorizeRecordEntityList = authorizeRecordEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CarsAuthorizeRecordAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eparking_carauthorize_item, parent, false);
        CarsAuthorizeRecordAdapter.PaymentViewHolder paymentViewHolder = new CarsAuthorizeRecordAdapter.PaymentViewHolder(v);
        paymentViewHolder.onClickListener = onClickListener;
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CarsAuthorizeRecordAdapter.PaymentViewHolder holder, final int position) {

        AuthorizeRecordEntity.ContentBean.ListsBean authorizeRecordEntity = authorizeRecordEntityList.get(position);
        holder.tv_car_brand.setText(authorizeRecordEntity.getPlate());
        holder.tv_eparking_place.setText(authorizeRecordEntity.getStation_name());
        holder.tv_authorization_user.setText(authorizeRecordEntity.getUser_to_name());
        holder.tv_authorization_phone.setText(authorizeRecordEntity.getUser_to_phone());
        holder.tv_car_status.setText(mContext.getResources().getString(R.string.parking_cancel_authorize));
        holder.tv_car_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CarsAuthorizeRecordActivity) mContext).setAuthorizeOperation(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return authorizeRecordEntityList == null ? 0 : authorizeRecordEntityList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_car_brand;
        TextView tv_eparking_place;
        TextView tv_authorization_user;
        TextView tv_authorization_phone;
        TextView tv_car_status;


        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_car_brand = (TextView) itemView.findViewById(R.id.tv_car_brand);
            tv_eparking_place = (TextView) itemView.findViewById(R.id.tv_eparking_place);
            tv_authorization_phone = (TextView) itemView.findViewById(R.id.tv_authorization_phone);
            tv_authorization_user = (TextView) itemView.findViewById(R.id.tv_authorization_user);
            tv_car_status = (TextView) itemView.findViewById(R.id.tv_car_status);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}