package com.example.hwysapp.utils;

import android.content.Context;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by 87990 on 2018/2/24.
 */

public class TipDialogUtil {

    public static void succss(Context mContext, String text) {
        final QMUITipDialog tipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(text)
                .create();
        tipDialog.show();
        Timer timer = new Timer();//实例化Timer类
        timer.schedule(new TimerTask() {
            public void run() {
                tipDialog.dismiss();
            }
        }, 1500);//五百毫秒
    }

    public static void fail(Context mContext, String text) {
        final QMUITipDialog tipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(text)
                .create();
        tipDialog.show();
        Timer timer = new Timer();//实例化Timer类
        timer.schedule(new TimerTask() {
            public void run() {
                tipDialog.dismiss();
            }
        }, 1500);//五百毫秒
    }

    public static void info(Context mContext, String text) {
        final QMUITipDialog tipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord(text)
                .create();
        tipDialog.show();
        Timer timer = new Timer();//实例化Timer类
        timer.schedule(new TimerTask() {
            public void run() {
                tipDialog.dismiss();
            }
        }, 1500);//五百毫秒
    }
    public static void showLoading(Context mContext, QMUITipDialog tipDialog,String text) {
        tipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(text)
                .create();
        tipDialog.show();
    }

    public static void dismisLoading(Context mContext, QMUITipDialog tipDialog) {
        tipDialog.dismiss();
    }

}
