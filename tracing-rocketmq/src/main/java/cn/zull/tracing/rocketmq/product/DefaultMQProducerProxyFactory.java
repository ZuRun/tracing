package cn.zull.tracing.rocketmq.product;

import cn.zull.tracing.rocketmq.RocketmqTraceContext;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author zurun
 * @date 2018/9/20 00:06:35
 */
@Component
public class DefaultMQProducerProxyFactory implements MethodInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 是否使用代理
     */
    private final Boolean useProxy;
    private final Boolean DefaultUseProxy = true;

    @Autowired
    private RocketmqTraceContext traceContext;

    private DefaultMQProducerProxyFactory(Boolean useProxy) {
        this.useProxy = useProxy;
    }

    private DefaultMQProducerProxyFactory() {
        this.useProxy = DefaultUseProxy;
    }

    /**
     * 实例化
     *
     * @param useProxy 是否使用代理
     * @return
     */
    public static DefaultMQProducer instantiation(Boolean useProxy) {

        return (DefaultMQProducer) new DefaultMQProducerProxyFactory(useProxy).
                createObj(new DefaultMQProducer());
    }

    public Object createObj(DefaultMQProducer target) {
        if (!useProxy) {
            return target;
        }

        //加载需要创建子类的类
        Enhancer enhancer = new Enhancer();
        //设置代理目标
        enhancer.setSuperclass(target.getClass());
        //设置回调
        enhancer.setCallback(this);

        enhancer.setClassLoader(target.getClass().getClassLoader());
        //返回子类对象
        return enhancer.create();
    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        // mq生产方,调用send方法之前,将链路信息set进去
        if ("send".equals(method.getName()) || "sendOneway".equals(method.getName())) {
            Object object = args[0];
            if (object instanceof Message) {
                Message message = (Message) object;
                trace(message);
            } else if (object instanceof Collection) {
                Collection<Message> collection = (Collection) object;
                collection.forEach(this::trace);
            }

        }
        return methodProxy.invokeSuper(target, args);
    }

    /**
     * mq生产方,发送消息前,将traceDto赋值给message properties属性
     *
     * @param message
     */
    private void trace(Message message) {
        logger.info("mq生产:");

        traceContext.consumer(message);

    }
}
