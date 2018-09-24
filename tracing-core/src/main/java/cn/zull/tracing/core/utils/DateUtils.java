package cn.zull.tracing.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zurun
 * @date 2018/9/24 21:05:52
 */
public class DateUtils {
    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowDateTimeString() {
        return formatter.format(new Date());
    }
}
