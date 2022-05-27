package srs.userinterface;

import java.util.ArrayList;
import srs.Board;
import srs.enums.ColorEnum;
import srs.pieces.Piece;

public interface UserInterface {
    public abstract void playerTurnMessage(ColorEnum c);
    public abstract void invalidPositionMessage();
    public abstract void requestFirstPositionMessage();
    public abstract void requestSecondPositionMessage();
    public abstract void showBoard(Board b);
    public abstract void showPiecesTaken(ArrayList<Piece> b_p_t, ArrayList<Piece> w_p_t);
    public abstract void cleanScreen();
}
