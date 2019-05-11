package com.quang.minh.nhanhnhuchop.fragment;

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
import com.quang.minh.nhanhnhuchop.model.player;
import com.quang.minh.nhanhnhuchop.model.player_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragment_server extends Fragment {
    private ArrayList<player> player_list;
    private player_adapter adapter;
    private ListView lv_server;
    private String url = "http://192.168.1.7:8080/nhanhNhuChop/getPlayer.php";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_server, container, false);
        lv_server = view.findViewById(R.id.lv_server);
        player_list = new ArrayList<>();
        adapter = new player_adapter(getContext(), R.layout.player_line, player_list);
        lv_server.setAdapter(adapter);
        get_high_score(url);
        return  view;
    }

    public void get_high_score(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i< response.length(); i++){
                    try {
                        JSONObject jo = response.getJSONObject(i);
                        player_list.add(new player( i+1 , jo.getString("ID"), jo.getString("NAME"),
                                jo.getInt("SCORE")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
