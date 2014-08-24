package com.terry.questionviewer;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ViewSourceActivity extends ActionBarActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_source);

        webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("http://www.cnblogs.com/hibraincol/");
    }


    final class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("WebView", "onPageStarted");
            super.onPageStarted(view, url, favicon);
        }
        public void onPageFinished(WebView view, String url) {
            Log.d("WebView","onPageFinished ");
            view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +
                    "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            super.onPageFinished(view, url);
        }
    }

    final class InJavaScriptLocalObj {
        public void showSource(String html) {
            Log.d("HTML", html);
        }
    }
}
