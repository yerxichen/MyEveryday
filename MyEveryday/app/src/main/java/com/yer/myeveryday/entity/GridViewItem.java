package com.yer.myeveryday.entity;

/**
 * Created by 87990 on 2017/12/4.
 */

public class GridViewItem {
    private int imageRes;
    private String title;

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GridViewItem(int imageRes, String title) {
        this.imageRes = imageRes;
        this.title = title;
    }
}
