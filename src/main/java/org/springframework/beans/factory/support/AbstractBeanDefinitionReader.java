package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.core.DefaultResourceLoader;
import org.springframework.core.ResourceLoader;
//没有完全实现接口所有方法的类必须是抽象的(incomplete)
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{
    private ResourceLoader resourceLoader;
    private BeanDefinitionRegistry registry;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    @Override
    public void loadBeanDefinitions(String[] locations) throws BeansException {
         for(String location:locations){
             loadBeanDefinitions(location);
         }
    }
}
