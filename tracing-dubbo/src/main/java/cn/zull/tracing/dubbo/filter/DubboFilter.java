package cn.zull.tracing.dubbo.filter;

import cn.zull.tracing.core.TraceContext;
import cn.zull.tracing.core.utils.SpringApplicationContext;
import cn.zull.tracing.dubbo.DubboTraceContext;
import cn.zull.tracing.dubbo.RpcTraceContext;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zurun
 * @date 2018/9/27 22:34:42
 */
@Activate(group = {Constants.PROVIDER, Constants.CONSUMER})
public class DubboFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RpcTraceContext traceContext;

    public DubboFilter() {
        System.out.println("-------Dubbo Filter-------");
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 线程入口是dubbo的,记得先清空线程绑定的变量(使用线程池的情况下)
        logger.info("dubbo filter");
        String sideVal = invoker.getUrl().getParameter(Constants.SIDE_KEY);
        if (Constants.CONSUMER_SIDE.equals(sideVal)) {
            traceContext.consumer(traceDTO -> {

            });
        } else {
            traceContext.product();
        }
        return invoker.invoke(invocation);
    }

    private TraceContext getTraceContext(){
        return  SpringApplicationContext.getBean(DubboTraceContext.class);
    }
}
