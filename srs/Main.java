package srs;

public class Main {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        //System.out.print("\033[H\033[2J");
        //System.out.flush();
        //game.showBoard();
        game.initializePieces();
        game.getUserInterface().cleanScreen();
        game.showBoard();
        System.out.println("");
        game.showPiecesTaken();
        while (true) {
            System.out.println("\n");
            game.playerTurnMessage();
            System.out.println("");
            game.movePiece();
            game.getUserInterface().cleanScreen();
            game.showBoard();
            System.out.println("");
            game.showPiecesTaken();
            System.out.println("\n");
            game.analizePawnPromotion();
            game.getUserInterface().cleanScreen();
            game.showBoard();
            System.out.println("");
            game.showPiecesTaken();
            game.changePlayer();
        }
    }
}
