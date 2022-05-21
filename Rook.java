import java.util.ArrayList;

public class Rook implements Piece {
    private final static String name = "ROOK";
    
    public Rook() {
        
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
