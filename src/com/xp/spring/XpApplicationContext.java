package com.xp.spring;


import java.beans.Introspector;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class XpApplicationContext {

    private Class appConfig;


    private ConcurrentHashMap<String,BeanDefinition> beanDefinitionsMap=new ConcurrentHashMap<>();

    private ConcurrentHashMap<String,Object> singletonObjectMap=new ConcurrentHashMap<>();


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

            if(classes==null||classes.size() == 0){
                return;
            }
            for (Class<?> childClasses : classes) {
                if(childClasses.isAnnotationPresent(Component.class)){
                    BeanDefinition beandefinition = new BeanDefinition();
                    beandefinition.setType(childClasses);
                    beandefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
                    if(childClasses.isAnnotationPresent(Scope.class)){
                        beandefinition.setScope(childClasses.getAnnotation(Scope.class).value());
                    }
                    beanDefinitionsMap.put(getBeanName(childClasses), beandefinition);
                }
            }

        }
    }

    private String getBeanName(Class clazz){
        Component component = (Component) clazz.getAnnotation(Component.class);
        return component.value().length()>0? component.value() : Introspector.decapitalize(clazz.getSimpleName());
    };

    private Object createBean(String beanName,BeanDefinition beandefinition){

        try {
            Object instance= beandefinition.getType().newInstance();

            for (Field declaredField : beandefinition.getType().getDeclaredFields()) {
                if(declaredField.isAnnotationPresent(Autowired.class)){
                    String autowiredBeanName = getBeanName(declaredField.getType());
                    Object autowiredBean=getBean(autowiredBeanName);
                    declaredField.setAccessible(true);
                    declaredField.set(instance,autowiredBean);
                }
            }

            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();

            }
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

       return null;
    };


    public Object getBean(String beanName){



        BeanDefinition beandefinition = beanDefinitionsMap.get(beanName);

        if(beandefinition==null){
            return null;
        }
        String scope = beandefinition.getScope();

        if(singletonObjectMap.get(beanName)!=null&&scope.equals(BeanDefinition.SCOPE_SINGLETON)){
            return singletonObjectMap.get(beanName);
        }
        if(scope.equals(BeanDefinition.SCOPE_SINGLETON)){
            Object bean= createBean(beanName,beandefinition);
            singletonObjectMap.put(beanName,bean);
            return bean;
        }

        Object bean= createBean(beanName,beandefinition);
        return bean;
    }
}
