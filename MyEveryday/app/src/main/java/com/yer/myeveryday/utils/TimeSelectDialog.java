package com.yer.myeveryday.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;
import android.widget.EditText;

import com.luck.picture.lib.tools.DateUtils;

import java.util.Calendar;

/**
 * Created by 87990 on 2018/1/8.
 */

public class TimeSelectDialog {
    private  Context mContext;
    private EditText editText;

    public TimeSelectDialog(Context mContext, EditText editText) {
        this.mContext = mContext;
        this.editText = editText;
    }

    public  void openTimeDialog(){
        Calendar calendar= java.util.Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(mContext, dateSetListener,year,month,day).show();
    }

    private  DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            editText.setText(year+"-"+month+"-"+dayOfMonth);
        }
    };
}
