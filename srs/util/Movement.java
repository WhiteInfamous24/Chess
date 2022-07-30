package srs.util;

import srs.Game;
import srs.pieces.Piece;
import srs.util.enums.ActionEnum;
import srs.util.enums.ColorEnum;
import srs.util.enums.PieceEnum;

public class Movement {
    
    private Piece piece;
    private Piece pieceAux; // pieza secundaria (null, pieza comida, segunda pieza enrocada)
    private Position positionOne;
    private Position positionTwo;
    private ActionEnum action;
    private ColorEnum player;
    
    public Movement(Position positionOne, Position positionTwo, ActionEnum action) {
        this.piece = Game.getBoard().getPiece(positionOne);
        this.pieceAux = Game.getBoard().getPiece(positionTwo);
        this.positionOne = positionOne;
        this.positionTwo = positionTwo;
        this.action = action;
        this.player = Game.getPlayer();
        if (action != null)
            sortCastlingPositions();
    }

    private void sortCastlingPositions() {
        if (action.equals(ActionEnum.CASTLING_UL) || action.equals(ActionEnum.CASTLING_BL) || action.equals(ActionEnum.CASTLING_UR) || action.equals(ActionEnum.CASTLING_BR))
            if (Game.getBoard().getPiece(positionOne).getNameOfPiece().equals(PieceEnum.ROOK)) {
                Position positionAux = positionOne;
                positionOne = positionTwo;
                positionTwo = positionAux;
            }
    }

    // GETTERS & SETTERS

    public Piece getPiece() {
        return piece;
    }

    public Piece getPieceAux() {
        return pieceAux;
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
