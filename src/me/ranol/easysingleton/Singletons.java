package me.ranol.easysingleton;

import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Singletons {
    private static final Map<Class<?>, Object> singletonMap = Collections.synchronizedMap(new HashMap<>());

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must be not null");
        }
        if (!singletonMap.containsKey(clazz)) {
            register(clazz);
        }
        Object got = singletonMap.get(clazz);
        if (clazz.isInstance(got)) {
            return (T) got;
        }
        throw new SingletonException("The singleton object " + clazz.getName() + " is not registered");
    }

    @SuppressWarnings("unchecked")
    public static <T> T get() {
        try {
            return get((Class<T>) Class.forName(new Exception().getStackTrace()[1].getClassName()));
        } catch (Exception e) {
            throw new SingletonException("Cannot get singleton object");
        }
    }

    private static void register(Class<?> clazz) {
        ReflectionFactory factory = ReflectionFactory.getReflectionFactory();
        try {
            if (clazz.isAnnotationPresent(Singleton.class)) {
                Constructor def = clazz.getDeclaredConstructor();
                Constructor init = factory.newConstructorForSerialization(clazz, clazz.getDeclaredConstructor());
                init.setAccessible(true);
                singletonMap.put(clazz, clazz.cast(init.newInstance()));
                return;
            }
        } catch (Exception e) {
            /* do nothing */
        }
        throw new SingletonException("Cannot register class");
    }
}