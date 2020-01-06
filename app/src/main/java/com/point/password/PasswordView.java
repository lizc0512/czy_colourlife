package com.point.password;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appsafekb.safekeyboard.NKeyBoardTextField;
import com.appsafekb.safekeyboard.interfaces.KeyBoardListener;
import com.appsafekb.safekeyboard.values.ValueFactory;

import cn.net.cyberway.R;

/**
 * 弹框里面的View
 */
public class PasswordView extends RelativeLayout {

    Context mContext;

    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？

    private ImageView[] imgList;      //用数组保存6个TextView，为什么用数组？

    private NKeyBoardTextField mInputView;

    private ImageView imgCancel;
    private TextView tv_forget_pawd;


    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        View view = View.inflate(context, R.layout.layout_popup_bottom, null);
        imgCancel =view.findViewById(R.id.iv_close_dialog);
        tv_forget_pawd = view.findViewById(R.id.tv_forget_pawd);
        initView(view);
        mInputView = new NKeyBoardTextField(context);
        mInputView.setNkeyboardType(3);
        mInputView.setNKeyboardRandom(ValueFactory.buildAllTrue());
        mInputView.setEditClearIcon(false);
        mInputView.setNKeyboardKeyEncryption(false);
        mInputView.clearNkeyboard();
        mInputView.showNKeyboard();
        mInputView.setNKeyboardMaxInputLength(6);
        mInputView.addTextChangedListener(textWatcher);
        mInputView.setKeyBoardListener(new KeyBoardListener() {
            @Override
            public void onKey(int j) {
                if (j == 5) {
                    for (int i = tvList.length - 1; i >= 0; i--) {
                        String content= tvList[i].getText().toString().trim();
                        if (!TextUtils.isEmpty(content)) {
                            tvList[i].setText("");
                            tvList[i].setVisibility(View.VISIBLE);
                            imgList[i].setVisibility(View.INVISIBLE);
                            break;
                        } else {
                            tvList[i].setText("");
                        }
                    }
                }
            }

            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mInputView.hideNKeyboard();
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
        addView(view);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String newStr = mInputView.getNKeyboardText();
            for (int i = 0; i < newStr.length(); i++) {
               String content=  tvList[i].getText().toString().trim();
               if (TextUtils.isEmpty(content)){
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

    }


    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput(final OnPasswordInputFinish pass) {
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
                    String strPassword = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    pass.inputFinish(strPassword);    //接口中要实现的方法，完成密码输入完成后的响应逻辑
                }
            }
        });
    }
    public ImageView getImgCancel() {
        return imgCancel;
    }

    public TextView getForgetText() {
        return tv_forget_pawd;
    }
}
