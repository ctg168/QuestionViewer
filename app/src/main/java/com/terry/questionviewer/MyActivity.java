package com.terry.questionviewer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

@SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
public class MyActivity extends Activity {

    private WebView browser;
    private Button btnBrowser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        btnBrowser = (Button) findViewById(R.id.button);

        btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "BUTTON===");
            }
        });


        browser = (WebView) findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new jsListenerInterface(this), "jsListener");

        browser.loadUrl("file:///android_asset/q_65168.html");
        browser.setWebViewClient(new MyWebViewClient());
    }

    private void addImageClickListener() {
        browser.loadUrl(
                "javascript:(function(){"
                        + " var imgList = document.getElementsByTagName(\"img\"); "
                        + " for(var i=0;i<imgList.length;i++)  "
                        + " {"
                        + "     imgList[i].onclick=function()  "
                        + "     {  "
                        + "        window.jsListener.openImage(this.src,i,imgList.length);  "
                        + "    }  "
                        + "}"
                        + "})()"
        );

    }

    public class jsListenerInterface {
        private Context context;

        public jsListenerInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void openImage(String img, int currentIndex, int imgCount) {

            Log.i("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "[" + "img " + "====" + currentIndex + "====" + imgCount + "]");

            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.putExtra("currentIndex", currentIndex);
            intent.putExtra("imgCount", imgCount);

            intent.setClass(context, ShowWebImageActivity.class);
            context.startActivity(intent);
        }

    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            addImageClickListener();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

}

