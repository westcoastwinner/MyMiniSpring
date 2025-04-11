package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.DefaultResourceLoader;

import java.util.Collections;
import java.util.Map;
//继承DefaultResourceLoader是否表示环境类本身就代表资源提供源?
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws BeansException {
        //创建BeanFactory,并加载BeanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        
        //在Bean实例化之前,执行BeanFactoryPostProcessor对beanDefinition做修改？
        invokeBeanFactoryPostPostprocessors(beanFactory);
        
        //赶在bean create之前注册BP
        registerBeanPostProcessors(beanFactory);

        //提前实例化单例bean
        beanFactory.preInstantiateSingletons();
    }

    /*
    * 注意,这里前提是BP类已经加载到JVM或已经注册到单例中心了,
    * 此处只是把他们和容器绑定(beanFactory.addBeanPostProcessor -> List<BeanPostProcessor>)
    * 方便后面bean create过程中的调用:
    *    for(BeanPostProcessor bp:beanPostProcessors){bp.postProcessBefore/AfterInitialization()}
    * 这就体现了beanFactory提供getBeansOfType方法的必要性
    * */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> bps = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for(BeanPostProcessor bp:bps.values()){
            beanFactory.addBeanPostProcessor(bp);
        }
    }

    protected void invokeBeanFactoryPostPostprocessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> bfps = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for(BeanFactoryPostProcessor bfp:bfps.values()){
            bfp.postProcessBeanFactory(beanFactory);
        }
    }

    protected abstract void refreshBeanFactory()throws BeansException;

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name,requiredType);
    }

    public Object getBean(String name){
        return getBeanFactory().getBean(name);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    @Override
    public void close() {
        doClose();
    }

    protected void doClose() {
        destroyBeans();
    }

    protected void destroyBeans() {
        getBeanFactory().destroySingletons();
    }

    @Override
    public void registerShutdownHook() {
        Thread shutdownHook = new Thread(() -> doClose());
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }
}
