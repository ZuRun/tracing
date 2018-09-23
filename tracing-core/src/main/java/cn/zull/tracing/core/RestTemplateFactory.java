package cn.zull.tracing.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;

/**
 * @author zurun
 * @date 2018/9/23 17:28:43
 */
public class RestTemplateFactory<T extends RestTemplate> implements MethodInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile static RestTemplateFactory factory;
    private final T restTemplate;
    private final RestTraceContext traceContext;

    private RestTemplateFactory(Class<? extends RestTemplate> c) {
        //加载需要创建子类的类
        Enhancer enhancer = new Enhancer();
        //设置代理目标
        enhancer.setSuperclass(c);
        //设置回调
        enhancer.setCallback(this);

        enhancer.setClassLoader(c.getClassLoader());
        //返回子类对象
        restTemplate = (T) enhancer.create();

        traceContext = new RestTraceContextImpl();
    }

    public static RestTemplate getSingleton() {
        return getSingleton(RestTemplate.class);
    }

    public static <T extends RestTemplate> RestTemplate getSingleton(Class<T> c) {
        if (factory == null) {
            synchronized (RestTemplateFactory.class) {
                if (factory == null) {
                    factory = new RestTemplateFactory(c);
                }
            }
        }
        return factory.restTemplate;
    }


    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if ("createRequest".equals(method.getName())) {
            ClientHttpRequest clientHttpRequest = (ClientHttpRequest) methodProxy.invokeSuper(target, args);
            logger.info(method.getName());

            traceContext.consumer(clientHttpRequest.getHeaders());

            return clientHttpRequest;
        }
        return methodProxy.invokeSuper(target, args);
    }
}
