package cn.zull.tracing.core.model;

import cn.zull.tracing.core.dto.TraceDTO;
import cn.zull.tracing.core.utils.DateUtils;

/**
 * @author zurun
 * @date 2018/10/2 17:54:37
 */
public interface TraceLog extends TraceLogGetAndSet {

    /**
     * 初始化
     *
     * @param trace
     * @return
     */
    default TraceLog init(TraceDTO trace) {

        return this.start().setCtm(trace.getCtm())
                .setTraceId(trace.getTraceId())
                .setSpanId(trace.getSpanId())
                .setStatus(TraceStatusEnum.SUCCESS)
                .setTraceDTO(trace);
    }

    /**
     * 链路当前节点开始计时
     *
     * @return
     */
    default TraceLog start() {
        setStartTime(System.currentTimeMillis());
        setStm(DateUtils.dateTimeFormat(getStartTime()));
        return this;
    }

    /**
     * 链路当前节点结束
     *
     * @return
     */
    default TraceLog stop() {
        Long time = System.currentTimeMillis();
        setEtm(DateUtils.dateTimeFormat(time));
        setCost(String.valueOf(time - getStartTime()));
        return this;
    }

    /**
     * 记录traceDTO
     *
     * @param traceDTO
     * @return
     */
    TraceLog setTraceDTO(TraceDTO traceDTO);

    Long getStartTime();

    TraceLog setStartTime(Long startTime);


}
