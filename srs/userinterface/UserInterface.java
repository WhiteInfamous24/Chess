package srs.userinterface;

import java.util.ArrayList;

import srs.Board;
import srs.Position;
import srs.enums.ColorEnum;
import srs.pieces.Piece;

public interface UserInterface {
    public abstract void playerTurnMessage(ColorEnum c);
    public abstract void checkMessage();
    public abstract void winnerMessage(ColorEnum c);
    public abstract void invalidPositionMessage();
    public abstract void invalidMovementMessage();
    public abstract void invalidInputMessage();
    public abstract Position requestFirstPositionMessage();
    public abstract Position requestSecondPositionMessage();
    public abstract Piece requestToChoosePiece(ColorEnum c);
    public abstract void showBoard(Board b);
    public abstract void showPiecesTaken(ArrayList<Piece> b_p_t, ArrayList<Piece> w_p_t);
    public abstract void cleanScreen();
    public abstract void insertVoidLine(int n);
}
