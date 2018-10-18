package cn.zull.tracing.core;

import cn.zull.tracing.core.dto.TraceDTO;

/**
 * 单方面请求,例如mysql,redis等
 * 无需考虑链路信息的传递
 *
 * @author zurun
 * @date 2018/10/18 23:55:11
 */
public interface UnilateralTraceContext {
    TraceDTO provider();
}
