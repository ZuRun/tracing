# Quick start
## 依赖

#### gradle
    // 全局设置版本号
    ext {
        tracing_version = '0.0.8-RELEASE'
    }
    
    compile "cn.zull.tracing:tracing-core:${tracing_version}" 
    compile "cn.zull.tracing:tracing-dubbo:${tracing_version}" 
    compile "cn.zull.tracing:tracing-mybatis:${tracing_version}" 
    compile "cn.zull.tracing:tracing-rocketmq:${tracing_version}" 
#### Maven 
    <dependency>
       <groupId>cn.zull.tracing</groupId>
       <artifactId>tracing-core</artifactId>
       <version>${tracing_version}</version>
    </dependency>  

根据需要添加依赖

## 处理已收集的链路信息

实现TracingLogHandler接口,并注册bean对象即可

示例:

```java
@Component
public class MyTracingLogHandler implements TracingLogHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handler(TraceLog traceLog) {
        logger.info("链路信息:{}", traceLog.toString());
    }
}
```

## 配置文件

新建`classpath:/tracing.properties`文件

默认配置:

```properties
# 是否开启链路监控
tracing.enable = true
# 是否将traceId添加到日志文件中(例如log4j等)
tracing.logShowTracing = true
# 是否开启rest请求默认链路拦截器
tracing.rest.filter = true
```

