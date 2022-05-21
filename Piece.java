import java.util.ArrayList;

public abstract class Piece {
    public static String name;
    public static String color;

    public Piece(String n, String c) {
        name = n;
        color = c;
    }

    public abstract ArrayList<Position> possibleMovements(Position pos);

    public final String getNameOfPiece() {
        return name;
    }
}
