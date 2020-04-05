package com.example.autobrary.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public TabAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.mNumOfTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                TabFrag_1 tab1 = new TabFrag_1();
                return tab1;
            case 1:
                TabFrag_2 tab2 = new TabFrag_2();
                return tab2;
            case 2:
                TabFrag_3 tab3 = new TabFrag_3();
                return tab3;
            default:
                return null;
        }
        //return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
