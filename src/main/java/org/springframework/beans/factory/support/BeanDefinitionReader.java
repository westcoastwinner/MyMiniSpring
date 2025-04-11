package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.core.Resource;
import org.springframework.core.ResourceLoader;
//实现类的目的是利用从ResourceLoader获取的资源输入流读取BeanDefinition到注册中心
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;
}
