package me.ranol.easysingleton;

import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.*;

public class Singletons {
    private static final Map<Class<?>, Object> singletonMap = Collections.synchronizedMap(new HashMap<>());

    public static <T> T get(Class<T> clazz) {
        if (clazz == null || !singletonMap.containsKey(clazz)) {
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

    public static <T> void register(SingletonResolver registerer) {
        register(registerer.getRegisterableClasses());
    }

    public static void register(Class<?>... classes) {
        register(Arrays.asList(classes));
    }

    public static void register(Collection<Class<?>> classes) {
        ReflectionFactory factory = ReflectionFactory.getReflectionFactory();
        for (Class<?> clazz : classes) {
            try {
                Constructor def = clazz.getDeclaredConstructor();
                Constructor init = factory.newConstructorForSerialization(clazz, clazz.getDeclaredConstructor());
                singletonMap.put(clazz, clazz.cast(init.newInstance()));
            } catch (Exception e) {
                /* do nothing */
            }
        }
    }

    public static SingletonResolver resolveByPackage(String pkg, ClassLoader... loaders) {
        String resourceName = pkg.replace("\\", "/").replace(".", "/");
        if (resourceName.charAt(0) == '/') {
            resourceName = resourceName.substring(1);
        }
        if (loaders == null || loaders.length <= 0) {
            loaders = new ClassLoader[] {Thread.currentThread().getContextClassLoader()};
        }
        Set<Class<?>> result = new HashSet<>();
        for (ClassLoader loader : loaders) {
            try {
                Enumeration<URL> enumeration = loader.getResources(pkg);
                while (enumeration.hasMoreElements()) {
                    URL url = enumeration.nextElement();
                    System.out.println(url.getFile());
                }
            } catch (Exception e) {
                /* do nothing */
            }
        }
        return () -> result;
    }
}