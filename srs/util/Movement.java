package srs.util;

import srs.Game;
import srs.pieces.Piece;
import srs.util.enums.ActionEnum;
import srs.util.enums.ColorEnum;

public class Movement {
    
    private Piece piece;
    private Position positionOne;
    private Position positionTwo;
    private ActionEnum action;
    private ColorEnum player;
    
    public Movement(Position positionOne, Position positionTwo, ActionEnum action) {
        this.piece = Game.getBoard().getPiece(positionOne);
        this.positionOne = positionOne;
        this.positionTwo = positionTwo;
        this.action = action;
        this.player = Game.getPlayer();
    }

    // GETTERS & SETTERS

    public Piece getPiece() {
        return piece;
    }

    public Position getPositionOne() {
        return positionOne;
    }

    public Position getPositionTwo() {
        return positionTwo;
    }

    public ActionEnum getAction() {
        return action;
    }

    public ColorEnum getPlayer() {
        return player;
    }
}
