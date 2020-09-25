package com.example.backstage.crs.util;

import java.text.NumberFormat;

public class Utils {

    public static String myPercent(int y, int z) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位  
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float) y / (float) z * 100);
        return result;
    }

}
