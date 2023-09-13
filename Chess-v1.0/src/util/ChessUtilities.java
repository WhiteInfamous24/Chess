package srs.util;

import java.util.ArrayList;

import srs.Game;
import srs.pieces.Piece;
import srs.util.enums.ActionEnum;
import srs.util.enums.ColorEnum;
import srs.util.enums.PieceEnum;

public class ChessUtilities {

    private static ChessUtilities instance;
    
    private ChessUtilities() { } // VOID

    public static ChessUtilities getInstance() {
        if (instance == null)
            instance = new ChessUtilities();
        return instance;
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
    public static boolean isPawnPromotion() {
        for (int i = 0; i < 8; i++) {
            if (Game.getPlayer().equals(ColorEnum.BLACK))
                if (Game.getBoard().getPiece(new Position(i, 7)) != null)
                    if (Game.getBoard().getPiece(new Position(i, 7)).getNameOfPiece().equals(PieceEnum.PAWN))
                        return true;
            if (Game.getPlayer().equals(ColorEnum.WHITE))
                if (Game.getBoard().getPiece(new Position(i, 0)) != null)
                    if (Game.getBoard().getPiece(new Position(i, 0)).getNameOfPiece().equals(PieceEnum.PAWN))
                        return true;
        }
        return false;
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
            if (isValidMovement(opponentPositionIterator, position))
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
                if (isValidMovement(opponentPositionIterator, positionIterator))
                    return true;
        return false;
    }

    /*
     * analiza si el movimiento que se desea realizar cumple con los posibles requisitos
     * -en caso de que la posicion final sea valida para tomar una pieza rival, y efectivamente haya una pieza para poder tomar,
     * se procede a verificar que en la trayectoria no haya obstaculos y si cumple, se captura la pieza
     * -en caso de que la posicion final se encuentre vacia, se procede a verificar que no haya obstaculos en el camino
     */
    public static boolean isValidMovement(Position positionOne, Position positionTwo) {
        ArrayList<Position> possible_movements = Game.getBoard().getPiece(positionOne).possibleMovements(positionOne);
        ArrayList<Position> possible_takes = Game.getBoard().getPiece(positionOne).possibleTakes(positionOne);
        if (Utilities.searchPositionInArray(possible_takes, positionTwo) && Game.getBoard().getPiece(positionTwo) != null) // veo si la posicion final se encuentra en una posicion de toma valido de la pieza
            return hasCollision(positionOne, positionTwo);
        else if (Utilities.searchPositionInArray(possible_movements, positionTwo) && Game.getBoard().getPiece(positionTwo) == null) // veo si la posicion final se encuentra en una posicion de movimiento valido de la pieza
            return hasCollision(positionOne, positionTwo);
        else
            return false;
    }

    /*
     * verifica que, si la ficha realiza una trayectoria larga, no colisione con otras piezas en el trayecto,
     * a excepcion de la posicion final donde puede llegar a haber una pieza cualquiera
     */
    public static boolean hasCollision(Position positionOne, Position positionTwo) {
        if (Game.getBoard().getPiece(positionOne).getLongMovement()) { // veo si la pieza realiza movimientos de trayectoria
            boolean noObstruction = true;
            int X_ONE = positionOne.getX();
            int Y_ONE = positionOne.getY();
            int X_TWO = positionTwo.getX();
            int Y_TWO = positionTwo.getY();
            int MAX;
            if (X_TWO == X_ONE) { // verifico si me muevo unicamente sobre el eje Y
                if (Y_TWO > Y_ONE) { // analizo movimiento para +Y
                    MAX = Y_TWO-Y_ONE; // seteo el limite de iteraciones para no salirme del tablero para +Y
                    for (int i = 1; i < MAX; i++) { // analizo movimiento para +Y
                        if (Game.getBoard().getPiece(new Position(X_ONE, Y_ONE+i)) != null && Y_ONE+i != Y_TWO)
                            noObstruction = false;
                        if (Y_ONE+i == Y_TWO) // analizo si llego a la posicion final y se retorna si hay obstruccion
                            return noObstruction;
                    }
                }
                else { // analizo movimiento para -Y
                    MAX = Y_ONE-Y_TWO; // seteo el limite de iteraciones para no salirme del tablero para -Y
                    for (int i = 1; i < MAX; i++) {
                        if (Game.getBoard().getPiece(new Position(X_ONE, Y_ONE-i)) != null && Y_ONE-i != Y_TWO)
                            noObstruction = false;
                        if (Y_ONE-i == Y_TWO) // analizo si llego a la posicion final y se retorna si hay obstruccion
                            return noObstruction;
                    }
                }
            }
            else if (Y_TWO == Y_ONE) { // verifico si me muevo unicamente sobre el eje X
                if (X_TWO > X_ONE) { // analizo movimiento para +X
                    MAX = X_TWO-X_ONE; // seteo el limite de iteraciones para no salirme del tablero para +X
                    for (int i = 1; i < MAX; i++) { // analizo movimiento para +X
                        if (Game.getBoard().getPiece(new Position(X_ONE+i, Y_ONE)) != null && X_ONE+i != X_TWO)
                            noObstruction = false;
                        if (X_ONE+i == X_TWO) // analizo si llego a la posicion final y se retorna si hay obstruccion
                            return noObstruction;
                    }
                }
                else { // analizo movimiento para -X
                    MAX = X_ONE-X_TWO; // seteo el limite de iteraciones para no salirme del tablero para -X
                    for (int i = 1; i < MAX; i++) {
                        if (Game.getBoard().getPiece(new Position(X_ONE-i, Y_ONE)) != null && X_ONE-i != X_TWO)
                            noObstruction = false;
                        if (X_ONE-i == X_TWO) // analizo si llego a la posicion final y se retorna si hay obstruccion
                            return noObstruction;
                    }
                }
            }
            else if (X_TWO > X_ONE && Y_TWO > Y_ONE) { // verifico si me muevo unicamente en direccion +X+Y
                if (8-X_ONE < 8-Y_ONE) // seteo el limite de iteraciones para no salirme del tablero buscando el eje mas chico
                    MAX = X_TWO-X_ONE;
                else
                    MAX = Y_TWO-Y_ONE;
                for (int i = 1; i < MAX; i++) { // analizo movimiento
                    if (Game.getBoard().getPiece(new Position(X_ONE+i, Y_ONE+i)) != null && Y_ONE+i != Y_TWO)
                        noObstruction = false;
                    if (Y_ONE+i == Y_TWO) // analizo si llego a la posicion final y se retorna si hay obstruccion
                        return noObstruction;
                }
            }
            else if (X_TWO > X_ONE && Y_TWO < Y_ONE) { // verifico si me muevo unicamente en el eje +X-Y
                if (8-X_ONE < Y_ONE+1) // seteo el limite de iteraciones para no salirme del tablero buscando el eje mas chico
                    MAX = X_TWO-X_ONE;
                else
                    MAX = Y_ONE-Y_TWO;
                for (int i = 1; i < MAX; i++) { // analizo movimiento
                    if (Game.getBoard().getPiece(new Position(X_ONE+i, Y_ONE-i)) != null && Y_ONE-i != Y_TWO)
                        noObstruction = false;
                    if (Y_ONE-i == Y_TWO) // analizo si llego a la posicion final y se retorna si hay obstruccion
                        return noObstruction;
                }
            }
            else if (X_TWO < X_ONE && Y_TWO > Y_ONE) { // verifico si me muevo unicamente en el eje -X+Y
                if (8-X_ONE < 8-Y_ONE) // seteo el limite de iteraciones para no salirme del tablero buscando el eje mas chico
                    MAX = X_ONE-X_TWO;
                else
                    MAX = Y_TWO-Y_ONE;
                for (int i = 1; i < MAX; i++) { // analizo movimiento
                    if (Game.getBoard().getPiece(new Position(X_ONE-i, Y_ONE+i)) != null && Y_ONE+i != Y_TWO)
                        noObstruction = false;
                    if (Y_ONE+i == Y_TWO) // analizo si llego a la posicion final y se retorna si hay obstruccion
                        return noObstruction;
                }
            }
            else if (X_TWO < X_ONE && Y_TWO < Y_ONE) { // verifico si me muevo unicamente en el eje -X-Y
                if (8-X_ONE < 8-Y_ONE) // seteo el limite de iteraciones para no salirme del tablero buscando el eje mas chico
                    MAX = X_ONE-X_TWO;
                else
                    MAX = Y_ONE-Y_TWO;
                for (int i = 1; i < MAX; i++) { // analizo movimiento
                    if (Game.getBoard().getPiece(new Position(X_ONE-i, Y_ONE-i)) != null && Y_ONE-i != Y_TWO)
                        noObstruction = false;
                    if (Y_ONE-i == Y_TWO) // analizo si llego a la posicion final y se retorna si hay obstruccion
                        return noObstruction;
                }
            }
            return noObstruction;
        }
        else
            return true;
    }

    /*
     * verifica si hay una pieza en la posicion final, donde si esta es igual a la del jugador o es una casilla nula, no habra opcion de comer pieza,
     * y en caso de ser una pieza rival, se analizaran los movimientos predefinidos de cada pieza para ver si puede comer
     */
    public static boolean couldTakeAPiece(Position positionOne, Position positionTwo) {
        if (Game.getBoard().getPiece(positionTwo) != null)
            if (!Game.getBoard().getPiece(positionTwo).getColorOfPiece().equals(Game.getPlayer()))
                if (Utilities.searchPositionInArray(Game.getBoard().getPiece(positionOne).possibleTakes(positionOne), positionTwo))
                    return isValidMovement(positionOne, positionTwo);
        return false;
    }

    /*
     * -ni el rey ni la torre deben haber sido movidas,
     * -el rey no puede estar en jaque,
     * -ninguna casilla de la trayectoria que recorrera el rey puede estar atacada,
     * -no deben haber piezas entre el rey y la torre,
     * el enrroque es moviendo el rey 2 casillas hacia la derecha o izquierda, y la torre, del lado al que se movio, salta sobre el rey
     */
    public static ActionEnum couldCastling(Position positionOne, Position positionTwo) {
        if (Game.getBoard().getPiece(positionOne).getNameOfPiece().equals(PieceEnum.ROOK)) {
            Position positionAux = positionOne;
            positionOne = positionTwo;
            positionTwo = positionAux;
        }
        Piece pieceOne = Game.getBoard().getPiece(positionOne); // KING
        Piece pieceTwo = Game.getBoard().getPiece(positionTwo); // ROOK
        if (pieceTwo.getColorOfPiece() == Game.getPlayer()) {
            if (pieceOne.getNameOfPiece().equals(PieceEnum.KING) && pieceTwo.getNameOfPiece().equals(PieceEnum.ROOK))
                if (hasCollision(positionTwo, positionOne))
                    if (!pieceOne.getWasMoved() && !pieceTwo.getWasMoved()) {
                        ArrayList<Position> positions = new ArrayList<>();
                        ActionEnum output;
                        if (positionTwo.getX() == 0)
                            if (Game.getPlayer().equals(ColorEnum.BLACK)) {
                                positions.add(new Position(2, 0));
                                positions.add(new Position(3, 0));
                                positions.add(new Position(4, 0));
                                output = ActionEnum.CASTLING_UL; // upper-left
                            }
                            else {
                                positions.add(new Position(2, 7));
                                positions.add(new Position(3, 7));
                                positions.add(new Position(4, 7));
                                output = ActionEnum.CASTLING_BL; // bottom-left
                            }
                        else
                            if (Game.getPlayer().equals(ColorEnum.BLACK)) {
                                positions.add(new Position(4, 0));
                                positions.add(new Position(5, 0));
                                positions.add(new Position(6, 0));
                                output = ActionEnum.CASTLING_UR; // upper-right
                            }
                            else {
                                positions.add(new Position(4, 7));
                                positions.add(new Position(5, 7));
                                positions.add(new Position(6, 7));
                                output = ActionEnum.CASTLING_BR; // bottom-right
                            }
                        if (!ChessUtilities.isAttacked(positions));
                            return output;
                    }
        }
        return null;
    }
}
