import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(ColorEnum c) {
        super(PieceEnum.PAWN, c, false);
    }

    public ArrayList<Position> possibleMovements(Position pos) {
        ArrayList<Position> output = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        if(y+1 <= 7 && color == ColorEnum.BLACK)
            output.add(new Position(x, y+1));
        if(y-1 >= 0 && color == ColorEnum.WHITE)
            output.add(new Position(x, y-1));
        return output;
    }
}
