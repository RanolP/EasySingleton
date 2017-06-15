package me.ranol.easysingleton;

import java.util.Collection;

@FunctionalInterface
public interface SingletonResolver {
    Collection<Class<?>> getRegisterableClasses();

    default void register() {
        Singletons.register(getRegisterableClasses());
    }
}