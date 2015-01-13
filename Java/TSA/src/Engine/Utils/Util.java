package Engine.Utils;

import Engine.Main;
import java.util.ArrayList;

public class Util {

    static Main main;
    
    public static void log(Object log) {
        System.out.println("[LOG] " + log);
    }

    public static void log(String prefix, Object log) {
        prefix = prefix.toUpperCase();
        System.out.printf("[%s] " + log, prefix);
    }

    public static Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
