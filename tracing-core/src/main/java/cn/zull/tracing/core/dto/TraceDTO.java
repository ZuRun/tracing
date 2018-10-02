package cn.zull.tracing.core.dto;

import cn.zull.tracing.core.utils.UUIDUtils;
import com.alibaba.fastjson.JSON;

/**
 * º
 *
 * @author zurun
 * @date 2018/9/17 23:51:10
 */
public class TraceDTO<T> implements BaseTraceDTO {
    private String traceId;
    private String spanId;
    /**
     * 链路创建时间(时间戳,ms)
     */
    private String ctm;

    private T properties;

    public TraceDTO() {
    }

    private TraceDTO(String traceId, String spanId, String ctm) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.ctm = ctm;
    }

    public static TraceDTO getInstance() {
        return getInstance(UUIDUtils.simpleUUID(), "0.0");
    }


    public static TraceDTO getInstance(String traceId, String spanId) {
        TraceDTO traceDTO = new TraceDTO(traceId, spanId, String.valueOf(System.currentTimeMillis()));
        return traceDTO;
    }


    public TraceDTO spanIdPlusOne() {
        this.spanId = this.spanIdPlusOne(this.getSpanId());
        return this;
    }

    public TraceDTO spanIdAddLevel() {
        this.spanId = this.spanIdAddLevel(this.getSpanId());
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getTraceId() {
        return traceId;
    }

    public TraceDTO setTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public String getSpanId() {
        return spanId;
    }

    public TraceDTO setSpanId(String spanId) {
        this.spanId = spanId;
        return this;
    }

    public String getCtm() {
        return ctm;
    }

    public TraceDTO setCtm(String ctm) {
        this.ctm = ctm;
        return this;
    }

    public T getProperties() {
        return properties;
    }

    public TraceDTO setProperties(T properties) {
        this.properties = properties;
        return this;
    }
}


