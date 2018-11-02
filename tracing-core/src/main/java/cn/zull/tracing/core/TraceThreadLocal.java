package cn.zull.tracing.core;

import cn.zull.tracing.core.dto.TraceDTO;

/**
 * @author zurun
 * @date 2018/9/24 23:26:37
 */
public class TraceThreadLocal {
    private static final ThreadLocal<TraceDTO> context = ThreadLocal.withInitial(TraceDTO::getInstance);

    public static void setContext(TraceDTO traceDTO) {
        context.set(traceDTO);
    }

    public static TraceDTO getContext() {
        return context.get();
    }
    
    public static String getTraceId() {
        TraceDTO traceDTO = context.get();
        return traceDTO == null ? null : traceDTO.getTraceId();
    }
}
