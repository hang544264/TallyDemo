package com.example.tallydemo;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class MyAdapter extends BaseAdapter{
    private static final String TAG = "lh";
    List<costList> myList;

    public MyAdapter(List<costList>list)
    {
        myList = list;
    }
    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = myLayoutInflater.inflate(R.layout.item,null);

        costList item = myList.get(position);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_date = view.findViewById(R.id.tv_date);
        TextView tv_money = view.findViewById(R.id.tv_money);
        TextView tv_way = view.findViewById(R.id.tv_way);

        tv_title.setText(myList.get(position).getTitle());
        tv_date.setText(myList.get(position).getDate());
        String a = (myList.get(position).getSort());

        SpannableString spannableString = new SpannableString(a);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FFA416")),
                spannableString.length() - spannableString.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_money.setText(spannableString);

        tv_money.append(":"+myList.get(position).getMoney());
        tv_way.setText(myList.get(position).getWay());
        Log.d(TAG, "getView: "+myList.get(position).getSort());
        return view;
    }

    private List<costList>getMyList;
    private LayoutInflater myLayoutInflater;

    public MyAdapter(Context context,List<costList> list){
        myList = list;
        myLayoutInflater = LayoutInflater.from(context);
    }

}
