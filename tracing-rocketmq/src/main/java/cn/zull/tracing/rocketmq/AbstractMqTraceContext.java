package cn.zull.tracing.rocketmq;

import cn.zull.tracing.core.AbstractTraceContext;
import cn.zull.tracing.core.dto.TraceDTO;

import java.util.Optional;

/**
 * @author zurun
 * @date 2018/9/20 10:11:18
 */
public abstract class AbstractMqTraceContext extends AbstractTraceContext implements MqTraceContext {

    @Override
    public TraceDTO getTraceDto() {
        return Optional.ofNullable(super.getTraceDto())
                .orElseGet(this::getTraceDtoByMq);
    }

    private TraceDTO getTraceDtoByMq() {
        return null;
    }

    @Override
    public TraceDTO consumer() {
        TraceDTO traceDTO = getTraceDto();
        return traceDTO;
    }


}
