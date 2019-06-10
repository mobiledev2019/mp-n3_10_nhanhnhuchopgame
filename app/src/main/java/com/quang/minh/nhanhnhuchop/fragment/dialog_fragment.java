package com.quang.minh.nhanhnhuchop.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.quang.minh.nhanhnhuchop.R;

public class dialog_fragment extends DialogFragment {
    ImageView img_close;
    Button bt_server, bt_canhan;
    View view;
    FragmentTransaction fragmentTransaction;
    int i = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_high_score, container,false);
        init();
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new fragment_server());
        fragmentTransaction.commit();
        bt_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==1) {
                    fragmentTransaction = getChildFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, new fragment_server());
                    fragmentTransaction.commit();
                    i = 0;
                }
            }
        });
        bt_canhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0) {
                    fragmentTransaction = getChildFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, new fragment_canhan());
                    fragmentTransaction.commit();
                    i = 1;
                }
            }
        });
        return view;
    }

    public void init(){
        img_close = view.findViewById(R.id.close);
        bt_server = view.findViewById(R.id.bt_server);
        bt_canhan = view.findViewById(R.id.bt_canhan);
    }

//    public void add_fragment(View view){
//        FragmentManager fragmentManager = getChildFragmentManager();
//        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//        Fragment fragment = null;
//        switch (view.getId()){
//            case R.id.bt_server:
//                fragment = new fragment_server();
//                break;
//            case R.id.bt_canhan:
//                fragment = new fragment_canhan();
//                break;
//        }
//        fragmentTransaction.replace(R.id.frame_layout, fragment);
//        fragmentTransaction.commit();
//    }
}
