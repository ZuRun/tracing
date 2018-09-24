package cn.zull.tracing.rocketmq;

import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * mq消费有点特殊,未找到走代理的方案
 *
 * @author zurun
 * @date 2018/9/23 23:43:12
 */
@Deprecated
public class MessageListenerConcurrentlyProxyFactory<T extends MessageListenerConcurrently> implements MethodInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile static MessageListenerConcurrentlyProxyFactory factory;
    private final MqTraceContext traceContext;
    private final T messageListenerConcurrently;

    private MessageListenerConcurrentlyProxyFactory(Class<T> c) {
        //加载需要创建子类的类
        Enhancer enhancer = new Enhancer();
        //设置代理目标
        enhancer.setSuperclass(c);
        //设置回调
        enhancer.setCallback(this);

        enhancer.setClassLoader(c.getClassLoader());
        //返回子类对象
        messageListenerConcurrently = (T) enhancer.create();

        traceContext = new RocketmqTraceContext();
    }

//    public static <T extends MessageListenerConcurrently> T getSingleton(Class<T> c) {
//        if (factory == null) {
//            synchronized (MessageListenerConcurrentlyProxyFactory.class) {
//                if (factory == null) {
//                    factory = new MessageListenerConcurrentlyProxyFactory(c);
//                }
//            }
//        }
//        return (T) factory.messageListenerConcurrently;
//    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
//        if ("consumeMessage".equals(method.getName())) {
//            Object object = args[0];
//            List<MessageExt> msgs= (List<MessageExt>) object;
//            Message message = (Message) object;
////            traceContext.product();
//        }

        return methodProxy.invokeSuper(target, args);
    }
}
