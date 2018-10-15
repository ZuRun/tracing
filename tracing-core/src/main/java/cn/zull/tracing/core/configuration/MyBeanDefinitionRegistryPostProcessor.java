package cn.zull.tracing.core.configuration;

import cn.zull.tracing.core.filter.TracingFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * 实现自定义的注册bean定义的逻辑
 *
 * @author zurun
 * @date 2018/10/15 20:59:59
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Autowired
    TracingProperties tracingProperties;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        // 是否启用默认http请求拦截器
        if (tracingProperties.getOpenDefaultRestFilter()) {
            RootBeanDefinition tracingFilter = new RootBeanDefinition(TracingFilter.class);
            //新增Bean定义
            beanDefinitionRegistry.registerBeanDefinition("tracingFilter", tracingFilter);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}