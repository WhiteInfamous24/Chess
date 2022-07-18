package srs.util;

public class ValidatePosition {

    private static ValidatePosition instance;
    
    private ValidatePosition() {

    }

    public static ValidatePosition getInstance() {
        if (instance == null)
            instance = new ValidatePosition();
        return instance;
    }
}
