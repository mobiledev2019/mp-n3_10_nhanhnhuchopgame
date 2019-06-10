package com.quang.minh.nhanhnhuchop.main.sub_Play;

import android.content.Context;
import android.database.Cursor;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.quang.minh.nhanhnhuchop.main.Play;
import com.quang.minh.nhanhnhuchop.main.sub_Home.Database_table;
import com.quang.minh.nhanhnhuchop.model.question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

public class Get_questions_data {

    public static void getData_from_server(String url, Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i<response.length(); i++){
                    try {
                        JSONObject jo = response.getJSONObject(i);
                        Play.questions_array.add(new question(jo.getInt("ID"), jo.getString("QUESTION"),
                                jo.getString("ANSWER1"), jo.getString("ANSWER2"), jo.getString("ANSWER3"),
                                jo.getString("ANSWER4"), jo.getInt("RESULT"), jo.getString("HINT")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Collections.shuffle(Play.questions_array);
//                setData(0);
//                Log.d("arr3", questions_array.size()+"");
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


    public static void getData_from_database(){
        Cursor cursor = Database_table.database.getData("SELECT * FROM Question");
        while(cursor.moveToNext()){
            Play.questions_array.add(new question(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getInt(6), cursor.getString(7)));
            Collections.shuffle(Play.questions_array);
        }
    }
}
