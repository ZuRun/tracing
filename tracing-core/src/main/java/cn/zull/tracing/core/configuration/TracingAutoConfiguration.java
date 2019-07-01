package cn.zull.tracing.core.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2018/9/22 23:28:49
 */
@Configuration
@ComponentScan(basePackages = {"cn.zull.tracing"})
public class TracingAutoConfiguration {

    public TracingAutoConfiguration() {
        System.out.println("------TracingAutoConfiguration------");
    }


}
