import java.util.ArrayList;

public interface Piece {
    public ArrayList<Position> possibleMovements(Position position);
    public String getNameOfPiece();
}
