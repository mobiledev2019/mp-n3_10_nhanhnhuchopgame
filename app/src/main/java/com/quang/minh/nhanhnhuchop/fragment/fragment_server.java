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
import com.quang.minh.nhanhnhuchop.main.sub_Home.Database_table;
import com.quang.minh.nhanhnhuchop.model.player;
import com.quang.minh.nhanhnhuchop.adapter.player_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragment_server extends Fragment {
    private ArrayList<player> player_list;
    private player_adapter adapter;
    private ListView lv_server;
    private String url = "http://192.168.43.148:8080/nhanhNhuChop/getPlayer.php";
    Cursor cursor;
    int i = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_server, container, false);
        lv_server = view.findViewById(R.id.lv_server);
        player_list = new ArrayList<>();
        adapter = new player_adapter(getContext(), R.layout.player_line, player_list);
        lv_server.setAdapter(adapter);

        get_high_score(url);
        getData();
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
//                        player_list.add(new player( i+1 , jo.getString("ID"), jo.getString("NAME"),
//                                jo.getInt("SCORE")));
//                        Database_table.database.insert_server(jo.getString("ID"),jo.getString("NAME"),jo.getInt("SCORE"));
                        Database_table.database.queryData("INSERT INTO Server VALUES('"+ jo.getString("ID") +"','"+jo.getString("NAME")+"','"+jo.getInt("SCORE")+"')");
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

    public void getData(){
        cursor = Database_table.database.getData("SELECT Id , Name , MAX(Score) AS MAXScore FROM Server GROUP BY Name ORDER BY MAXScore DESC");
        if(cursor.getCount()!=0) {
            while (cursor.moveToNext()) {
                    player_list.add(new player(i, cursor.getString(0), cursor.getString(1), cursor.getInt(2)));
                    i = i + 1;
            }
            adapter.notifyDataSetChanged();
        }
        Database_table.database.queryData("DELETE FROM Server");
    }
}
