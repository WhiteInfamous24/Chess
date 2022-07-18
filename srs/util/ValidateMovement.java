package srs.util;

public class ValidateMovement {
    
    private static ValidateMovement instance;
    
    private ValidateMovement() {

    }

    public static ValidateMovement getInstance() {
        if (instance == null)
            instance = new ValidateMovement();
        return instance;
    }
}
