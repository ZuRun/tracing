package cn.zull.tracing.dubbo.product.filter;

import cn.zull.tracing.core.RpcTraceContext;
import cn.zull.tracing.core.dto.TraceDTO;
import cn.zull.tracing.core.utils.SpringApplicationContext;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zurun
 * @date 2018/9/17 10:38:28
 */
@Activate(group = Constants.PROVIDER)
public class DubboFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RpcTraceContext traceContext = SpringApplicationContext.getBean(RpcTraceContext.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 线程入口是dubbo的,记得先清空线程绑定的变量(使用线程池的情况下)
        logger.info("filter");

        TraceDTO traceDTO = traceContext.getTraceBoByRpcContext();
        logger.info("traceBO:{}", traceDTO);
        return invoker.invoke(invocation);
    }
}
