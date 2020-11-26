package com.proteus.web.util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Prasad Rao on 09-06-2020 14:54
 **/
public class WebViewDownloadListener implements DownloadListener {

    private Context context;

    public WebViewDownloadListener(Context context) {
        this.context = context;
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition,
        String mimeType, long contentLength) {
        download(url, userAgent, contentDisposition, mimeType);
    }

    public void download(String url, String userAgent, String contentDisposition, String mimeType) {
        DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(url));
        downloadRequest.setMimeType(getMimeTypeFromUrl(url, mimeType));
        String cookie = CookieManager.getInstance().getCookie(url);
        downloadRequest.addRequestHeader("cookie", cookie);
        downloadRequest.addRequestHeader("User-Agent", userAgent);
        downloadRequest.setDescription("Downloading File...");
        downloadRequest.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
            URLUtil.guessFileName(url, contentDisposition, mimeType));
        DownloadManager downloadManager =
            (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(downloadRequest);
        }
    }

    private String getMimeTypeFromUrl(String url, String defaultMimeType) {
        if (TextUtils.isEmpty(url)) return defaultMimeType;
        String[] fileExtensions = url.split("\\?")[0].split("\\.");
        String fileExtension = fileExtensions[fileExtensions.length - 1];
        if ("pdf".equalsIgnoreCase(fileExtension)) return "application/pdf";
        if ("jpg".equalsIgnoreCase(fileExtension)) return "image/jpg";
        if ("jpeg".equalsIgnoreCase(fileExtension)) return "application/jpeg";
        return defaultMimeType;
    }
}
