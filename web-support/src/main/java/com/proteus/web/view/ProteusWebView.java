package com.proteus.web.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.flipkart.android.proteus.ProteusView;
import com.proteus.web.callback.WebPageCallback;
import com.proteus.web.client.ProteusChromeClient;
import com.proteus.web.client.ProteusWebViewClient;
import com.proteus.web.util.WebViewDownloadListener;

/**
 * Created by Prasad Rao on 09-06-2020 10:49
 **/
public class ProteusWebView extends WebView implements ProteusView {

    private Manager manager;

    private WebPageCallback webPageCallback;

    @SuppressLint("SetJavaScriptEnabled")
    public ProteusWebView(Context context) {
        super(context);
        clearCache(true);
        clearHistory();

        WebSettings settings = getSettings();
        settings.setMixedContentMode(0);
        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        CookieManager.getInstance().setAcceptCookie(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        setWebViewClient(new ProteusWebViewClient(this));
        setWebChromeClient(new ProteusChromeClient());
        setDownloadListener(new WebViewDownloadListener(context));
    }

    public WebPageCallback getWebPageCallback() {
        return webPageCallback;
    }

    public void setWebPageCallback(WebPageCallback webPageCallback) {
        this.webPageCallback = webPageCallback;
    }

    @Override
    public Manager getViewManager() {
        return manager;
    }

    @Override
    public void setViewManager(@NonNull Manager manager) {
        this.manager = manager;
    }

    @NonNull
    @Override
    public View getAsView() {
        return this;
    }
}
