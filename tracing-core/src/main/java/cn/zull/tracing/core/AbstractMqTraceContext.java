package cn.zull.tracing.core;

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
}
