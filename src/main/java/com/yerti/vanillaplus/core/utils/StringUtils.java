package com.yerti.vanillaplus.core.utils;

public class StringUtils {

    public static int parse(String text) {
        return Integer.parseInt(text.replaceAll("[\\D]", ""));
    }

}
