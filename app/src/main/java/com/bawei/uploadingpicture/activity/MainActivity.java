package com.bawei.uploadingpicture.activity;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.uploadingpicture.R;
import com.bawei.uploadingpicture.bean.Data;
import com.bawei.uploadingpicture.core.DataCall;
import com.bawei.uploadingpicture.presenter.UploadingPresenter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import io.reactivex.processors.UnicastProcessor;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
private RelativeLayout dialog;
private TextView text1,text2,text3;
UploadingPresenter uploadingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      dialog = findViewById(R.id.dialog);
        text1  = findViewById(R.id.btn_photograph);
        text2 = findViewById(R.id.btn_photo_album);
        text3 = findViewById(R.id.cancel);
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        uploadingPresenter = new UploadingPresenter(new UploadCall());

    }

    public void picture(View view) {
        Toast.makeText(this, "点击了图片", Toast.LENGTH_SHORT).show();
        if (dialog!=null&&!dialog.isShown()){
            dialog.setVisibility(View.VISIBLE);
        }
    }
    @Override
   public void onClick(View v) {
        //点击拍照
        if (v.getId()==R.id.btn_photograph){
            Toast.makeText(this, "点击了拍照", Toast.LENGTH_SHORT).show();
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
        }//点击相册
        else if(v.getId()==R.id.btn_photo_album){
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
        }//点击取消
        else if(v.getId()==R.id.cancel){
                dialog.setVisibility(View.GONE);
        }
    }
    class  UploadCall implements DataCall{

        @Override
        public void success(Data data, Object... args) {

        }

        @Override
        public void fail(Data data, Object... args) {

        }
    }
}
