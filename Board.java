public class Board {
    private Piece board[][] = new Piece[8][8];

    public Board() {
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                board[i][j] = null;
    }

    public void setPiece(Piece piece, Position position) {
        board[position.getX()][position.getY()] = piece;
    }

    public Piece getPiece(Position position) {
        return board[position.getX()][position.getY()];
    }

    public void movePiece(Position position_1, Position position_2) {
        board[position_2.getX()][position_2.getY()] = board[position_1.getX()][position_1.getY()];
        board[position_1.getX()][position_1.getY()] = null;
    }
}
