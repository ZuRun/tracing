package cn.zull.tracing.core.log;

import cn.zull.tracing.core.configuration.TracingProperties;
import cn.zull.tracing.core.exception.TracingException;
import cn.zull.tracing.core.dto.TraceDTO;
import cn.zull.tracing.core.model.TraceLog;
import cn.zull.tracing.core.utils.SpringApplicationContext;
import cn.zull.tracing.core.utils.TracingLogEntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author zurun
 * @date 2018/10/2 15:49:37
 */
@Component
public class CollectingLogUtils {
    private static final Logger logger = LoggerFactory.getLogger(CollectingLogUtils.class);

    @Autowired
    TracingLogHandler tracingLogHandler;

    @Autowired
    TracingProperties tracingProperties;

    @Autowired
    TracingLogEntityFactory tracingLogEntityFactory;

    private static CollectingLogUtils collectingLogs;

    public <R> R collectionLogs(TraceDTO traceDTO, Function<TraceLog, R> function) {
        TraceLog traceLog = tracingLogEntityFactory.createObject(traceDTO);
        try {
            return function.apply(traceLog);
        } finally {
            try {
                traceLog.stop();
                logger.info(traceLog.toString());
                if (tracingProperties.getEnable()) {
                    tracingLogHandler.handler(traceLog);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static <R> R collectionLog(TraceDTO traceDTO, Function<TraceLog, R> function) {
        return getBean().collectionLogs(traceDTO, function);
    }

    private static CollectingLogUtils getBean() {
        if (collectingLogs == null) {
            synchronized (CollectingLogUtils.class) {
                if (collectingLogs == null) {
                    collectingLogs = SpringApplicationContext.getBean(CollectingLogUtils.class);
                }
                if (collectingLogs == null) {
                    logger.error("CollectingLogs is null");
                    throw new TracingException("CollectingLogs is null");
                }
            }
        }
        return collectingLogs;
    }
}
