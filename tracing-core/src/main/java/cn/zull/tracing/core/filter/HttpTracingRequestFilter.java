package cn.zull.tracing.core.filter;

import javax.servlet.ServletRequest;

/**
 * @author zurun
 * @date 2019/5/31 16:00:43
 */
public interface HttpTracingRequestFilter extends HttpTracingFilter {
    /**
     * http链路拦截器之前执行,返回true表示收集链路日志,false表示不收集链路日志
     *
     * @param servletRequest
     * @return
     */
    boolean beforeTrace(ServletRequest servletRequest);
}
