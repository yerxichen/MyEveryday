package com.example.hwysapp.utils;

/**
 * Created by 87990 on 2018/2/24.
 */

public class MyUtil {

    public static String Date2Day(String date){
        String[] str=date.split("-");
        String day=str[2];
        String res="";
        if(day.substring(0,1).equals("0")){
            res=day.substring(1,2)+"号";
        }else{
            res=day+"号";
        }
        return res;
    }
}
