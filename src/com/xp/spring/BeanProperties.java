package com.xp.spring;

/**
 * @author xupei
 */
public class BeanProperties {

    private String beanName;

    private String property;


    public BeanProperties(String beanName, String property) {
        this.beanName = beanName;
        this.property = property;
    }
    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
