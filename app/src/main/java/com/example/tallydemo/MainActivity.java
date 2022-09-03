package com.example.tallydemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "lh" ;
    private DBHelper helper;
    private ListView listView;
    private ImageButton Add;
    private ImageButton btn_me;
    private List<costList> list;

    private TextView sum_pay;
    private TextView sum_income;

    private TextView txt_year,txt_month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        intViews();
        intData();
        setData();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

//        单击的item的id
        int id = Integer.valueOf((int) info.id);
        String myId = list.get(id).get_id();

        switch (item.getItemId()){
            case 0:
//                修改
                String myDate = list.get(id).getDate();
                String myTitle = list.get(id).getTitle();
                int myMoney = list.get(id).getMoney();
                String myWay = list.get(id).getWay();
                String mySort = list.get(id).getSort();

                Intent intent = new Intent();
                Bundle bb = new Bundle();
                bb.putInt("flag",1);//修改是1，添加是0
                bb.putString("myId",myId);
                bb.putString("myDate",myDate);
                bb.putString("myTitle",myTitle);
                bb.putInt("myMoney",myMoney);
                bb.putString("myWay",myWay);
                bb.putString("mySort",mySort);
                intent.putExtras(bb);
                intent.setClass(MainActivity.this,new_cost.class);
                startActivityForResult(intent,1);
                setData();
                break;
            case 1:
//                删除
                SQLiteDatabase db = helper.getWritableDatabase();
                db.delete("account","_id=?",new String[]{myId+""});
                intData();
                setData();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void intData() {
        list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("account",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            costList costList = new costList();
            costList.set_id(cursor.getString(cursor.getColumnIndex("_id")));
            costList.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
            costList.setDate(cursor.getString(cursor.getColumnIndex("Date")));
            costList.setMoney(cursor.getInt(cursor.getColumnIndex("Money")));
            costList.setSort(cursor.getString(cursor.getColumnIndex("Sort")));
            costList.setWay(cursor.getString(cursor.getColumnIndex("Way")));
            list.add(costList);
        }
        for (int i = 0;i<list.size();i++){
            Log.d(TAG, "intData: 这是i"+list.get(i));
        }
        db.close();
        listView.setAdapter(new MyAdapter(this,list));
    }


//数据初始化
    private void intViews() {
        helper=new DBHelper(MainActivity.this);
        listView = findViewById(R.id.list_view);
        Add=findViewById(R.id.add);
        btn_me = findViewById(R.id.btn_me);
        sum_income = findViewById(R.id.sum_income);
        sum_pay = findViewById(R.id.sum_pay);

        txt_year = findViewById(R.id.txt_year);
        txt_month = findViewById(R.id.txt_month);

        Calendar calendar = Calendar.getInstance();
        txt_year.setText(calendar.get(Calendar.YEAR)+"年");
        txt_month.setText(calendar.get(Calendar.MONTH)+1+"月");

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("选择操作");
                menu.add(0, 0, 0, "修改");
                menu.add(0, 1, 0, "删除");
            }
        });


        btn_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("flag",0);
                intent.setClass(MainActivity.this,MyActivity.class);
                startActivityForResult(intent,1);
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("flag",0);
                intent.setClass(MainActivity.this, new_cost.class);
                startActivityForResult(intent,1);
            }
        });
        Log.d("TAG", "intViews: 111111111111111");
        intData();
    }

//    当前月份赋值
    private void setData() {
        int intPay = 0;
        int intIncome = 0;

        for(int i = 0; i<list.size(); i++){
            if ((list.get(i).getSort()).equals("Pay")){
                intPay=intPay+list.get(i).getMoney();
            }else if ((list.get(i).getSort()).equals("Income")){
                intIncome+=list.get(i).getMoney();
            }
        }
        Log.d(TAG, "setData: "+intPay);
        Log.d(TAG, "setData: "+intIncome);
        sum_pay.setText(intPay+"");
        sum_income.setText(intIncome+"");
    }

//    /*
//    Activity之间跳转值的传递
//    我们这里假设有A、B两个Activity，由A跳转到B，然后B返回到A。
//    在这一过程中，我们在A启动B的过程中调用startActivityForResult（）方法来启动B，
//    然后在B中调用Context.setResult（）方法来传递返回的Code,
//    并在A中的onActivityResult（）方法中解析返回的结果，并做相应的逻辑操作。
//     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode ==1){
            this.intData();
            this.setData();
        }
    }
}