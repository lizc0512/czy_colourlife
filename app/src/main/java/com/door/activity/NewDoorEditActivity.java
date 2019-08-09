package com.door.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.door.adapter.NewEditDoorRVAdapter;
import com.door.adapter.NewNotEditDoorRVAdapter;
import com.door.entity.DoorBaseEntity;
import com.door.entity.DoorOftenCallBack;
import com.door.entity.EditDoorOftenCallBack;
import com.door.entity.OnRecyclerItemClickListener;
import com.door.entity.SingleCommunityEntity;
import com.door.model.NewDoorModel;
import com.door.view.DoorRenameDialog;
import com.nohttp.utils.FullyLinearLayoutManager;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemStateChangedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.net.cyberway.R;


public class NewDoorEditActivity extends BaseActivity implements NewHttpResponse, View.OnClickListener {
    private SharedPreferences shared;
    public SharedPreferences.Editor editor;
    private ImageView iv_editdoor_close;
    private TextView tv_editdoor_confirm;
    private NewEditDoorRVAdapter newEditDoorRVAdapter;
    private NewNotEditDoorRVAdapter newNotEditDoorRVAdapter;
    private NewDoorModel openModel;
    private List<SingleCommunityEntity.ContentBean.CommonUseBean> list = new ArrayList<>();
    private List<SingleCommunityEntity.ContentBean.CommonUseBean> list_usetemp = new ArrayList<>();
    private List<SingleCommunityEntity.ContentBean.NotCommonUseBean> list_not = new ArrayList<>();
    private List<SingleCommunityEntity.ContentBean.NotCommonUseBean> list_nottemp = new ArrayList<>();
    private SwipeMenuRecyclerView recyclerView;
    private RecyclerView recyclerView_not;
    private DoorRenameDialog doorRenameDialog;
    private int adapterPosition;
    private int notfixpositon;
    private PopupWindow popupWindowDoor;
    private TextView tv_homelead_know;
    private TextView tv_editdoor_title;
    private String communityUuid;
    private String communityName;
    private Boolean ismove = false;
    private List<DoorBaseEntity> olddoor_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_door_edit);
        Intent intent = getIntent();
        communityUuid = intent.getStringExtra(UserAppConst.EDITDOORCOMMUNITYUUID);
        communityName = intent.getStringExtra(UserAppConst.EDITDOORCOMMUNITYNAME);
        list.clear();
        shared = getSharedPreferences(UserAppConst.USERINFO, 0);
        editor = shared.edit();
        openModel = new NewDoorModel(this);
        initView();
        initData();
    }

    private void initData() {
        openModel.getSingleCommunityList(0, communityUuid, true, this);
        Boolean isfirst = shared.getBoolean(UserAppConst.DOOREDITSHOWPOP, false);
        if (isfirst == false) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!NewDoorEditActivity.this.isFinishing()) {
                        initPopup();
                    }
                }
            }, 1500);
        }
    }

    private void initPopup() {
        popupWindowDoor = new PopupWindow(this);
        View contentview = LayoutInflater.from(this).inflate(R.layout.dooreditlead, null);
        popupWindowDoor = new PopupWindow(contentview,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        tv_homelead_know = (TextView) contentview.findViewById(R.id.tv_homelead_know);
        tv_homelead_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowDoor.dismiss();
                setBackgroundAlpha(1.0f);
            }
        });

        setBackgroundAlpha(0.7f);
        popupWindowDoor.setOutsideTouchable(true);
        popupWindowDoor.setBackgroundDrawable(new BitmapDrawable());
        //设置位置
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_dooredit_layout, null);
        try {
            popupWindowDoor.showAtLocation(rootview, Gravity.TOP, 0, 0);
        } catch (Exception e) {

        }
        popupWindowDoor.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        editor.putBoolean(UserAppConst.DOOREDITSHOWPOP, true);
        editor.commit();
    }

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        this.getWindow().setAttributes(lp);
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = (int) (90 * getResources().getDisplayMetrics().density + 0.5f);
            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(NewDoorEditActivity.this)
                    .setBackgroundColor(getResources().getColor(R.color.color_fe6262))
                    .setText(getResources().getString(R.string.message_del)) // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(16) // 文字大小。
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };

    private void initView() {
        tv_editdoor_confirm = (TextView) findViewById(R.id.tv_editdoor_confirm);
        tv_editdoor_title = (TextView) findViewById(R.id.tv_editdoor_title);
        tv_editdoor_title.setText(getResources().getString(R.string.rename_door_name));
        iv_editdoor_close = (ImageView) findViewById(R.id.iv_editdoor_close);
        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.edit_door_recyclerview);
        recyclerView_not = (RecyclerView) findViewById(R.id.edit_door_notrecyclerview);
        tv_editdoor_confirm.setOnClickListener(this);
        iv_editdoor_close.setOnClickListener(this);
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(NewDoorEditActivity.this));// 布局管理器。
        recyclerView_not.setLayoutManager(new FullyLinearLayoutManager(NewDoorEditActivity.this));// 布局管理器。
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView_not.setNestedScrollingEnabled(false);
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        recyclerView.addItemDecoration(new DividerItemDecoration(NewDoorEditActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView_not.addItemDecoration(new DividerItemDecoration(NewDoorEditActivity.this, LinearLayoutManager.VERTICAL));
        recyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        recyclerView.setLongPressDragEnabled(true);
        recyclerView.setOnItemMoveListener(mOnItemMoveListener);
        recyclerView.setOnItemStateChangedListener(onItemStateChangedListener);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
        recyclerView.smoothCloseMenu();
        newEditDoorRVAdapter = new NewEditDoorRVAdapter(NewDoorEditActivity.this, list);
        recyclerView.setAdapter(newEditDoorRVAdapter);
        newEditDoorRVAdapter.setDoorOftenCallBack(new DoorOftenCallBack() {
            @Override
            public void getData(String result, int positon) {
                showRenameDialog(positon);
            }
        });
        newEditDoorRVAdapter.setEditDoorOftenCallBack(new EditDoorOftenCallBack() {
            @Override
            public void getEditData(String result, int positon) {
                recyclerView.smoothOpenRightMenu(positon);
            }
        });
        newNotEditDoorRVAdapter = new NewNotEditDoorRVAdapter(NewDoorEditActivity.this, list_not);
        recyclerView_not.setAdapter(newNotEditDoorRVAdapter);
        newNotEditDoorRVAdapter.setDoorOftenCallBack(new DoorOftenCallBack() {
            @Override
            public void getData(String result, int positon) {
                if (list.size() >= 6) {
                    ToastUtil.toastShow(NewDoorEditActivity.this, getResources().getString(R.string.door_most_added));
                } else {
                    if (positon < list_not.size()) {
                        list_usetemp.clear();
                        notfixpositon = positon;
                        SingleCommunityEntity.ContentBean.CommonUseBean commonUseBean = new SingleCommunityEntity.ContentBean.CommonUseBean();
                        commonUseBean.setCommunity_type(list_not.get(notfixpositon).getCommunity_type());
                        commonUseBean.setConnection_type(list_not.get(notfixpositon).getConnection_type());
                        commonUseBean.setDoor_id(list_not.get(notfixpositon).getDoor_id());
                        commonUseBean.setDoor_img(list_not.get(notfixpositon).getDoor_img());
                        commonUseBean.setDoor_name(list_not.get(notfixpositon).getDoor_name());
                        commonUseBean.setDoor_type(list_not.get(notfixpositon).getDoor_type());
                        commonUseBean.setPosition(list_not.get(notfixpositon).getPosition());
                        commonUseBean.setQr_code(list_not.get(notfixpositon).getQr_code());
                        list_usetemp.add(commonUseBean);
                        ismove = true;
                        newEditDoorRVAdapter.addData(list.size(), list_usetemp.get(0));
                        newEditDoorRVAdapter.notifyDataSetChanged();
                        newNotEditDoorRVAdapter.removeData(notfixpositon);
                        newNotEditDoorRVAdapter.notifyDataSetChanged();
                        ToastUtil.toastShow(NewDoorEditActivity.this, getResources().getString(R.string.door_add_success));
                    }
                }
            }
        });
    }

    /**
     * 保存编辑内容
     */
    private void showSaveDataDialog() {
        doorRenameDialog = new DoorRenameDialog(NewDoorEditActivity.this, R.style.custom_dialog_theme);
        doorRenameDialog.show();
        doorRenameDialog.setCanceledOnTouchOutside(true);
        doorRenameDialog.dialog_content.setVisibility(View.INVISIBLE);
        doorRenameDialog.dialog_title.setText(getResources().getString(R.string.door_save_content));
        doorRenameDialog.btn_cancel.setTextColor(getResources().getColor(R.color.tab_text_color_defaul));
        doorRenameDialog.btn_open.setText(getResources().getString(R.string.customer_save_address));
        doorRenameDialog.dialog_content.setSelection(doorRenameDialog.dialog_content.getText().length());
        doorRenameDialog.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSaveData();
                doorRenameDialog.dismiss();
            }
        });
        doorRenameDialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doorRenameDialog.dismiss();
                NewDoorEditActivity.this.finish();
            }
        });
    }

    /**
     * 门禁重命名
     *
     * @param position
     */
    private void showRenameDialog(final int position) {
        doorRenameDialog = new DoorRenameDialog(NewDoorEditActivity.this, R.style.custom_dialog_theme);
        doorRenameDialog.show();
        doorRenameDialog.setCanceledOnTouchOutside(true);
        doorRenameDialog.dialog_content.setText(list.get(position).getDoor_name());
        doorRenameDialog.dialog_content.setSelection(doorRenameDialog.dialog_content.getText().length());
        doorRenameDialog.dialog_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > start || s.toString().length() <= start) {
                    doorRenameDialog.dialog_content.setTextColor(getResources().getColor(R.color.black_text_color));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        doorRenameDialog.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = doorRenameDialog.dialog_content.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    if (name.length() > 20) {
                        ToastUtil.toastShow(NewDoorEditActivity.this, getResources().getString(R.string.door_most_length));
                    } else {
                        doorRenameDialog.dismiss();
                        ismove = true;
                        list.get(position).setDoor_name(name);
                        newEditDoorRVAdapter.notifyDataSetChanged();
                        ToastUtil.toastShow(NewDoorEditActivity.this, getResources().getString(R.string.door_rename_success));
                    }
                } else {
                    ToastUtil.toastShow(NewDoorEditActivity.this, getResources().getString(R.string.input_door_name));
                }
            }
        });
        doorRenameDialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doorRenameDialog.dismiss();
            }
        });
    }

    private OnItemStateChangedListener onItemStateChangedListener = new OnItemStateChangedListener() {
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            switch (actionState) {
                case ItemTouchHelper.ACTION_STATE_DRAG:
                    break;
                case ItemTouchHelper.ACTION_STATE_IDLE:
                    newEditDoorRVAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private OnItemMoveListener mOnItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = viewHolder1.getAdapterPosition();
            // Item被拖拽时，交换数据，并更新adapter。
            Collections.swap(list, fromPosition, toPosition);
            newEditDoorRVAdapter.notifyItemMoved(fromPosition, toPosition);
            ismove = true;
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder viewHolder) {
            int position = viewHolder.getAdapterPosition();
            // Item被侧滑删除时，删除数据，并更新adapter。
            list.remove(position);
            newEditDoorRVAdapter.notifyItemRemoved(position);
        }
    };
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            list_nottemp.clear();
            adapterPosition = menuBridge.getAdapterPosition();
            if (list.size() < 2) {
                ToastUtil.toastShow(NewDoorEditActivity.this, getResources().getString(R.string.door_remain_amost_one));
            } else {
                SingleCommunityEntity.ContentBean.NotCommonUseBean notCommonUseBean = new SingleCommunityEntity.ContentBean.NotCommonUseBean();
                notCommonUseBean.setCommunity_type(list.get(adapterPosition).getCommunity_type());
                notCommonUseBean.setConnection_type(list.get(adapterPosition).getConnection_type());
                notCommonUseBean.setDoor_id(list.get(adapterPosition).getDoor_id());
                notCommonUseBean.setDoor_img(list.get(adapterPosition).getDoor_img());
                notCommonUseBean.setDoor_name(list.get(adapterPosition).getDoor_name());
                notCommonUseBean.setDoor_type(list.get(adapterPosition).getDoor_type());
                notCommonUseBean.setPosition(list.get(adapterPosition).getPosition());
                notCommonUseBean.setQr_code(list.get(adapterPosition).getQr_code());
                list_nottemp.add(notCommonUseBean);
                ismove = true;
                list_not.add(0, notCommonUseBean);
//                newNotEditDoorRVAdapter.addData(0, list_nottemp.get(0));//非常用
                newNotEditDoorRVAdapter.notifyDataSetChanged();
                newEditDoorRVAdapter.removeData(adapterPosition);//常用
                newEditDoorRVAdapter.notifyDataSetChanged();
                ToastUtil.toastShow(NewDoorEditActivity.this, getResources().getString(R.string.door_remove_success));
            }
        }
    };

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        SingleCommunityEntity singleCommunityEntity = GsonUtils.gsonToBean(result, SingleCommunityEntity.class);
                        if (null != singleCommunityEntity.getContent() && !("").equals(singleCommunityEntity.getContent())) {
                            list.addAll(singleCommunityEntity.getContent().getCommon_use());
                            if (list.size() > 6) {
                                list = list.subList(0, 6);
                            }
                            olddoor_list.clear();
                            for (int i = 0; i < list.size(); i++) {
                                DoorBaseEntity doorBaseEntity = new DoorBaseEntity();
                                doorBaseEntity.setDoor_id(list.get(i).getDoor_id());
                                doorBaseEntity.setPosition(String.valueOf(i));
                                String name = list.get(i).getDoor_name();
                                name = name.replace(" ", "");
                                doorBaseEntity.setName(name);
                                olddoor_list.add(doorBaseEntity);
                            }
                            list_not.addAll(singleCommunityEntity.getContent().getNot_common_use());
                        }
                        if (null == newEditDoorRVAdapter) {
                            newEditDoorRVAdapter = new NewEditDoorRVAdapter(NewDoorEditActivity.this, list);
                            recyclerView.setAdapter(newEditDoorRVAdapter);
                        } else {
                            newEditDoorRVAdapter.setData(list);
                        }
                        if (null == newNotEditDoorRVAdapter) {
                            newNotEditDoorRVAdapter = new NewNotEditDoorRVAdapter(NewDoorEditActivity.this, list_not);
                            recyclerView_not.setAdapter(newNotEditDoorRVAdapter);
                        } else {
                            newNotEditDoorRVAdapter.setData(list_not);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    recyclerView.smoothCloseMenu();
                    recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
                        @Override
                        public void onItemClick(RecyclerView.ViewHolder vh) {

                        }

                        @Override
                        public void onItemLongClick(RecyclerView.ViewHolder vh) {
                        }
                    });
                }
                break;
            case 5:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int content = jsonObject.getInt("content");
                        if (content == 1) {
                            ToastUtil.toastShow(NewDoorEditActivity.this, getResources().getString(R.string.door_edit_success));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent();
//                                    intent.putExtra("itemlist", (Serializable) list);
                                    setResult(RESULT_OK, intent);
                                    NewDoorEditActivity.this.finish();
                                }
                            }, 1000);

                        } else {
                            String message = jsonObject.getString("message");
                            ToastUtil.toastShow(NewDoorEditActivity.this, message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    //调整常用门禁顺序
    public void changePisition(List<SingleCommunityEntity.ContentBean.CommonUseBean> list) {
        List<DoorBaseEntity> newdoor_list = new ArrayList<>();
        newdoor_list.clear();
        for (int i = 0; i < list.size(); i++) {
            DoorBaseEntity doorBaseEntity = new DoorBaseEntity();
            doorBaseEntity.setDoor_id(list.get(i).getDoor_id());
            doorBaseEntity.setPosition(String.valueOf(i));
            String name = list.get(i).getDoor_name();
            name = name.replace(" ", "");
            doorBaseEntity.setName(name);
            newdoor_list.add(doorBaseEntity);
        }
        JSONObject tmpObj = null;
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray1 = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < olddoor_list.size(); i++) {
            String id = olddoor_list.get(i).getDoor_id();
            jsonArray.put(id);
        }
        for (int i = 0; i < newdoor_list.size(); i++) {
            tmpObj = new JSONObject();
            try {
                tmpObj.put("name", newdoor_list.get(i).getName());
                tmpObj.put("door_id", newdoor_list.get(i).getDoor_id());
                tmpObj.put("position", i);
                jsonArray1.put(tmpObj);
                tmpObj = null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            jsonObject.put("clean_door", jsonArray);
            jsonObject.put("new_door", jsonArray1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        openModel.changeDoorPositonFixlist(5, String.valueOf(jsonObject), true, NewDoorEditActivity.this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ismove == true) {
                showSaveDataDialog();
            } else {
                NewDoorEditActivity.this.finish();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void initSaveData() {
        if (null != newEditDoorRVAdapter && list.size() > 0) {
            changePisition(list);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_editdoor_confirm:
                if (ismove == true) {
                    initSaveData();
                } else {
                    NewDoorEditActivity.this.finish();
                }
                break;
            case R.id.iv_editdoor_close:
                if (ismove == true) {
                    showSaveDataDialog();
                } else {
                    NewDoorEditActivity.this.finish();
                }
                break;
        }
    }
}
