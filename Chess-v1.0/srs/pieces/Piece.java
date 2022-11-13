package srs.pieces;

import java.util.ArrayList;

import srs.util.Position;
import srs.util.enums.ColorEnum;
import srs.util.enums.PieceEnum;

public abstract class Piece {

    protected PieceEnum name;
    protected ColorEnum color;
    protected boolean longMovement;
    protected boolean wasMoved;

    public Piece(PieceEnum name, ColorEnum color, boolean longMovement) {
        this.name = name;
        this.color = color;
        this.longMovement = longMovement;
        wasMoved = false;
    }

    public abstract ArrayList<Position> possibleMovements(Position position);
    public abstract ArrayList<Position> possibleTakes(Position position);

    public final PieceEnum getNameOfPiece() {
        return name;
    }

    public final ColorEnum getColorOfPiece() {
        return color;
    }

    public final boolean getLongMovement() {
        return longMovement;
    }

    public final boolean getWasMoved() {
        return wasMoved;
    }

    public final void setWasMoved(boolean wasMoved) {
        this.wasMoved = wasMoved;
    }
}
