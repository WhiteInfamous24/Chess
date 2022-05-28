package srs.userinterface;

import java.util.ArrayList;

import srs.Board;
import srs.Position;
import srs.enums.ColorEnum;
import srs.pieces.Bishop;
import srs.pieces.Knight;
import srs.pieces.Piece;
import srs.pieces.Queen;
import srs.pieces.Rook;

public class UserInterfaceWindows implements UserInterface {
    public UserInterfaceWindows() {

    }
    
    public void playerTurnMessage(ColorEnum c) {

    }

    public void invalidPositionMessage() {

    }

    public void invalidInputMessage() {
        
    }

    public Position requestFirstPositionMessage() {
        return new Position(0, 0);
    }

    public Position requestSecondPositionMessage() {
        return new Position(0, 0);
    }

    public Piece requestToChoosePiece(ColorEnum c) {
        return new Queen(c);
    }

    public void showBoard(Board b) {

    }

    public void showPiecesTaken(ArrayList<Piece> b_p_t, ArrayList<Piece> w_p_t) {

    }

    public void cleanScreen() {

    }

    public void insertVoidLine(int n) {
        
    }
}