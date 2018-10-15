package cn.zull.tracing.core.filter;

import cn.zull.tracing.core.RestTraceContext;
import cn.zull.tracing.core.after.TracingLogPostProcessingUtils;
import cn.zull.tracing.core.dto.TraceDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author zurun
 * @date 2018/10/15 20:29:56
 */
public class TracingFilter implements Filter {
    @Autowired
    RestTraceContext traceContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        TraceDTO traceDto = traceContext.consumer(traceDTO -> {

        });
        TracingLogPostProcessingUtils.collectionLog(traceDto, traceLog -> {
            try {
                traceLog.setTraceType("http-filter");
                // 过滤的实际业务
                filterChain.doFilter(servletRequest, servletResponse);
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