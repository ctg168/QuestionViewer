package com.terry.questionviewer;


import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ShowWebImageActivity extends Activity {

    private String imagePath = null;
    private ZoomableImageView imageView = null;
    private TextView tvImageCount = null;

    private int imgCount, currentIndex;

    private Handler messageHandler;

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

            new Thread(new Runnable() {
                @Override
                public void run() {
//                        cachedImage = asyncImageLoader.loadDrawable(imageUrl, position);
//                        imageView.setImageDrawable(cachedImage);
                    try {

                        imageView.setImageBitmap(((BitmapDrawable) ShowWebImageActivity.loadImageFromUrl(imgDefine)).getBitmap());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static Drawable loadImageFromUrl(String url) throws IOException {

        URL m = new URL(url);
        InputStream i = (InputStream) m.getContent();
        Drawable d = Drawable.createFromStream(i, "src");
        return d;
    }


}
