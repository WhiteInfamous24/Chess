package srs.userinterface.impl;

import srs.pieces.Piece;
import srs.userinterface.UserInterface;
import srs.util.Position;
import srs.util.enums.InputRequestEnum;

public class UserInterfaceWindows implements UserInterface {
    
    private static String userInterfaceType;

    public UserInterfaceWindows() {
        userInterfaceType = "WINDOWS";
    }
    
    @Override
    public InputRequestEnum inputRequest() {
        return null; // IMPLEMENT
    }

    @Override
    public void mainMenu() { }

    @Override
    public void userInterfaceSelectionMenu() { }

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
        return null; // IMPLEMENT
    }

    @Override
    public Position secondPositionRequestMessage() {
        return null; // IMPLEMENT
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

    // GETTERS & SETTERS

    @Override
    public String getUserInterfaceType() {
        return userInterfaceType;
    }
}
