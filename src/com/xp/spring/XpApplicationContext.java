package com.xp.spring;


import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class XpApplicationContext {

    private Class appConfig;


    private ConcurrentHashMap<String,Class> beanDefinitionsMap=new ConcurrentHashMap<>();


    public XpApplicationContext(Class appConfigClass) {
        this.appConfig = appConfigClass;

        if(appConfigClass.isAnnotationPresent(ComponentScan.class)){

            ComponentScan componentScanAnnotation = (ComponentScan) appConfigClass.getAnnotation(ComponentScan.class);

            String scanPath= componentScanAnnotation.value();

            List<Class<?>> classes =null;
            try {
                classes= ScanClassUtil.getAllClasses(scanPath);
            }catch (Exception e) {
                e.printStackTrace();
            }

            if(classes!=null&&classes.size() > 0){
                for (Class<?> childClasses : classes) {
                    if(childClasses.isAnnotationPresent(Component.class)){
                        String beanName=  getBeanName(childClasses);
                        beanDefinitionsMap.put(beanName,childClasses);
                    }
                }
            }


        }
    }

    private String getBeanName(Class clazz){
        Component component = (Component) clazz.getAnnotation(Component.class);
        return component.value().length()>0? component.value() : clazz.getSimpleName();
    };

    private Object createBean(String beanName){
        if(beanDefinitionsMap.containsKey(beanName)){
            try {
                return beanDefinitionsMap.get(beanName).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    };


    public Object getBean(String beanName){
        return createBean(beanName);
    }
}
