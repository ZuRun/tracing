package cn.zull.tracing.core.log;

import cn.zull.tracing.core.dto.TraceDTO;

/**
 * 将常用链路信息添加到日志文件中(例如log4j等)
 *
 * @author zurun
 * @date 2018/10/6 23:38:47
 */
public interface LogPrintHandler {

    void handler(TraceDTO traceDTO);
}
