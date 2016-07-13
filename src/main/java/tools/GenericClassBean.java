/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author Jayang
 */
import java.lang.reflect.InvocationTargetException;  
import java.lang.reflect.Method;  
  
/** 
 * 泛型类，获取实例化对象 
 *  
 * @author huaernan 
 *  
 * @param <T> 
 */  
public class GenericClassBean<T> {  
  
    private static Class beanClass;  
  
    public GenericClassBean(Class beanClass) {  
        this.beanClass = beanClass;  
    }  
  
    /** 
     * 获取对象示例 
     *  
     * @param t 
     * @return 
     */  
    public T getClassBean(T t) {  
        try {  
            t = (T) beanClass.newInstance();  
        } catch (InstantiationException | IllegalAccessException e) {  
            e.printStackTrace();  
        }  
        return t;  
  
    }  
  
    /** 
     * 给对象属性赋值 
     *  
     * @param propertyName 
     *            对象的set方法名称 
     * @param paropertyValue 
     *            对象set方法set的值 
     */  
    public static void setClassBeanPropertyValue(Object object,  
            String propertyName, String paropertyValue) {  
        Method method = null;  
        /* 
         * propertyName对应的是对象的属性名称，和数据库列名称也要相同，例如User类的属性有username， 
         * 对应的数据库字段也是username,对应的方法名称就是setUsername 
         */  
        propertyName = propertyName.substring(0, 1).toUpperCase()  
                + propertyName.substring(1);  
        String setMethod = "set" + propertyName;  
        try {  
            // 找到对象的set方法  
            method = beanClass.getMethod(setMethod, String.class);  
        } catch (NoSuchMethodException | SecurityException e) {  
            e.printStackTrace();  
        }  
        try {  
            // 调用方法set方法给属性赋值  
            method.invoke(object, paropertyValue);  
        } catch (IllegalAccessException | IllegalArgumentException  
                | InvocationTargetException e) {  
            e.printStackTrace();  
        }  
    }  
  
}  