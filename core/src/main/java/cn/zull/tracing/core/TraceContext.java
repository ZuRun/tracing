package cn.zull.tracing.core;

import cn.zull.tracing.core.dto.TraceDTO;

/**
 * @author zurun
 * @date 2018/9/17 09:25:01
 */
public interface TraceContext {

    /**
     * 获取traceDto
     *
     * @return
     */
    TraceDTO getTraceDto();

    /**
     * 将dto新增到threadLocal入口
     *
     * @param traceDTO
     */
    void addTraceDTO(TraceDTO traceDTO);
}
