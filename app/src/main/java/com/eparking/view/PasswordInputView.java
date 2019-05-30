package com.eparking.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

import com.BeeFramework.view.Util;

import cn.net.cyberway.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * @name ${yuansk}
 * @class name：com.eparking.view
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/25 8:55
 * @change
 * @chang time
 * @class describe
 */
public class PasswordInputView extends EditText {
    private Context mContext;

    private int passwordLength = 4;
    private Paint passwordPaint = new Paint(ANTI_ALIAS_FLAG);
    private int numsLength;
    private String nums;
    private int numwidth;


    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float cx, cy = height / 2;
        float half = width / passwordLength / 2;
        float everyWidth = width / passwordLength;
        float lineSpce = Util.DensityUtil.getZoom(mContext) * 22;
        passwordPaint.setColor(Color.parseColor("#E6E9EA"));
        for (int i = 0; i < passwordLength; i++) {
            canvas.drawRect(lineSpce + everyWidth * i, height - 2, everyWidth * (i + 1) - lineSpce, height, passwordPaint);
        }
        passwordPaint.setColor(Color.parseColor("#333B46"));
        for (int i = 0; i < numsLength; i++) {
            cx = width * i / passwordLength + half;
            String txt = nums.substring(i, i + 1);
            this.numwidth = (int) Math.ceil(passwordPaint.measureText(txt));
            canvas.drawText(txt, cx - (numwidth / 2), cy + (numwidth / 2), passwordPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.numsLength = text.toString().length();
        this.nums = text.toString();
        invalidate();
        if (mBtn != null) {
            if (numsLength == 4) {
                mBtn.setBackgroundResource(R.drawable.shape_authorizeation_btn);
                mBtn.setEnabled(true);
            } else {
                mBtn.setBackgroundResource(R.drawable.shape_openticket_default);
                mBtn.setEnabled(false);
            }
        }
    }

    private Button mBtn;

    public void setBtnClick(Button btn) {
        this.mBtn = btn;
    }

//    private int getPasswordLength() {
//        return passwordLength;
//    }
//
//    private void setPasswordLength(int passwordLength) {
//        this.passwordLength = passwordLength;
//        invalidate();
//    }

    public void setPasswordPaintSize() {
        passwordPaint.setTextSize(Util.DensityUtil.getZoom(mContext) * 60);
    }
}
