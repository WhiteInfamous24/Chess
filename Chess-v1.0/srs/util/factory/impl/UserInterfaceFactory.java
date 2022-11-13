package srs.util.factory.impl;

import srs.userinterface.UserInterface;
import srs.userinterface.impl.UserInterfaceConsole;
import srs.userinterface.impl.UserInterfaceWindows;
import srs.util.factory.Factory;

public class UserInterfaceFactory extends Factory<UserInterface> {

    private static UserInterfaceFactory instance;

    private UserInterfaceFactory() { } // VOID

    public static UserInterfaceFactory getInstance() {
        if (instance == null)
            instance = new UserInterfaceFactory();
        return instance;
    }

    @Override
    public final UserInterface build(String toBuild) {
        return switch (toBuild) {
            case "CONSOLE" -> new UserInterfaceConsole();
            case "WINDOWS" -> new UserInterfaceWindows();
            default -> new UserInterfaceConsole();
        };
    }
}
