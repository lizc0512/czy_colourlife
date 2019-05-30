package com.customerInfo.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.CircleImageView;
import com.customerInfo.protocol.RealNameTokenEntity;
import com.gem.GemConstant;
import com.gem.util.GemDialogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.permission.AndPermission;
import com.tencent.authsdk.AuthConfig;
import com.tencent.authsdk.AuthSDKApi;
import com.tencent.authsdk.IDCardInfo;
import com.tencent.authsdk.callback.IdentityCallback;
import com.tendcloud.tenddata.TCAgent;
import com.user.UserAppConst;
import com.user.entity.PortraitEntity;
import com.user.model.NewUserModel;
import com.youmai.hxsdk.HuxinSdkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.csh.colourful.life.utils.GlideImageLoader;
import cn.csh.colourful.life.utils.GsonUtils;
import cn.csh.colourful.life.view.imagepicker.ImagePicker;
import cn.csh.colourful.life.view.imagepicker.bean.ImageItem;
import cn.csh.colourful.life.view.imagepicker.ui.ImageGridActivity;
import cn.csh.colourful.life.view.imagepicker.view.CropImageView;
import cn.net.cyberway.R;

/**
 * 个人信息
 * Created by liusw on 2016/1/14.
 */
public class CustomerInfoActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czyTitleLayout;
    private ImageView ivGem;
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
    private boolean isChangeUserInfo;
    private NewUserModel newUserModel;
    private String gender;
    private String name;
    private String nickName;
    private ImagePicker imagePicker;

    private boolean isShowNotice = false;
    private String realToken;
    private String realName = "";
    private int customer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);
        initPicker();
        initPublic();
        initView();
        initPhoto();
        initData();
        TCAgent.onEvent(getApplicationContext(), "203003");
        String mobile = shared.getString(UserAppConst.Colour_login_mobile, "");
        nickname_tv.setText(shared.getString(UserAppConst.Colour_NIACKNAME, ""));
        name_tv.setText(shared.getString(UserAppConst.Colour_NAME, ""));
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
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
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
        ll_real_name = findViewById(R.id.ll_real_name);
        tv_real_name = findViewById(R.id.tv_real_name);
        tv_is_real = findViewById(R.id.tv_is_real);
        iv_real_name = findViewById(R.id.iv_real_name);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        photo_ll.setOnClickListener(this);
        twoD_code_ll.setOnClickListener(this);
        nickname_ll.setOnClickListener(this);
        name_ll.setOnClickListener(this);
        address_ll.setOnClickListener(this);
        ll_gender.setOnClickListener(this);
        ll_real_name.setOnClickListener(this);
        ivGem = (ImageView) findViewById(R.id.iv_gem);
        GemDialogUtil.showGemDialog(ivGem, this, GemConstant.mineInformation, "");
    }

    private void initPhoto() {
        String headImgUrl = shared.getString(UserAppConst.Colour_head_img, "");
        ImageLoader.getInstance().displayImage(headImgUrl, photo_img, BeeFrameworkApp.optionsImage);
    }

    private void initData() {
        isChangeHead = false;
        mShared = this.getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        customer_id = mShared.getInt(UserAppConst.Colour_User_id, 0);

        newUserModel = new NewUserModel(this);
        String realName = mShared.getString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "");
        if (!TextUtils.isEmpty(realName) || "dismiss".equals(realName)) {
            realNameFormat(realName);
        }
        newUserModel.getIsRealName(2, this);//是否实名认证
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.user_top_view_back:
                intent = new Intent();
                setResult(1, intent);
                finish();
                break;
            case R.id.user_top_view_right:
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
                if (isChangeUserInfo) {
                    newUserModel.changeUserInfomation(0, name, nickName, Integer.valueOf(gender), this);
                } else {
                    if (isChangeHead) {
                        newUserModel.uploadPortrait(1, mImagePath, isShowNotice, this);
                    } else {
                        finish();
                    }
                }
                TCAgent.onEvent(getApplicationContext(), "203022");
                break;
            case R.id.photo_ll:
                TCAgent.onEvent(getApplicationContext(), "203002");
                choosePicture();
                break;
            case R.id.twoD_code_ll:
                intent = new Intent(this, CustomerMakeZXingActivity.class);
                startActivity(intent);
                break;
            case R.id.nickname_ll:
                TCAgent.onEvent(getApplicationContext(), "203004");
                CustomerNameActivity.startCustomerNameActivityForResult(this, nickname_tv.getText().toString(), "nikeName");
                break;
            case R.id.name_ll:
                TCAgent.onEvent(getApplicationContext(), "203008");
                CustomerNameActivity.startCustomerNameActivityForResult(this, name_tv.getText().toString(), "name");
                break;
            case R.id.address_ll:
                TCAgent.onEvent(getApplicationContext(), "203016");
                intent = new Intent(this, CustomerSwitchAddressActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_gender:
                TCAgent.onEvent(getApplicationContext(), "203012");
                CustomerNameActivity.startCustomerNameActivityForResult(this, tv_gender.getText().toString(), "gender");
                break;
            case R.id.ll_real_name://实名认证
                if (TextUtils.isEmpty(tv_real_name.getText().toString().trim()) && !TextUtils.isEmpty(realToken)) {
                    startAuthenticate(realToken);
                }
                break;
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
                    address_tv.setText(data.getStringExtra("community"));
                    break;
                case 2://修改名字
                    isChangeUserInfo = true;
                    name_tv.setText(data.getStringExtra("name"));
                    break;
                case 3://修改昵称
                    isChangeUserInfo = true;
                    nickname_tv.setText(data.getStringExtra("name"));
                    break;
                case 4://修改性别
                    isChangeUserInfo = true;
                    tv_gender.setText(data.getStringExtra("name"));
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
                mEditor.commit();
                HuxinSdkManager.instance().setNickName(nickName);
                HuxinSdkManager.instance().setSex(gender);
                if (isChangeHead) {
                    newUserModel.uploadPortrait(1, mImagePath, isShowNotice, this);
                } else {
                    Intent intent = new Intent();
                    setResult(1, intent);
                    finish();
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
                            Intent intent = new Intent();
                            setResult(1, intent);
                            finish();
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
                        String code = jsonObject.getString("code");
                        if ("0".equals(code)) {
                            String content = jsonObject.getString("content");
                            JSONObject data = new JSONObject(content);
                            int isIdentity = data.getInt("is_identity");
                            String real = data.getString("real_name");
                            if (1 == isIdentity) {
                                mEditor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, real).commit();
                                realNameFormat(real);
                            } else {
                                realName = "";
                                tv_real_name.setText("");
                                tv_is_real.setText(getResources().getString(R.string.customer_real_no));
                                iv_real_name.setVisibility(View.VISIBLE);
                                getRealToken();
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
            case 3:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        RealNameTokenEntity entity = GsonUtils.gsonToBean(result, RealNameTokenEntity.class);
                        RealNameTokenEntity.ContentBean bean = entity.getContent();
                        realToken = bean.getBizToken();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 4://认证
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        if ("0".equals(code)) {
                            String content = jsonObject.getString("content");
                            if ("1".equals(content)) {
                                ToastUtil.toastShow(this, "认证成功");
                                mEditor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, realName).commit();
                                realNameFormat(realName);
                            }
                        } else {
                            String message = jsonObject.getString("message");
                            ToastUtil.toastShow(this, message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        iv_real_name.setVisibility(View.INVISIBLE);
    }

    /**
     * 获取实名认证Token
     */
    private void getRealToken() {
        newUserModel.getRealNameToken(3, this, false);
    }

    /**
     * 实名认证
     */
    private void startAuthenticate(String realToken) {
        AuthConfig.Builder configBuilder = new AuthConfig.Builder(realToken, R.class.getPackage().getName());
        AuthSDKApi.startMainPage(this, configBuilder.build(), mListener);
    }

    /**
     * 监听实名认证返回
     */
    private IdentityCallback mListener = data -> {
        boolean identityStatus = data.getBooleanExtra(AuthSDKApi.EXTRA_IDENTITY_STATUS, false);
        if (identityStatus) {//identityStatus true 已实名
            IDCardInfo idCardInfo = data.getExtras().getParcelable(AuthSDKApi.EXTRA_IDCARD_INFO);
            if (idCardInfo != null) {//身份证信息   idCardInfo.getIDcard();//身份证号码
                realName = idCardInfo.getName();//姓名
                newUserModel.submitRealName(4, idCardInfo.getIDcard(), realName, this);//提交实名认证
            }
        }
    };
}
