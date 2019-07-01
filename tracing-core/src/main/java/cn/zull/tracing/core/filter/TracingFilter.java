package cn.zull.tracing.core.filter;

import cn.zull.tracing.core.RestTraceContext;
import cn.zull.tracing.core.after.TracingLogPostProcessingUtils;
import cn.zull.tracing.core.dto.TraceDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zurun
 * @date 2018/10/15 20:29:56
 */
public class TracingFilter implements Filter {
    @Autowired
    RestTraceContext traceContext;

    @Autowired
    HttpTracingRequestFilter requestFilter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (requestFilter != null && !requestFilter.beforeTrace(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        TraceDTO traceDto = traceContext.consumer(traceDTO -> {
        });
        BodyCachingHttpServletRequestWrapper requestWrapper =
                new BodyCachingHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        BodyCachingHttpServletResponseWrapper responseWrapper = new BodyCachingHttpServletResponseWrapper((HttpServletResponse) servletResponse);

        TracingLogPostProcessingUtils.collectionLog(traceDto, traceLog -> {
            try {
                traceLog.setTraceType("http-filter").setReqpkg(requestWrapper.getBodyString())
                        .setUrl(((HttpServletRequest) servletRequest).getRequestURL().toString());
                // 过滤的实际业务
                filterChain.doFilter(requestWrapper, responseWrapper);
                // 记录返回的body
                traceLog.setRespkg(responseWrapper.getBodyString());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public void destroy() {

    }
}