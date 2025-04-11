package org.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Field;

//抽象bean工厂的抽象子类:实现了createBean逻辑
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return doCreateBean(beanName,beanDefinition);
    }

    private Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Class beanClass=beanDefinition.getBeanClass();
        Object bean=null;
        try{
            //实例化
            bean=createBeanInstance(beanDefinition);
            //依赖注入
            applyPropertyValues(beanName, bean, beanDefinition);
            //初始化
            initializeBean(beanName, bean, beanDefinition);
        }catch (Exception e){
            throw new BeansException("Instantiation of bean failed", e);
        }

        //单独注册有销毁方法的bean
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        //注册其余的单例bean
        if(beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }
        return bean;
    }


    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
         Object wrappedBean=applyBeanPostProcessorsBeforeInitialization(bean,beanName);

         invokeInitMethods(beanName,wrappedBean,beanDefinition);
         
         wrappedBean=applyBeanPostProcessorsAfterInitialization(wrappedBean,beanName);
         return wrappedBean;
    }

    protected void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {
    }


    private void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
           for(PropertyValue pv:beanDefinition.getPropertyValues().getPropertyValues()){
                 String name=pv.getName();
                 Object value=pv.getValue();

                 if(value instanceof BeanReference){
                     value=getBean(((BeanReference) value).getBeanName());
                  }

                 Class clazz=beanDefinition.getBeanClass();
               try {
                   Field field = clazz.getField("name");
                   field.set(name,value);
               } catch (Exception e) {
                   throw new BeansException("Error setting property values for bean: " + beanName, e);
               }

           }
    }

    /*
    * 将有销毁方法的单例bean单独注册在另一个map中
    * */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        //只有单例bean才会执行销毁方法
        if(beanDefinition.isSingleton()){
             if(bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())){
                 //父类DefaultSingletonBeanRegistry的方法,与addSingleton地位平齐
                 registerDisposableBean(beanName,(DisposableBean) bean);
             }
         }
    }

    private Object createBeanInstance(BeanDefinition beanDefinition) {
        return getInstantiationStrategy().instantiate(beanDefinition);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }


    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result=existingBean;
        for(BeanPostProcessor bp:getBeanPostProcessors()){
            Object current=bp.postProcessBeforeInitialization(result,beanName);
            if(current==null){
                return result;
            }
            result=current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result=existingBean;
        for(BeanPostProcessor bp:getBeanPostProcessors()){
            Object current=bp.postProcessAfterInitialization(result,beanName);
            if(current==null){
                return result;
            }
            result=current;
        }
        return result;
    }


}
