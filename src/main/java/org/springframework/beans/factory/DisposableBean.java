package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

public interface DisposableBean {
    void destroy() throws Exception;
}
