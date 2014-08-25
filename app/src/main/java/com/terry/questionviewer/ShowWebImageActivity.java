package com.terry.questionviewer;


import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ShowWebImageActivity extends Activity {

    private String imagePath;
    private ZoomableImageView imageView = null;
    private TextView tvImageCount;

    private int imgCount, currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_webimage);

        imageView = (ZoomableImageView) findViewById(R.id.img_viewer);
        tvImageCount = (TextView) findViewById(R.id.tvImageCount);

        //接收数据
        this.imagePath = getIntent().getStringExtra("image");
        this.imgCount = getIntent().getIntExtra("imgCount", 0);
        this.currentIndex = getIntent().getIntExtra("currentIndex", 1);

        //加载界面
        LoadImage(imagePath);
        tvImageCount.setText(String.valueOf(currentIndex) + "/" + String.valueOf(imgCount));

    }

    private void LoadImage(final String imgDefine) {
        String Base64Prefix = "data:image/png;base64,";
        if (imgDefine.startsWith(Base64Prefix)) {
            imageView.setImageBitmap(Util.Base64StringToBitmap(imgDefine.substring(Base64Prefix.length())));
        } else {
            Toast.makeText(getApplicationContext(), "没有做普通图片的查看,只做了Bast64的", Toast.LENGTH_SHORT).show();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        imageView.setImageBitmap(((BitmapDrawable) ShowWebImageActivity.loadImageFromUrl(imgDefine)).getBitmap());
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            });
        }
    }

//    public static Drawable loadImageFromUrl(String url) throws IOException {
//
//        URL m = new URL(url);
//        InputStream i = (InputStream) m.getContent();
//        Drawable d = Drawable.createFromStream(i, "src");
//        return d;
//    }
}
