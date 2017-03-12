package com.example.www.rssreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        String url = getIntent().getStringExtra("link");
        final WebView web_View = (WebView) findViewById(R.id.web_view);
        web_View.setWebViewClient(new WebViewClient());
        web_View.loadUrl(url);
    }
}
