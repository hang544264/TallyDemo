package com.example.tallydemo;

public class costList {
    private String _id;
    private String Title;
    private String Date;
    private int Money;
    private String Sort;
    private String Way;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        this.Sort = sort;
    }

    public String getWay() {
        return Way;
    }

    public void setWay(String way) {
        this.Way = way;
    }
}
