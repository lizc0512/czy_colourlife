package com.message.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.eparking.helper.CustomDialog;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.message.activity.SystemMsgDetailActivity;
import com.message.adapter.MessageAdapter;
import com.message.model.MessageModel;
import com.message.model.SHOPMSG;
import com.message.model.SYSTEMMSG;
import com.message.protocol.HomeconfigGetuserinfourlGetApi;
import com.message.protocol.HomeconfigGetuserinfourlGetResponse;
import com.message.protocol.INFOURL;
import com.message.protocol.PushmessageDeleteDeleteApi;
import com.message.protocol.PushmessageGetApi;
import com.message.protocol.PushmessageGetResponse;
import com.message.protocol.WetownMessagecentergetlistPostApi;
import com.message.protocol.WetownMessagecentergetlistPostResponse;
import com.umeng.analytics.MobclickAgent;
import com.user.UserAppConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 系统通知fragment
 * Created by chenql on 16/1/13.
 */
public class MessageFragment extends Fragment implements HttpApiResponse {

    private View rootView;

    private XListView xlistview_message;

    private MessageAdapter adapter;

    private List dataList;

    private MessageModel messageModel;

    // 系统通知分页标记
    private int page = 1;
    private int page2 = 1;
    // 商家消息分页标记
    private String mainType = "0";
    private String lastId = "0";
    private int limit = 8;

    private String msgType = MessageAdapter.SYSTEM_MSG; // 默认为系统通知

    private int selectedPosition;

    private CustomDialog dialog;

    private final int READ_SYSTEM_MSG = 1001; // 读一条系统通知

    private boolean isLoadingData = false;//是否正在加载数据，用于控制重复加载
    private INFOURL infourl;
    private SharedPreferences mShared;
    private PushmessageGetApi systemMessageApi;
    private WetownMessagecentergetlistPostApi wetownMessagecentergetlistPostApi;
    private RelativeLayout empty_layout;
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        msgType = getArguments().getString("msgType");
        messageModel = new MessageModel(getActivity());
        dataList = new ArrayList<>();
        mShared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
        activity = getActivity();
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_message_center, null);
            rootView.setOnClickListener(null);
            prepareView();
            getData(true);
        }
        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void getData(boolean showProgress) {
        if (msgType.equals(MessageAdapter.SHOP_MSG)) {//商家信息
            String shopCache = mShared.getString(UserAppConst.SHOPMESSAGE, "");
            if (!TextUtils.isEmpty(shopCache)) {
                wetownMessagecentergetlistPostApi = new WetownMessagecentergetlistPostApi();
                try {
                    wetownMessagecentergetlistPostApi.response.fromJson(new JSONObject(shopCache));
                    ShopAdapter(wetownMessagecentergetlistPostApi.response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            showProgress = false;
            messageModel.getShopMsg(MessageFragment.this, mainType, lastId, limit + "", showProgress);
        } else {//系统通知
            String systemCache = mShared.getString(UserAppConst.SYSTEMMESSAGE, "");
            if (!TextUtils.isEmpty(systemCache)) {
                systemMessageApi = new PushmessageGetApi();
                try {
                    systemMessageApi.response.fromJson(new JSONObject(systemCache));
                    SystemAdapter(systemMessageApi.response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showProgress = false;
            }
            messageModel.getSystemMsg(MessageFragment.this, page, limit, showProgress);
        }
    }

    private void prepareView() {
        xlistview_message = (XListView) rootView.findViewById(R.id.xlistview_message);
        empty_layout = (RelativeLayout) rootView.findViewById(R.id.empty_layout);
        xlistview_message.setXListViewListener(new IXListViewListener() {
            @Override
            public void onRefresh(int id) {
            }

            @Override
            public void onLoadMore(int id) {
                if (!isLoadingData) {
                    isLoadingData = true;
                    if (msgType.equals(MessageAdapter.SHOP_MSG)) {
                        // 商家消息
                        messageModel.getShopMsg(MessageFragment.this, mainType, lastId, limit + "", false);
                        ++page2;
                    } else {
                        // 默认为系统通知
                        messageModel.getSystemMsg(MessageFragment.this, ++page, limit, false);
                    }
                }
            }
        }, 0);

        xlistview_message.setPullLoadEnable(true);
        xlistview_message.setPullRefreshEnable(false);
        xlistview_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (msgType.equals(MessageAdapter.SHOP_MSG)) {
                    // TODO 商家消息详情
                    selectedPosition = position - 1;
                    SHOPMSG shopMsg = (SHOPMSG) dataList.get(selectedPosition);
                    setJump(Integer.parseInt(shopMsg.subtype), shopMsg.relatedid, shopMsg.name);

                } else if (msgType.equals(MessageAdapter.SYSTEM_MSG)) {
                    // 系统通知详情
                    selectedPosition = position - 1;
                    SYSTEMMSG systemMsg = (SYSTEMMSG) dataList.get(selectedPosition);
                    //后续要添加url需要判断url来跳转相应的界面
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putSerializable("systemMsg", systemMsg);
//
//                    Intent intent1 = new Intent(getActivity(), SystemMsgDetailActivity.class);
//                    intent1.putExtras(bundle1);
//                    startActivityForResult(intent1, READ_SYSTEM_MSG);

                    if (TextUtils.isEmpty(systemMsg.url) && systemMsg.resource_id.equals("0")) {

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("systemMsg", systemMsg);

                        Intent intent = new Intent(getActivity(), SystemMsgDetailActivity.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, READ_SYSTEM_MSG);
                    } else if (TextUtils.isDigitsOnly(systemMsg.url)) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("systemMsg", systemMsg);

                        Intent intent = new Intent(getActivity(), SystemMsgDetailActivity.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, READ_SYSTEM_MSG);

                    } else if (systemMsg.url.startsWith("colour")) {
                        LinkParseUtil.parse(getActivity(), systemMsg.url, systemMsg.title);

                    } else if (systemMsg.url.startsWith("http")) {
                        messageModel.getSystemMsgNew(MessageFragment.this, systemMsg.resource_id, systemMsg.url, "1",
                                String.valueOf(mShared.getInt(UserAppConst.Colour_User_id, -1)), true);

                    }

                }
            }

        });

        xlistview_message.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (msgType.equals(MessageAdapter.SYSTEM_MSG)) {
                    // 删除一条系统通知
                    selectedPosition = position - 1;
                    showDeleteDialog();
                }
                return true;
            }
        });
    }

    /**
     * 删除消息提示对话框
     */
    protected void showDeleteDialog() {

        dialog = new CustomDialog(getActivity(), R.style.custom_dialog_theme);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.dialog_title.setText("提示");
        dialog.dialog_content.setText("是否删除该消息？");
        dialog.dialog_button_ok.setText("确定");
        dialog.dialog_button_cancel.setText("取消");

        dialog.dialog_button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageModel.deleteSystemMsg(MessageFragment.this, ((SYSTEMMSG) dataList.get(selectedPosition)).id);
                dialog.dismiss();
            }
        });

        dialog.dialog_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case READ_SYSTEM_MSG:
                ((SYSTEMMSG) dataList.get(selectedPosition)).is_read = "1"; // 标记一条系统通知已读
                adapter.notifyDataSetChanged();
                break;
            default:
                ((SHOPMSG) dataList.get(selectedPosition)).isread = "1"; // 标记一条商家消息已读
                adapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 系统消息适配
     *
     * @param response
     */
    private void SystemAdapter(PushmessageGetResponse response) {
        try {
            if (response.push.size() == 0) {
                if (page == 1) {
                    showNoData();
                } else {
                    xlistview_message.stopLoadMore();
                    xlistview_message.setPullLoadEnable(false);
                }
            } else {
                dataList.addAll(response.push);
                setAdapter(dataList);
                if (page == 1 && response.push.size() < limit) {
                    xlistview_message.setPullLoadEnable(false);
                    xlistview_message.loadMoreHide();
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 商家信息适配
     *
     * @param response
     */
    private void ShopAdapter(WetownMessagecentergetlistPostResponse response) {
        try {
            if ("0".equals(response.result)) {
                if (response.MList.size() == 0) {
                    if (page2 == 1) {
                        showNoData();
                    } else {
                        xlistview_message.stopLoadMore();
                        xlistview_message.setPullLoadEnable(false);
                    }
                } else {
                    dataList.addAll(response.MList);
                    lastId = response.LastID;
                    setAdapter(dataList);
                    if (page2 == 1 && response.MList.size() < limit) {
                        xlistview_message.setPullLoadEnable(false);
                        xlistview_message.loadMoreHide();
                    }
                }
            } else {
                showNoData();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void OnHttpResponse(HttpApi api) {

        if (api.getClass().equals(PushmessageGetApi.class)) {
            // 系统通知
            PushmessageGetResponse response = ((PushmessageGetApi) api).response;
            SystemAdapter(response);
            isLoadingData = false;
        } else if (api.getClass().equals(WetownMessagecentergetlistPostApi.class)) {
            // 商家消息
            WetownMessagecentergetlistPostResponse response = ((WetownMessagecentergetlistPostApi) api).response;
            ShopAdapter(response);
            xlistview_message.stopLoadMore();
            isLoadingData = false;
        } else if (api.getClass().equals(PushmessageDeleteDeleteApi.class)) {
            // 删除一条系统通知
            dataList.remove(selectedPosition);
            adapter.notifyDataSetChanged();
        } else if (api.getClass().equals(HomeconfigGetuserinfourlGetApi.class)) {
            HomeconfigGetuserinfourlGetResponse response = ((HomeconfigGetuserinfourlGetApi) api).response;
            infourl = response.data;
            LinkParseUtil.parse(getActivity(), infourl.completeUrl, "");
        }
    }

    /**
     * 隐藏列表，显示没有数据的视图
     */
    private void showNoData() {
        xlistview_message.setVisibility(View.GONE);
        empty_layout.setVisibility(View.VISIBLE);
        ImageView img_empty = (ImageView) rootView.findViewById(R.id.img_empty);
        TextView tv_tips = (TextView) rootView.findViewById(R.id.tv_tips);
        TextView tv_description = (TextView) rootView.findViewById(R.id.tv_description);
        rootView.findViewById(R.id.btn_operation).setVisibility(View.GONE);
        img_empty.setVisibility(View.VISIBLE);
        tv_tips.setVisibility(View.VISIBLE);
        tv_description.setVisibility(View.GONE);
        if (msgType.equals(MessageAdapter.SYSTEM_MSG)) {
            tv_tips.setText("暂无通知");
        } else {
            tv_tips.setText("暂无消息");
        }
        img_empty.setImageResource(R.drawable.e4_history_empty);
    }

    /**
     * 标记已读，刷新数据
     *
     * @param msgType 消息类型（商家消息MessageAdapter.SHOP_MSG、系统通知MessageAdapter.SYSTEM_MSG）
     */
    public void setAllRead(String msgType) {
        if (msgType.equals(MessageAdapter.SHOP_MSG)) {
            // 商家消息设为已读，刷新商家消息列表数据
            for (int i = 0; i < dataList.size(); i++) {
                try {
                    ((SHOPMSG) dataList.get(i)).isread = "1";
                } catch (Exception e) {

                }
            }
        } else {
            // 系统通知设为已读，刷新系统通知列表数据
            for (int i = 0; i < dataList.size(); i++) {
                try {
                    ((SYSTEMMSG) dataList.get(i)).is_read = "1";
                } catch (Exception e) {

                }
            }
        }
        if (null != adapter) {
            adapter.notifyDataSetChanged();
        }
    }

    private void setAdapter(List dataList) {
        if (null != activity) {
            if (adapter == null) {
                adapter = new MessageAdapter(getActivity(), dataList, msgType);
                xlistview_message.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        }
        xlistview_message.stopLoadMore();
    }

    // 跳转设置
    private void setJump(int subtype, String tid, String name) {
        switch (subtype) {
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(msgType);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(msgType);
    }

}
