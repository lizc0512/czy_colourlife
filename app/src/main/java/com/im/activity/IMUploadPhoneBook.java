package com.im.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.im.entity.MobileBookEntity;
import com.im.entity.MobileInforEntity;
import com.im.entity.UploadMobileEntity;
import com.im.helper.CacheMobileInforHelper;
import com.im.model.IMUploadPhoneModel;
import com.im.utils.BaseUtil;
import com.im.utils.CharacterParser;
import com.im.view.UploadPhoneDialog;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;
import com.permission.AndPermission;
import com.user.UserAppConst;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/25 16:38
 * @change
 * @chang time
 * @class describe
 */

public class IMUploadPhoneBook extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_bind_phone;
    private Button btn_upload_phonebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_phonebook);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        tv_bind_phone = findViewById(R.id.tv_bind_phone);
        btn_upload_phonebook = findViewById(R.id.btn_upload_phonebook);
        user_top_view_back.setOnClickListener(this);
        btn_upload_phonebook.setOnClickListener(this);
        user_top_view_title.setText("绑定手机号");
        String bindPhone = shared.getString(UserAppConst.Colour_login_mobile, "");
        tv_bind_phone.setText("当前绑定的手机号:" + bindPhone);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermission(IMUploadPhoneBook.this, Manifest.permission.READ_CONTACTS)) {
                filledData();
            } else {
                ArrayList<String> permission = new ArrayList<>();
                permission.add(Manifest.permission.READ_CONTACTS);
                if (AndPermission.hasAlwaysDeniedPermission(IMUploadPhoneBook.this, permission)) {
                    ToastUtil.toastShow(getApplicationContext(), "通讯录权限被禁止，请去开启该权限");
                    finish();
                } else {
                    AndPermission.with(this)
                            .permission(Manifest.permission.READ_CONTACTS)
                            .start();
                }
            }
        } else {
            filledData();
        }
        imUploadPhoneModel = new IMUploadPhoneModel(IMUploadPhoneBook.this);
    }

    /**
     * 为ListView填充数据
     *
     * @return
     */
    private List<UploadMobileEntity> phoneList = new ArrayList<UploadMobileEntity>();

    private void filledData() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, BaseUtil.PHONES_PROJECTION, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String phoneName = cursor.getString(0);
                String phoneNumber = cursor.getString(1);
                String realPhone = BaseUtil.trimTelNum(phoneNumber);
                if (realPhone.startsWith("1")) {
                    UploadMobileEntity uploadMobileEntity = new UploadMobileEntity();
                    uploadMobileEntity.setComment(phoneName);
                    uploadMobileEntity.setMobile(realPhone);
                    phoneList.add(uploadMobileEntity);
                }
            }
        }
    }

    private IMUploadPhoneModel imUploadPhoneModel = null;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_upload_phonebook:
                showUploadDialog();
                break;
        }
    }

    private UploadPhoneDialog uploadPhoneDialog;

    private void showUploadDialog() {
        uploadPhoneDialog = new UploadPhoneDialog(IMUploadPhoneBook.this, R.style.custom_dialog_theme);
        uploadPhoneDialog.show();
        uploadPhoneDialog.setCancelable(false);
        uploadPhoneDialog.setCanceledOnTouchOutside(false);
        uploadPhoneDialog.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoneDialog.dismiss();
                String jsonarr = GsonUtils.gsonString(phoneList);
                imUploadPhoneModel.uploadMobilePhone(0, jsonarr, IMUploadPhoneBook.this);
            }
        });
        uploadPhoneDialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoneDialog.dismiss();
            }
        });
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                    if (baseContentEntity.getCode() == 0) {
                        MobileBookEntity mobileBookEntity = GsonUtils.gsonToBean(result, MobileBookEntity.class);
                        List<MobileBookEntity.ContentBean> listBeanList = null;
                        if (mobileBookEntity.getCode() == 0) {
                            listBeanList = mobileBookEntity.getContent();
                        }
                        List<MobileInforEntity> mobileInforEntityList = new ArrayList<>();
                        if (null != listBeanList && listBeanList.size() > 0) {
                            CharacterParser characterParser = CharacterParser.getInstance();
                            for (MobileBookEntity.ContentBean contentBean : listBeanList) {
                                MobileInforEntity mobileInforEntity = new MobileInforEntity();
                                String comment = contentBean.getComment();
                                if (TextUtils.isEmpty(comment)){
                                    mobileInforEntity.setSortLetters("#");
                                }else{
                                    String pinyin = characterParser.getSelling(comment);
                                    String sortString = pinyin.substring(0, 1).toUpperCase();
                                    // 正则表达式，判断首字母是否是英文字母
                                    if (sortString.matches("[A-Z]")) {
                                        mobileInforEntity.setSortLetters(sortString.toUpperCase());
                                    } else {
                                        mobileInforEntity.setSortLetters("#");
                                    }
                                }
                                mobileInforEntity.setComment(comment);
                                mobileInforEntity.setUuid(contentBean.getUuid());
                                mobileInforEntity.setState(contentBean.getState());
                                mobileInforEntity.setCommunity_name(contentBean.getCommunity_name());
                                mobileInforEntity.setCommunity_uuid(contentBean.getCommunity_uuid());
                                mobileInforEntity.setGender(contentBean.getGender());
                                mobileInforEntity.setMobile(contentBean.getMobile());
                                mobileInforEntity.setPortrait(contentBean.getGender());
                                mobileInforEntity.setName(contentBean.getName());
                                mobileInforEntity.setNick_name(contentBean.getNick_name());
                                mobileInforEntity.setUser_id(contentBean.getUser_id());
                                mobileInforEntityList.add(mobileInforEntity);
                            }
                        }
                        CacheMobileInforHelper.instance().insertOrUpdate(IMUploadPhoneBook.this, mobileInforEntityList);
                        Intent phoneIntent = new Intent(IMUploadPhoneBook.this, IMMobileBookActivity.class);
                        phoneIntent.putExtra(IMMobileBookActivity.FRISTLOAD, true);
                        startActivity(phoneIntent);
                        finish();
                        ToastUtil.toastShow(IMUploadPhoneBook.this, "通讯录上传成功");
                    }
                }
                break;
        }
    }
}
