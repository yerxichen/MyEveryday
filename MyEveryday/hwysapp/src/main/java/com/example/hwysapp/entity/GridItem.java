package com.example.hwysapp.entity;

/**
 * Created by 87990 on 2018/2/16.
 */

public class GridItem {
    private int imgRes;
    private String title;

    public GridItem(int imgRes, String title) {
        this.imgRes = imgRes;
        this.title = title;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
