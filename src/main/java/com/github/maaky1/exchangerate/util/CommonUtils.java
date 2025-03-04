package com.github.maaky1.exchangerate.util;

import java.util.Map;

public class CommonUtils {

    public static String replacePlaceholders(String url, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url = url.replace("[" + entry.getKey() + "]", entry.getValue());
        }
        return url;
    }
}
