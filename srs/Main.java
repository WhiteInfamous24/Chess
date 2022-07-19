package srs;

import srs.util.ChessUtilities;

public class Main {

    public static void main(String[] args) throws Exception {

        Controller.getInstance();
        do {
            Controller.getUserInterface().cleanScreen();
            Controller.getUserInterface().showBoard();
            Controller.getUserInterface().insertVoidLine(1);
            Controller.getUserInterface().showPiecesTaken();
            Controller.getUserInterface().insertVoidLine(2);
            Controller.getUserInterface().playerTurnMessage();
            Controller.getUserInterface().insertVoidLine(1);
            Controller.movePiece();
            Game.changePlayer();
        } while (!ChessUtilities.isCheckmate());
        Controller.getUserInterface().cleanScreen();
        Controller.getUserInterface().winnerMessage();
        Controller.getUserInterface().insertVoidLine(1);
    }
}
