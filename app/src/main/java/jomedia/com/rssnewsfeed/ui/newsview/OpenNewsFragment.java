package jomedia.com.rssnewsfeed.ui.newsview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import jomedia.com.rssnewsfeed.ui.BaseFragment;

import static android.content.Context.CONNECTIVITY_SERVICE;

@SuppressLint("SetJavaScriptEnabled")
public class OpenNewsFragment extends BaseFragment {

    public static final String URL = "newsLink";
    private WebView mWebView;

    public OpenNewsFragment() {
    }

    public static OpenNewsFragment getInstance(String link){
        OpenNewsFragment fragment = new OpenNewsFragment();
        Bundle args = new Bundle();
        args.putString(URL, link);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgressBar();
        mWebView = new WebView(getContext());

        mWebView.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
        mWebView.getSettings().setAppCachePath(getContext().getCacheDir().getAbsolutePath());
        mWebView.getSettings().setAllowFileAccess( true );
        mWebView.getSettings().setAppCacheEnabled( true );

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        if(savedInstanceState == null) {
            if ( !isNetworkAvailable() ) { // loading offline
                mWebView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
            }
            if (getArguments() != null) {
                mWebView.loadUrl(getArguments().getString(URL));
            }
            mWebView.setWebViewClient(new ItemWebViewClient());
        }
        addViewInContainer(mWebView);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mWebView.saveState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mWebView.restoreState(savedInstanceState);
    }

    public boolean canGoBack() {
        return mWebView != null && mWebView.canGoBack();
    }

    public void goBack() {
        if(mWebView != null) {
            mWebView.goBack();
        }
    }
    private class ItemWebViewClient extends WebViewClient {

        public ItemWebViewClient(){
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            final Uri uri = request.getUrl();
            view.loadUrl(uri.toString());
            return true;
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideProgressBar();
        }
    }
}
