package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

public interface FactoryBean<T> {

    T getObjext() throws BeansException;

    boolean isSingleton();
}
