package cn.zull.tracing.core.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author zurun
 * @date 2018/10/2 18:02:52
 */
@Component
@Data
@PropertySource(value = "classpath:/tracing.properties")
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

    @Value("${tracing.rest.filter:true}")
    private Boolean openDefaultRestFilter;

    @Value("${server.port:0}")
    private String serverPort;

}
