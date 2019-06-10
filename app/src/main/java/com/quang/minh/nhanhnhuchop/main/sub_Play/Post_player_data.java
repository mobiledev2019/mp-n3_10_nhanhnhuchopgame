package com.quang.minh.nhanhnhuchop.main.sub_Play;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.quang.minh.nhanhnhuchop.main.Home;
import com.quang.minh.nhanhnhuchop.main.Play;
import com.quang.minh.nhanhnhuchop.main.sub_Home.Database_table;

import java.util.HashMap;
import java.util.Map;

public class Post_player_data {

    public static void postData(String url_post, Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_post
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                Cursor cursor = Database_table.database.getData("SELECT * FROM Account");
                while(cursor.moveToNext()){
                    if(Home.login==1) {
                        params.put("id_player", cursor.getString(0));
                        params.put("name_player", cursor.getString(1));
                        params.put("score_player", Play.diem + "");
                        Log.d("diem", cursor.getString(0));
                        Log.d("diem", cursor.getString(1));
                        Log.d("diem", Play.diem + "");
                    }
                }
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
