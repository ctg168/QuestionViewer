package com.terry.questionviewer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

@SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
public class MyActivity extends Activity {

    WebView browser;
    Button btnSourceViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        btnSourceViewer = (Button) findViewById(R.id.btnSourceViewer);
        browser = (WebView) findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new jsListenerInterface(this), "jsListener");

        browser.loadUrl("file:///android_asset/q_65168.html");
        browser.setWebViewClient(new MyWebViewClient());
    }

    public class jsListenerInterface {
        private Context context;

        public jsListenerInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void openImage(String img, int currentIndex, int imgCount) {
            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.putExtra("currentIndex", currentIndex);
            intent.putExtra("imgCount", imgCount);

            intent.setClass(context, ShowWebImageActivity.class);
            context.startActivity(intent);
        }

        @JavascriptInterface
        public void showSource(String html) {
            Log.d("HTML", html);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            //Add image click listener ==> jsListenerInterface.openImage
            view.loadUrl(
                    "javascript:(function(){"
                            + "var imgList = document.getElementsByTagName(\"img\"); "
                            + "for(var i=0;i<imgList.length;i++)  "
                            + "{"
                            + "    imgList[i].onclick=function()  "
                            + "    {  "
                            + "       window.jsListener.openImage(this.src,i,imgList.length);  "
                            + "    }  "
                            + "}"
                            + "})()"
            );
            //Add view source listener ==> jsListenerInterface.showSource
            view.loadUrl("javascript:window.jsListener.showSource('<head>'+" +
                    "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}

