package com.quang.minh.nhanhnhuchop.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class alarm_service extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent it = new Intent(context, service.class);
        context.startService(it);
        Log.d("zxc", "Hello");
    }
}
