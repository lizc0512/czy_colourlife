package com.point.password;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsafekb.safekeyboard.NKeyBoardTextField;
import com.appsafekb.safekeyboard.interfaces.KeyBoardListener;
import com.appsafekb.safekeyboard.values.ValueFactory;
import com.external.eventbus.EventBus;
import com.point.activity.ForgetPayPawdActivity;

import cn.net.cyberway.R;

import static com.user.UserMessageConstant.POINT_INPUT_PAYPAWD;


/**
 * 输入支付密码
 *
 * @author lining
 */
public class PopEnterPassword {

    Activity mContext;
    public Dialog mDialog;
    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？

    private ImageView[] imgList;      //用数组保存6个TextView，为什么用数组？

    private NKeyBoardTextField mInputView;

    private ImageView imgCancel;
    private TextView tv_forget_pawd;


    public PopEnterPassword(Activity context) {
        this.mContext = context;
        View view = View.inflate(context, R.layout.layout_popup_bottom, null);
        mDialog = new Dialog(context, R.style.mykeyboard_dialogtheme);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        imgCancel = view.findViewById(R.id.iv_close_dialog);
        tv_forget_pawd = view.findViewById(R.id.tv_forget_pawd);
        initView(view);
        mInputView = new NKeyBoardTextField(context);
        mInputView.setNkeyboardType(3);
        mInputView.setNKeyboardRandom(ValueFactory.buildAllTrue());
        mInputView.setEditClearIcon(false);
        mInputView.setNKeyboardKeyEncryption(false);
        mInputView.setNKeyboardMaxInputLength(6);
        mInputView.addTextChangedListener(textWatcher);
        mInputView.setKeyBoardListener(new KeyBoardListener() {
            @Override
            public void onKey(int j) {
                if (j == 5) {
                    for (int i = tvList.length - 1; i >= 0; i--) {
                        String content = tvList[i].getText().toString().trim();
                        if (!TextUtils.isEmpty(content)) {
                            tvList[i].setText("");
                            tvList[i].setVisibility(View.VISIBLE);
                            imgList[i].setVisibility(View.INVISIBLE);
                            break;
                        } else {
                            tvList[i].setText("");
                        }
                    }
                } else {
                    mDialog.dismiss();
                }
            }

            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }

            @Override
            public void onShow(DialogInterface dialogInterface) {

            }

            @Override
            public void onConfigLoadSucc() {

            }

            @Override
            public boolean onCompleteKeyboardKeepShow() {
                return false;
            }
        });
        // 监听X关闭按钮
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        // 监听键盘上方的返回
        tv_forget_pawd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Intent forget_intent = new Intent(mContext, ForgetPayPawdActivity.class);
                mContext.startActivity(forget_intent);
            }
        });
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mInputView.hideNKeyboard();
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String newStr = mInputView.getNKeyboardText();
            for (int i = 0; i < newStr.length(); i++) {
                String content = tvList[i].getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    String newNum = newStr.substring(i, i + 1);
                    tvList[i].setText(newNum);
                    tvList[i].setVisibility(View.INVISIBLE);
                    imgList[i].setVisibility(View.VISIBLE);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void initView(View view) {


        tvList = new TextView[6];

        imgList = new ImageView[6];

        tvList[0] = (TextView) view.findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) view.findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) view.findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) view.findViewById(R.id.tv_pass4);
        tvList[4] = (TextView) view.findViewById(R.id.tv_pass5);
        tvList[5] = (TextView) view.findViewById(R.id.tv_pass6);


        imgList[0] = (ImageView) view.findViewById(R.id.img_pass1);
        imgList[1] = (ImageView) view.findViewById(R.id.img_pass2);
        imgList[2] = (ImageView) view.findViewById(R.id.img_pass3);
        imgList[3] = (ImageView) view.findViewById(R.id.img_pass4);
        imgList[4] = (ImageView) view.findViewById(R.id.img_pass5);
        imgList[5] = (ImageView) view.findViewById(R.id.img_pass6);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 10000:
                        String finalStrPassword = (String) msg.obj;
                        Message message = Message.obtain();
                        message.what = POINT_INPUT_PAYPAWD;
                        message.obj = finalStrPassword;
                        EventBus.getDefault().post(message);
                        mDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        };
        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    String strPassword = "";
                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    Message message = Message.obtain();
                    message.what = 10000;
                    message.obj = strPassword;
                    handler.sendMessageDelayed(message, 100);
                }
            }
        });

    }


    public void show() {
        mDialog.show();
        mInputView.clearNkeyboard();
        mInputView.showNKeyboard();
    }

}
