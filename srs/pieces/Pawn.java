package srs.pieces;

import java.util.ArrayList;

import srs.util.Position;
import srs.util.enums.ColorEnum;
import srs.util.enums.PieceEnum;

public class Pawn extends Piece {

    public Pawn(ColorEnum color) {
        super(PieceEnum.PAWN, color, false);
    }

    public ArrayList<Position> possibleMovements(Position position) {
        ArrayList<Position> output = new ArrayList<Position>();
        int x = position.getX();
        int y = position.getY();
        if (y+1 <= 7 && color == ColorEnum.BLACK)
            output.add(new Position(x, y+1));
        if (y-1 >= 0 && color == ColorEnum.WHITE)
            output.add(new Position(x, y-1));
        if (y+2 <= 7 && color == ColorEnum.BLACK && !wasMoved)
            output.add(new Position(x, y+2));
        if (y-2 >= 0 && color == ColorEnum.WHITE && !wasMoved)
            output.add(new Position(x, y-2));
        return output;
    }

    public ArrayList<Position> possibleTakes(Position position) {
        ArrayList<Position> output = new ArrayList<Position>();
        int x = position.getX();
        int y = position.getY();
        if (y+1 <= 7 && x+1 <= 7 && color == ColorEnum.BLACK)
            output.add(new Position(x+1, y+1));
        if (y-1 >= 0 && x+1 <= 7 && color == ColorEnum.WHITE)
            output.add(new Position(x+1, y-1));
        if (y+1 <= 7 && x-1 >= 0 && color == ColorEnum.BLACK)
            output.add(new Position(x-1, y+1));
        if (y-1 >= 0 && x-1 >= 0 && color == ColorEnum.WHITE)
            output.add(new Position(x-1, y-1));
        return output;
    }
}
