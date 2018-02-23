package com.yer.myeveryday.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by 87990 on 2017/12/1.
 */

public class Zdy extends DataSupport{
    private int id;
    private String dmfl;//代码分类
    private String dm;//代码
    private String mc;//名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDmfl() {
        return dmfl;
    }

    public void setDmfl(String dmfl) {
        this.dmfl = dmfl;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
}
