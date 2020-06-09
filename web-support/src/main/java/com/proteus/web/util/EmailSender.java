package com.proteus.web.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Prasad Rao on 09-06-2020 10:57
 **/
public class EmailSender {
    public static void sendEmail(Context context, String[] to, String cc, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_CC, cc);
        intent.setType("plain/text");

        context.startActivity(intent);
    }
}
