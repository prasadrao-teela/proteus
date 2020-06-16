package com.proteus.web.client;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Message;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.core.content.ContextCompat;

import com.proteus.web.constant.FileSelection;
import com.proteus.web.util.FileChooserHelper;

import java.util.Arrays;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Prasad Rao on 09-06-2020 12:32
 **/
public class ProteusChromeClient extends WebChromeClient {

    final FileChooserHelper fileChooserHelper;

    public ProteusChromeClient() {
        this.fileChooserHelper = FileChooserHelper.getInstance();
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture,
        Message resultMsg) {
        final WebView.HitTestResult hitTestResult = view.getHitTestResult();
        final String data = hitTestResult.getExtra();
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
        view.getContext().startActivity(browserIntent);
        return false;
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
            isPermissionGranted(webView.getContext(), WRITE_EXTERNAL_STORAGE) &&
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
        }
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