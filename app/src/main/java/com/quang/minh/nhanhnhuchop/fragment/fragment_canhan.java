package com.quang.minh.nhanhnhuchop.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.quang.minh.nhanhnhuchop.R;
import com.quang.minh.nhanhnhuchop.main.sub_Home.Database_table;
import com.quang.minh.nhanhnhuchop.model.player;
import com.quang.minh.nhanhnhuchop.adapter.player_adapter;

import java.util.ArrayList;

public class fragment_canhan extends Fragment {
    private ArrayList<player> player_list;
    private player_adapter adapter;
    private ListView lv_canhan;
    int i = 1;
    Cursor cursor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canhan, container, false);
        lv_canhan = view.findViewById(R.id.lv_canhan);
        player_list = new ArrayList<>();
        adapter = new player_adapter(getContext(), R.layout.player_line, player_list);
        lv_canhan.setAdapter(adapter);
        getData();
        return  view;
    }

    public void getData(){
        cursor = Database_table.database.getData("SELECT * FROM CaNhan ORDER BY Score DESC");
        if(cursor.getCount()!=0) {
            while (cursor.moveToNext()) {
                if(i<=10) {
                    player_list.add(new player(i, null, "Player " + cursor.getInt(0), cursor.getInt(1)));
                    i = i + 1;
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}
