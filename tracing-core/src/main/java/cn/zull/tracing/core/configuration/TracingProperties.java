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
    private Boolean enable ;

    @Value("${tracing.entity.path:}")
    private String entityPath ;

    @Value("${server.port:0}")
    private String serverPort ;


    public Boolean getEnable() {
        return enable;
    }

    public TracingProperties setEnable(Boolean enable) {
        this.enable = enable;
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
