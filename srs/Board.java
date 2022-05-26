package srs;

import srs.pieces.Piece;

public class Board {
    private Piece board[][] = new Piece[8][8];

    public Board() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                board[i][j] = null;
    }

    public void setPiece(Piece p, Position pos) {
        board[pos.getX()][pos.getY()] = p;
    }

    public Piece getPiece(Position pos) {
        return board[pos.getX()][pos.getY()];
    }

    public void movePiece(Position pos_1, Position pos_2) {
        board[pos_2.getX()][pos_2.getY()] = board[pos_1.getX()][pos_1.getY()];
        board[pos_1.getX()][pos_1.getY()] = null;
        if (!board[pos_2.getX()][pos_2.getY()].getWasMoved())
            board[pos_2.getX()][pos_2.getY()].setWasMoved();
    }
}
