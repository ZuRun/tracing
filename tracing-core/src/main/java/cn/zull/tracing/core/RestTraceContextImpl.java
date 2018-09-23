package cn.zull.tracing.core;

import cn.zull.tracing.core.dto.TraceDTO;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author zurun
 * @date 2018/9/23 15:12:08
 */
@Component
public class RestTraceContextImpl extends AbstractTraceContext implements RestTraceContext {

    @Override
    public void product(@NotNull Consumer<TraceDTO> traceDTOConsumer) {
        TraceDTO traceDTO = getRestTraceDto();
        traceDTOConsumer.accept(traceDTO);
        setContext(traceDTO);
    }

    @Override
    public TraceDTO consumer(HttpHeaders httpHeaders) {
        TraceDTO traceDTO = super.getContext();
        httpHeaders.add("tracing", traceDTO.toString());
        return traceDTO;
    }

    private TraceDTO getRestTraceDto() {
        return Optional.of((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .map(ServletWebRequest::new)
                .map(servletWebRequest -> servletWebRequest.getHeader("tracing"))
                .filter(str -> !StringUtils.isEmpty(str))
                .map(str -> JSON.parseObject(str, TraceDTO.class))
                .orElseGet(TraceDTO::new);
    }
}
