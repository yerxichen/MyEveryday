package com.yer.myeveryday.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.blankj.utilcode.util.TimeUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMax;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.Digits;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.yer.myeveryday.R;
import com.yer.myeveryday.adapter.CommonAdapter;
import com.yer.myeveryday.adapter.FullyGridLayoutManager;
import com.yer.myeveryday.adapter.GridImageAdapter;
import com.yer.myeveryday.adapter.ViewHolder;
import com.yer.myeveryday.entity.Account;
import com.yer.myeveryday.entity.Zdy;
import com.yer.myeveryday.utils.TimeSelectDialog;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class JzxzActivity extends BaseActivity implements Validator.ValidationListener{
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private RecyclerView recyclerView;
    private BootstrapButton subButton;

    private Spinner spinner1,spinner2,spinner3;

    @NotEmpty(message = "物品名称不能为空")
    private EditText editText1;
    @DecimalMax(value = 100000,message = "不能超过10万")
    @DecimalMin(value = 0.01,message = "不能小于1分")
    private EditText editText2;
    @Max(value = 10000,message = "数量不能超过1万")
    @Min(value = 1,message = "数量不能小于1")
    private EditText editText3;
    @NotEmpty(message = "请选择购买日期")
    private EditText editText4;
    private EditText editText5;
    private String wpfl,wpmc,wpdj,wpsl,jldw,gmsq,syr,bzsm;
    private Validator validator;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void bindView() {
        setView("新增账单", R.layout.activity_jzxz);
    }

    @Override
    public void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.recycler);
        spinner1= (Spinner) findViewById(R.id.spinner1);
        spinner2= (Spinner) findViewById(R.id.spinner2);
        spinner3= (Spinner) findViewById(R.id.spinner3);
        editText1= (EditText) findViewById(R.id.ed1);
        editText2= (EditText) findViewById(R.id.ed2);
        editText3= (EditText) findViewById(R.id.ed3);
        editText4= (EditText) findViewById(R.id.ed4);
        editText5= (EditText) findViewById(R.id.ed5);
        subButton= (BootstrapButton) findViewById(R.id.subButton);
    }

    @Override
    public void doBusiness() {
        editText4.setFocusable(false);
        editText4.setInputType(InputType.TYPE_NULL);
        editText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimeSelectDialog(JzxzActivity.this,editText4).openTimeDialog();
            }
        });
        FullyGridLayoutManager manager = new FullyGridLayoutManager(JzxzActivity.this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(JzxzActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                selectPicture();
            }
        });
        adapter.setList(selectList);
        adapter.setSelectMax(9);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(JzxzActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(JzxzActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(JzxzActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
        final List<Zdy> list1= DataSupport.where("dmfl = ?", "wp").find(Zdy.class);
        spinner1.setAdapter(new CommonAdapter<Zdy>(mContext,list1,R.layout.spinner_text) {
            @Override
            public void convert(ViewHolder helper, Zdy item) {
                helper.setText(R.id.tv,item.getMc());
            }
        });
        final List<Zdy> list2= DataSupport.where("dmfl = ?", "jldw").find(Zdy.class);
        spinner2.setAdapter(new CommonAdapter<Zdy>(mContext,list2,R.layout.spinner_text) {
            @Override
            public void convert(ViewHolder helper, Zdy item) {
                helper.setText(R.id.tv,item.getMc());
            }
        });
        final List<Zdy> list3= DataSupport.where("dmfl = ?", "syr").find(Zdy.class);
        spinner3.setAdapter(new CommonAdapter<Zdy>(mContext,list3,R.layout.spinner_text) {
            @Override
            public void convert(ViewHolder helper, Zdy item) {
                helper.setText(R.id.tv,item.getMc());
            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wpfl=list1.get(position).getDm();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jldw=list2.get(position).getDm();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                syr=list3.get(position).getDm();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        validator=new Validator(mContext);
        validator.setValidationListener(this);
    }

    private void selectPicture(){
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(JzxzActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
//                .previewVideo()// 是否可预览视频 true or false
//                .enablePreviewAudio() // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(false)// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
//                .isGif()// 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
//                .freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
//                .circleDimmedLayer()// 是否圆形裁剪 true or false
//                .showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//                .showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
//                .minimumCompressSize(100)// 小于100kb的图片不压缩
//                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//                .rotateEnabled() // 裁剪是否可旋转图片 true or false
//                .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onValidationSucceeded() {
        wpmc=editText1.getText().toString();
        wpdj=editText2.getText().toString();
        wpsl=editText3.getText().toString();
        gmsq=editText4.getText().toString();
        bzsm=editText5.getText().toString();
        //插入数据库
        //插入数据库
        Account account = new Account();
        account.setWpfl(wpfl);
        account.setWpmc(wpmc);
        account.setWpdj(Double.valueOf(wpdj.toString()));
        account.setWpsl(Integer.parseInt(wpsl));
        account.setJldw(jldw);
        account.setBz(bzsm);
        account.setGmr(syr);

        account.setGmrq(TimeUtils.string2Date(gmsq));

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
