package cn.zull.tracing.core.utils;

import cn.zull.tracing.core.configuration.TracingProperties;
import cn.zull.tracing.core.exception.TracingException;
import cn.zull.tracing.core.dto.TraceDTO;
import cn.zull.tracing.core.model.DefaultTraceLog;
import cn.zull.tracing.core.model.TraceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * new链路日志实体的工厂
 *
 * @author zurun
 * @date 2018/10/2 19:37:26
 */
@Component
public class TracingLogEntityFactory {
    @Autowired
    TracingProperties tracingProperties;

    private Class<TraceLog> clazz;

    public TraceLog createObject(TraceDTO traceDTO) {
        try {
            return getClazz().newInstance();
        } catch (Exception e) {
            return new DefaultTraceLog().init(traceDTO);
        }
    }

    private Class<TraceLog> getClazz() throws ClassNotFoundException {
        if (clazz == null) {
            synchronized (TracingLogEntityFactory.class) {
                if (clazz == null) {
                    String path = tracingProperties.getEntityPath();
                    if (StringUtils.isEmpty(path)) {
                        tracingProperties.setEnable(false);
                        throw new TracingException("请检查配置tracing.entity.path");
                    }
                    Class<?> c = Class.forName(path);
                    if (c.isAssignableFrom(TraceLog.class)) {
                        clazz = (Class<TraceLog>) c;
                    } else {
                        tracingProperties.setEnable(false);
                        throw new TracingException("请检查配置tracing.entity.path");
                    }
                }
            }
        }
        return clazz;
    }
}
