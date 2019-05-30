package com.mycarinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.invite.model.Alpha;
import com.mycarinfo.protocol.COLOURTICKETCARBRANDINFO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.net.cyberway.R;

/**
 * adapter
 * Created by chenql on 16/1/11.
 */
public class AllCarAdapter extends BaseAdapter implements SectionIndexer {

    private List<COLOURTICKETCARBRANDINFO> contacts = new ArrayList<>();
    private LayoutInflater mInflater;

    private HashMap<String, Integer> alphaIndexer;
    private String[] sections;

    public AllCarAdapter(Context context, List<COLOURTICKETCARBRANDINFO> lists) {
        mInflater = LayoutInflater.from(context);
        this.contacts = lists;
        this.alphaIndexer = new HashMap<String, Integer>();
        this.sections = new String[lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            String name = Alpha.getAlpha(lists.get(i).prefix);//h获取A B C D...
            alphaIndexer.put(name, i);// #,A,B,C,D,F,G,Z
            sections[i] = name;
        }
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public COLOURTICKETCARBRANDINFO getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_allcar, null);

            holder.name = (TextView) convertView.findViewById(R.id.allcar_item_name);
            holder.contactId = (TextView) convertView.findViewById(R.id.allcar_item_contactId);
            holder.sortKey = (TextView) convertView.findViewById(R.id.allcar_item_alpha);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final COLOURTICKETCARBRANDINFO contact = contacts.get(position);

        holder.name.setText(contact.name);

        if (holder.contactId != null) {
            //holder.contactId.setText(contact.id);
        }

        String currentStr = Alpha.getAlpha(contact.prefix);//获取首字母分类

        String previewStr = (position - 1) >= 0 ?
                Alpha.getAlpha(contacts.get(position - 1).prefix) :
                " ";
        if (!previewStr.equals(currentStr)) {//查看时候同一个字母下的车辆
            holder.sortKey.setVisibility(View.VISIBLE);
            holder.sortKey.setText(currentStr);//不是，显示出来

        } else {
            holder.sortKey.setVisibility(View.GONE);//是，隐藏起来
        }

        return convertView;
    }

    @Override
    public int getPositionForSection(int section) {

        for (int i = 0; i < getCount(); i++) {
            String sortStr = sections[i];
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    private class ViewHolder {
        private TextView name;
        private TextView contactId;
        private TextView sortKey;
    }
}