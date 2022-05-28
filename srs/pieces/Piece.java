package srs.pieces;

import java.util.ArrayList;

import srs.Position;
import srs.enums.ColorEnum;
import srs.enums.PieceEnum;

public abstract class Piece {
    protected PieceEnum name;
    protected ColorEnum color;
    protected boolean long_movement;
    protected boolean was_moved;

    public Piece(PieceEnum n, ColorEnum c, boolean l_m) {
        name = n;
        color = c;
        long_movement = l_m;
        was_moved = false;
    }

    public abstract ArrayList<Position> possibleMovements(Position pos);
    public abstract ArrayList<Position> possibleTakes(Position pos);

    public final PieceEnum getNameOfPiece() {
        return name;
    }

    public final ColorEnum getColorOfPiece() {
        return color;
    }

    public final boolean getLongMovement() {
        return long_movement;
    }

    public final boolean getWasMoved() {
        return was_moved;
    }

    public final void setWasMoved() {
        was_moved = true;
    }
}