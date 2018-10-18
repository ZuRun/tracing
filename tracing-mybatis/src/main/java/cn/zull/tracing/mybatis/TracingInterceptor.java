package cn.zull.tracing.mybatis;

import cn.zull.tracing.core.UnilateralTraceContext;
import cn.zull.tracing.core.after.TracingLogPostProcessingUtils;
import cn.zull.tracing.core.dto.TraceDTO;
import cn.zull.tracing.core.exception.TracingInnerException;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * @author zurun
 * @date 2018/10/18 23:55:45
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})})
public class TracingInterceptor implements Interceptor {
    @Autowired
    UnilateralTraceContext unilateralTraceContext;

    public TracingInterceptor(){
        System.out.println("Mybatis Interceptor");
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        TraceDTO traceDTO = unilateralTraceContext.provider();
        return TracingLogPostProcessingUtils.collectionLog(traceDTO, traceLog -> {
            try {
                traceLog.setTraceType("mysql");
                return invocation.proceed();
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new TracingInnerException(e);
            }
        });
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
