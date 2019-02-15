package cn.zull.tracing.core.log;

import cn.zull.tracing.core.configuration.TracingProperties;
import cn.zull.tracing.core.dto.TraceDTO;
import cn.zull.tracing.core.exception.TracingException;
import cn.zull.tracing.core.utils.SpringApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zurun
 * @date 2018/10/7 01:14:00
 */
@Component
public class LogPrintHandlerUtils {
    private static final Logger logger = LoggerFactory.getLogger(LogPrintHandlerUtils.class);

    @Autowired
    TracingProperties properties;

    @Autowired(required = false)
    LogPrintHandler logPrintHandler;

    private static LogPrintHandlerUtils logPrintHandlerUtils;


    public void handler(TraceDTO traceDTO) {
        if (!properties.getLogShowTracing()) {
            logger.debug("普通日志中不记录链路信息");
            return;
        }
        if (logPrintHandler == null) {
            logPrintHandler = new LogPrintHandlerAdapter();
        }
        logPrintHandler.handler(traceDTO);

    }

    public static void logHandler(TraceDTO traceDTO) {
        getBean().handler(traceDTO);
    }

    private static LogPrintHandlerUtils getBean() {
        if (logPrintHandlerUtils == null) {
            synchronized (LogPrintHandlerUtils.class) {
                if (logPrintHandlerUtils == null) {
                    logPrintHandlerUtils = SpringApplicationContext.getBean(LogPrintHandlerUtils.class);
                }
                if (logPrintHandlerUtils == null) {
                    logger.error("LogPrintHandlerUtils is null");
                    throw new TracingException("LogPrintHandlerUtils is null");
                }
            }
        }
        return logPrintHandlerUtils;
    }
}
