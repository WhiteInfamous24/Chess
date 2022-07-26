package srs.userinterface;

import srs.pieces.Piece;
import srs.util.Position;

public interface UserInterface {
    
    public abstract void playerTurnMessage();
    public abstract void checkMessage();
    public abstract void winnerMessage();
    public abstract void invalidPositionMessage();
    public abstract void invalidMovementMessage();
    public abstract void invalidInputMessage();
    public abstract Position firstPositionRequestMessage();
    public abstract Position secondPositionRequestMessage();
    public abstract Piece choosePieceRequest();
    public abstract void showBoard();
    public abstract void showPiecesTaken();
    public abstract void cleanScreen();
    public abstract void insertVoidLine(int n);
}
