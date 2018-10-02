package cn.zull.tracing.core;

import cn.zull.tracing.core.dto.TraceDTO;

/**
 * @author zurun
 * @date 2018/9/24 23:26:37
 */
public class TraceThreadLocal {
    private static final ThreadLocal<TraceDTO> context = ThreadLocal.withInitial(TraceDTO::getInstance);

    protected static void setContext(TraceDTO traceDTO) {
        context.set(traceDTO);
    }

    protected static TraceDTO getContext() {
        return context.get();
    }

}
