package cn.zull.tracing.core;

import cn.zull.tracing.core.model.TraceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;

/**
 * @author zurun
 * @date 2018/9/17 09:49:14
 */
public abstract class AbstractTraceContext implements TraceContext {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final ThreadLocal<TraceDTO> context = ThreadLocal.withInitial(TraceDTO::getInstance);

    protected void print() {
        logger.info("traceDTO:{}", getContext());
    }

    @Override
    public TraceDTO getTraceDto() {
        return getContext();
    }

    @Override
    public void product(@NotNull Consumer<TraceDTO> traceDTOConsumer) {
        TraceDTO traceDTO = TraceDTO.getInstance();
        traceDTOConsumer.accept(traceDTO);
        setContext(traceDTO);
        print();
    }

    @Override
    public TraceDTO consumer() {
        return getTraceDto();
    }

    public void setContext(TraceDTO traceDTO) {
        context.set(traceDTO);
    }

    public TraceDTO getContext() {
        return context.get();
    }
}
