package com.example.websockets.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    private final static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String formatDate(Date date) {
        return format.format(date);
    }
}
