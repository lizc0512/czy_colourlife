package com.invite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.invite.model.Alpha;
import com.invite.model.ContactsEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.net.cyberway.R;

/**
 * 通讯录adapter
 * Created by chenql on 16/1/11.
 */
public class ContactAdapter extends BaseAdapter implements SectionIndexer {

    private List<ContactsEntity> contacts = new ArrayList<>();
    private LayoutInflater mInflater;

    private HashMap<String, Integer> alphaIndexer;
    private String[] sections;

    public ContactAdapter(Context context, List<ContactsEntity> lists) {
        mInflater = LayoutInflater.from(context);
        this.contacts = lists;
        this.alphaIndexer = new HashMap<String, Integer>();
        this.sections = new String[lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            String name = Alpha.getAlpha(lists.get(i).getSort_key());
            alphaIndexer.put(name, i);// #,A,B,C,D,F,G,Z
            sections[i] = name;
        }
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public ContactsEntity getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.list_item_contacts, null);

            holder.name = (TextView) convertView.findViewById(R.id.contacts_item_name);
            holder.contactId = (TextView) convertView.findViewById(R.id.contacts_item_contactId);
            holder.sortKey = (TextView) convertView.findViewById(R.id.contacts_item_alpha);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ContactsEntity contact = contacts.get(position);

        holder.name.setText(contact.getName());

        if (holder.contactId != null) {
            holder.contactId.setText(contact.getContactId());
        }

        String currentStr = Alpha.getAlpha(contact.getSort_key());

        String previewStr = (position - 1) >= 0 ?
                Alpha.getAlpha(contacts.get(position - 1).getSort_key()) :
                " ";

        if (!previewStr.equals(currentStr)) {
            holder.sortKey.setVisibility(View.VISIBLE);
            holder.sortKey.setText(currentStr);

        } else {
            holder.sortKey.setVisibility(View.GONE);
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