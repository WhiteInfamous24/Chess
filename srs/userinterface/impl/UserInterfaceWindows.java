package srs.userinterface.impl;

import java.util.ArrayList;

import srs.Game;
import srs.pieces.Piece;
import srs.userinterface.UserInterface;
import srs.util.Board;
import srs.util.Position;
import srs.util.factory.impl.PieceFactory;

public class UserInterfaceWindows implements UserInterface {
    
    public UserInterfaceWindows() { }
    
    @Override
    public void playerTurnMessage() { }

    @Override
    public void checkMessage() { }

    @Override
    public void winnerMessage() { }

    @Override
    public void invalidPositionMessage() { }

    @Override
    public void invalidMovementMessage() { }

    @Override
    public void invalidInputMessage() { }

    @Override
    public Position firstPositionRequestMessage() {
        return new Position(0, 0);
    }

    @Override
    public Position secondPositionRequestMessage() {
        return new Position(0, 0);
    }

    @Override
    public final Piece choosePieceRequest() {
        return null; // IMPLEMENT
    }

    @Override
    public void showBoard() { }

    @Override
    public void showPiecesTaken() { }

    @Override
    public void cleanScreen() { }

    @Override
    public void insertVoidLine(int n) { }
}
