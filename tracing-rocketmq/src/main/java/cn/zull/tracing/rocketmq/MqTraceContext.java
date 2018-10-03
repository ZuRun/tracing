package cn.zull.tracing.rocketmq;

import cn.zull.tracing.core.TraceContext;
import cn.zull.tracing.core.dto.TraceDTO;
import org.apache.rocketmq.common.message.Message;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

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
    TraceDTO product(Message message);

    TraceDTO consumer(@NotNull Consumer<TraceDTO> traceDTOConsumer, Message message);

}
