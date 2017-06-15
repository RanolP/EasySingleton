package examples;

import me.ranol.easysingleton.Singleton;
import me.ranol.easysingleton.Singletons;

@Singleton
public class SingletonObject {
    private SingletonObject() {
        throw new UnsupportedOperationException("Cannot instantiation singleton object");
    }

    public static SingletonObject getInstance() {
        return Singletons.get();
    }

    public void shit() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "shit.";
    }
}