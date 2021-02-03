package com.proteus.web.client;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.proteus.web.constant.FileSelection;
import com.proteus.web.constant.RequestCode;
import com.proteus.web.util.FileChooserHelper;

import java.util.Arrays;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

/**
 * Created by Prasad Rao on 09-06-2020 12:32
 **/
public class ProteusChromeClient extends WebChromeClient {

    final FileChooserHelper fileChooserHelper;

    public ProteusChromeClient() {
        this.fileChooserHelper = FileChooserHelper.getInstance();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture,
        Message resultMsg) {
        if(isDialog){
            WebView newWebView = new WebView(view.getContext());
            newWebView.getSettings().setJavaScriptEnabled(true);
            newWebView.getSettings().setMixedContentMode(0);
            CookieManager.getInstance().setAcceptThirdPartyCookies(newWebView, true);
            newWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            CookieManager.getInstance().setAcceptCookie(true);
            newWebView.getSettings().setDomStorageEnabled(true);
            newWebView.getSettings().setAllowFileAccess(true);
            newWebView.getSettings().setSupportMultipleWindows(true);
            newWebView.getSettings().setJavaScriptEnabled(true);
            newWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            newWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
            newWebView.getSettings().setSupportMultipleWindows(true);
            view.addView(newWebView);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(newWebView);
            resultMsg.sendToTarget();

            newWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });

        } else {
            final WebView.HitTestResult hitTestResult = view.getHitTestResult();
            final String data = hitTestResult.getExtra();
            if (data == null)
                return false;
            final Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
            view.getContext().startActivity(browserIntent);
        }
        return true;
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
        FileChooserParams fileChooserParams) {
        if (fileChooserHelper.getFilePathCallback() != null) {
            this.fileChooserHelper.getFilePathCallback().onReceiveValue(null);
        }
        this.fileChooserHelper.setFilePathCallback(filePathCallback);
        FileSelection fileSelectionType = getFileSelectionType(fileChooserParams);
        showFileChooser(webView, fileSelectionType);
        return true;
    }

    private void showFileChooser(WebView webView, FileSelection fileSelectionType) {
        if (isPermissionGranted(webView.getContext(), READ_EXTERNAL_STORAGE) &&
            isPermissionGranted(webView.getContext(), CAMERA)) {
            switch (fileSelectionType) {
                case CAMERA:
                    FileChooserHelper.getInstance().openCamera(webView.getContext());
                    break;
                case GALLERY:
                    FileChooserHelper.getInstance().openGallery(webView.getContext());
                    break;
                case BOTH:
                    FileChooserHelper.getInstance().openFileChooser(webView.getContext());
                    break;
            }
        } else {
            if (fileChooserHelper.getFilePathCallback() != null) {
                this.fileChooserHelper.getFilePathCallback().onReceiveValue(null);
                fileChooserHelper.setFilePathCallback(null);
            }
            Activity activity = getActivity(webView.getContext());
            if (activity != null) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        CAMERA}, RequestCode.PERMISSIONS);
            } else {
                Toast.makeText(webView.getContext(), "Please grant Storage & Camera permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private Activity getActivity(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            } else {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }
        return null;
    }

    private FileSelection getFileSelectionType(FileChooserParams params) {
        if (params.isCaptureEnabled()) return FileSelection.CAMERA;
        String[] acceptTypes = params.getAcceptTypes();
        if (acceptTypes != null && Arrays.binarySearch(acceptTypes, "*/*") != -1) {
            return FileSelection.BOTH;
        }
        return FileSelection.GALLERY;
    }

    private boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) ==
            PackageManager.PERMISSION_GRANTED;
    }
}
