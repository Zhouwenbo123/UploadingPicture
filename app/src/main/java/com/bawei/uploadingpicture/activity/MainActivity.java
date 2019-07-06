package com.bawei.uploadingpicture.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.uploadingpicture.R;
import com.bawei.uploadingpicture.bean.Data;
import com.bawei.uploadingpicture.core.DataCall;
import com.bawei.uploadingpicture.presenter.UploadingPresenter;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

import io.reactivex.processors.UnicastProcessor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
private RelativeLayout dialog;
private TextView text1,text2,text3;
private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            initView();

    }

    private void initView() {
        dialog = findViewById(R.id.dialog);
        image = findViewById(R.id.image);
        text1  = findViewById(R.id.btn_photograph);
        text2 = findViewById(R.id.btn_photo_album);
        text3 = findViewById(R.id.cancel);
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
    }

    public void picture(View view) {
        Toast.makeText(this, "点击了图片", Toast.LENGTH_SHORT).show();
        if (dialog!=null&&!dialog.isShown()){
            dialog.setVisibility(View.VISIBLE);
        }
    }
    @Override
   public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_photograph:
                //点击拍照
                clickImage();
                break;
            case R.id.btn_photo_album:
                //点击相册
                clickAlbum();
                break;
            case R.id.btn_cancel:
                //点击取消
                dialog.setVisibility(View.GONE);
                break;
        }
    }

    private void clickAlbum() {
        PictureSelector.create(MainActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void clickImage() {

        PictureSelector.create(MainActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList != null && selectList.size() > 0) {
                        final String compressPath = selectList.get(0).getCompressPath();
                        System.out.println("path==========" + compressPath);
                        //获取到地址后   转码 上传更新头像
                        File file = new File(compressPath);
                        //文件转换成内存对象
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        //
                        MultipartBody.Part part = MultipartBody.Part.createFormData(
                                "image", file.getName(), requestBody
                        );
                        //上传
                        new UploadingPresenter(new DataCall() {
                            @Override
                            public void success(Data data, Object... args) {
                                Glide.with(MainActivity.this)
                                        .load(compressPath)
                                        .into(image);
                                dialog.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this,
                                        data.message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void fail(Data result, Object... args) {
                                Toast.makeText(MainActivity.this,
                                        result.message, Toast.LENGTH_SHORT).show();
                            }
                        }).requestData("4956","15624087143664956",part);
                    }
                    break;
            }
        }
    }
}
