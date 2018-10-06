package cn.zull.tracing.core;

import cn.zull.tracing.core.dto.TraceDTO;
import cn.zull.tracing.core.exception.TracingInnerException;
import cn.zull.tracing.core.after.TracingLogPostProcessingUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @author zurun
 * @date 2018/10/6 21:29:43
 */
public class RestTracingInterceptor implements ClientHttpRequestInterceptor {
    private final RestTraceContext traceContext = new RestTraceContextImpl();

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        traceContext.product(request.getHeaders());
        TraceDTO traceDTO = traceContext.provider();
        return TracingLogPostProcessingUtils.collectionLog(traceDTO, traceLog -> {
            traceLog.setTraceType("RestTemplate")
                    .setUrl(request.getURI().toString());
            try {
                return execution.execute(request, body);
            } catch (IOException e) {
                e.printStackTrace();
                throw new TracingInnerException(e);
            }
        });

    }
}
