package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

public interface InitializingBean {

    void afterPropertiesSet()throws BeansException;
}
