package com.community.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.BeeFramework.activity.BaseFragment;
import com.community.adapter.CommunityDetailsCommentAdapter;
import com.community.entity.CommunityDynamicsListEntity;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * @ProjectName:
 * @Package: com.community.fragment
 * @ClassName: CommunityCommentListFragment
 * @Description: 动态评论的列表
 * @Author: yuansk
 * @CreateDate: 2020/2/21 10:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/21 10:46
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class CommunityCommentListFragment extends BaseFragment {
    private SwipeMenuRecyclerView rv_community_comment;
    private TextView tv_no_data;

    private List<CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean> allCommentBeanList = new ArrayList<>();

    private CommunityDetailsCommentAdapter communityDetailsCommentAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_community_commentlist;
    }

    @Override
    protected void initView(View rootView) {
        rv_community_comment = rootView.findViewById(R.id.rv_community_comment);
        tv_no_data = rootView.findViewById(R.id.tv_no_data);
        //进行点赞列表的显示
        communityDetailsCommentAdapter = new CommunityDetailsCommentAdapter(getActivity(), allCommentBeanList);
        rv_community_comment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_community_comment.setAdapter(communityDetailsCommentAdapter);
        showEmptyData();
    }

    private void showEmptyData() {
        if (allCommentBeanList == null || allCommentBeanList.size() == 0) {
            tv_no_data.setVisibility(View.VISIBLE);
        } else {
            tv_no_data.setVisibility(View.GONE);
        }
    }


    public void showCommentList(List<CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean> commentBeanList) {
        allCommentBeanList.clear();
        if (null != commentBeanList) {
            allCommentBeanList.addAll(commentBeanList);
        }
    }

    //删除评论
    public void delComment(int position) {
        allCommentBeanList.remove(position);
        communityDetailsCommentAdapter.notifyItemChanged(position);
        showEmptyData();
    }

    //新增评论或回复
    public void addDelRelay(CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean commentBean) {
        allCommentBeanList.add(commentBean);
        communityDetailsCommentAdapter.notifyDataSetChanged();
        showEmptyData();
    }
}
