package com.xp.spring;


import java.lang.annotation.Annotation;

public class XpApplicationContext {

    private Class appConfig;


    public XpApplicationContext(Class appConfigClass) {
        this.appConfig = appConfigClass;

        if(appConfigClass.isAnnotationPresent(ComponentScan.class)){

            ComponentScan componentScanAnnotation = (ComponentScan) appConfigClass.getAnnotation(ComponentScan.class);

            String scanPath= componentScanAnnotation.value();
            System.out.println(scanPath);

            // 获取scanPath目录下面所有的类
            ClassLoader classLoader =Thread.currentThread().getContextClassLoader();return;



        }
    }

    public Object getBean(String beanName){
        return null;
    }
}
