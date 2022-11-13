package model.pieces.impl;

import java.util.ArrayList;

import model.pieces.Piece;
import utils.Position;
import utils.enums.ColorEnum;
import utils.enums.PieceEnum;

public class King extends Piece {
    public King(ColorEnum c) {
        super(PieceEnum.KING, c, false);
    }

    public ArrayList<Position> possibleMovements(Position pos) {
        ArrayList<Position> output = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        if (y+1 <= 7) {
            output.add(new Position(x, y+1));
            if (x+1 <= 7)
                output.add(new Position(x+1, y+1));
            if (x-1 >= 0)
                output.add(new Position(x-1, y+1));
        }
        if (y-1 >= 0) {
            output.add(new Position(x, y-1));
            if (x+1 <= 7)
                output.add(new Position(x+1, y-1));
            if (x-1 >= 0)
                output.add(new Position(x-1, y-1));
        }
        if (x+1 <= 7)
            output.add(new Position(x+1, y));
        if (x-1 >= 0)
            output.add(new Position(x-1, y));
        return output;
    }

    public ArrayList<Position> possibleTakes(Position pos) {
        ArrayList<Position> output = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        if (y+1 <= 7) {
            output.add(new Position(x, y+1));
            if (x+1 <= 7)
                output.add(new Position(x+1, y+1));
            if (x-1 >= 0)
                output.add(new Position(x-1, y+1));
        }
        if (y-1 >= 0) {
            output.add(new Position(x, y-1));
            if (x+1 <= 7)
                output.add(new Position(x+1, y-1));
            if (x-1 >= 0)
                output.add(new Position(x-1, y-1));
        }
        if (x+1 <= 7)
            output.add(new Position(x+1, y));
        if (x-1 >= 0)
            output.add(new Position(x-1, y));
        return output;
    }
}
