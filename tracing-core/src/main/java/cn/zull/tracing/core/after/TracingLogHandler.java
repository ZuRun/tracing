package cn.zull.tracing.core.after;

import cn.zull.tracing.core.model.TraceLog;

/**
 * @author zurun
 * @date 2018/10/2 15:45:15
 */
public interface TracingLogHandler {

    /**
     * 处理日志
     *
     * @param traceLog
     */
    void handler(TraceLog traceLog);
}
