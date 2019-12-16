package com.point.password;

import android.content.Context;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.external.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.net.cyberway.R;

import static com.user.UserMessageConstant.POINT_INPUT_CODE;

/**
 * 弹框里面的View
 */
public class InputCodeView extends RelativeLayout {

    Context mContext;

    private VirtualKeyboardView virtualKeyboardView;

    private TextView[] tvList;      //用数组保存4个TextView，为什么用数组？

    private GridView gridView;
    private TextView tv_get_code;

    private ArrayList<Map<String, String>> valueList;

    private int currentIndex = -1;    //用于记录当前输入密码格位置

    public InputCodeView(Context context) {
        this(context, null);
    }

    public InputCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        View view = View.inflate(context, R.layout.layout_popup_inputcode, null);

        virtualKeyboardView =  view.findViewById(R.id.virtualKeyboardView);
        tv_get_code = view.findViewById(R.id.tv_get_code);
        gridView = virtualKeyboardView.getGridView();

        initValueList();

        initView(view);

        setupView();

        addView(view);
    }

    private void initView(View view) {
        tvList = new TextView[4];
        tvList[0] = (TextView) view.findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) view.findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) view.findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) view.findViewById(R.id.tv_pass4);
    }

    // 这里，我们没有使用默认的数字键盘，因为第10个数字不显示.而是空白
    private void initValueList() {
        valueList = new ArrayList<>();
        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<String, String>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "10");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "12");
            }
            valueList.add(map);
        }
    }

    private void setupView() {

        // 这里、重新为数字键盘gridView设置了Adapter
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(mContext, valueList);
        gridView.setAdapter(keyBoardAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮
                    if (currentIndex >= -1 && currentIndex < 3) {      //判断输入位置————要小心数组越界
                        ++currentIndex;
                        tvList[currentIndex].setText(valueList.get(position).get("name"));
                    }
                } else {
                    if (position == 11) {      //点击退格键
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界
                            tvList[currentIndex].setText("");
                            currentIndex--;
                        }
                    }else if (position==9){
                        setOnFinishInput();
                    }
                }
            }
        });
    }

    //设置监听方法，在第4位输入完成后触发
    public void setOnFinishInput() {
        String thridPawd = tvList[3].getText().toString();
        if (!TextUtils.isEmpty(thridPawd)) {
            String strCode = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱
            for (int i = 0; i < 4; i++) {
                strCode += tvList[i].getText().toString().trim();
            }
            Message message = Message.obtain();
            message.what = POINT_INPUT_CODE;
            message.obj = strCode;
            EventBus.getDefault().post(message);
        }else{
            ToastUtil.toastShow(mContext,"请输入4位有效的验证码");
        }
    }

    public VirtualKeyboardView getVirtualKeyboardView() {
        return virtualKeyboardView;
    }

    public TextView getCodeViewText() {
        return tv_get_code;
    }
}
