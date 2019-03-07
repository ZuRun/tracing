package cn.zull.tracing.core;

import cn.zull.tracing.core.after.TracingLogPostProcessingUtils;
import cn.zull.tracing.core.dto.TraceDTO;
import cn.zull.tracing.core.exception.TracingInnerException;
import cn.zull.tracing.core.model.TraceLog;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.function.BiConsumer;

/**
 * @author zurun
 * @date 2018/10/6 21:29:43
 */
public class RestTracingInterceptor implements ClientHttpRequestInterceptor {
    private final RestTraceContext traceContext = new RestTraceContextImpl();
    /**
     * request发送之前
     */
    private BiConsumer<TraceLog, HttpRequest> beforeConsumer = (a, b) -> {
    };
    /**
     * 接受到resp后
     */
    private BiConsumer<TraceLog, ClientHttpResponse> afterConsumer = (a, b) -> {
    };

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        traceContext.product(request.getHeaders());
        TraceDTO traceDTO = traceContext.provider();
        return TracingLogPostProcessingUtils.collectionLog(traceDTO, traceLog -> {
            traceLog.setTraceType("RestTemplate").setUrl(request.getURI().toString());
            beforeConsumer.accept(traceLog, request);
            try {
                ClientHttpResponse response = execution.execute(request, body);
                afterConsumer.accept(traceLog, response);
                return response;
            } catch (IOException e) {
                e.printStackTrace();
                throw new TracingInnerException(e);
            }
        });

    }

    public void beforeRequest(BiConsumer<TraceLog, HttpRequest> beforeConsumer) {
        this.beforeConsumer = beforeConsumer;
    }

    public void afterRequest(BiConsumer<TraceLog, ClientHttpResponse> afterConsumer) {
        this.afterConsumer = afterConsumer;
    }

    public static void main(String[] args) {
        ClientHttpRequestInterceptor interceptor = new RestTracingInterceptor();
        ((RestTracingInterceptor) interceptor).beforeRequest((traceLog, req) -> {
//            req.getHeaders().get
        });
    }
}
