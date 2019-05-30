package com.cashier.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.cashier.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/22 11:02
 * @change
 * @chang time
 * @class describe
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitleArray;
    private List<Fragment> fragmentList;


    public ViewPagerAdapter(FragmentManager fm, Context context,
                            List fragmentList, String[] tabTitleArray) {
        super(fm);

        this.tabTitleArray = tabTitleArray;
        this.fragmentList = fragmentList;
    }


    /* 重写与TabLayout配合 */

    @Override
    public int getCount() {
        return tabTitleArray.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleArray[position % tabTitleArray.length];
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
}
