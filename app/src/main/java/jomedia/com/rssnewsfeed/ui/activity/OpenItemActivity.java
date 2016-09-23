package jomedia.com.rssnewsfeed.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import jomedia.com.rssnewsfeed.R;

public class OpenItemActivity  extends AppCompatActivity {
    public static final String URL = "newsLink";
    private WebView mWebView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_open_item);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            mWebView = (WebView)findViewById(R.id.web_view);
            mWebView.getSettings().setJavaScriptEnabled(true);
        if(savedInstanceState == null) {
            if (getIntent() != null) {
                mWebView.loadUrl(getIntent().getStringExtra(URL));
            }
            mWebView.setWebViewClient(new ItemWebViewClient(progressBar));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mWebView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mWebView.restoreState(savedInstanceState);
        progressBar.setVisibility(View.GONE);
    }

    private class ItemWebViewClient extends WebViewClient {
        private ProgressBar progressBar;

        public ItemWebViewClient(ProgressBar progressBar){
            this.progressBar=progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
