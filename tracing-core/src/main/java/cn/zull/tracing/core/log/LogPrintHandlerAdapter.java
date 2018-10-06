package cn.zull.tracing.core.log;

import cn.zull.tracing.core.dto.TraceDTO;
import org.slf4j.MDC;

/**
 * @author zurun
 * @date 2018/10/6 23:40:27
 */
public class LogPrintHandlerAdapter implements LogPrintHandler {
    @Override
    public void handler(TraceDTO traceDTO) {
        MDC.put("traceId", traceDTO.getTraceId());
    }
}
