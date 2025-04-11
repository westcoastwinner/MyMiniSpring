package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory{

    //返回指定类型的所有实例
    <T> Map<String,T> getBeansOfType(Class<T> type) throws BeansException;

    String[] getBeanDefinitionNames();
}
