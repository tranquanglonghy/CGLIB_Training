package com.java.reflection;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.core.Predicate;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static Class<?> createBeanClass(
            final String className,
            /* bean properties, name -> type */
            final Map<String, Class<?>> properties){

        final BeanGenerator beanGenerator = new BeanGenerator();

        /* use our own hard coded class name instead of a real naming policy */
        beanGenerator.setNamingPolicy(new NamingPolicy(){
            @Override public String getClassName(final String prefix,
                                                 final String source, final Object key, final Predicate names){
                return className;
            }});
        BeanGenerator.addProperties(beanGenerator, properties);

        return (Class<?>) beanGenerator.createClass();
    }

    public static void main(final String[] args) throws Exception{
        final Map<String, Class<?>> properties =
                new HashMap<String, Class<?>>();
        properties.put("foo", Integer.class);
        properties.put("bar", String.class);

        final Class<?> beanClass =
                createBeanClass("Response", properties);

        Field[] fields = beanClass.getDeclaredFields();
        Method methods = beanClass.getDeclaredMethod("getFoo", null);
        Method method2 = beanClass.getDeclaredMethod("setFoo", Integer.class);


        Constructor<?> ctor = beanClass.getConstructor();
        Object object = ctor.newInstance(new Object[] {});

        method2.invoke(object, 1);
        System.out.println(methods.invoke(object, null));

        System.out.println(methods);
        System.out.println(method2);

    }
//public static void main(final String[] args) throws Exception{
//    final Map<String, Class<?>> properties =
//            new HashMap<String, Class<?>>();
//    properties.put("foo", Integer.class);
//    properties.put("bar", String.class);
//    properties.put("baz", int[].class);
//
//    final Class<?> beanClass =
//            createBeanClass("Res", properties);
//    System.out.println(beanClass);
//
//
//}
}