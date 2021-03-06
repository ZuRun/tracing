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
    public TraceDTO consumer(@NotNull Consumer<TraceDTO> traceDTOConsumer) {
        TraceDTO traceDTO = getRestTraceDto();
        traceDTOConsumer.accept(traceDTO);
        printTraceLog(traceDTO);
        return setContext(traceDTO);
    }

    @Override
    public TraceDTO product(HttpHeaders httpHeaders) {
        TraceDTO traceDTO = super.getThreadLocalTraceDto();
        httpHeaders.add("X-Tracing", traceDTO.toString());
        return traceDTO;
    }

    @Override
    public TraceDTO provider() {
        return super.getContextAndSpanIdPlusOne(traceDTO -> {
        });
    }

    private TraceDTO getRestTraceDto() {
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .map(ServletWebRequest::new)
                .map(servletWebRequest -> servletWebRequest.getHeader("X-Tracing"))
                .filter(str -> !StringUtils.isEmpty(str))
                .map(str -> JSON.parseObject(str, TraceDTO.class).spanIdAddLevel())
                .orElseGet(TraceDTO::getInstance);
    }
}
