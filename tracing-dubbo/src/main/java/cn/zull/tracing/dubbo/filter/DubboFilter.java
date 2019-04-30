package cn.zull.tracing.dubbo.filter;


import cn.zull.tracing.core.after.TracingLogPostProcessingUtils;
import cn.zull.tracing.core.dto.TraceDTO;
import cn.zull.tracing.core.model.TraceStatusEnum;
import cn.zull.tracing.core.utils.SpringApplicationContext;
import cn.zull.tracing.dubbo.DubboTraceContext;
import cn.zull.tracing.dubbo.RpcTraceContext;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zurun
 * @date 2018/9/27 22:34:42
 */
@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
public class DubboFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile RpcTraceContext traceContext;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 线程入口是dubbo的,记得先清空线程绑定的变量(使用线程池的情况下)
        logger.debug("dubbo filter");
        String sideVal = invoker.getUrl().getParameter(Constants.SIDE_KEY);
        if (Constants.CONSUMER_SIDE.equals(sideVal)) {
            TraceDTO traceDTO = getTraceContext().product();
            return TracingLogPostProcessingUtils.collectionLog(traceDTO, traceLog -> {
                final String url = invoker.getUrl().getProtocol() + "://" + invoker.getUrl().getIp() +
                        ":" + invoker.getUrl().getPort() +
                        "/" + invoker.getUrl().getServiceInterface() +
                        "#" + invocation.getMethodName();
                traceLog.setTraceType("dubbo-consumer")
                        .setUrl(url)
                        .setReqpkg(JSON.toJSONString(invocation.getArguments()));
                // 判断是否请求成功
                Result result = invoker.invoke(invocation);
                if (result.hasException()) {
                    traceLog.setStatus(TraceStatusEnum.FAIL);
                }
                return result;
            });
        } else if (Constants.PROVIDER_SIDE.equals(sideVal)) {
            TraceDTO traceDTO = getTraceContext().consumer(TraceDTO::getTraceId);
            return TracingLogPostProcessingUtils.collectionLog(traceDTO, traceLog -> {
                final String url = invoker.getUrl().getProtocol() + "://" + invoker.getUrl().getIp() +
                        ":" + invoker.getUrl().getPort() +
                        "/" + invoker.getUrl().getServiceInterface() +
                        "#" + invocation.getMethodName();
                traceLog.setTraceType("dubbo-provider")
                        .setUrl(url)
                        .setReqpkg(JSON.toJSONString(invocation.getArguments()));
                Result result = invoker.invoke(invocation);
                // 判断是否请求成功
                if (result.hasException()) {
                    traceLog.setStatus(TraceStatusEnum.FAIL);
                }
                return result;
            });
        }
        return invoker.invoke(invocation);
    }

    private RpcTraceContext getTraceContext() {
        if (traceContext == null) {
            synchronized (RpcTraceContext.class) {
                if (traceContext == null) {
                    traceContext = SpringApplicationContext.getBean(DubboTraceContext.class);
                }
                if (traceContext == null) {
                    logger.error("RpcTraceContext is null");
                }
            }
        }
        return traceContext;
    }
}
