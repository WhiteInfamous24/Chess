package srs.util;

import java.util.ArrayList;

public class Utilities {
    
    private static Utilities instance;
    
    private Utilities() {

    }

    public static Utilities getInstance() {
        if (instance == null)
            instance = new Utilities();
        return instance;
    }

    /*
     * se le pasa por argumento un arraylist de posiciones y una posicion, y el metodo retornara un booleano
     * en el caso de que exista, o no, alguna posicion en el arraylist con las mismas coordenadas que la posicion
     * que se paso por argumento de la funcion
     */
    public static boolean searchPositionInArray(ArrayList<Position> positions, Position position) {
        for (Position positionIterator : positions)
            if (positionIterator.getX() == position.getX() && positionIterator.getY() == position.getY())
                return true;
        return false;
    }
}
