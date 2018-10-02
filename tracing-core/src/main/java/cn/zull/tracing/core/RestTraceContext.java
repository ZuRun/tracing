package cn.zull.tracing.core;

import cn.zull.tracing.core.dto.TraceDTO;
import org.springframework.http.HttpHeaders;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

/**
 * @author zurun
 * @date 2018/9/23 23:21:24
 */
public interface RestTraceContext extends TraceContext {
    /**
     * 将dto新增到threadLocal入口(生产方/线程入口)
     * 1. dubbo生产者      : 通过RpcContext获取dto并添加到threadLocal
     * 2. controller层     : 检查请求头中是否有tracing,没有则new一个dto并添加到threadLocal
     * 3. mq消费者         : 获取message中properties属性,转为dto并添加到threadLocal
     *
     * @param traceDTOConsumer
     */
    void consumer(@NotNull Consumer<TraceDTO> traceDTOConsumer);


    /**
     *
     * @param httpHeaders
     * @return
     */
    TraceDTO product(HttpHeaders httpHeaders);
}
