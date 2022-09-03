package com.example.tallydemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //当系统到我们设定的时间点的时候会发送广播，执行这里
        Log.d("11111111111111111111", "onReceive: 11111111111111111");
        Toast.makeText(context,"记账时间到了",Toast.LENGTH_SHORT).show();
    }
}
