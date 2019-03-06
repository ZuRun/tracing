package cn.zull.tracing.core.model;

/**
 * @author zurun
 * @date 2018/10/2 21:53:36
 */
public interface TraceLogGetAndSet {
    String getTraceId();

    TraceLog setTraceId(String traceId);

    String getSpanId();

    TraceLog setSpanId(String spanId);

    String getTraceType();

    TraceLog setTraceType(String traceType);

    String getEndPoint();

    TraceLog setEndPoint(String endPoint);

    String getUrl();

    TraceLog setUrl(String url);

    /**
     * 链路创建时间(时间戳,ms)
     *
     * @return
     */
    String getCtm();

    TraceLog setCtm(String ctm);

    /**
     * 链路时长
     *
     * @return
     */
    String getCost();

    TraceLog setCost(String cost);

    /**
     * 开始时间 2018-09-24 18:12:13
     *
     * @return
     */
    String getStm();

    TraceLog setStm(String stm);

    /**
     * 结束时间
     *
     * @return
     */
    String getEtm();

    TraceLog setEtm(String etm);

    /**
     * 返回体
     *
     * @return
     */
    String getRespkg();

    DefaultTraceLog setRespkg(String respkg);

    /**
     * 请求体
     *
     * @return
     */
    String getReqpkg();

    DefaultTraceLog setReqpkg(String reqpkg);

    /**
     * 链路是否成功
     *
     * @return
     */
    TraceStatusEnum getStatus();

    TraceLog setStatus(TraceStatusEnum status);
}
