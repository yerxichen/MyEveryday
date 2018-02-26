package com.example.hwysapp.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment {

    private View view;
    private LinearLayout ll_jcgx, ll_ztgg, ll_zxzh;
    private QMUITipDialog tipDialog;

    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        initLoading(getActivity(),"版本检查中...");
        ll_ztgg = view.findViewById(R.id.ll_ztgg);
        ll_zxzh = view.findViewById(R.id.ll_zxzh);
        ll_jcgx = view.findViewById(R.id.ll_jcgx);
        ll_ztgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenuDialog();
            }
        });
        ll_zxzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmMessageDialog();
            }
        });
        ll_jcgx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUpdate();
            }
        });
        return view;
    }

    private void showMenuDialog() {
        final String[] items = new String[]{"小", "正常", "大"};
        new QMUIDialog.MenuDialogBuilder(getActivity())
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                SpUtil.put(getActivity(), "fontmode", "1");
                                break;
                            case 1:
                                SpUtil.put(getActivity(), "fontmode", "2");
                                break;
                            case 2:
                                SpUtil.put(getActivity(), "fontmode", "3");
                                break;
                        }
                        dialog.dismiss();
                        showMessageNegativeDialog();

                    }
                })
                .show();
    }

    private void showConfirmMessageDialog() {
        final QMUIDialog.CheckBoxMessageDialogBuilder builder = new QMUIDialog.CheckBoxMessageDialogBuilder(getActivity())
                .setTitle("退出后是否删除账号信息?")
                .setMessage("删除账号信息")
                .setChecked(false);
        builder.addAction("取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        });
        builder.addAction("退出", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
//                        Log.d("my", "onClick: "+index);
                dialog.dismiss();
                if (builder.isChecked()) {
                    SpUtil.put(getActivity(), "YHM", "");
                    SpUtil.put(getActivity(), "MM", "");
                }
                getActivity().finish();
            }
        });
        builder.show();
    }

    private void showMessageNegativeDialog() {
        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("字体更改提示")
                .setMessage("是否重启应用使更改生效")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "重启", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        getActivity().recreate();

                    }
                })
                .show();
    }

    public void getUpdate() {
        tipDialog.show();
        OkGo.<String>post(Constants.URL + "version/getVersionInfo.do")
                .tag(this)
                .params("yhm", SpUtil.get(getActivity(), "YHM", "").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       tipDialog.dismiss();
                        try {
                            String s = response.body().toString();
                            JSONObject obj = new JSONObject(s);
                            int code = obj.getInt("CODE");
                            if(code> CommonUtil.getVersionCode(getActivity())){
                                showMessagePositiveDialog();
                            }else{
                                TipDialogUtil.succss(getActivity(),"已经是最新版本");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Log.d(TAG, "onSuccess: "+response.body().toString());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        tipDialog.dismiss();
                        TipDialogUtil.fail(getContext(),"网络异常");
                    }
                });
    }

    public void initLoading(Context mContext,String text) {
        tipDialog = new QMUITipDialog.Builder(mContext)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(text)
                .create();
    }

    private void showMessagePositiveDialog() {
        new QMUIDialog.MessageDialogBuilder(getActivity())
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
