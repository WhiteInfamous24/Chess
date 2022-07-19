package srs;

import java.util.ArrayList;

import srs.pieces.Bishop;
import srs.pieces.King;
import srs.pieces.Knight;
import srs.pieces.Pawn;
import srs.pieces.Piece;
import srs.pieces.Queen;
import srs.pieces.Rook;
import srs.util.Board;
import srs.util.Movement;
import srs.util.Position;
import srs.util.enums.ActionEnum;
import srs.util.enums.CastlingCornerEnum;
import srs.util.enums.ColorEnum;
import srs.util.enums.PieceEnum;

public class Game {

    private static Game instance;
    private static Board board;
    private static ColorEnum player;
    private static ArrayList<Piece> blackPiecesTaken;
    private static ArrayList<Piece> whitePiecesTaken;
    private static ArrayList<Movement> movements;

    /*
     * constructor de la clase
     */
    private Game() {
        board = new Board();
        player = ColorEnum.WHITE;
        blackPiecesTaken = new ArrayList<>();
        whitePiecesTaken = new ArrayList<>();
        movements = new ArrayList<>();
    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    /*
     * inicializa todas las piezas de ajedrez en sus posiciones
     */
    public static void initializePieces() {
        for (int i = 0; i < 8; i++) {
            board.setPiece(new Pawn(ColorEnum.BLACK), new Position(i, 1));
            board.setPiece(new Pawn(ColorEnum.WHITE), new Position(i, 6));
        }
        board.setPiece(new Rook(ColorEnum.BLACK), new Position(0, 0));
        board.setPiece(new Rook(ColorEnum.BLACK), new Position(7, 0));
        board.setPiece(new Rook(ColorEnum.WHITE), new Position(0, 7));
        board.setPiece(new Rook(ColorEnum.WHITE), new Position(7, 7));
        board.setPiece(new Knight(ColorEnum.BLACK), new Position(1, 0));
        board.setPiece(new Knight(ColorEnum.BLACK), new Position(6, 0));
        board.setPiece(new Knight(ColorEnum.WHITE), new Position(1, 7));
        board.setPiece(new Knight(ColorEnum.WHITE), new Position(6, 7));
        board.setPiece(new Bishop(ColorEnum.BLACK), new Position(2, 0));
        board.setPiece(new Bishop(ColorEnum.BLACK), new Position(5, 0));
        board.setPiece(new Bishop(ColorEnum.WHITE), new Position(2, 7));
        board.setPiece(new Bishop(ColorEnum.WHITE), new Position(5, 7));
        board.setPiece(new Queen(ColorEnum.BLACK), new Position(3, 0));
        board.setPiece(new Queen(ColorEnum.WHITE), new Position(3, 7));
        board.setPiece(new King(ColorEnum.BLACK), new Position(4, 0));
        board.setPiece(new King(ColorEnum.WHITE), new Position(4, 7));
    }

    /*
     * alterna entre jugador de color "white" y "black"
     */
    public static void changePlayer() {
        if (player.equals(ColorEnum.WHITE))
            player = ColorEnum.BLACK;
        else
            player = ColorEnum.WHITE;
    }

    /*
     * se encarga de realizar el movimiento que se indica con el parametro "action" entre las 2 posiciones
     * que se pasan por parametro de la funcion, dando por supuesto que se cumplen previamente 
     * las condiciones necesarias para realizar el movimiento
     */
    public static void movePiece(Position positionOne, Position positionTwo, ActionEnum action) {
        switch (action) {
            case MOVE -> {

            }
            case PIECE_TAKING -> {

            }
            case CASTLING -> {

            }
            case PAWN_PROMOTION -> {

            }
            default -> {
                // VOID
            }
        }
    }

    /*
     * deshace el ultimo movimiento que se ejecuto en la partida
     */
    public static void undoLastMove() {
        Movement movement = movements.get(movements.size()-1);
        board.undoMovement(movement.getPositionTwo(), movement.getPositionOne(), movement.getWasMovedOld());
        movements.remove(movements.size()-1);
    }

    /*
     * se encarga de realizar el enroque de torre y rey entre las posiciones que se pasan por parametro del metodo
     * se da por supuesto que las posiciones pasadas por parametro son una torre y un rey,
     * y que se cumplen todas las condiciones previas para realizar el enroque
     */
    public static void performCastling(Position positionOne, Position positionTwo, CastlingCornerEnum corner) {
        if (board.getPiece(positionOne).getNameOfPiece().equals(PieceEnum.ROOK)) {
            Position positionAux = positionOne;
            positionOne = positionTwo;
            positionTwo = positionAux;
        }
        Piece king = board.getPiece(positionOne);
        Piece rook = board.getPiece(positionTwo);
        switch (corner) {
            case UL -> {
                movements.add(new Movement(positionTwo, new Position(2, 0), rook.getWasMoved()));
                board.movePiece(positionTwo, new Position(2, 0));
                movements.add(new Movement(positionOne, new Position(1, 0), king.getWasMoved()));
                board.movePiece(positionOne, new Position(1, 0));
            }
            case BL -> {
                movements.add(new Movement(positionTwo, new Position(3, 7), rook.getWasMoved()));
                board.movePiece(positionTwo, new Position(3, 7));
                movements.add(new Movement(positionOne, new Position(2, 7), king.getWasMoved()));
                board.movePiece(positionOne, new Position(2, 7));
            }
            case UR -> {
                movements.add(new Movement(positionTwo, new Position(4, 0), rook.getWasMoved()));
                board.movePiece(positionTwo, new Position(4, 0));
                movements.add(new Movement(positionOne, new Position(5, 0), king.getWasMoved()));
                board.movePiece(positionOne, new Position(5, 0));
            }
            case BR -> {
                movements.add(new Movement(positionTwo, new Position(5, 7), rook.getWasMoved()));
                board.movePiece(positionTwo, new Position(5, 7));
                movements.add(new Movement(positionOne, new Position(6, 7), king.getWasMoved()));
                board.movePiece(positionOne, new Position(6, 7));
            }
            default -> {
                // VOID
            }
        }
    }

    // GETTERS & SETTERS

    public static Board getBoard() {
        return board;
    }

    public static ColorEnum getPlayer() {
        return player;
    }

    public static ColorEnum getOpponent() {
        if (player.equals(ColorEnum.BLACK))
            return ColorEnum.WHITE;
        return ColorEnum.BLACK;
    }

    public static ArrayList<Piece> getBlackPiecesTaken() {
        return blackPiecesTaken;
    }

    public static ArrayList<Piece> getWhitePiecesTaken() {
        return whitePiecesTaken;
    }

    public static ArrayList<Movement> getMovements() {
        return movements;
    }
}
