package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry{
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }

        return beanDefinition;
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
         beanDefinitionMap.keySet().forEach(this::getBean);
    }

    /*
    * 获取相应类型的Bean实例,本质还是getBean,所以如果没有就会触发create过程
    * type==beanDefinition.getClass->getBean(beanDefinition.getName)
    * */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        HashMap<String, T> map = new HashMap<>();
        beanDefinitionMap.forEach((beanName,beandefinition)->{
//              if(beandefinition.getBeanClass()==type){  错误写法
            if(beandefinition.getBeanClass().isAssignableFrom(type)){
                T bean =(T) getBean(beanName);
                map.put(beanName,bean);
            }

        });
        return map;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        Set<String> beanNames = beanDefinitionMap.keySet();
        //经典集合变数组的假数组形参
        return beanNames.toArray(new String[0]);
    }
}
