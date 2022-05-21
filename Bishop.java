import java.util.ArrayList;

public class Bishop implements Piece {
    private final static String name = "BISHOP";
    
    public Bishop() {
        
    }

    public ArrayList<Position> possibleMovements(Position position) {
        ArrayList<Position> output = new ArrayList<Position>();
        output.add();
        return output;
    }

    public String getNameOfPiece() {
        return name;
    }
}
