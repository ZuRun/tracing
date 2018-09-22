package cn.zull.tracing.core;

import cn.zull.tracing.core.dto.TraceDTO;
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

    protected static final ThreadLocal<TraceDTO> context = ThreadLocal.withInitial(TraceDTO::new);

    protected void print() {
        logger.info("traceBo:{}", context.get());
    }

    @Override
    public TraceDTO getTraceDto() {
        return context.get();
    }

    @Override
    public void product(@NotNull Consumer<TraceDTO> traceDTOConsumer) {
        TraceDTO traceDTO = new TraceDTO();
        traceDTOConsumer.accept(traceDTO);
        context.set(traceDTO);
        print();
    }

    @Override
    public TraceDTO consumer() {
        return getTraceDto();
    }
}
