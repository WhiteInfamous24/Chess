package srs;

public class Main {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.initializePieces();
        game.getUserInterface().cleanScreen();
        game.showBoard();
        game.insertVoidLine(1);
        game.showPiecesTaken();
        while (true) {
            game.insertVoidLine(2);
            game.playerTurnMessage();
            game.insertVoidLine(1);
            game.movePiece();
            game.getUserInterface().cleanScreen();
            game.showBoard();
            game.insertVoidLine(1);
            game.showPiecesTaken();
            game.insertVoidLine(2);
            game.analizePawnPromotion();
            game.getUserInterface().cleanScreen();
            game.showBoard();
            game.insertVoidLine(1);
            game.showPiecesTaken();
            game.changePlayer();
        }
    }
}
