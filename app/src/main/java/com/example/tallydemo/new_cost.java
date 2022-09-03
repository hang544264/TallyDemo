package com.example.tallydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class new_cost extends AppCompatActivity {

    private DBHelper helper;
    private EditText et_cost_title;
    private EditText et_cost_money;
    private DatePicker dp_cost_date;
    private String way;
    private String sort;
    private int flag;
    private String myId;

    private String TAG = "lh";

//    sort
    private RadioGroup group_sort;
    private RadioButton rad_pay;
    private RadioButton rad_income;

//    way
    private RadioGroup group_way;
    private RadioButton rad_cash;
    private RadioButton rad_weChat;
    private RadioButton rad_aliPay;
    private RadioButton rad_else;

    public new_cost() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cost);
        initViews();
        getView();
    }

//    修改
    private void getView() {
        Intent listIntent =getIntent();
        flag = listIntent.getExtras().getInt("flag");
        if (flag == 1){
            myId = listIntent.getExtras().getString("myId");
            String myTitle = listIntent.getExtras().getString("myTitle");
            int myMoney = listIntent.getExtras().getInt("myMoney");
            String s = listIntent.getExtras().getString("mySort");
            String w = listIntent.getExtras().getString("myWay");
            Log.d(TAG, "getView: "+s);
            switch (s){
                case "Pay":rad_pay.setChecked(true);
                    break;
                case "Income":rad_income.setChecked(true);
                    break;
            }
            switch (w){
                case "Cash":rad_cash.setChecked(true);
                    break;
                case "WeChat":rad_weChat.setChecked(true);
                    break;
                case "AilPay":rad_aliPay.setChecked(true);
                    break;
                case "Else":rad_else.setChecked(true);
                    break;
            }
            et_cost_title.setText(myTitle+"");
            et_cost_money.setText(myMoney+"");
        }

    }

//    初始化
    private void initViews() {
        helper = new DBHelper(new_cost.this);
        et_cost_title = findViewById(R.id.et_cost_title);
        et_cost_money = findViewById(R.id.et_cost_money);
        dp_cost_date = findViewById(R.id.dp_cost_date);

        group_sort = findViewById(R.id.Group_sort);
        rad_pay = findViewById(R.id.rad_pay);
        rad_income = findViewById(R.id.rad_income);

        group_way = findViewById(R.id.Group_way);
        rad_cash = findViewById(R.id.rad_cash);
        rad_weChat = findViewById(R.id.rad_weChat);
        rad_aliPay = findViewById(R.id.rad_aliPay);
        rad_else = findViewById(R.id.rad_else);

        group_sort.setOnCheckedChangeListener(new MyRadioButtonListener());
    }

//    获取RadioButton的方法1 需要初始化所有组件
    class MyRadioButtonListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rad_pay:
                    sort = rad_pay.getText().toString();
                    break;
                case R.id.rad_income:
                    sort = rad_income.getText().toString();
                    break;
            }
        }
    }


    public void  okButton(View view) {
        if (flag == 0) {
            String titleStr = et_cost_title.getText().toString().trim();
            String moneyStr = et_cost_money.getText().toString().trim();
//        获取sort
            String sortStr = sort;
            Log.d(TAG, "okButton: " + sortStr);
//        获取way   获取RadioButton的方法1 不需要初始化所有组件
            String wayStr = null;
            for (int i = 0; i < group_way.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) group_way.getChildAt(i);
                if (radioButton.isChecked()) {
                    wayStr = radioButton.getText().toString();
                    break;
                }
            }
            Log.d(TAG, "okButton: " + wayStr);
            String dateStr = dp_cost_date.getYear() + "-" + (dp_cost_date.getMonth() + 1) + "-"
                    + dp_cost_date.getDayOfMonth();
            if ("".equals(moneyStr)) {
                Toast toast = Toast.makeText(this, "请填写金额", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("Title", titleStr);
                values.put("Money", moneyStr);
                values.put("Date", dateStr);
                values.put("Sort", sortStr);
                values.put("Way", wayStr);
                long account = db.insert("account", null, values);
                Log.d(TAG, "okButton: " + account);
                if (account > 0) {
                    Toast toast = Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    setResult(1);
                    finish();
                } else {
                    Toast toast = Toast.makeText(this, "请重试", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    db.close();
                }
                setResult(1);
                finish();
            }
        }else if (flag == 1) {
            String titleStr = et_cost_title.getText().toString().trim();
            String moneyStr = et_cost_money.getText().toString().trim();
//        获取sort
            String sortStr = sort;
            Log.d(TAG, "okButton: " + sortStr);
//        获取way   获取RadioButton的方法1 不需要初始化所有组件
            String wayStr = null;
            for (int i = 0; i < group_way.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) group_way.getChildAt(i);
                if (radioButton.isChecked()) {
                    wayStr = radioButton.getText().toString();
                    break;
                }
            }
            Log.d(TAG, "okButton: " + wayStr);
            String dateStr = dp_cost_date.getYear() + "-" + (dp_cost_date.getMonth() + 1) + "-"
                    + dp_cost_date.getDayOfMonth();
            if ("".equals(moneyStr)) {
                Toast toast = Toast.makeText(this, "请填写金额", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("Title", titleStr);
                values.put("Money", moneyStr);
                values.put("Date", dateStr);
                values.put("Sort", sortStr);
                values.put("Way", wayStr);
                long account = db.update("account", values, "_id=?", new String[]{myId + ""});
                Log.d(TAG, "okButton: " + account);
                if (account > 0) {
                    Toast toast = Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    setResult(1);
                    finish();
                } else {
                    Toast toast = Toast.makeText(this, "请重试", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    db.close();
                }
                setResult(1);
                finish();
            }
        }
    }
}
