import java.util.ArrayList;

public class King implements Piece {
    private final static String name = "King";
    
    public King() {
        
    }

    public ArrayList<Position> possibleMovements(Position position) {
        int x = position.getX();
        int y = position.getY();
        ArrayList<Position> output = new ArrayList<Position>();
        output.add(new Position(x, y+1));
        output.add(new Position(x, y-1));
        output.add(new Position(x+1, y));
        output.add(new Position(x-1, y));
        output.add(new Position(x+1, y+1));
        output.add(new Position(x-1, y+1));
        output.add(new Position(x+1, y-1));
        output.add(new Position(x-1, y-1));
        return output;
    }

    public String getNameOfPiece() {
        return name;
    }
}
