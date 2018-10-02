package cn.zull.tracing.core.log;

import cn.zull.tracing.core.model.TraceDTO;
import cn.zull.tracing.core.model.TraceInfo;
import cn.zull.tracing.core.utils.SpringApplicationContext;
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

    private static CollectingLogUtils collectingLogs;

    public static <R> R collectionLog(TraceDTO traceDTO, Function<TraceInfo, R> function) {
        TraceInfo traceInfo = new TraceInfo(traceDTO);
        try {
            return function.apply(traceInfo);
        } finally {
            traceInfo.stop();
            logger.info(traceInfo.toString());
            getBean().tracingLogHandler.handler(traceDTO);
        }
    }

    private static CollectingLogUtils getBean() {
        if (collectingLogs == null) {
            synchronized (CollectingLogUtils.class) {
                if (collectingLogs == null) {
                    collectingLogs = SpringApplicationContext.getBean(CollectingLogUtils.class);
                }
                if (collectingLogs == null) {
                    logger.error("CollectingLogs is null");
                    throw new RuntimeException("CollectingLogs is null");
                }
            }
        }
        return collectingLogs;
    }
}
