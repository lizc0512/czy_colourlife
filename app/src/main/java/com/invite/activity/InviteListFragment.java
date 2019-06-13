package com.invite.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseFragment;
import com.BeeFramework.model.NewHttpResponse;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.invite.adapter.InviteMyAdapter;
import com.invite.adapter.InviteProfitAdapter;
import com.invite.model.NewInviteModel;
import com.invite.protocol.InviteDetailListEntity;
import com.invite.protocol.InviteInviteRecodeEntity;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * 我的邀请，累计收益
 * Created by hxg on 19/5/24.
 */
public class InviteListFragment extends BaseFragment implements NewHttpResponse {

    private static final String IS_PROFIT = "is_profit";
    private static final String STATUS = "status";

    private XListView xlv_invite_list;
    private LinearLayout ll_empty;

    private boolean isProfit = false;
    private int page = 1;
    private int pageSize = 10;
    private int status = 0;
    private NewInviteModel newInviteModel;
    private InviteMyAdapter myAdapter;
    private InviteProfitAdapter profitAdapter;
    private List<InviteInviteRecodeEntity.ContentBean.ListBean> myInviteList = new ArrayList<>();
    private List<InviteDetailListEntity.ContentBean.ListBean> profitInviteList = new ArrayList<>();

    public static InviteListFragment newInstance(boolean isProfit, int status) {
        Bundle args = new Bundle();
        args.putBoolean(IS_PROFIT, isProfit);
        args.putInt(STATUS, status);
        InviteListFragment fragment = new InviteListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        isProfit = bundle.getBoolean(IS_PROFIT);//是否是累计收益
        status = bundle.getInt(STATUS);
        newInviteModel = new NewInviteModel(getActivity());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_invite_list;
    }

    @Override
    protected void initView(View rootView) {
        xlv_invite_list = rootView.findViewById(R.id.xlv_invite_list);
        ll_empty = rootView.findViewById(R.id.ll_empty);

        xlv_invite_list.setPullRefreshEnable(true);
        xlv_invite_list.setPullLoadEnable(true);
        xlv_invite_list.loadMoreHide();
        xlv_invite_list.setAdapter(null);
        ViewCompat.setNestedScrollingEnabled(xlv_invite_list, true);
        xlv_invite_list.setXListViewListener(new IXListViewListener() {
            @Override
            public void onRefresh(int id) {
                page = 1;
                if (isProfit) {
                    newInviteModel.allProfit(0, status, page, pageSize, InviteListFragment.this);
                } else {
                    newInviteModel.myInvite(1, status, page, pageSize, InviteListFragment.this);
                }
            }

            @Override
            public void onLoadMore(int id) {
                page++;
                if (isProfit) {
                    newInviteModel.allProfit(0, status, page, pageSize, InviteListFragment.this);
                } else {
                    newInviteModel.myInvite(1, status, page, pageSize, InviteListFragment.this);
                }
            }
        }, 0);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            xlv_invite_list.startHeaderRefresh();
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                isFirst = true;
                if (!TextUtils.isEmpty(result)) {
                    BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                    if (baseContentEntity.getCode() == 0) {
                        if (page == 1) {
                            xlv_invite_list.stopRefresh();
                            profitInviteList.clear();
                        } else {
                            xlv_invite_list.stopLoadMore();
                        }

                        try {
                            InviteDetailListEntity entity = GsonUtils.gsonToBean(result, InviteDetailListEntity.class);
                            InviteDetailListEntity.ContentBean content = entity.getContent();
                            pageSize = content.getPage_size();
                            profitInviteList.addAll(content.getList());
                            if (null == profitAdapter) {
                                profitAdapter = new InviteProfitAdapter(getActivity(), profitInviteList, status);
                                xlv_invite_list.setAdapter(profitAdapter);
                            } else {
                                profitAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        ToastUtil.toastShow(getActivity(), baseContentEntity.getMessage());
                    }
                } else {
                    if (page == 1) {
                        xlv_invite_list.stopRefresh();
                    } else {
                        xlv_invite_list.stopLoadMore();
                    }
                }
                ll_empty.setVisibility(profitInviteList.size() == 0 ? View.VISIBLE : View.GONE);
                if (1 == page) {
                    xlv_invite_list.setPullRefreshEnable(false);
                }
                break;
            case 1:
                isFirst = true;
                if (!TextUtils.isEmpty(result)) {
                    BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                    if (baseContentEntity.getCode() == 0) {
                        if (page == 1) {
                            xlv_invite_list.stopRefresh();
                            myInviteList.clear();
                        } else {
                            xlv_invite_list.stopLoadMore();
                        }

                        try {
                            InviteInviteRecodeEntity entity = GsonUtils.gsonToBean(result, InviteInviteRecodeEntity.class);
                            InviteInviteRecodeEntity.ContentBean content = entity.getContent();
                            pageSize = content.getPage_size();
                            myInviteList.addAll(content.getList());
                            if (null == myAdapter) {
                                myAdapter = new InviteMyAdapter(getActivity(), myInviteList);
                                xlv_invite_list.setAdapter(myAdapter);
                            } else {
                                myAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        ToastUtil.toastShow(getActivity(), baseContentEntity.getMessage());
                    }
                } else {
                    if (page == 1) {
                        xlv_invite_list.stopRefresh();
                    } else {
                        xlv_invite_list.stopLoadMore();
                    }
                }
                ll_empty.setVisibility(myInviteList.size() == 0 ? View.VISIBLE : View.GONE);
                if (1 == page) {
                    xlv_invite_list.setPullRefreshEnable(false);
                }
                break;
        }

    }
}
