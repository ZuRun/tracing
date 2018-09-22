package cn.zull.tracing.rocketmq;

import cn.zull.tracing.core.dto.TraceDTO;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

/**
 * @author zurun
 * @date 2018/9/20 10:20:58
 */
@Component
public class RocketmqTraceContext extends AbstractMqTraceContext {

    /**
     * mq生产者,调用消费方法
     *
     * @return
     */
    @Override
    public TraceDTO consumer(Message message) {
        TraceDTO traceDTO = getTraceDto();
        message.putUserProperty("traceId", traceDTO.getTraceId());
        message.putUserProperty("spanId", traceDTO.getSpanId());
        return traceDTO;
    }
}
