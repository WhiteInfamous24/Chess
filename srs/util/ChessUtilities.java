package srs.util;

import java.util.ArrayList;

import srs.pieces.Piece;
import srs.userinterface.UserInterface;
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
    public Position searchKing(Board board, ColorEnum color) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(new Position(i, j));
                if (piece != null)
                    if (piece.getNameOfPiece().equals(PieceEnum.KING) && piece.getColorOfPiece().equals(color))
                        return new Position(i, j);
            }
        return null;
    }

    /*
     * retorna un booleano indicando si el rey indicado por parametro se encuentra en jaque
     */
    public boolean thereIsCheck(Board board, ColorEnum player) {
        return itsAttacked(board, player, searchKing(board, player));
    }

    /*
     * se le pasa una posicion que debe ser la del rey, y retorna un arraylist con las posiciones que no se ven atacadas alrrededor del rey
     */
    public ArrayList<Position> notAttackedPositionsInCheck(Board board, ColorEnum player, Position position) {
        ArrayList<Position> output = new ArrayList<>();
        ArrayList<Position> aroundPositions = board.getPiece(position).possibleMovements(position);
        for (Position positionIterator : aroundPositions)
            if (!itsAttacked(board, player, positionIterator))
                output.add(positionIterator);
        return output;
    }

    /*
     * analiza si el rey se ve acorralado, viendo si en caso de haber jaque, el rey tiene movimientos posibles,
     * incluso en el caso de que pueda llegar a comer una pieza rival
     */
    public boolean thereIsCheckmate(Board board, ColorEnum player) {
        if (thereIsCheck(board, player)) {
            ArrayList<Position> tentativeMovements = notAttackedPositionsInCheck(board, player, searchKing(board, player));
            ArrayList<Position> toRemove = new ArrayList<>();
            for (Position positionIterator : tentativeMovements)
                if (board.getPiece(positionIterator) != null)
                    if (board.getPiece(positionIterator).getColorOfPiece().equals(player))
                        toRemove.add(positionIterator);
                    else
                        if (itsAttacked(board, player, positionIterator))
                            toRemove.add(positionIterator);
            tentativeMovements.removeAll(toRemove);
            if (tentativeMovements.size() == 0)
                return true;
        }
        return false;
    }

    /*
     * analiza si existen peones que hayan completado todo su recorrido y, en caso de haber,
     * se pide por interfaz de usuario que se elija en que pieza desea que se convierta,
     * para luego ser reemplazada en el tablero
     */
    public void analizePawnPromotion(Board board, ColorEnum player, UserInterface userInterface) {
        Position position = null;
        for (int i = 0; i < 8; i++) {
            if (player.equals(ColorEnum.BLACK))
                if (board.getPiece(new Position(i, 7)) != null)
                    if (board.getPiece(new Position(i, 7)).getNameOfPiece().equals(PieceEnum.PAWN))
                        position = new Position (i, 7);
            if (player.equals(ColorEnum.WHITE))
                if (board.getPiece(new Position(i, 0)) != null)
                    if (board.getPiece(new Position(i, 0)).getNameOfPiece().equals(PieceEnum.PAWN))
                        position = new Position(i, 0);
        }
        if (position != null)
            board.setPiece(userInterface.requestToChoosePiece(player), position);
    }

    /*
     * se le pasa una posicion por argumento y devuelve un booleano en el caso de
     * que esa posicion se vea atacada por alguna pieza enemiga
     */
    public boolean itsAttacked(Board board, ColorEnum player, Position position) {
        ColorEnum opponent;
        ArrayList<Position> opponentPositions = new ArrayList<>();
        if (player.equals(ColorEnum.BLACK))
            opponent = ColorEnum.WHITE;
        else
            opponent = ColorEnum.BLACK;
        for (int i = 0; i < 8; i++) // cargo todas las piezas rivales del tablero
            for (int j = 0; j < 8; j++)
                if (board.getPiece(new Position(i, j)) != null)
                    if (board.getPiece(new Position(i, j)).getColorOfPiece().equals(opponent))
                        opponentPositions.add(new Position(i, j));
        for (Position opponentPositionIterator : opponentPositions) // analizo los movimientos de todas las fichas enemigas recopiladas y debo corroborar si atacan las posiciones pasadas por parametro
            if (ValidateMovement.getInstance().isValidMovement(board, opponentPositionIterator, position))
                return true;
        return false;
    }

    /*
     * se le pasa un arraylist de posiciones por argumento y devuelve un booleano en el caso de
     * que alguna de esas posiciones se vea atacada po alguna pieza enemiga
     */
    public boolean itsAttacked(Board board, ColorEnum player, ArrayList<Position> positions) {
        ColorEnum opponent;
        ArrayList<Position> opponentPositions = new ArrayList<>();
        if (player.equals(ColorEnum.BLACK))
            opponent = ColorEnum.WHITE;
        else
            opponent = ColorEnum.BLACK;
        for (int i = 0; i < 8; i++) // cargo todas las piezas rivales del tablero
            for (int j = 0; j < 8; j++)
                if (board.getPiece(new Position(i, j)) != null)
                    if (board.getPiece(new Position(i, j)).getColorOfPiece().equals(opponent))
                        opponentPositions.add(new Position(i, j));
        for (Position positionIterator : positions) // analizo los movimientos de todas las fichas enemigas recopiladas y debo corroborar si atacan las posiciones pasadas por parametro
            for (Position opponentPositionIterator : opponentPositions)
                if (ValidateMovement.getInstance().isValidMovement(board, opponentPositionIterator, positionIterator))
                    return true;
        return false;
    }
}
