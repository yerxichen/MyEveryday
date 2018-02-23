package com.yer.myeveryday.entity;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by 87990 on 2017/12/1.
 */

public class Account extends DataSupport {
    private int id;
    private String wpfl;//物品分类
    private String wpmc;//物品名称
    private Double wpdj;//物品单价
    private int wpsl;//物品数量
    private String jldw;//计量单位

    public String getJldw() {
        return jldw;
    }

    public void setJldw(String jldw) {
        this.jldw = jldw;
    }

    private String gmr;//购买人
    private Date gmrq;//购买日期
    private String wpzp;//物品照片
    private String bz;//备注


    public void setId(int id) {
        this.id = id;
    }

    public String getWpfl() {
        return wpfl;
    }

    public void setWpfl(String wpfl) {
        this.wpfl = wpfl;
    }

    public String getWpmc() {
        return wpmc;
    }

    public void setWpmc(String wpmc) {
        this.wpmc = wpmc;
    }

    public Double getWpdj() {
        return wpdj;
    }

    public void setWpdj(Double wpdj) {
        this.wpdj = wpdj;
    }

    public int getWpsl() {
        return wpsl;
    }

    public void setWpsl(int wpsl) {
        this.wpsl = wpsl;
    }


    public String getGmr() {
        return gmr;
    }

    public void setGmr(String gmr) {
        this.gmr = gmr;
    }

    public Date getGmrq() {
        return gmrq;
    }

    public void setGmrq(Date gmrq) {
        this.gmrq = gmrq;
    }

    public String getWpzp() {
        return wpzp;
    }

    public void setWpzp(String wpzp) {
        this.wpzp = wpzp;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public int getId() {
        return id;
    }
}
