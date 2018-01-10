package com.caizilong.netesase.splash.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.caizilong.netesase.R;

public class WebViewActivity extends AppCompatActivity {

    public static final String ACTION_NAME = "action";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        String stringExtra = intent.getStringExtra(ACTION_NAME);

        setContentView(R.layout.activity_web_view);


        mWebView = findViewById(R.id.webview);

        mWebView.loadUrl(stringExtra);

        // 启用javasscript权限
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient(){

            // 处理url重定向不要抛到系统浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {


        if (mWebView.canGoBack()){

            mWebView.goBack();
            return;

        }
        super.onBackPressed();


    }
}
