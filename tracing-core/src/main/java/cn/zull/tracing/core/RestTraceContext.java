package cn.zull.tracing.core;

import cn.zull.tracing.core.dto.TraceDTO;
import org.springframework.http.HttpHeaders;

/**
 * @author zurun
 * @date 2018/9/23 23:21:24
 */
public interface RestTraceContext extends TraceContext {
    /**
     *
     * @param httpHeaders
     * @return
     */
    TraceDTO consumer(HttpHeaders httpHeaders);
}
