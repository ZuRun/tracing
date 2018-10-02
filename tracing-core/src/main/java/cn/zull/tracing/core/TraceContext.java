package cn.zull.tracing.core;

import cn.zull.tracing.core.dto.TraceDTO;
import cn.zull.tracing.core.model.DefaultTraceLog;

import java.util.function.Function;

/**
 * @author zurun
 * @date 2018/9/17 09:25:01
 */
public interface TraceContext {

    /**
     * 从threadLocal中获取traceDto
     *
     * @return
     */
    TraceDTO getThreadLocalTraceDto();

    default TraceDTO getContext() {
        return TraceThreadLocal.getContext();
    }


    /**
     * 将dto新增到threadLocal入口(生产方/线程入口)
     * 1. dubbo生产者      : 通过RpcContext获取dto并添加到threadLocal
     * 2. controller层     : 检查请求头中是否有tracing,没有则new一个dto并添加到threadLocal
     * 3. mq消费者         : 获取message中properties属性,转为dto并添加到threadLocal
     *
     * @param traceDTOConsumer
     */
//    void consumer(@NotNull Consumer<TraceDTO> traceDTOConsumer);

    /**
     * 1. dubbo消费者    : 获取threadLocal中dto并添加到RpcContext中
     * 2. restTemplate  : 获取threadLocal中dto并添加到请求头中
     * 3. mq生产者       :  获取threadLocal中dto 并添加到message中properties属性
     *
     * @return dto
     */
//    TraceDTO product();
    default <R> R collectionLog(Function<DefaultTraceLog, R> function) {
        return null;
    }
}
