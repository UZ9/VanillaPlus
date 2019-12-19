package com.yerti.vanillaplus.core.utils;

import java.text.DecimalFormat;

public class MathUtils {

    public static int clamp(int value, int min, int max) {
        if (value > max) return max;
        if (value < min) return min;
        return value;
    }


    public static long clamp(long value, long min, long max) {
        if (value > max) return max;
        if (value < min) return min;
        return value;
    }


    public static float clamp(float value, float min, float max) {
        if (value > max) return max;
        if (value < min) return min;
        return value;
    }


    public static double clamp(double value, double min, double max) {
        if (value > max) return max;
        if (value < min) return min;
        return value;
    }

    public static String getFormattedDouble(double d) {
        DecimalFormat format = new DecimalFormat("##.##");

        double d2 = d / 1000000000000000D;
        if (d2 > 1.0) return format.format(d2).replace(",", ".") + "Q";

        d2 = d / 1000000000000D;
        if (d2 > 1.0) return format.format(d2).replace(",", ".") + "T";

        d2 = d / 1000000000D;
        if (d2 > 1.0) return format.format(d2).replace(",", ".") + "B";

        d2 = d / 1000000D;
        if (d2 > 1.0) return format.format(d2).replace(",", ".") + "M";

        d2 = d / 1000D;
        if (d2 > 1.0) return format.format(d2).replace(",", ".") + "K";

        return format.format(d).replace(",", ".");
    }




}
