package examples;

import me.ranol.easysingleton.Singletons;

public class Main {
    public static void main(String[] args) {
        Singletons.resolveByPackage("me.ranol.easysingleton").register();

        SingletonObject2.getInstance().holy();
        SingletonObject.getInstance().shit();
    }
}