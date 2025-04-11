package org.springframework.context.event;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
       for(ApplicationListener<ApplicationEvent> listener:applicationListeners){
           if(supportsEvent(listener, event)){
               listener.onApplicationEvent(event);
           }
       }
    }

    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> listener, ApplicationEvent event) {

        //INCOMPLETE!!!留待后续实现

       return false;
    }


}
