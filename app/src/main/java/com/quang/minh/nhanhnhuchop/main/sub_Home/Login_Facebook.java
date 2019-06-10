package com.quang.minh.nhanhnhuchop.main.sub_Home;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.quang.minh.nhanhnhuchop.database.database;
import com.quang.minh.nhanhnhuchop.main.Home;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Login_Facebook {
    String name = "";
    String id = "" ;
    public void set_login_button(){
        Home.callbackManager = CallbackManager.Factory.create();
        //        loginButton.setPublishPermissions(Arrays.asList("public_picture","email"));
        Home.loginButton.setReadPermissions(Arrays.asList("public_profile, email"));
//        database.queryData("CREATE TABLE IF NOT EXISTS Account(Id VARCHAR(200) PRIMARY KEY, Name VARCHAR(200))");
        Home.loginButton.registerCallback(Home.callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Home.set_visible(View.VISIBLE);

                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("JSON", response.getJSONObject().toString());
                        try {
                            name = object.getString("name");
                            id = object.getString("id");
                            Home.prof.setProfileId(object.getString("id"));
                            Home.tv_name_acc_facebook.setText(name);
                            //Bitmap bmp = BitmapFactory.decodeResource(getResources(), prof);
//                            BitmapDrawable bitmapDrawable = (BitmapDrawable) prof.getBackground();
//                            Bitmap bitmap = bitmapDrawable.getBitmap();
//                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                            byte[] profile = byteArrayOutputStream.toByteArray();

                            Database_table.database.queryData("INSERT INTO Account VALUES('"+id+"','"+name+"')");
                            Home.login = 1;
                            Home.editor.putInt("login", Home.login);
                            Home.editor.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameter = new Bundle();
                parameter.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameter);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    public void set_Login_Logout_Button(){
        if(Home.login == 0)
            Log.d("123","123");
            set_login_button();
        if(Home.login == 1) {
            Log.d("123","456");
            Home.set_visible(View.VISIBLE);
            Cursor cursor = Database_table.database.getData("SELECT * FROM Account");
            while (cursor.moveToNext()) {
                id = cursor.getString(0);
                name = cursor.getString(1);
                Home.prof.setProfileId(id);
                Home.tv_name_acc_facebook.setText(name);
            }
        }
        Home.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("123","789");
                LoginManager.getInstance().logOut();
                Home.set_visible(View.INVISIBLE);
                Database_table.database.queryData("DELETE FROM Account");
                Home.login = 0;
                Home.editor.putInt("login", Home.login);
                Home.editor.commit();
            }
        });
    }

}
