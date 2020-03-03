package com.community.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.BeeFramework.activity.BaseFragment;
import com.community.adapter.CommunityDetailsLikeAdapter;
import com.community.entity.CommunityDynamicsListEntity;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * @ProjectName:
 * @Package: com.community.fragment
 * @ClassName: CommunityLikeListFragment
 * @Description: 动态点赞的列表
 * @Author: yuansk
 * @CreateDate: 2020/2/21 10:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/21 10:45
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class CommunityLikeListFragment extends BaseFragment {

    private SwipeMenuRecyclerView rv_community_comment;
    private TextView tv_no_data;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_community_commentlist;
    }

    private List<CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean> allZanBeanList=new ArrayList<>();
    private CommunityDetailsLikeAdapter  communityDetailsLikeAdapter;
    @Override
    protected void initView(View rootView) {
        rv_community_comment = rootView.findViewById(R.id.rv_community_comment);
        tv_no_data = rootView.findViewById(R.id.tv_no_data);
        //进行点赞列表的显示
        communityDetailsLikeAdapter=new CommunityDetailsLikeAdapter(getActivity(),allZanBeanList);
        rv_community_comment.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        rv_community_comment.setAdapter(communityDetailsLikeAdapter);
        showEmptyData();
    }

    private void  showEmptyData(){
        if (allZanBeanList==null||allZanBeanList.size()==0){
            tv_no_data.setVisibility(View.VISIBLE);
        }else{
            tv_no_data.setVisibility(View.GONE);
        }
    }



    public void showLikeList(List<CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean> zanBeanList ){
        allZanBeanList.clear();
        if (null!=zanBeanList){
            allZanBeanList.addAll(zanBeanList);
        }
    }

    //取消点赞
    public void  cancelLike(int pos){
        allZanBeanList.remove(pos);
        communityDetailsLikeAdapter.notifyItemRemoved(pos);
        communityDetailsLikeAdapter.notifyItemChanged(pos, allZanBeanList.size());
        showEmptyData();
    }

    public void  clickZan(CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean  zanBean){
        allZanBeanList.add(zanBean);
        communityDetailsLikeAdapter.notifyDataSetChanged();
        showEmptyData();
    }
}
