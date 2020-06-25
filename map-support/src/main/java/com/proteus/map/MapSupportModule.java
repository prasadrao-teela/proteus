package com.proteus.map;

import com.flipkart.android.proteus.ProteusBuilder;
import com.proteus.map.parser.MapViewParser;

public class MapSupportModule implements ProteusBuilder.Module {

    private MapSupportModule() { }

    public static MapSupportModule create() {
        return new MapSupportModule();
    }

    @Override
    public void registerWith(ProteusBuilder builder) {
        builder.register(new MapViewParser<>());
    }
}

