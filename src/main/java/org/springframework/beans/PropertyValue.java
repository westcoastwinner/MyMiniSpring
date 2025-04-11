package org.springframework.beans;
/*
* PropertyValue是Spring在构造Bean时封装的“属性(字段)名+属性(字段)值”对,
* Spring通过它来对Bean的字段赋值,支持基本值、Bean引用、SpEL表达式、集合等各种类型。
*
* */
public class PropertyValue {
    private final String name;
    private final Object value;//value是一个Object类型,Spring会根据情况做类型转换或引用解析。

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
