package com.quang.minh.nhanhnhuchop.main;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.quang.minh.nhanhnhuchop.R;
import com.quang.minh.nhanhnhuchop.fragment.dialog_fragment;
import com.quang.minh.nhanhnhuchop.main.sub_Home.Database_table;
import com.quang.minh.nhanhnhuchop.main.sub_Home.Hash_Key;
import com.quang.minh.nhanhnhuchop.main.sub_Home.Login_Facebook;
import com.quang.minh.nhanhnhuchop.service.alarm_service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Home extends AppCompatActivity{
    public static ProfilePictureView prof;
    public static LoginButton loginButton;
    public static TextView tv_name_acc_facebook, tv_all;
    TextView tv_timePicker, tv_noti;
    private Button btChoiNgay;
    Spinner spinner_repeat;
    public static MediaPlayer home_mp3;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    AlarmManager alarmManager;
    public static PendingIntent pendingIntent;
    public static int vitri = 1;
    public static int login = 0;
    public static int insert_data = 0;
    public static int check_am_thanh = 1 , check_nhac_nen = 1;
    public static CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setSharedPreferences();
        init();
        set_visible(View.INVISIBLE);
        Hash_Key hash_key = new Hash_Key();
        hash_key.find_hashkey();
        Database_table database_table = new Database_table();
        database_table.database(this);
        database_table.setInsert_data();
        Login_Facebook login_facebook = new Login_Facebook();
        login_facebook.set_Login_Logout_Button();
    }
    public void init(){
        btChoiNgay = findViewById(R.id.bt_choi_ngay);
        prof = findViewById(R.id.profile_picture);
        loginButton = findViewById(R.id.login_button_facebook);
        tv_name_acc_facebook = findViewById(R.id.tv_name_acc_facebook);
        tv_all = findViewById(R.id.tv_all);
        editor = sharedPreferences.edit();
        home_mp3 = MediaPlayer.create(this, R.raw.home);
        if(check_nhac_nen == 1){
            home_mp3.start();
            home_mp3.setLooping(true);
        }
    }

    public static void set_visible(int visible){
        tv_all.setVisibility(visible);
        tv_name_acc_facebook.setVisibility(visible);
        prof.setVisibility(visible);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    protected void onStart() {
//        LoginManager.getInstance().logOut();
//        LoginManager.getInstance().getAuthType();
//        database.queryData("DELETE FROM Account");
//        super.onStart();
//    }

    public void choi_ngay(View view){
        btChoiNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(home_mp3.isPlaying())
                    home_mp3.stop();
                Intent it = new Intent(Home.this, Rules.class);
                startActivity(it);
            }
        });
    }

    public void cai_dat(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_cai_dat);
        dialog.show();
        final SwitchCompat switchCompat = dialog.findViewById(R.id.switch_thongbao);
        tv_noti = dialog.findViewById(R.id.tv_noti);
        ImageView img_close = dialog.findViewById(R.id.close);
        CheckBox cb_nhac_nen = dialog.findViewById(R.id.cb_nhac_nen);
        CheckBox cb_am_thanh = dialog.findViewById(R.id.cb_am_thanh);
        tv_timePicker = dialog.findViewById(R.id.tv_timePicker);
        spinner_repeat = dialog.findViewById(R.id.spinner_repeat);
        cb_nhac_nen.setChecked(sharedPreferences.getBoolean("check_nhac_nen", true));
        cb_am_thanh.setChecked(sharedPreferences.getBoolean("check_am_thanh", true));
        switchCompat.setChecked(sharedPreferences.getBoolean("switch_info", false));
        tv_timePicker.setText(sharedPreferences.getString("time_set", "Đặt giờ"));
        spinner_repeat.getItemAtPosition(sharedPreferences.getInt("repeat_set", 0));
        tv_noti.setText("Bạn đặt thời gian chơi là "+sharedPreferences.getString("time_set", "Đặt giờ"));

        if(switchCompat.isChecked()){
            tv_timePicker.setEnabled(true);
            spinner_repeat.setEnabled(true);
            tv_noti.setVisibility(View.VISIBLE);
            switch_on();
        }
        else{
            tv_timePicker.setEnabled(false);
            spinner_repeat.setEnabled(false);
            tv_noti.setVisibility(View.INVISIBLE);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tv_timePicker.setEnabled(true);
                    spinner_repeat.setEnabled(true);
                    tv_noti.setVisibility(View.VISIBLE);
                    switch_on();
                    editor.putBoolean("switch_info", true);
                    editor.commit();
                }
                else {
                    Intent intent = new Intent(Home.this, alarm_service.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(Home.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);

                    tv_timePicker.setEnabled(false);
                    spinner_repeat.setEnabled(false);
                    tv_noti.setVisibility(View.INVISIBLE);
                    editor.putBoolean("switch_info", false);
                    editor.commit();
                }
            }
        });


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        cb_am_thanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check_am_thanh = 1;
                    editor.putInt("id_am_thanh", check_am_thanh);
                    editor.putBoolean("check_am_thanh", true);
                    editor.commit();
                }
                else {
                    check_am_thanh = 0;
                    editor.putInt("id_am_thanh", check_am_thanh);
                    editor.putBoolean("check_am_thanh", false);
                    editor.commit();
                }
            }
        });

        cb_nhac_nen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check_nhac_nen =1;
                    home_mp3 = MediaPlayer.create(Home.this, R.raw.home);
                    home_mp3.start();
                    editor.putInt("id_nhac_nen", check_nhac_nen);
                    editor.putBoolean("check_nhac_nen", true);
                    editor.commit();
                }
                else {
                    check_nhac_nen = 0;
                    if(home_mp3.isPlaying())
                        home_mp3.stop();
                    editor.putInt("id_nhac_nen", check_nhac_nen);
                    editor.putBoolean("check_nhac_nen", false);
                    editor.commit();
                }
            }
        });
    }

    public void diem_cao(View view) {
        dialog_fragment dialog_fragment = new dialog_fragment();
        dialog_fragment.show(getSupportFragmentManager(), "fragment");
//        Intent intent = new Intent(Home.this, HighScore.class);
//        startActivity(intent);
    }

    public void setSharedPreferences(){
        sharedPreferences = getSharedPreferences("note", MODE_PRIVATE);
        check_am_thanh = sharedPreferences.getInt("id_am_thanh",check_am_thanh);
        check_nhac_nen = sharedPreferences.getInt("id_nhac_nen",check_nhac_nen);
        login = sharedPreferences.getInt("login", login);
        vitri = sharedPreferences.getInt("vitri", vitri);
        insert_data = sharedPreferences.getInt("insert_data", insert_data);
    }

    public void switch_on(){
        ArrayList<String> array_repeat = new ArrayList<>();
        array_repeat.add("1 ngày");
        array_repeat.add("2 ngày");
        array_repeat.add("3 ngày");
        ArrayAdapter adapter_repeat = new ArrayAdapter(Home.this, R.layout.spinner_custom, array_repeat);
        spinner_repeat.setAdapter(adapter_repeat);

        final Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minutes = calendar.get(Calendar.MINUTE);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        //final String time = simpleDateFormat.format(calendar.getTime());

        tv_timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogFragment newFragment = new timePickerFragment();
//                newFragment.show(getSupportFragmentManager(), "datePicker");
                TimePickerDialog timePickerDialog = new TimePickerDialog(Home.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //calendar.set(0,0,0, hourOfDay , minute);
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        calendar.set(Calendar.SECOND,0);
                        tv_timePicker.setText(simpleDateFormat.format(calendar.getTime()));

                        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        Intent intent = new Intent(Home.this, alarm_service.class);
                        pendingIntent = PendingIntent.getBroadcast(Home.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                        tv_noti.setText("Bạn đặt thời gian chơi là "+String.valueOf(tv_timePicker.getText()));
                        editor.putString("time_set", String.valueOf(tv_timePicker.getText()));
                        editor.commit();
                    }
                }, hour, minutes, true);
                timePickerDialog.show();
            }
        });
        String text_repeat = spinner_repeat.getSelectedItem().toString();
        editor.putInt("repeat_set", spinner_repeat.getSelectedItemPosition());
        Log.d("123", spinner_repeat.getSelectedItemPosition()+"");
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.setCanceledOnTouchOutside(false);
        Button btThoat = dialog.findViewById(R.id.bt_thoat);
        Button btQuayLai = dialog.findViewById(R.id.bt_quay_lai);
        TextView tvAll = dialog.findViewById(R.id.tvAll);
        tvAll.setText("Bạn muốn thoát trò chơi?");
        dialog.show();
        btQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }


//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
//        calendar.set(Calendar.MINUTE,minute);
//        calendar.set(Calendar.SECOND,0);
////        calendar.set(0,0,0, hourOfDay , minute);
//        //tv_timePicker.setText(""+ DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar));
//        tv_timePicker.setText(simpleDateFormat.format(calendar.getTime()));
//        editor.putString("time_set", String.valueOf(tv_timePicker.getText()));
//        editor.commit();
//        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent intent = new Intent(Home.this, alarm_service.class);
//        pendingIntent = PendingIntent.getBroadcast(Home.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//    }
}
