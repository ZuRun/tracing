package cn.zull.tracing.rocketmq;

import cn.zull.tracing.core.TraceContext;
import cn.zull.tracing.core.model.TraceDTO;
import org.apache.rocketmq.common.message.Message;

import javax.validation.constraints.NotNull;

/**
 * @author zurun
 * @date 2018/9/20 10:11:36
 */
public interface MqTraceContext extends TraceContext {
    /**
     * 消费dto
     *
     * @param message
     * @return
     */
    TraceDTO consumer(Message message);

    void product(@NotNull Message message);
}
