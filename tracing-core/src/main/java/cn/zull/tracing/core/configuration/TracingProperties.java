package cn.zull.tracing.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zurun
 * @date 2018/10/2 18:02:52
 */
@Component
public class TracingProperties {
    @Value("${tracing.enable:true}")
    private Boolean enable;

    /**
     * 是否将常用链路信息添加到日志文件中(例如log4j等)
     */
    @Value("${tracing.logShowTracing:true}")
    private Boolean logShowTracing;

    @Value("${tracing.entity.path:}")
    private String entityPath;

    @Value("${server.port:0}")
    private String serverPort;


    public Boolean getEnable() {
        return enable;
    }

    public TracingProperties setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public Boolean getLogShowTracing() {
        return logShowTracing;
    }

    public TracingProperties setLogShowTracing(Boolean logShowTracing) {
        this.logShowTracing = logShowTracing;
        return this;
    }

    public String getEntityPath() {
        return entityPath;
    }

    public TracingProperties setEntityPath(String entityPath) {
        this.entityPath = entityPath;
        return this;
    }

    public String getServerPort() {
        return serverPort;
    }

    public TracingProperties setServerPort(String serverPort) {
        this.serverPort = serverPort;
        return this;
    }
}
