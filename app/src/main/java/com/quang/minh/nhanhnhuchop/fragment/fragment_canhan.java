package com.quang.minh.nhanhnhuchop.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.quang.minh.nhanhnhuchop.R;
import com.quang.minh.nhanhnhuchop.main.Home;
import com.quang.minh.nhanhnhuchop.model.player;
import com.quang.minh.nhanhnhuchop.model.player_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        cursor = Home.database.getData("SELECT * FROM CaNhan ORDER BY Score DESC");
        if(cursor.getCount()!=0) {
            while (cursor.moveToNext()) {
                player_list.add(new player(i, null, "Player " + i, cursor.getInt(0)));
                i = i+1;
            }
            adapter.notifyDataSetChanged();
        }
    }
}
