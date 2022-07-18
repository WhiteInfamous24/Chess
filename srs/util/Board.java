package srs.util;

import srs.pieces.Piece;

public class Board {
    
    private Piece board[][] = new Piece[8][8];

    public Board() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                board[i][j] = null;
    }

    public void movePiece(Position positionOne, Position positionTwo) {
        board[positionTwo.getX()][positionTwo.getY()] = board[positionOne.getX()][positionOne.getY()];
        board[positionOne.getX()][positionOne.getY()] = null;
        if (!board[positionTwo.getX()][positionTwo.getY()].getWasMoved())
            board[positionTwo.getX()][positionTwo.getY()].setWasMoved(true);
    }

    public void undoMovement(Position positionOne, Position positionTwo, boolean wasMovedOld) {
        board[positionTwo.getX()][positionTwo.getY()] = board[positionOne.getX()][positionOne.getY()];
        board[positionOne.getX()][positionOne.getY()] = null;
        board[positionTwo.getX()][positionTwo.getY()].setWasMoved(wasMovedOld);
    }

    // GETTERS & SETTERS

    public void setPiece(Piece piece, Position position) {
        board[position.getX()][position.getY()] = piece;
    }

    public Piece getPiece(Position position) {
        return board[position.getX()][position.getY()];
    }
}
