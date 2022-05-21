import java.util.ArrayList;

public class Pawn implements Piece {
    private final static String name = "PAWN";
    
    public Pawn() {
        
    }

    public ArrayList<Position> possibleMovements(Position position) {
        ArrayList<Position> output = new ArrayList<Position>();
        output.add(new Position(position.getX(), position.getY()+1));
        return output;
    }

    public String getNameOfPiece() {
        return name;
    }
}
