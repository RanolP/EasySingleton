package examples;

import me.ranol.easysingleton.Singleton;
import me.ranol.easysingleton.Singletons;

@Singleton
public class SingletonObject2 {
    private SingletonObject2() {
    }

    public static SingletonObject2 getInstance() {
        return Singletons.get(SingletonObject2.class);
    }

    public void holy() {
        System.out.print(toString());
    }

    @Override
    public String toString() {
        return "Holy ";
    }
}