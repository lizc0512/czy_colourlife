package com.invite.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.im.view.SideBar;
import com.invite.adapter.ContactAdapter;
import com.invite.model.ContactInfo;
import com.invite.model.ContactsEntity;
import com.permission.AndPermission;
import com.permission.PermissionListener;
import com.permission.Rationale;
import com.permission.RationaleListener;
import com.user.UserAppConst;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;


/**
 * 通讯录
 * Created by chenql on 16/1/11.
 */
@Route(path = UserAppConst.COLOURLIFE_PHONE_ADDRESSBOOK)
public class ContactsActivity extends BaseActivity {

    private FrameLayout czy_title_layout;
    private List<ContactsEntity> mContacts;
    private ListView lv_contacts;
    private SideBar side_bar;
    private ContactAdapter mAdapter;
    private final int REQUEST_READ_CONTACTS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        initTopView();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }


    private void getData() {
        ContactInfo contactInfo = new ContactInfo(this);
        mContacts = contactInfo.GetContactList(false);
        if (mContacts == null || mContacts.size() == 0) {
            ToastUtil.toastShow(getApplicationContext(),getResources().getString(R.string.user_readcontacts_notice));
            finish();
        } else {
            mAdapter = new ContactAdapter(ContactsActivity.this, mContacts);
            lv_contacts.setAdapter(mAdapter);
        }
    }

    private void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermission(ContactsActivity.this, Manifest.permission.READ_CONTACTS)) {
                getData();
            } else {
                ArrayList<String> permission = new ArrayList<>();
                permission.add(Manifest.permission.READ_CONTACTS);
                if (AndPermission.hasAlwaysDeniedPermission(ContactsActivity.this, permission)) {
                    ToastUtil.toastShow(getApplicationContext(),getResources().getString(R.string.user_readcontacts_notice));
                    finish();
                } else {
                    AndPermission.with(this)
                            .requestCode(REQUEST_READ_CONTACTS)
                            .permission(Manifest.permission.READ_CONTACTS)
                            .callback(permissionListener)
                            .rationale(rationaleListener)
                            .start();
                }
            }
        } else {
            getData();
        }
    }

    private void initView() {
        lv_contacts = (ListView) findViewById(R.id.lv_contacts);
        side_bar = (SideBar) findViewById(R.id.side_bar);

        // 设置右侧触摸监听
        side_bar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    lv_contacts.setSelection(position);
                }
            }
        });

        lv_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("mobile", mContacts.get(position).getPhoneNumber());
                intent.putExtra("name", mContacts.get(position).getName());
                setResult(1002, intent);  //sdk调用彩之云的通讯录 不能修改resultCode
                finish();
            }
        });
    }

    private void initTopView() {
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        TextView tvTitle = (TextView) findViewById(R.id.user_top_view_title);
        tvTitle.setText(getString(R.string.contacts));

        ImageView imgBack = (ImageView) findViewById(R.id.user_top_view_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, imgBack, tvTitle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {

        }
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_READ_CONTACTS: {
                    getData();
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            if (AndPermission.hasAlwaysDeniedPermission(ContactsActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                ToastUtil.toastShow(getApplicationContext(),getResources().getString(R.string.user_readcontacts_notice));
                finish();
            }
        }
    };


    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
            ToastUtil.toastShow(getApplicationContext(),getResources().getString(R.string.user_readcontacts_notice));
            finish();
        }
    };
}