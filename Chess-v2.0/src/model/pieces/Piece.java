package model.pieces;

import java.util.ArrayList;

import utils.Position;
import utils.enums.ColorEnum;
import utils.enums.PieceEnum;

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

    public final PieceEnum getName() {
        return name;
    }

    public final ColorEnum getColor() {
        return color;
    }

    public final boolean getLongMovement() {
        return long_movement;
    }

    public final boolean getWasMoved() {
        return was_moved;
    }
    
    public final void setWasMoved(boolean b) {
        was_moved = b;
    }
}
