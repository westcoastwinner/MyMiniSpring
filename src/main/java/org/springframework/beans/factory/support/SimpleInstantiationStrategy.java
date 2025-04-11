package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Class beanClass=beanDefinition.getBeanClass();
        try {
                Constructor cons = beanClass.getDeclaredConstructor();
                return cons.newInstance();
            } catch (Exception e) {
                throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
            }

    }

}
