package cn.zull.tracing.dubbo;

import cn.zull.tracing.core.TraceContext;
import cn.zull.tracing.core.model.TraceDTO;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

/**
 * @author zurun
 * @date 2018/9/17 10:20:06
 */
public interface RpcTraceContext extends TraceContext {

    void consumer(@NotNull Consumer<TraceDTO> traceDTOConsumer);

    /**
     * 调用远程rpc接口,将threadLocal中TraceDTO赋值给rpcContext
     */
    void addRpcContext();

    /**
     * 1. dubbo消费者    : 获取threadLocal中dto并添加到RpcContext中
     * 2. restTemplate  : 获取threadLocal中dto并添加到请求头中
     * 3. mq生产者       :  获取threadLocal中dto 并添加到message中properties属性
     *
     * @return dto
     */
    TraceDTO product();
}
