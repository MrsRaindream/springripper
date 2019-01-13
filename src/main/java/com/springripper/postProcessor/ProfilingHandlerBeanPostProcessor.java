package com.springripper.postProcessor;

import com.springripper.annotations.Profiling;
import com.springripper.controller.ProfilingController;
import com.springripper.controller.ProfilingControllerMBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by raindream on 04.01.19.
 */
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap<>();

    private ProfilingController profilingController = new ProfilingController();


    public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(profilingController, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();

        if (map.get(beanName) != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    if(profilingController.isEnable()) {
                        System.out.println("do profiling >>>");
                        long before = System.nanoTime();
                        Object invokeRes = method.invoke(bean, objects);
                        long after = System.nanoTime();

                        System.out.println("method executing time is " + (after - before));
                        System.out.println("finished profiling.");
                        return invokeRes;
                    } else {
                        return method.invoke(bean, objects);
                    }
                }
            });
        }
        return bean;
    }
}
