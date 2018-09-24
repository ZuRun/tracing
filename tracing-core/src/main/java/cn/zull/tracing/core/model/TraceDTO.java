package cn.zull.tracing.core.model;

import cn.zull.tracing.core.utils.UUIDUtils;
import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * º
 *
 * @author zurun
 * @date 2018/9/17 23:51:10
 */
@Data
public class TraceDTO<T> implements Trace {
    private String traceId;
    private String spanId;
    /**
     * 链路创建时间(时间戳,ms)
     */
    private Long ctm;

    private T properties;


    private TraceDTO(String traceId) {
    }

    public static TraceDTO getInstance() {
        return getInstance(UUIDUtils.simpleUUID(), "0.0");
    }


    public static TraceDTO getInstance(String traceId, String spanId) {
        TraceDTO traceDTO = new TraceDTO(traceId);
        return traceDTO;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}


