import java.util.ArrayList;

public abstract class Piece {
    protected PieceEnum name;
    protected ColorEnum color;
    protected boolean can_jump;

    public Piece(PieceEnum n, ColorEnum c, boolean c_j) {
        name = n;
        color = c;
        can_jump = c_j;
    }

    public abstract ArrayList<Position> possibleMovements(Position pos);

    public final PieceEnum getNameOfPiece() {
        return name;
    }

    public final ColorEnum getColorOfPiece() {
        return color;
    }

    public final boolean getCanJump() {
        return can_jump;
    }
}
