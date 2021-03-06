package com.customerInfo.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.CircleImageView;
import com.community.utils.ImagePickerLoader;
import com.customerInfo.protocol.IdentityStateEntity;
import com.customerInfo.view.CustomerInfoDialog;
import com.external.eventbus.EventBus;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.myproperty.activity.MyPropertyActivity;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.permission.AndPermission;
import com.realaudit.activity.RealCheckResultActivity;
import com.realaudit.activity.RealCheckWaitingActivity;
import com.realaudit.activity.RealCommonSubmitActivity;
import com.realaudit.activity.RealNameInforActivity;
import com.realaudit.model.IdentityNameModel;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.entity.PortraitEntity;
import com.user.model.NewUserModel;
import com.youmai.hxsdk.HuxinSdkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.csh.colourful.life.view.pickview.OptionsPickerView;
import cn.net.cyberway.R;

import static com.realaudit.activity.RealCheckResultActivity.CHECKREASON;
import static com.realaudit.activity.RealCheckResultActivity.CHECKSTATE;
import static com.realaudit.activity.RealCheckWaitingActivity.CHECKTIME;

/**
 * 个人信息
 * Created by liusw on 2016/1/14.
 */
public class CustomerInfoActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public static String FROM_WEB = "from_web";

    private FrameLayout czyTitleLayout;
    private ImageView mBack;
    private TextView mTitle;
    private TextView mRightText;
    private LinearLayout photo_ll;       //头像
    private CircleImageView photo_img;
    private LinearLayout twoD_code_ll;   //我的二维码
    private LinearLayout nickname_ll;    //昵称
    private TextView nickname_tv;
    private LinearLayout name_ll;        //姓名
    private TextView name_tv;
    private LinearLayout email_ll; //邮箱
    private TextView email_tv;
    private LinearLayout address_ll;     //地址
    private TextView address_tv;
    private TextView mobile_tv;      //手机号
    private LinearLayout ll_gender;      //性别
    private LinearLayout ll_real_name;      //实名认证
    private TextView tv_real_name;      //实名认证
    private TextView tv_is_real;      //实名认证
    private ImageView iv_real_name;      //实名认证
    private TextView tv_gender;
    private static final int AVATAR_ALBUM = 2;
    private String mImagePath;
    private SharedPreferences mShared;
    public SharedPreferences.Editor mEditor;
    private boolean isChangeHead;
    private boolean isChangeUserInfo = false;
    private NewUserModel newUserModel;
    private String gender;
    private String name;
    private String nickName;
    private String email;
    private ImagePicker imagePicker;
    private boolean isShowNotice = false;
    private String realName = "";
    private int customer_id;
    private boolean fromWeb;
    private String identifyState;
    private String idCardNumber="";
    private String faceImage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);
        initPicker();
        initPublic();
        initView();
        initPhoto();
        initData();
        String mobile = shared.getString(UserAppConst.Colour_login_mobile, "");
        nickname_tv.setText(shared.getString(UserAppConst.Colour_NIACKNAME, ""));
        name_tv.setText(shared.getString(UserAppConst.Colour_NAME, ""));
        email_tv.setText(shared.getString(UserAppConst.COLOUR_EMAIL, "").trim());
        address_tv.setText(shared.getString(UserAppConst.Colour_login_community_name, ""));
        mobile_tv.setText(mobile);
        gender = shared.getString(UserAppConst.Colour_GENDER, "0");
        if ("0".equals(gender)) {//0未知，1男 2女
            tv_gender.setText("--");
        } else if ("1".equals(gender)) {
            tv_gender.setText(getResources().getString(R.string.customer_man));
        } else if ("2".equals(gender)) {
            tv_gender.setText(getResources().getString(R.string.customer_femal));
        }
    }

    private void initPicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setSaveRectangle(true);
        imagePicker.setCrop(true);
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setFocusWidth(400);   //裁剪框的宽度。
        imagePicker.setFocusHeight(400);  //裁剪框的高度。
        imagePicker.setOutPutX(600);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(600);//保存文件的高度。单位像素
    }

    private void initPublic() {
        czyTitleLayout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        mRightText = (TextView) findViewById(R.id.user_top_view_right);
        mTitle.setText(getResources().getString(R.string.title_personal_infor));
        mRightText.setVisibility(View.VISIBLE);
        mRightText.setText(getResources().getString(R.string.customer_save_address));
        mBack.setOnClickListener(this);
        mRightText.setOnClickListener(this);
        ThemeStyleHelper.rightTexteFrameLayout(getApplicationContext(), czyTitleLayout, mBack, mTitle, mRightText);
        if (!EventBus.getDefault().isregister(CustomerInfoActivity.this)) {
            EventBus.getDefault().register(CustomerInfoActivity.this);
        }
    }

    private void initView() {
        photo_ll = (LinearLayout) findViewById(R.id.photo_ll);
        photo_img = (CircleImageView) findViewById(R.id.photo_img);
        twoD_code_ll = (LinearLayout) findViewById(R.id.twoD_code_ll);
        nickname_ll = (LinearLayout) findViewById(R.id.nickname_ll);
        nickname_tv = (TextView) findViewById(R.id.nickname_tv);
        name_ll = (LinearLayout) findViewById(R.id.name_ll);
        name_tv = (TextView) findViewById(R.id.name_tv);
        address_ll = (LinearLayout) findViewById(R.id.address_ll);
        address_tv = (TextView) findViewById(R.id.address_tv);
        mobile_tv = (TextView) findViewById(R.id.mobile_tv);
        ll_gender = (LinearLayout) findViewById(R.id.ll_gender);
        email_ll = (LinearLayout) findViewById(R.id.email_ll);
        email_tv = findViewById(R.id.email_tv);
        ll_real_name = findViewById(R.id.ll_real_name);
        tv_real_name = findViewById(R.id.tv_real_name);
        tv_is_real = findViewById(R.id.tv_is_real);
        iv_real_name = findViewById(R.id.iv_real_name);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        photo_ll.setOnClickListener(this);
        twoD_code_ll.setOnClickListener(this);
        nickname_ll.setOnClickListener(this);
        name_ll.setOnClickListener(this);
        email_ll.setOnClickListener(this);
        address_ll.setOnClickListener(this);
        ll_gender.setOnClickListener(this);
        ll_real_name.setOnClickListener(this);
    }

    private void initPhoto() {
        String headImgUrl = shared.getString(UserAppConst.Colour_head_img, "");
        GlideImageLoader.loadImageDefaultDisplay(CustomerInfoActivity.this,headImgUrl,photo_img,
                R.drawable.icon_default_portrait,R.drawable.icon_default_portrait);
    }

    private void initData() {
        fromWeb = getIntent().getBooleanExtra(FROM_WEB, false);
        isChangeUserInfo = false;
        isChangeHead = false;
        mShared = this.getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        customer_id = mShared.getInt(UserAppConst.Colour_User_id, 0);

        newUserModel = new NewUserModel(this);
        realName = mShared.getString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "");
        if (!TextUtils.isEmpty(realName) || "dismiss".equals(realName)) {
            realNameFormat(realName);
        }
        newUserModel.getIsRealName(2, false, this);//是否实名认证
    }

    private CustomerInfoDialog dialog;

    /**
     * 检测是否完善信息
     */
    private boolean checkMsg() {
        boolean isFinish = true;
        if (dialog == null) {
            dialog = new CustomerInfoDialog(this);
        }

        //没完善
        boolean isDefaultCommunity = "03b98def-b5bd-400b-995f-a9af82be01da".equals(mShared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da"));
        if (isDefaultCommunity || TextUtils.isEmpty(nickname_tv.getText().toString())
                || TextUtils.isEmpty(name_tv.getText().toString())
                || name_tv.getText().toString().contains("访客")
                || TextUtils.isEmpty(tv_gender.getText().toString())
                || "--".equals(tv_gender.getText().toString().trim())
                || TextUtils.isEmpty(email_tv.getText().toString().trim())
                || "0".equals(email_tv.getText().toString().trim())) {
            if (fromWeb) {
                dialog.tv_title.setText("个人信息录入未完成");
                dialog.tv_msg.setText("您需要完善姓名/性别/地址/邮箱信息，离开将不能获得现金券奖励");
                dialog.tv_continue.setText("继续完善");
            }
            isFinish = false;
        }

        if (fromWeb && !isFinish) {
            dialog.show();
            dialog.tv_leave.setOnClickListener(v1 -> {
                if (dialog != null) {
                    dialog.dismiss();
                }
                setResult(200, new Intent());
                save(false);
            });
            dialog.tv_continue.setOnClickListener(v2 -> {
                if (dialog != null) {
                    dialog.dismiss();
                }
            });
        }

        return isFinish;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.user_top_view_back:
                if (fromWeb) {
                    setResult(200, new Intent());
                    finish();
                } else {
                    intent = new Intent();
                    setResult(1, intent);
                    finish();
                }
                break;
            case R.id.user_top_view_right:
                boolean isFinish = checkMsg();
                if (!fromWeb || isFinish) {
                    save(isFinish);
                }
                break;
            case R.id.photo_ll:
                choosePicture();
                break;
            case R.id.twoD_code_ll:
                intent = new Intent(this, CustomerMakeZXingActivity.class);
                startActivity(intent);
                break;
            case R.id.nickname_ll:
                CustomerNameActivity.startCustomerNameActivityForResult(this, nickname_tv.getText().toString(), "nikeName");
                break;
            case R.id.name_ll:
                CustomerNameActivity.startCustomerNameActivityForResult(this, name_tv.getText().toString(), "name");
                break;
            case R.id.email_ll:
                CustomerNameActivity.startCustomerNameActivityForResult(this, email_tv.getText().toString(), "email");
                break;
            case R.id.address_ll:
                intent = new Intent(this, MyPropertyActivity.class);
                intent.putExtra(MyPropertyActivity.FROM_CUSTOMER_INFO, true);
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_gender:
                ArrayList<String> genderList = new ArrayList<>();
                genderList = new ArrayList<>();
                genderList.add(getResources().getString(R.string.customer_man));
                genderList.add(getResources().getString(R.string.customer_femal));
                showSexPickerView(genderList);
                break;
            case R.id.ll_real_name://实名认证
                if (TextUtils.isEmpty(tv_real_name.getText().toString().trim())) {
                    intent = new Intent(CustomerInfoActivity.this, RealCommonSubmitActivity.class);
                    startActivity(intent);
                } else {
                    if ("2".equals(identifyState)) {
                        IdentityNameModel identityNameModel = new IdentityNameModel(CustomerInfoActivity.this);
                        identityNameModel.getApplyStatus(7, CustomerInfoActivity.this);
                    } else {
                        Intent real_intent = new Intent(CustomerInfoActivity.this, RealNameInforActivity.class);
                        real_intent.putExtra(RealNameInforActivity.REALNAME, realName);
                        real_intent.putExtra(RealNameInforActivity.REALNUMBER, idCardNumber);
                        real_intent.putExtra(RealNameInforActivity.REALFACEIMAGE, faceImage);
                        startActivity(real_intent);
                    }
                }
                break;
        }
    }

    private void showSexPickerView(ArrayList<String> genderList) {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                isChangeUserInfo = true;
                tv_gender.setText(genderList.get(options1));
            }
        })
                .setTitleText("性别")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setCancelColor(Color.parseColor("#629ef0"))
                .setSubmitColor(Color.parseColor("#629ef0"))
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(genderList);//一级选择器
        pvOptions.show();
    }

    /**
     * 保存
     *
     * @param isFinish true 已完成，false 未完成
     */
    private void save(boolean isFinish) {
        //保存修改信息
        gender = tv_gender.getText().toString();
        if (getResources().getString(R.string.customer_man).equals(gender)) {
            gender = "1";
        } else if (getResources().getString(R.string.customer_femal).equals(gender)) {
            gender = "2";
        } else {
            gender = "0";
        }
        name = name_tv.getText().toString().trim();
        nickName = nickname_tv.getText().toString().trim();
        email = email_tv.getText().toString().trim();
        if (isChangeUserInfo) {
            newUserModel.changeUserInfomation(0, name, nickName, email, Integer.valueOf(gender), isFinish ? (fromWeb ? "task_web" : "task_native") : "", this);
        } else {
            if (isChangeHead) {
                newUserModel.uploadPortrait(1, mImagePath, isShowNotice, this);
            } else {
                finish();
            }
        }
    }

    private void choosePicture() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View contentView = this.getLayoutInflater().inflate(R.layout.user_avatar_dialog, null);
        dialog.setContentView(contentView);

        android.view.ViewGroup.LayoutParams cursorParams = contentView.getLayoutParams();
        cursorParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(cursorParams);
        dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
        dialog.show();

        TextView album = (TextView) dialog.findViewById(R.id.avatar_album);
        TextView photograph = (TextView) dialog.findViewById(R.id.avatar_photograph);
        TextView cancel = (TextView) dialog.findViewById(R.id.avatar_cancel);

        //从相册选择上传
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                imagePicker.setCrop(true);
                Intent intent = new Intent(CustomerInfoActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, AVATAR_ALBUM);
            }
        });
        //拍照上传
        photograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (AndPermission.hasPermission(getApplicationContext(), Manifest.permission.CAMERA)) {
                        imagePicker.setCrop(false);
                        Intent intent = new Intent(CustomerInfoActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, AVATAR_ALBUM);
                    } else {
                        ToastUtil.toastShow(getApplicationContext(), getResources().getString(R.string.user_camerapermission_notice));
                    }
                } else {
                    imagePicker.setCrop(false);
                    Intent intent = new Intent(CustomerInfoActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, AVATAR_ALBUM);
                }
                dialog.dismiss();
            }
        });
        //取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK && requestCode == 1) {
            switch (resultCode) {
                case 1://修改地址
                    try {
                        String address = data.getStringExtra("community");
                        if (null != address) {
                            address_tv.setText(address);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2://修改名字
                    isChangeUserInfo = true;
                    name_tv.setText(data.getStringExtra("name"));
                    break;
                case 3://修改昵称
                    isChangeUserInfo = true;
                    nickname_tv.setText(data.getStringExtra("name"));
                    break;
                case 5:
                    isChangeUserInfo = true;
                    email_tv.setText(data.getStringExtra("name"));
                    break;
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (requestCode == AVATAR_ALBUM) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images.size() > 0) {
                    try {
                        mImagePath = images.get(0).path;
                        if (!TextUtils.isEmpty(mImagePath)) {
                            Bitmap bm = BitmapFactory.decodeFile(mImagePath);
                            photo_img.setImageBitmap(bm);
                            isChangeHead = true;
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                mEditor.putString(UserAppConst.Colour_NIACKNAME, nickName);
                mEditor.putString(UserAppConst.Colour_NAME, name);
                mEditor.putString(UserAppConst.Colour_GENDER, gender);
                mEditor.putString(UserAppConst.COLOUR_EMAIL, email);
                mEditor.commit();
                HuxinSdkManager.instance().setNickName(nickName);
                HuxinSdkManager.instance().setSex(gender);
                if (isChangeHead) {
                    newUserModel.uploadPortrait(1, mImagePath, isShowNotice, this);
                } else {
                    if (fromWeb) {
                        setResult(200, new Intent());
                        finish();
                    } else {
                        Intent intent = new Intent();
                        setResult(1, intent);
                        finish();
                    }
                }
                break;
            case 1:
                if (TextUtils.isEmpty(result)) {
                    if (!isShowNotice) {//服务器可能提示上传失败，重新上传一次
                        isShowNotice = true;
                        newUserModel.uploadPortrait(1, mImagePath, isShowNotice, this);
                    }
                } else {
                    try {
                        PortraitEntity portraitEntity = GsonUtils.gsonToBean(result, PortraitEntity.class);
                        if (portraitEntity.getCode() == 0) {
                            mEditor.putString(UserAppConst.Colour_head_img, portraitEntity.getContent().getUrl()).commit();
                            if (fromWeb) {
                                setResult(200, new Intent());
                                finish();
                            } else {
                                Intent intent = new Intent();
                                setResult(1, intent);
                                finish();
                            }
                        } else {
                            if (!isShowNotice) {//服务器可能提示上传失败，重新上传一次
                                isShowNotice = true;
                                newUserModel.uploadPortrait(1, mImagePath, isShowNotice, this);
                            }
                        }
                    } catch (Exception e) {
                        if (!isShowNotice) {//服务器可能提示上传失败，重新上传一次
                            isShowNotice = true;
                            newUserModel.uploadPortrait(1, mImagePath, isShowNotice, this);
                        }
                    }
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String code = jsonObject.optString("code");
                        if ("0".equals(code)) {
                            String content = jsonObject.optString("content");
                            JSONObject data = new JSONObject(content);
                            int isIdentity = data.optInt("is_identity");
                            realName = data.optString("real_name");
                            idCardNumber = data.optString("number");
                            faceImage = data.optString("face_img");
                            if (1 == isIdentity) {
                                mEditor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, realName).commit();
                                realNameFormat(realName);
                            } else {
                                realName = "";
                                tv_real_name.setText("");
                                tv_is_real.setText(getResources().getString(R.string.customer_real_no));
                                iv_real_name.setVisibility(View.VISIBLE);
                                mEditor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "").commit();
                            }
                        } else {
                            String message = jsonObject.getString("message");
                            ToastUtil.toastShow(this, message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 6:
                try {
                    IdentityStateEntity identityStateEntity = GsonUtils.gsonToBean(result, IdentityStateEntity.class);
                    IdentityStateEntity.ContentBean contentBean = identityStateEntity.getContent();
                    identifyState = contentBean.getState();
                } catch (Exception e) {

                }
                break;
            case 7:
                try {
                    IdentityStateEntity identityStateEntity = GsonUtils.gsonToBean(result, IdentityStateEntity.class);
                    IdentityStateEntity.ContentBean contentBean = identityStateEntity.getContent();
                    String checkStatus = contentBean.getStatus();
                    Intent intent = null;
                    switch (checkStatus) {
                        case "1":
                            intent = new Intent(CustomerInfoActivity.this, RealCheckWaitingActivity.class);
                            intent.putExtra(CHECKTIME, contentBean.getCreated_at());
                            break;
                        case "2":
                        case "3":
                            intent = new Intent(CustomerInfoActivity.this, RealCheckResultActivity.class);
                            intent.putExtra(CHECKREASON, contentBean.getRemark());
                            break;
                    }
                    intent.putExtra(CHECKSTATE, checkStatus);
                    startActivity(intent);
                } catch (Exception e) {

                }
                break;
        }
    }

    /**
     * 校验名字
     */
    private void realNameFormat(String realName) {
        if (realName.length() > 2) {
            realName = realName.substring(0, 1) + "*" + realName.substring(realName.length() - 1);
        } else if (realName.length() == 2) {
            realName = realName.substring(0, 1) + "*";
        }
        tv_real_name.setText(realName);
        tv_is_real.setText(getResources().getString(R.string.customer_real_already));
        iv_real_name.setVisibility(View.GONE);
        IdentityNameModel identityNameModel = new IdentityNameModel(CustomerInfoActivity.this);
        identityNameModel.getIdentityState(6, CustomerInfoActivity.this);
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.REAL_CHANGE_STATE:
                newUserModel.getIsRealName(2, true, this);//是否实名认证
                break;
            case UserMessageConstant.REAL_SUCCESS_STATE:
                realName = message.obj.toString();
                realNameFormat(realName);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(CustomerInfoActivity.this)) {
            EventBus.getDefault().unregister(CustomerInfoActivity.this);
        }
    }
}
