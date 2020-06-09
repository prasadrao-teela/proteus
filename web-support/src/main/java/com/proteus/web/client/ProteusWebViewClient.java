package com.proteus.web.client;

import android.annotation.TargetApi;
import android.net.MailTo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.proteus.web.util.EmailSender;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Prasad Rao on 09-06-2020 10:54
 **/
public class ProteusWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        String urlDecoded = null;
        try {
            urlDecoded = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return overrideUrlLoading(view, urlDecoded);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        final Uri url = request.getUrl();
        return url == null || overrideUrlLoading(view, url.getPath());
    }

    private boolean overrideUrlLoading(WebView webView, String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("webviewClose=true")) {
                webView.clearHistory();
                //TODO: Back press callback
                return true;
            }
            if (url.startsWith("mailto:")) {
                MailTo mail = MailTo.parse(url);
                EmailSender.sendEmail(webView.getContext(), new String[]{
                    mail.getTo()}, mail.getSubject(), mail.getBody(), mail.getCc());
                return true;
            }
            webView.loadUrl(url);
        }
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

}
