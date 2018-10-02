package cn.zull.tracing.core.dto;

import org.springframework.util.StringUtils;

/**
 * @author zurun
 * @date 2018/9/24 21:14:22
 */
public interface BaseTraceDTO {
    String DEFAULT_SPAN_ID = "2.1";

    /**
     * spanId 版本号同级+1  0.1-->0.2
     *
     * @return
     */
    default String spanIdPlusOne(String spanId) {
        if (StringUtils.isEmpty(spanId)) {
            spanId = DEFAULT_SPAN_ID;
        } else {
            spanId = plusOne(spanId);
        }
        return spanId;
    }


    /**
     * spanId 版本号增加一级  0.1-->0.1.1
     *
     * @return
     */
    default String spanIdAddLevel(String spanId) {
        if (StringUtils.isEmpty(spanId)) {
            spanId = DEFAULT_SPAN_ID;
        } else {
            spanId = spanId + ".1";
        }
        return spanId;
    }


    default String plusOne(String spanId) {
//        System.out.println(spanId.substring(spanId.lastIndexOf(".") + 1));
        return spanId.substring(0, spanId.lastIndexOf(".") + 1) + (Integer.valueOf(spanId.substring(spanId.lastIndexOf(".") + 1)) + 1);
    }


}
