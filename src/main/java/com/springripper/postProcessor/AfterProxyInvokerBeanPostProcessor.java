package com.springripper.postProcessor;

import com.springripper.annotations.AfterProxyMyOwn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;

/**
 * Created by raindream on 06.01.19.
 */
public class AfterProxyInvokerBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
            String originalClassName = beanDefinition.getBeanClassName();
            try {
                Method[] methods = Class.forName(originalClassName).getMethods();
                for (Method method : methods) {
                    if(method.isAnnotationPresent(AfterProxyMyOwn.class)) {
                        Object bean = applicationContext.getBean(beanName);
                        Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        currentMethod.invoke(bean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
