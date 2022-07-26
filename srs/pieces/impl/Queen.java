package srs.pieces.impl;

import java.util.ArrayList;

import srs.pieces.Piece;
import srs.util.Position;
import srs.util.enums.ColorEnum;
import srs.util.enums.PieceEnum;

public class Queen extends Piece {

    public Queen(ColorEnum color) {
        super(PieceEnum.QUEEN, color, true);
    }

    public ArrayList<Position> possibleMovements(Position position) {
        ArrayList<Position> output = new ArrayList<Position>();
        int x = position.getX();
        int y = position.getY();
        for (int i = 1; i < 7; i++) {
            if (y+i <= 7) {
                output.add(new Position(x, y+i));
                if (x+i <= 7)
                    output.add(new Position(x+i, y+i));
                if (x-i >= 0)
                    output.add(new Position(x-i, y+i));
            }
            if (y-i >= 0) {
                output.add(new Position(x, y-i));
                if (x+i <= 7)
                    output.add(new Position(x+i, y-i));
                if (x-i >= 0)
                    output.add(new Position(x-i, y-i));
            }
            if (x+i <= 7)
                output.add(new Position(x+i, y));
            if (x-i >= 0)
                output.add(new Position(x-i, y));
        }
        return output;
    }

    public ArrayList<Position> possibleTakes(Position position) {
        ArrayList<Position> output = new ArrayList<Position>();
        int x = position.getX();
        int y = position.getY();
        for (int i = 1; i < 7; i++) {
            if (y+i <= 7) {
                output.add(new Position(x, y+i));
                if (x+i <= 7)
                    output.add(new Position(x+i, y+i));
                if (x-i >= 0)
                    output.add(new Position(x-i, y+i));
            }
            if (y-i >= 0) {
                output.add(new Position(x, y-i));
                if (x+i <= 7)
                    output.add(new Position(x+i, y-i));
                if (x-i >= 0)
                    output.add(new Position(x-i, y-i));
            }
            if (x+i <= 7)
                output.add(new Position(x+i, y));
            if (x-i >= 0)
                output.add(new Position(x-i, y));
        }
        return output;
    }
}
