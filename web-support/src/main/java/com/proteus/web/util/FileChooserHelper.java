package com.proteus.web.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.ValueCallback;

import androidx.core.content.FileProvider;

import com.proteus.web.constant.RequestCode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Prasad Rao on 09-06-2020 13:54
 **/
public final class FileChooserHelper {

    private ValueCallback<Uri[]> filePathCallback;
    private String picturePath;

    private static FileChooserHelper sInstance;

    public static FileChooserHelper getInstance() {
        if (sInstance == null) {
            synchronized (FileChooserHelper.class) {
                if (sInstance == null) {
                    sInstance = new FileChooserHelper();
                }
            }
        }
        return sInstance;
    }

    public ValueCallback<Uri[]> getFilePathCallback() {
        return filePathCallback;
    }

    public void setFilePathCallback(ValueCallback<Uri[]> filePathCallback) {
        this.filePathCallback = filePathCallback;
    }

    public void openCamera(Context context) {
        Intent cameraIntent = prepareCameraIntent(context);
        getActivity(context).startActivityForResult(cameraIntent, RequestCode.OPEN_FILE_CHOOSER);
    }

    private Intent prepareCameraIntent(Context context) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = getFilePath(context);
            if (photoFile != null) {
                picturePath = "file:" + photoFile.getAbsolutePath();
                Uri uriForFile = FileProvider.getUriForFile(context,
                    context.getPackageName() + ".provider", photoFile);
                cameraIntent.putExtra("PhotoPath", picturePath);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }
        return cameraIntent;
    }

    public void openGallery(Context context) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.setType("*/*");
        getActivity(context).startActivityForResult(Intent.createChooser(galleryIntent,
            "ChooseFile"), RequestCode.OPEN_FILE_CHOOSER);
    }

    public void openFileChooser(Context context) {
        Intent cameraIntent = prepareCameraIntent(context);
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("*/*");
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});
        getActivity(context).startActivityForResult(chooserIntent, RequestCode.OPEN_FILE_CHOOSER);
    }

    private File getFilePath(Context context) {
        try {
            final String timeStamp = SimpleDateFormat.getDateInstance().format(new Date());
            String imageFileName = "img_" + timeStamp + "_";
            File storageDir = context.getFilesDir();
            return File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            return null;
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (RequestCode.OPEN_FILE_CHOOSER == requestCode) {
            Uri[] result = null;
            if (resultCode == Activity.RESULT_OK) {
                if (null == filePathCallback) {
                    return;
                }
                if (intent == null ||
                    intent.getData() == null) { //Capture Photo if no image available
                    if (picturePath != null) {
                        result = new Uri[]{Uri.parse(picturePath)};
                    }
                } else {
                    result = new Uri[]{Uri.parse(intent.getDataString())};
                }
            }
            filePathCallback.onReceiveValue(result);
            filePathCallback = null;
        }
    }
}
