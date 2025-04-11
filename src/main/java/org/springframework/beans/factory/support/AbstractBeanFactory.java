package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;
//抽象Bean工厂类 :实现获取bean的逻辑--先从bean注册中心拿,没有则根据beanDefinition创建
//                 具体的创建逻辑交给子类实现

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory{
    //维护BP列表
    private final List<BeanPostProcessor> beanPostProcessors=new ArrayList<>();

    @Override
    public Object getBean(String name) throws BeansException {
        Object sharedInstance=getSingleton(name);

        if(sharedInstance!=null){//说明确实是单例bean,可以从单例中心获取到,但还要判断是否为FactoryBean
            //如果是FactoryBean，从FactoryBean#getObject中创建bean
            return getObjectForBeanInstance(sharedInstance, name);
        }

        //走到这里说明是prototype,需要从beanDefinition构建
        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition);
        return getObjectForBeanInstance(bean,name);

    }

    protected Object getObjectForBeanInstance(Object sharedInstance, String name) {
        if(sharedInstance instanceof FactoryBean){
            return ((FactoryBean)sharedInstance).getObjext();
        }
        return sharedInstance;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }
}
