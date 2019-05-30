package com.im.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.im.adapter.SortAdapter;
import com.im.entity.MobileBookEntity;
import com.im.entity.MobileInforEntity;
import com.im.entity.UploadMobileEntity;
import com.im.helper.CacheFriendInforHelper;
import com.im.helper.CacheMobileInforHelper;
import com.im.model.IMUploadPhoneModel;
import com.im.utils.BaseUtil;
import com.im.utils.CharacterParser;
import com.im.utils.PinyinComparator;
import com.im.view.SideBar;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;
import com.permission.AndPermission;
import com.user.UserAppConst;
import com.user.model.NewUserModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/22 15:13
 * @change
 * @chang time
 * @class describe  IM的手机通讯录
 */

public class IMMobileBookActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String FRISTLOAD = "fristload";
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private boolean isFrist;

    private void initViews() {
        //实例化汉字转拼音类
        pinyinComparator = new PinyinComparator();
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        user_top_view_title.setText("手机通讯录");
        user_top_view_back.setOnClickListener(this);
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });
        sortListView = (ListView) findViewById(R.id.phone_lv);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                MobileInforEntity listBean = listBeanList.get(position);
                String state = listBean.getState();
                if ("0".equals(state)) {
                    Intent intent = new Intent(IMMobileBookActivity.this, IMCustomerInforActivity.class);
                    intent.putExtra(IMFriendInforActivity.USERUUID, listBean.getUuid());
                    startActivity(intent);
                } else if ("2".equals(state)) {
                    Intent intent = new Intent(IMMobileBookActivity.this, IMInviteRegisterActivity.class);
                    intent.putExtra(IMInviteRegisterActivity.USERPHONE, listBean.getMobile());
                    intent.putExtra(IMInviteRegisterActivity.USERNAME, listBean.getComment());
                    startActivity(intent);
                } else if ("3".equals(state)) {
                    if (shared.getString(UserAppConst.Colour_User_uuid, "").equals(listBean.getUuid())) {
                        Intent intent = new Intent(IMMobileBookActivity.this, IMUserSelfInforActivity.class);
                        intent.putExtra(IMFriendInforActivity.USERUUID, listBean.getUuid());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(IMMobileBookActivity.this, IMFriendInforActivity.class);
                        intent.putExtra(IMFriendInforActivity.USERUUID, listBean.getUuid());
                        startActivity(intent);
                    }
                }
            }
        });
        isFrist = getIntent().getBooleanExtra(FRISTLOAD, false);
        friendUUidList.addAll(CacheFriendInforHelper.instance().toQueryFriendUUIdList(IMMobileBookActivity.this));
        if (isFrist) {
            listBeanList.addAll(CacheMobileInforHelper.instance().toQueryMobileList(IMMobileBookActivity.this));
        } else {
            listBeanList.addAll(CacheMobileInforHelper.instance().toQueryMobileList(IMMobileBookActivity.this));
            uploadDiffPhone();
        }
        initData();
        adapter = new SortAdapter(this, listBeanList);
        sortListView.setAdapter(adapter);
    }

    private void initData() {
        Collections.sort(listBeanList, pinyinComparator);
        for (int j = 0; j < friendUUidList.size(); j++) {
            inner:
            for (MobileInforEntity listBean : listBeanList) {
                String userUUid = listBean.getUuid();
                if (!TextUtils.isEmpty(userUUid) && userUUid.equals(friendUUidList.get(j))) {
                    listBean.setState("3");
                    break inner;
                } else {
                    continue;
                }
            }
        }
    }

    private void uploadDiffPhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermission(IMMobileBookActivity.this, Manifest.permission.READ_CONTACTS)) {
                filledDiffData();
            }
        } else {
            filledDiffData();
        }
    }

    private List<UploadMobileEntity> phoneList = new ArrayList<UploadMobileEntity>();

    private void filledDiffData() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, BaseUtil.PHONES_PROJECTION, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String phoneName = cursor.getString(0);
                String phoneNumber = cursor.getString(1);
                String realPhone = BaseUtil.trimTelNum(phoneNumber);
                MobileInforEntity mobileInforEntity = CacheMobileInforHelper.instance().toQueryFriendnforById(IMMobileBookActivity.this, realPhone);
                if (null == mobileInforEntity) {
                    if (realPhone.startsWith("1")) {
                        UploadMobileEntity uploadMobileEntity = new UploadMobileEntity();
                        uploadMobileEntity.setComment(phoneName);
                        uploadMobileEntity.setMobile(realPhone);
                        phoneList.add(uploadMobileEntity);
                    }
                } else {
                    continue;
                }
            }
        }
        if (phoneList.size() > 0) {
            String jsonarr = GsonUtils.gsonString(phoneList);
            IMUploadPhoneModel imUploadPhoneModel = new IMUploadPhoneModel(IMMobileBookActivity.this);
            imUploadPhoneModel.uploadMobilePhone(1, jsonarr, IMMobileBookActivity.this);
        }
    }

    public void inviteRegister(String mobile) {
        NewUserModel newUserModel = new NewUserModel(IMMobileBookActivity.this);
        newUserModel.inviteRegister(0, mobile, IMMobileBookActivity.this);
    }


    private List<MobileInforEntity> listBeanList = new ArrayList<>();

    private List<String> friendUUidList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_book);
        initViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                ToastUtil.toastShow(IMMobileBookActivity.this, "已发送邀请");
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                    if (baseContentEntity.getCode() == 0) {
                        MobileBookEntity mobileBookEntity = GsonUtils.gsonToBean(result, MobileBookEntity.class);
                        List<MobileBookEntity.ContentBean> contentBeanList = null;
                        if (mobileBookEntity.getCode() == 0) {
                            contentBeanList = mobileBookEntity.getContent();
                        }
                        List<MobileInforEntity> mobileInforEntityList = new ArrayList<>();
                        if (null != contentBeanList && contentBeanList.size() > 0) {
                            CharacterParser characterParser = CharacterParser.getInstance();
                            for (MobileBookEntity.ContentBean contentBean : contentBeanList) {
                                MobileInforEntity mobileInforEntity = new MobileInforEntity();
                                String comment = contentBean.getComment();
                                String pinyin = characterParser.getSelling(comment);
                                String sortString = pinyin.substring(0, 1).toUpperCase();
                                // 正则表达式，判断首字母是否是英文字母
                                if (sortString.matches("[A-Z]")) {
                                    mobileInforEntity.setSortLetters(sortString.toUpperCase());
                                } else {
                                    mobileInforEntity.setSortLetters("#");
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
                                listBeanList.add(mobileInforEntity);
                            }
                        }
                        CacheMobileInforHelper.instance().insertOrUpdate(IMMobileBookActivity.this, mobileInforEntityList);
                        initData();
                        if (null != adapter) {
                            adapter.updateListView(listBeanList);
                        }
                    }
                }
                break;
        }
    }
}
