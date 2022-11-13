package model.pieces.impl;

import java.util.ArrayList;

import model.pieces.Piece;
import utils.Position;
import utils.enums.ColorEnum;
import utils.enums.PieceEnum;

public class Rook extends Piece {
    public Rook(ColorEnum c) {
        super(PieceEnum.ROOK, c, true);
    }

    public ArrayList<Position> possibleMovements(Position pos) {
        ArrayList<Position> output = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        for (int i = 1; i < 7; i++) {
            if (y+i <= 7)
                output.add(new Position(x, y+i));
            if (y-i >= 0)
                output.add(new Position(x, y-i));
            if (x+i <= 7)
                output.add(new Position(x+i, y));
            if (x-i >= 0)
                output.add(new Position(x-i, y));
        }
        return output;
    }

    public ArrayList<Position> possibleTakes(Position pos) {
        ArrayList<Position> output = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        for (int i = 1; i < 7; i++) {
            if (y+i <= 7)
                output.add(new Position(x, y+i));
            if (y-i >= 0)
                output.add(new Position(x, y-i));
            if (x+i <= 7)
                output.add(new Position(x+i, y));
            if (x-i >= 0)
                output.add(new Position(x-i, y));
        }
        return output;
    }
}
