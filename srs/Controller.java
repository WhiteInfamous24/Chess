package srs;

public class Controller {

    private static Controller instance;

    private Controller() {
    
    }

    public static Controller getInstante() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }
}
