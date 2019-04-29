package com.quang.minh.nhanhnhuchop.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class fragment_adapter extends FragmentStatePagerAdapter {
    private String listTab[] = {"Server","Cá nhân"};
    private fragment_server fragment_server;
    private fragment_canhan fragment_canhan;
    public fragment_adapter(FragmentManager fm) {
        super(fm);
        fragment_server = new fragment_server();
        fragment_canhan = new fragment_canhan();
    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0) {
            return fragment_server;
        }
        else if(i==1){
            return fragment_canhan;
        }
        else{

        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
