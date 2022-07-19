package srs.userinterface;

import java.util.ArrayList;

import srs.Game;
import srs.pieces.Bishop;
import srs.pieces.Knight;
import srs.pieces.Piece;
import srs.pieces.Queen;
import srs.pieces.Rook;
import srs.util.Board;
import srs.util.Position;
import srs.util.enums.ColorEnum;

public class UserInterfaceWindows implements UserInterface {
    
    public UserInterfaceWindows() {

    }
    
    public void playerTurnMessage() {

    }

    public void checkMessage() {
        
    }

    public void winnerMessage() {
        
    }

    public void invalidPositionMessage() {
        
    }

    public void invalidMovementMessage() {

    }

    public void invalidInputMessage() {
        
    }

    public Position requestFirstPositionMessage() {
        return new Position(0, 0);
    }

    public Position requestSecondPositionMessage() {
        return new Position(0, 0);
    }

    public Piece requestToChoosePiece() {
        return new Queen(Game.getPlayer()); // IMPLEMENT
    }

    public void showBoard() {

    }

    public void showPiecesTaken() {

    }

    public void cleanScreen() {

    }

    public void insertVoidLine(int n) {
        
    }
}
