package cn.zull.tracing.core.log;

import cn.zull.tracing.core.model.TraceDTO;

/**
 * @author zurun
 * @date 2018/10/2 15:45:15
 */
public interface TracingLogHandler {

    /**
     * 处理日志
     *
     * @param traceDTO
     */
    void handler(TraceDTO traceDTO);
}
