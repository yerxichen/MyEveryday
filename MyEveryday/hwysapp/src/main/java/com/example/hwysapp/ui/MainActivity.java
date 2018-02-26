package com.example.hwysapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hwysapp.adapter.ViewPagerAdapter;
import com.example.hwysapp.fragment.Fragment1;
import com.example.hwysapp.fragment.Fragment2;
import com.example.hwysapp.fragment.Fragment3;

import java.util.ArrayList;
import java.util.List;
import com.example.hwysapp.R;
import com.example.hwysapp.utils.CommonUtil;
import com.example.hwysapp.utils.Constants;
import com.example.hwysapp.utils.SpUtil;
import com.example.hwysapp.utils.TipDialogUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseFragmentActivity {
    private ViewPager viewPager;
    private BottomNavigationView navigation;
    private List<Fragment> fragments=new ArrayList<>();
    private Fragment fragment1,fragment2,fragment3;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };


    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        TextView back=findViewById(R.id.back);
        back.setVisibility(View.GONE);
        setView("带十分小心上路    携一份平安回家",R.layout.activity_main);
    }

    @Override
    public void initView() {
        getUpdate();
        fragment1=new Fragment1();
        fragment2=new Fragment2();
        fragment3=new Fragment3();
        viewPager= (ViewPager) findViewById(R.id.viewpager);

    }

    @Override
    public void doBusiness() {
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentPagerAdapter=new ViewPagerAdapter(fragmentManager,fragments);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigation.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        navigation.setSelectedItemId(R.id.navigation_dashboard);
                        break;
                    case 2:
                        navigation.setSelectedItemId(R.id.navigation_notifications);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //按返回按钮不会退出程序  直接回到桌面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void getUpdate() {
        OkGo.<String>post(Constants.URL + "version/getVersionInfo.do")
                .tag(this)
                .params("yhm", SpUtil.get(mContext, "YHM", "").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        try {
                            String s = response.body().toString();
                            JSONObject obj = new JSONObject(s);
                            int code = obj.getInt("CODE");
                            if(code> CommonUtil.getVersionCode(mContext)){
                                showMessagePositiveDialog();
                            }else{
                                //TipDialogUtil.succss(mContext,"已经是最新版本");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Log.d(TAG, "onSuccess: "+response.body().toString());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }



    private void showMessagePositiveDialog() {
        new QMUIDialog.MessageDialogBuilder(mContext)
                .setTitle("提示")
                .setMessage("发现新版本")
                .addAction("以后再说", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("立即更新", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();

                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(Constants.APKURL);
                        intent.setData(content_url);
                        startActivity(intent);

                    }
                })
                .show();
    }
}
