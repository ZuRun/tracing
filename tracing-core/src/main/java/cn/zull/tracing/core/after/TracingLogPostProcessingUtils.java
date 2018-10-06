package cn.zull.tracing.core.after;

import cn.zull.tracing.core.configuration.TracingProperties;
import cn.zull.tracing.core.dto.TraceDTO;
import cn.zull.tracing.core.exception.TracingException;
import cn.zull.tracing.core.exception.TracingInnerException;
import cn.zull.tracing.core.model.TraceLog;
import cn.zull.tracing.core.model.TraceStatusEnum;
import cn.zull.tracing.core.utils.SpringApplicationContext;
import cn.zull.tracing.core.utils.TracingGlobal;
import cn.zull.tracing.core.TracingLogEntityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * 对收集到的日志进行后续处理
 *
 * @author zurun
 * @date 2018/10/2 15:49:37
 */
@Component
public class TracingLogPostProcessingUtils {
    private static final Logger logger = LoggerFactory.getLogger(TracingLogPostProcessingUtils.class);

    @Autowired(required = false)
    TracingLogHandler tracingLogHandler;

    @Autowired
    TracingProperties tracingProperties;

    @Autowired
    TracingLogEntityFactory tracingLogEntityFactory;

    private static TracingLogPostProcessingUtils collectingLogs;

    public <R> R collectionLogs(TraceDTO traceDTO, Function<TraceLog, R> function) {
        TraceLog traceLog = tracingLogEntityFactory.createObject(traceDTO)
                .setEndPoint(TracingGlobal.getInstance().getHostInfo().getEndPoint());
        try {
            R r = function.apply(traceLog);
            if (r instanceof RuntimeException) {
                RuntimeException e = (RuntimeException) r;
                traceLog.setStatus(TraceStatusEnum.FAIL);
                throw e;
            } else if (r instanceof Exception) {
                Exception e = (Exception) r;
                traceLog.setStatus(TraceStatusEnum.FAIL);
                throw new TracingInnerException(e);
            } else if (r instanceof Throwable) {
                Throwable throwable = (Throwable) r;
                traceLog.setStatus(TraceStatusEnum.FAIL);
                throw new TracingInnerException(throwable);
            }
            return r;
        } finally {
            try {
                traceLog.stop();
                logger.info(traceLog.toString());
                if (tracingProperties.getEnable() && tracingLogHandler != null) {
                    tracingLogHandler.handler(traceLog);
                } else {
                    logger.info("不收集日志");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static <R> R collectionLog(TraceDTO traceDTO, Function<TraceLog, R> function) {
        return getBean().collectionLogs(traceDTO, function);
    }

    private static TracingLogPostProcessingUtils getBean() {
        if (collectingLogs == null) {
            synchronized (TracingLogPostProcessingUtils.class) {
                if (collectingLogs == null) {
                    collectingLogs = SpringApplicationContext.getBean(TracingLogPostProcessingUtils.class);
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
