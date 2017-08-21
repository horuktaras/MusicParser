package com.noop.parser.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    protected static String getDatetimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("_yyyyMMdd_HHmm");
        return sdf.format(new Date());
    }
}
