package com.example.tallydemo;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class MyActivity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_time;
    private Button btn_set;
    private TextView txt_my_name;
    private Button btn_qu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        intViews();
        getName();
    }

    private void getName() {
        Intent intent = getIntent();
        int flag = intent.getExtras().getInt("flag");
        if (flag == 1){
            txt_my_name.setText(intent.getExtras().getString("name")+"");
        }
    }

    private void intViews() {
        btn_qu = findViewById(R.id.btn_qu);
        txt_my_name = findViewById(R.id.txt_my_name);
        btn_login = findViewById(R.id.btn_login);
        btn_time = findViewById(R.id.btn_time);
        btn_set = findViewById(R.id.btn_set);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyActivity.this,loginActivity.class);
                startActivityForResult(intent,1);
                finish();
            }
        });

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY);    //得到小时
                int minute  = calendar.get(Calendar.MINUTE);            //得到分钟
                new TimePickerDialog(MyActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //  这个方法是得到选择后的 小时、分钟，分别对应着三个参数 —   hourOfDay、minute
                        String time = hourOfDay+":"+minute;
                        setTime(hourOfDay,minute);
                        Toast.makeText(MyActivity.this,"你设置了每天"+time+"的提醒",Toast.LENGTH_SHORT).show();
                    }
                }, hourOfDay, minute, true).show();
            }
        });

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyActivity.this,"暂未开放",Toast.LENGTH_SHORT).show();
            }
        });

        btn_qu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, AlarmReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(MyActivity.this, 0,
                        intent, 0);
                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                //取消警报
                am.cancel(pi);
                Toast.makeText(MyActivity.this, "关闭了提醒", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTime(int hour,int minute) {
//        得到日历实例，主要是为了下面的获取时间
        Calendar mCalendar  = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());

//        获取当前毫秒值
        long systemTime = System.currentTimeMillis();

//        是设置日历的时间，主要是让日历的年月日和当前同步
        mCalendar.setTimeInMillis(System.currentTimeMillis());
//         这里时区需要设置一下，不然可能个别手机会有8个小时的时间差
        mCalendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//         设置在几点提醒
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        mCalendar.set(Calendar.MINUTE, minute);
//        设置在几分提醒
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);

//         获取上面设置的毫秒值
        long selectTime = mCalendar.getTimeInMillis();
//         如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if(systemTime > selectTime) {
            mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(MyActivity.this,AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(MyActivity.this, 0, intent, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

        /*
         * 重复提醒
         * 第一个参数是警报类型；下面有介绍
         * 第二个参数网上说法不一，很多都是说的是延迟多少毫秒执行这个闹钟，但是我用的刷了MIUI的三星手机的实际效果是与单次提醒的参数一样，即设置的13点25分的时间点毫秒值
         * 第三个参数是重复周期，也就是下次提醒的间隔 毫秒值 我这里是一天后提醒
         */
//        AlarmManager.RTC，硬件闹钟，不唤醒手机（也可能是其它设备）休眠；当手机休眠时不发射闹钟。
//        AlarmManager.RTC_WAKEUP，硬件闹钟，当闹钟发躰时唤醒手机休眠；
//        AlarmManager.ELAPSED_REALTIME，真实时间流逝闹钟，不唤醒手机休眠；当手机休眠时不发射闹钟。
//        AlarmManager.ELAPSED_REALTIME_WAKEUP，真实时间流逝闹钟，当闹钟发躰时唤醒手机休眠；
//
//        RTC闹钟和ELAPSED_REALTIME最大的差别就是前者可以通过修改手机时间触发闹钟事件，后者要通过真实时间的流逝，即使在休眠状态，时间也会被计算。

        am.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), (1000 * 60 * 60 * 24), pi);
    }
}
