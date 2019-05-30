package com.im.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.im.entity.MobileBookEntity;
import com.im.helper.CacheFriendInforHelper;
import com.im.model.IMUploadPhoneModel;
import com.im.utils.BaseUtil;
import com.im.view.SearchResultDialog;
import com.nohttp.utils.GsonUtils;
import com.permission.AndPermission;
import com.user.UserAppConst;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.JoinCommunityActivity;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/19 11:10
 * @change
 * @chang time
 * @class describe  搜索好友 社群 群聊
 */

public class IMSearchConditionActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private EditText ed_search_content;
    private TextView tv_hint;
    private ImageView iv_delete_text;
    private TextView tv_cancel;
    private TextView tv_search_community;
    private TextView tv_search_group;
    private TextView tv_search_friend;
    private TextView tv_notice;
    private LinearLayout input_content_layout;
    private TextView tv_mobile;
    private List<String> friendUUidList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_condition);
        ed_search_content = findViewById(R.id.ed_search_content);
        tv_hint = findViewById(R.id.tv_hint);
        iv_delete_text = findViewById(R.id.iv_delete_text);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_notice = findViewById(R.id.tv_notice);
        input_content_layout = findViewById(R.id.input_content_layout);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_search_community = findViewById(R.id.tv_search_community);
        tv_search_group = findViewById(R.id.tv_search_group);
        tv_search_friend = findViewById(R.id.tv_search_friend);
        tv_cancel.setOnClickListener(this);
        iv_delete_text.setOnClickListener(this);
        input_content_layout.setOnClickListener(this);
        tv_search_community.setVisibility(View.GONE);
        tv_notice.setVisibility(View.GONE);
        tv_search_group.setVisibility(View.GONE);
        tv_search_friend.setVisibility(View.GONE);
        friendUUidList = CacheFriendInforHelper.instance().toQueryFriendUUIdList(IMSearchConditionActivity.this);
        tv_hint.setText("搜索好友");
        ed_search_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputContent = s.toString().trim();
                int length = inputContent.length();
                if (length > 0) {
                    iv_delete_text.setVisibility(View.VISIBLE);
                    tv_hint.setText("");
                    if (length == 11) {
                        input_content_layout.setVisibility(View.VISIBLE);
                        tv_mobile.setText(inputContent);
                    } else {
                        input_content_layout.setVisibility(View.GONE);
                    }
                } else {
                    iv_delete_text.setVisibility(View.GONE);
                    tv_hint.setText("搜索好友");
                }
            }
        });
        ed_search_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchContent = tv_mobile.getText().toString().trim();
                    if (11 == searchContent.length()) {
                        IMUploadPhoneModel imUploadPhoneModel = new IMUploadPhoneModel(IMSearchConditionActivity.this);
                        imUploadPhoneModel.getUserInforByMobile(0, searchContent, IMSearchConditionActivity.this);
                    } else {
                        ToastUtil.toastShow(IMSearchConditionActivity.this, "请输入完整的11位手机号");
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_delete_text:
                ed_search_content.setText("");
                iv_delete_text.setVisibility(View.GONE);
                tv_hint.setText("搜索好友");
                input_content_layout.setVisibility(View.GONE);
                break;
            case R.id.input_content_layout:
                IMUploadPhoneModel imUploadPhoneModel = new IMUploadPhoneModel(IMSearchConditionActivity.this);
                imUploadPhoneModel.getUserInforByMobile(0, tv_mobile.getText().toString().trim(), this);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        MobileBookEntity mobileBookEntity = GsonUtils.gsonToBean(result, MobileBookEntity.class);
                        if (mobileBookEntity.getCode() == 0) {
                            MobileBookEntity.ContentBean listBean = mobileBookEntity.getContent().get(0);
                            String state = listBean.getState();
                            String uuid = listBean.getUuid();
                            if ("0".equals(state)) {
                                if (friendUUidList.contains(uuid)) {
                                    if (shared.getString(UserAppConst.Colour_User_uuid, "").equals(listBean.getUuid())) {
                                        Intent intent = new Intent(IMSearchConditionActivity.this, IMUserSelfInforActivity.class);
                                        intent.putExtra(IMFriendInforActivity.USERUUID, listBean.getUuid());
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(IMSearchConditionActivity.this, IMFriendInforActivity.class);
                                        intent.putExtra(IMFriendInforActivity.USERUUID, listBean.getUuid());
                                        startActivity(intent);
                                    }
                                } else {
                                    Intent intent = new Intent(IMSearchConditionActivity.this, IMCustomerInforActivity.class);
                                    intent.putExtra(IMFriendInforActivity.USERUUID, listBean.getUuid());
                                    startActivity(intent);
                                }
                            } else {
                                getLocalPhoneList();
                            }
                        }
                    } catch (Exception e) {
                        getLocalPhoneList();
                    }
                    break;
                }
        }
    }

    private void getLocalPhoneList() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermission(IMSearchConditionActivity.this, Manifest.permission.READ_CONTACTS)) {
                filledData();
            } else {
                showNoResultDialog();
            }
        } else {
            filledData();
        }
    }

    /**
     * 为ListView填充数据
     *
     * @return
     */

    private void filledData() {
        String searchPhone = tv_mobile.getText().toString().trim();
        String searchName = "";
        boolean flag = false;
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, BaseUtil.PHONES_PROJECTION, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String phoneName = cursor.getString(0);
                String phoneNumber = cursor.getString(1);
                String realPhone = BaseUtil.trimTelNum(phoneNumber);
                if (realPhone.equals(searchPhone)) {
                    flag = true;
                    searchName = phoneName;
                    break;
                }
            }
        }
        if (flag) {
            Intent intent = new Intent(IMSearchConditionActivity.this, IMInviteRegisterActivity.class);
            intent.putExtra(IMInviteRegisterActivity.USERPHONE, searchPhone);
            intent.putExtra(IMInviteRegisterActivity.USERNAME, searchName);
            startActivity(intent);
        } else {
            showNoResultDialog();
        }
    }

    private void showNoResultDialog() {
        SearchResultDialog searchResultDialog = new SearchResultDialog(IMSearchConditionActivity.this, R.style.custom_dialog_theme);
        searchResultDialog.show();
        searchResultDialog.setCancelable(true);
        searchResultDialog.setCanceledOnTouchOutside(true);
    }
}