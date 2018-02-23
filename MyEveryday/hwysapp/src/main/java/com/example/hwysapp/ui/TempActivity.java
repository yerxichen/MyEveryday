package com.example.hwysapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hwysapp.R;

public class TempActivity extends BaseActivity {


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        TextView back=findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        setView("友情提示",R.layout.activity_temp);
    }

    @Override
    public void initView() {

    }

    @Override
    public void doBusiness() {

    }
}
