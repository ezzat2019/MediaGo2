package com.example.programmer.mediago2.viewpagers;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyViewPager extends FragmentPagerAdapter {

    List<Fragment> fragmentList;
    List<CharSequence> names;
    public MyViewPager(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
        names=new ArrayList();

        names.add("SONGS");
        names.add("ARTISTS");
        names.add("ALBUMS");



    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
