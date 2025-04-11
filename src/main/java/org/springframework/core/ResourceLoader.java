package org.springframework.core;
//可以理解为根据字符串获取对应资源的流对象
public interface ResourceLoader {

    Resource getResource(String location);
}
