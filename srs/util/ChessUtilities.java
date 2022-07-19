package srs.util;

import java.util.ArrayList;

import srs.Game;
import srs.pieces.Piece;
import srs.util.enums.ColorEnum;
import srs.util.enums.PieceEnum;

public class ChessUtilities {

    private static ChessUtilities instance;
    
    private ChessUtilities() {

    }

    public static ChessUtilities getInstance() {
        if (instance == null)
            instance = new ChessUtilities();
        return instance;
    }

    /*
     * retorna la posicion del rey del jugador que fue pasado por argumento del metodo
     */
    public static Position searchKing(ColorEnum player) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                Piece piece = Game.getBoard().getPiece(new Position(i, j));
                if (piece != null)
                    if (piece.getNameOfPiece().equals(PieceEnum.KING) && piece.getColorOfPiece().equals(player))
                        return new Position(i, j);
            }
        return null;
    }

    /*
     * retorna un booleano indicando si el rey indicado por parametro se encuentra en jaque
     */
    public static boolean isCheck() {
        return isAttacked(searchKing(Game.getPlayer()));
    }

    /*
     * se le pasa una posicion que debe ser la del rey, y retorna un arraylist con las posiciones que no se ven atacadas alrrededor del rey
     */
    public static ArrayList<Position> notAttackedPositionsInCheck(Position position) {
        ArrayList<Position> output = new ArrayList<>();
        ArrayList<Position> aroundPositions = Game.getBoard().getPiece(position).possibleMovements(position);
        for (Position positionIterator : aroundPositions)
            if (!isAttacked(positionIterator))
                output.add(positionIterator);
        return output;
    }

    /*
     * analiza si el rey se ve acorralado, viendo si en caso de haber jaque, el rey tiene movimientos posibles,
     * incluso en el caso de que pueda llegar a comer una pieza rival
     */
    public static boolean isCheckmate() {
        if (isCheck()) {
            ArrayList<Position> tentativeMovements = notAttackedPositionsInCheck(searchKing(Game.getPlayer()));
            ArrayList<Position> toRemove = new ArrayList<>();
            for (Position positionIterator : tentativeMovements)
                if (Game.getBoard().getPiece(positionIterator) != null)
                    if (Game.getBoard().getPiece(positionIterator).getColorOfPiece().equals(Game.getPlayer()))
                        toRemove.add(positionIterator);
                    else
                        if (isAttacked(positionIterator))
                            toRemove.add(positionIterator);
            tentativeMovements.removeAll(toRemove);
            if (tentativeMovements.size() == 0)
                return true;
        }
        return false;
    }

    /*
     * busca si existe un peon que haya completado todo su recorrido y retorna la posicion
     */
    public static Position isPawnPromotion() {
        for (int i = 0; i < 8; i++) {
            if (Game.getPlayer().equals(ColorEnum.BLACK))
                if (Game.getBoard().getPiece(new Position(i, 7)) != null)
                    if (Game.getBoard().getPiece(new Position(i, 7)).getNameOfPiece().equals(PieceEnum.PAWN))
                        return  new Position (i, 7);
            if (Game.getPlayer().equals(ColorEnum.WHITE))
                if (Game.getBoard().getPiece(new Position(i, 0)) != null)
                    if (Game.getBoard().getPiece(new Position(i, 0)).getNameOfPiece().equals(PieceEnum.PAWN))
                        return new Position(i, 0);
        }
        return null;
    }

    /*
     * se le pasa una posicion por argumento y devuelve un booleano en el caso de
     * que esa posicion se vea atacada por alguna pieza enemiga
     */
    public static boolean isAttacked(Position position) {
        ArrayList<Position> opponentPositions = new ArrayList<>();
        for (int i = 0; i < 8; i++) // cargo todas las piezas rivales del tablero
            for (int j = 0; j < 8; j++)
                if (Game.getBoard().getPiece(new Position(i, j)) != null)
                    if (Game.getBoard().getPiece(new Position(i, j)).getColorOfPiece().equals(Game.getOpponent()))
                        opponentPositions.add(new Position(i, j));
        for (Position opponentPositionIterator : opponentPositions) // analizo los movimientos de todas las fichas enemigas recopiladas y debo corroborar si atacan las posiciones pasadas por parametro
            if (ValidateMovement.isValidMovement(opponentPositionIterator, position))
                return true;
        return false;
    }

    /*
     * se le pasa un arraylist de posiciones por argumento y devuelve un booleano en el caso de
     * que alguna de esas posiciones se vea atacada po alguna pieza enemiga
     */
    public static boolean isAttacked(ArrayList<Position> positions) {
        ArrayList<Position> opponentPositions = new ArrayList<>();
        for (int i = 0; i < 8; i++) // cargo todas las piezas rivales del tablero
            for (int j = 0; j < 8; j++)
                if (Game.getBoard().getPiece(new Position(i, j)) != null)
                    if (Game.getBoard().getPiece(new Position(i, j)).getColorOfPiece().equals(Game.getOpponent()))
                        opponentPositions.add(new Position(i, j));
        for (Position positionIterator : positions) // analizo los movimientos de todas las fichas enemigas recopiladas y debo corroborar si atacan las posiciones pasadas por parametro
            for (Position opponentPositionIterator : opponentPositions)
                if (ValidateMovement.isValidMovement(opponentPositionIterator, positionIterator))
                    return true;
        return false;
    }
}
