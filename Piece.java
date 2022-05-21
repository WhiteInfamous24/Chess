import java.util.ArrayList;

public abstract class Piece {
    public static PieceEnum name;
    public static ColorEnum color;

    public Piece(PieceEnum n, ColorEnum c) {
        name = n;
        color = c;
    }

    public abstract ArrayList<Position> possibleMovements(Position pos);

    public final PieceEnum getNameOfPiece() {
        return name;
    }

    public final ColorEnum getColorOfPiece() {
        return color;
    }
}
