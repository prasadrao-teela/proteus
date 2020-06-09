package com.proteus.web;

import com.flipkart.android.proteus.ProteusBuilder;
import com.proteus.web.parser.WebViewParser;

/**
 * Created by Prasad Rao on 09-06-2020 10:42
 **/
public class WebSupportModule implements ProteusBuilder.Module {

    private WebSupportModule() { }

    public static WebSupportModule create() {
        return new WebSupportModule();
    }

    @Override
    public void registerWith(ProteusBuilder builder) {
        builder.register(new WebViewParser<>());
    }
}
