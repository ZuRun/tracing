package cn.zull.tracing.rocketmq;

import cn.zull.tracing.core.dto.TraceDTO;
import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

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
    public TraceDTO product(Message message) {
        return getContextAndSpanIdPlusOne(traceDTO -> message.putUserProperty("tracing", traceDTO.toString()));

//        message.putUserProperty("traceId", traceDTO.getTraceId());
//        message.putUserProperty("spanId", traceDTO.getSpanId());
//        message.putUserProperty("ctm", traceDTO.getCtm());
//        message.putUserProperty("properties", traceDTO.propertiesString());
    }

    /**
     * mq消费者
     *
     * @param message
     */
    @Override
    public TraceDTO consumer(@NotNull Consumer<TraceDTO> traceDTOConsumer, Message message) {
        TraceDTO traceDTO = JSON.parseObject(message.getUserProperty("tracing"), TraceDTO.class).spanIdAddLevel();
        traceDTOConsumer.accept(traceDTO);
        printTraceLog(traceDTO);
        return super.setContext(traceDTO);
    }


}
