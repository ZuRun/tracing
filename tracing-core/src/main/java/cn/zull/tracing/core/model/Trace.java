package cn.zull.tracing.core.model;

import org.springframework.util.StringUtils;

/**
 * @author zurun
 * @date 2018/9/24 21:14:22
 */
public interface Trace {
    String DEFAULT_SPAN_ID = "2.1";

    /**
     * spanId 版本号同级+1  0.1-->0.2
     *
     * @return
     */
    default void spanIdPlusOne(String spanId) {
        if (StringUtils.isEmpty(spanId)) {
            spanId = DEFAULT_SPAN_ID;
        } else {
            spanId = plusOne(spanId);
        }
        this.setSpanId(spanId);
    }


    /**
     * spanId 版本号增加一级  0.1-->0.1.1
     *
     * @return
     */
    default void spanIdAddLevel(String spanId) {
        if (StringUtils.isEmpty(spanId)) {
            spanId = DEFAULT_SPAN_ID;
        } else {
            spanId = spanId + ".1";
        }
        this.setSpanId(spanId);
    }


    default String plusOne(String spanId) {
//        System.out.println(spanId.substring(spanId.lastIndexOf(".") + 1));
        return spanId.substring(0, spanId.lastIndexOf(".") + 1) + (Integer.valueOf(spanId.substring(spanId.lastIndexOf(".") + 1)) + 1);
    }


    String getSpanId();

    void setSpanId(String spanId);
}
