package cn.zull.tracing.core.model;

import cn.zull.tracing.core.dto.TraceDTO;
import com.alibaba.fastjson.JSON;

/**
 * @author zurun
 * @date 2018/9/24 18:10:14
 */
public class DefaultTraceLog implements TraceLog {
    private transient TraceDTO traceDTO;
    private transient Long startTime;

    private String traceId;
    private String spanId;
    /**
     * 链路类型
     */
    private String traceType;
    /**
     * 主机信息 ip:port
     */
    private String endPoint;
    /**
     * 远程地址
     */
    private String url;

    /**
     * 链路创建时间(时间戳,ms)
     */
    private String ctm;
    /**
     * 链路时长
     */
    private String cost;
    /**
     * 开始时间 2018-09-24 18:12:13
     */
    private String stm;
    /**
     * 结束时间
     */
    private String etm;
    /**
     * 链路是否成功
     */
    private TraceStatusEnum status = TraceStatusEnum.SUCCESS;

    public DefaultTraceLog() {
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


    @Override
    public TraceLog setTraceDTO(TraceDTO traceDTO) {
        this.traceDTO = traceDTO;
        return this;
    }

    @Override
    public TraceLog setStartTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    @Override
    public TraceLog setTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    @Override
    public TraceLog setSpanId(String spanId) {
        this.spanId = spanId;
        return this;
    }

    @Override
    public TraceLog setTraceType(String traceType) {
        this.traceType = traceType;
        return this;
    }

    @Override
    public TraceLog setEndPoint(String endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    @Override
    public TraceLog setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public TraceLog setCtm(String ctm) {
        this.ctm = ctm;
        return this;
    }

    @Override
    public TraceLog setCost(String cost) {
        this.cost = cost;
        return this;
    }

    @Override
    public TraceLog setStm(String stm) {
        this.stm = stm;
        return this;
    }

    @Override
    public TraceLog setEtm(String etm) {
        this.etm = etm;
        return this;
    }

    @Override
    public TraceLog setStatus(TraceStatusEnum status) {
        this.status = status;
        return this;
    }

    public TraceDTO getTraceDTO() {
        return traceDTO;
    }

    @Override
    public Long getStartTime() {
        return startTime;
    }

    @Override
    public String getTraceId() {
        return traceId;
    }

    @Override
    public String getSpanId() {
        return spanId;
    }

    @Override
    public String getTraceType() {
        return traceType;
    }

    @Override
    public String getEndPoint() {
        return endPoint;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getCtm() {
        return ctm;
    }

    @Override
    public String getCost() {
        return cost;
    }

    @Override
    public String getStm() {
        return stm;
    }

    @Override
    public String getEtm() {
        return etm;
    }

    @Override
    public TraceStatusEnum getStatus() {
        return status;
    }
}
