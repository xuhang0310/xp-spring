package com.xp.spring;

/**
 * @author xupei
 */
public class BeanDefinition {


    public static  final String SCOPE_SINGLETON = "singleton";
     private Class type;

    private String scope;


    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
