package com.point.password;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import cn.net.cyberway.R;

/**
 * 九宫格键盘适配器
 */
public class KeyBoardAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<Map<String, String>> valueList;

    public KeyBoardAdapter(Context mContext, ArrayList<Map<String, String>> valueList) {
        this.mContext = mContext;
        this.valueList = valueList;
    }

    @Override
    public int getCount() {
        return valueList.size();
    }

    @Override
    public Object getItem(int position) {
        return valueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.grid_item_virtual_keyboard, null);
            viewHolder = new ViewHolder();
            viewHolder.btnKey = convertView.findViewById(R.id.btn_keys);
            viewHolder.imgDelete = convertView.findViewById(R.id.imgDelete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String value = valueList.get(position).get("name");
        switch (value) {
            case "10":
                viewHolder.imgDelete.setImageResource(R.drawable.keyboard_finish_img);
                viewHolder.imgDelete.setVisibility(View.VISIBLE);
                viewHolder.btnKey.setVisibility(View.INVISIBLE);
                break;
            case "12":
                viewHolder.imgDelete.setImageResource(R.drawable.keyboard_delete_img);
                viewHolder.imgDelete.setVisibility(View.VISIBLE);
                viewHolder.btnKey.setVisibility(View.INVISIBLE);
                break;
            case "":
                viewHolder.imgDelete.setVisibility(View.INVISIBLE);
                viewHolder.btnKey.setVisibility(View.VISIBLE);
                viewHolder.btnKey.setText(value);
                viewHolder.btnKey.setBackgroundColor(Color.parseColor("#e0e0e0"));
                break;
            default:
                viewHolder.imgDelete.setVisibility(View.INVISIBLE);
                viewHolder.btnKey.setVisibility(View.VISIBLE);
                viewHolder.btnKey.setText(value);
                break;
        }
        return convertView;
    }

    /**
     * 存放控件
     */
    public final class ViewHolder {
        public TextView btnKey;
        public ImageView imgDelete;
    }
}
