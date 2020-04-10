package com.customerInfo.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseFragment;
import com.BeeFramework.model.NewHttpResponse;
import com.appsafekb.safekeyboard.NKeyBoardTextField;
import com.appsafekb.safekeyboard.values.ValueFactory;
import com.user.UserAppConst;
import com.user.model.NewUserModel;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.fragment
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/4/17 11:20
 * @change
 * @chang time
 * @class describe   通过就密码找回新密码
 */
public class ChangePawdByOldFragment extends BaseFragment implements View.OnClickListener, NewHttpResponse {

    private Button complete_btn;
    private NewUserModel newUserModel;
    private NKeyBoardTextField password_et;
    private NKeyBoardTextField new_password_et;
    private String oldPawd="";
    private String newPawd="";
    private String savePawd="";

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_change_pawd;
    }

    @Override
    protected void initView(View rootView) {
        password_et = rootView.findViewById(R.id.ed_old_pawd);
        new_password_et = rootView.findViewById(R.id.ed_new_pawd);
        complete_btn = rootView.findViewById(R.id.btn_finish);
        complete_btn.setOnClickListener(this);


        password_et.setNkeyboardType(0);
        password_et.setNKeyboardRandom(ValueFactory.buildAllTrue());
        password_et.setEditClearIcon(true);
        password_et.setNKeyboardKeyEncryption(false);
        password_et.clearNkeyboard();
        password_et.showNKeyboard();



        new_password_et.setNkeyboardType(0);
        new_password_et.setNKeyboardRandom(ValueFactory.buildAllTrue());
        new_password_et.setEditClearIcon(true);
        new_password_et.setNKeyboardKeyEncryption(false);


        password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                oldPawd = password_et.getNKeyboardText();
                changeBtnStatus();
            }
        });
        new_password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newPawd = new_password_et.getNKeyboardText();
                changeBtnStatus();
            }
        });
        newUserModel = new NewUserModel(getActivity());
        changeBtnStatus();
    }

    private void changeBtnStatus() {
        if (!TextUtils.isEmpty(oldPawd) && !TextUtils.isEmpty(newPawd)) {
            complete_btn.setEnabled(true);
            complete_btn.setBackgroundResource(R.drawable.rect_round_blue);
        } else {
            complete_btn.setEnabled(false);
            complete_btn.setBackgroundResource(R.drawable.rect_round_gray);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_finish:
                if (fastClick()) {
                    if (newPawd.equals(oldPawd)) {
                        ToastUtil.toastShow(getActivity(), "新密码不能与旧密码一致");
                    } else {
                        savePawd = newPawd;
                        newUserModel.changeUserPassword(0, newPawd, oldPawd, this);
                        password_et.hideNKeyboard();
                        new_password_et.hideNKeyboard();
                    }
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                ToastUtil.toastShow(getActivity(), "修改密码成功～");
                editor.putString(UserAppConst.Colour_login_password, savePawd).apply();
                getActivity().finish();
                break;
        }
    }
}
