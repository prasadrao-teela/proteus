package com.flipkart.android.proteus.util;

import android.text.InputType;

public class InputTypes {
    public static int getInputType(String type) {
        switch (type) {
            case "date":
                return InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE;
            case "datetime":
                return InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_NORMAL;
            case "number":
                return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL;
            case "numberDecimal":
                return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;
            case "numberPassword":
                return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD;
            case "numberSigned":
                return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED;
            case "phone":
                return InputType.TYPE_CLASS_PHONE;
            case "text":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
            case "textAutoComplete":
                return InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE;
            case "textAutoCorrect":
                return InputType.TYPE_TEXT_FLAG_AUTO_CORRECT;
            case "textCapCharacters":
                return InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS;
            case "textCapSentences":
                return InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
            case "textCapWords":
                return InputType.TYPE_TEXT_FLAG_CAP_WORDS;
            case "textEmailAddress":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            case "textEmailSubject":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT;
            case "textFilter":
                return InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE;
            case "textLongMessage":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE;
            case "textMultiLine":
                return InputType.TYPE_TEXT_FLAG_MULTI_LINE;
            case "textNoSuggestions":
                return InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
            case "textPassword":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            case "textPersonName":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME;
            case "textPhonetic":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PHONETIC;
            case "textPostalAddress":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS;
            case "textShortMessage":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE;
            case "textUri":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI;
            case "textVisiblePassword":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
            case "textWebEditText":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT;
            case "textWebEmailAddress":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS;
            case "textWebPassword":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;
            case "time":
                return InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME;
            default:
                return InputType.TYPE_NULL;
        }
    }
}
