package com.door.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类内的每一个生成的 Fragment 都将保存在内存之中，
 * 因此适用于那些相对静态的页，数量也比较少的那种；
 * 如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，
 * 应该使用FragmentStatePagerAdapter  https://www.jianshu.com/p/a778649c254d
 * <p>
 * hxg 2019.09.29 q:929842234
 */
public class DoorFragmentAdapter extends FragmentPagerAdapter {

    private List<String> mTitles;
    private List<Fragment> fragmentList = new ArrayList<>();

    public DoorFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public DoorFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitles) {
        super(fm);
        setFragments(fm, fragmentList, mTitles);
    }

    /**
     * 创建或刷新fragment
     */
    public void setFragments(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitles) {
        this.mTitles = mTitles;
        if (this.fragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragmentList) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();//提交
            ft = null;
            fm.executePendingTransactions();//执行
        }
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position % mTitles.size());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}
