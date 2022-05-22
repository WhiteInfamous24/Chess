public class Game {
    private Board board;

    public Game() {
        board = new Board();
    }

    public void initializePieces() {
        for(int i = 0; i < 8; i++) {
            board.setPiece(new Pawn(ColorEnum.BLACK), new Position(i, 1));
            board.setPiece(new Pawn(ColorEnum.WHITE), new Position(i, 6));
        }
        board.setPiece(new Rook(ColorEnum.BLACK), new Position(0, 0));
        board.setPiece(new Rook(ColorEnum.WHITE), new Position(0, 7));
        board.setPiece(new Rook(ColorEnum.BLACK), new Position(7, 0));
        board.setPiece(new Rook(ColorEnum.WHITE), new Position(7, 7));
        board.setPiece(new Knight(ColorEnum.BLACK), new Position(1, 0));
        board.setPiece(new Knight(ColorEnum.WHITE), new Position(1, 7));
        board.setPiece(new Knight(ColorEnum.BLACK), new Position(6, 0));
        board.setPiece(new Knight(ColorEnum.WHITE), new Position(6, 7));
        board.setPiece(new Bishop(ColorEnum.BLACK), new Position(2, 0));
        board.setPiece(new Bishop(ColorEnum.WHITE), new Position(2, 7));
        board.setPiece(new Bishop(ColorEnum.BLACK), new Position(5, 0));
        board.setPiece(new Bishop(ColorEnum.WHITE), new Position(5, 7));
        board.setPiece(new Queen(ColorEnum.BLACK), new Position(4, 0));
        board.setPiece(new King(ColorEnum.WHITE), new Position(4, 7));
        board.setPiece(new Queen(ColorEnum.WHITE), new Position(3, 7));
        board.setPiece(new King(ColorEnum.BLACK), new Position(3, 0));
    }

    public void showBoard() {
        char row = 'A';
        System.out.println("");
        for(int i = 0; i < 8; i++) {
            System.out.println("|---||----------|----------|----------|----------|----------|----------|----------|----------|");
            System.out.print("| " + row + " |");
            row++;
            for(int j = 0; j < 8; j++) { 
                System.out.print("|");
                if(board.getPiece(new Position(j, i)) != null)
                    System.out.printf("%10s", board.getPiece(new Position(j, i)).getNameOfPiece());
                else
                    System.out.printf("%10s", board.getPiece(new Position(j, i)));
            }
            System.out.println("|");
            System.out.print("|   |");
            for(int j = 0; j < 8; j++) {
                System.out.print("|");
                if(board.getPiece(new Position(j, i)) != null)
                    System.out.printf("%10s", board.getPiece(new Position(j, i)).getColorOfPiece());
                else
                    System.out.printf("%10s", board.getPiece(new Position(j, i)));
            }
            System.out.println("|");
            System.out.println("|   ||          |          |          |          |          |          |          |          |");
        }
        System.out.println("|===||==========|==========|==========|==========|==========|==========|==========|==========|");
        System.out.println("|   ||1         |2         |3         |4         |5         |6         |7         |8         |");
        System.out.println("|--------------------------------------------------------------------------------------------|");
    }
}
