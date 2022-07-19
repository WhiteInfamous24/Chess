package srs.util;

import java.util.ArrayList;

import srs.Game;
import srs.pieces.Piece;
import srs.util.enums.CastlingCornerEnum;
import srs.util.enums.ColorEnum;
import srs.util.enums.PieceEnum;

public class ValidateMovement {
    
    private static ValidateMovement instance;
    
    private ValidateMovement() {

    }

    public static ValidateMovement getInstance() {
        if (instance == null)
            instance = new ValidateMovement();
        return instance;
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
    public static CastlingCornerEnum couldCastling(Position positionOne, Position positionTwo) {
        CastlingCornerEnum output = null;
        Piece pieceOne = Game.getBoard().getPiece(positionOne);
        Piece pieceTwo = Game.getBoard().getPiece(positionTwo);
        if (pieceOne.getNameOfPiece().equals(PieceEnum.ROOK)) {
            Position aux_position = positionOne;
            positionOne = positionTwo;
            positionTwo = aux_position;
            Piece pieceAux = pieceOne;
            pieceOne = pieceTwo;
            pieceTwo = pieceAux;
        }
        if (pieceTwo.getColorOfPiece() == Game.getPlayer()) {
            if (pieceOne.getNameOfPiece().equals(PieceEnum.KING) && pieceTwo.getNameOfPiece().equals(PieceEnum.ROOK))
                if (hasCollision(positionTwo, positionOne))
                    if (!pieceOne.getWasMoved() && !pieceTwo.getWasMoved()) {
                        ArrayList<Position> positions = new ArrayList<>();
                        if (positionTwo.getX() == 0)
                            if (Game.getPlayer().equals(ColorEnum.BLACK)) {
                                positions.add(new Position(1, 0));
                                positions.add(new Position(2, 0));
                                positions.add(new Position(3, 0));
                                output = CastlingCornerEnum.UL; // upper left
                            }
                            else {
                                positions.add(new Position(2, 7));
                                positions.add(new Position(3, 7));
                                positions.add(new Position(4, 7));
                                output = CastlingCornerEnum.BL; // bottom left
                            }
                        else
                            if (Game.getPlayer().equals(ColorEnum.BLACK)) {
                                positions.add(new Position(3, 0));
                                positions.add(new Position(4, 0));
                                positions.add(new Position(5, 0));
                                output = CastlingCornerEnum.UR; // upper right
                            }
                            else {
                                positions.add(new Position(4, 7));
                                positions.add(new Position(5, 7));
                                positions.add(new Position(6, 7));
                                output = CastlingCornerEnum.BR; // bottom right
                            }
                        if (!ChessUtilities.isAttacked(positions));
                            return output;
                    }
        }
        return output;
    }
}
