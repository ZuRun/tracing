package cn.zull.tracing.core.filter;

import org.apache.catalina.connector.RequestFacade;

import javax.servlet.ServletRequest;

/**
 * @author zurun
 * @date 2019/5/31 16:02:41
 */
public abstract class AbstractHttpTracingRequestFilter implements HttpTracingRequestFilter {

    @Override
    public boolean beforeTrace(ServletRequest servletRequest) {

        return !whetherIgnoreUri((RequestFacade)servletRequest);

//        String uri = ((RequestFacade) servletRequest).getRequestURI();
//
//        if (ignoreUri().contains(uri)) {
//            return false;
//        }
//        if (ignoreUri().contains(uri)) {
//            return false;
//        }
//        if (ignoreUri().contains(uri)) {
//            return false;
//        }
//        return true;

//        servletRequest.getRemoteAddr()+"------"+servletRequest.getRemoteHost()
    }

    /**
     * 是否忽略
     *
     * @return true表示忽略, false表示不忽略
     */
    public abstract boolean whetherIgnoreUri(RequestFacade requestFacade);

}
