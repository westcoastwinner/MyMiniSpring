package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

//本来吧已经把父类的抽象方法实现完了,结果又把loadBeanDefinitions交给子类实现了,所以又得是抽象类
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;//lazy-init on refresh

    @Override
    protected void refreshBeanFactory() throws BeansException {
          DefaultListableBeanFactory beanFactory=createBeanFactory();
          loadBeanDefinitions(beanFactory);
          this.beanFactory=beanFactory;
    }

    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);


}
