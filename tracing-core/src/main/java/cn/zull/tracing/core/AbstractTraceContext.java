package cn.zull.tracing.core;

import cn.zull.tracing.core.model.TraceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * @author zurun
 * @date 2018/9/17 09:49:14
 */
public abstract class AbstractTraceContext implements TraceContext {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public TraceDTO getThreadLocalTraceDto() {
        return getContext();
    }

//    @Override
//    public void product(@NotNull Consumer<TraceDTO> traceDTOConsumer) {
//        TraceDTO traceDTO = TraceDTO.getInstance();
//        traceDTOConsumer.accept(traceDTO);
//        setContext(traceDTO);
//        print();
//    }
//
//    @Override
//    public TraceDTO consumer() {
//        return getTraceDto();
//    }

    protected TraceDTO getContextAndSpanIdPlusOne(Consumer<TraceDTO> consumer) {
        TraceDTO traceDTO = getContext().spanIdPlusOne();
        consumer.accept(traceDTO);
        setContext(traceDTO);
        logger.info("getAndSet traceDTO:{}", getContext());
        return traceDTO;
    }

    protected void setContext(TraceDTO traceDTO) {
        TraceThreadLocal.setContext(traceDTO);
        logger.info("update traceDTO:{}", getContext());
    }
}
