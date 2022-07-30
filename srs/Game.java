package srs;

import java.util.ArrayList;

import srs.pieces.Piece;
import srs.util.Board;
import srs.util.Movement;
import srs.util.Position;
import srs.util.enums.ActionEnum;
import srs.util.enums.ColorEnum;
import srs.util.enums.PieceEnum;
import srs.util.factory.impl.PieceFactory;

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
        String[] blackPieces = { "ROOK_BLACK", "KNIGHT_BLACK", "BISHOP_BLACK", "QUEEN_BLACK", "KING_BLACK", "BISHOP_BLACK", "KNIGHT_BLACK", "ROOK_BLACK" };
        String[] whitePieces = { "ROOK_WHITE", "KNIGHT_WHITE", "BISHOP_WHITE", "QUEEN_WHITE", "KING_WHITE", "BISHOP_WHITE", "KNIGHT_WHITE", "ROOK_WHITE" };
        for (int i = 0; i < 8; i++) {
            board.setPiece(PieceFactory.getInstance().build("PAWN_BLACK"), new Position(i, 1));
            board.setPiece(PieceFactory.getInstance().build("PAWN_WHITE"), new Position(i, 6));
            board.setPiece(PieceFactory.getInstance().build(blackPieces[i]), new Position(i, 0));
            board.setPiece(PieceFactory.getInstance().build(whitePieces[i]), new Position(i, 7));
        }
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
        recordMovement(positionOne, positionTwo, action);
        switch (action) {
            case MOVE -> board.movePiece(positionOne, positionTwo);
            case PIECE_TAKING -> board.movePiece(positionOne, positionTwo);
            case CASTLING_UL -> performCastling(positionOne, positionTwo, ActionEnum.CASTLING_UL);
            case CASTLING_BL -> performCastling(positionOne, positionTwo, ActionEnum.CASTLING_BL);
            case CASTLING_UR -> performCastling(positionOne, positionTwo, ActionEnum.CASTLING_UR);
            case CASTLING_BR -> performCastling(positionOne, positionTwo, ActionEnum.CASTLING_BR);
            default -> { } // VOID
        }
    }

    public static void pawnPromotion(Piece piece, Position position) {
        ActionEnum action;
        switch (piece.getNameOfPiece()) {
            case QUEEN -> action = ActionEnum.PAWN_PROMOTION_QUEEN;
            case BISHOP -> action = ActionEnum.PAWN_PROMOTION_BISHOP;
            case KNIGHT -> action = ActionEnum.PAWN_PROMOTION_KNIGHT;
            case ROOK -> action = ActionEnum.PAWN_PROMOTION_ROOK;
            default -> action = null;
        }
        recordMovement(position, null, action);
        board.setPiece(piece, position);
    }

    /*
     * deshace el ultimo movimiento que se ejecuto en la partida
     */
    public static void undoLastMove() {
        Movement lastMovement = movements.get(movements.size()-1);
        boolean isPawnPromotion = false;
        switch (lastMovement.getAction()) {
            case MOVE -> board.setPiece(lastMovement.getPiece(), lastMovement.getPositionOne());
            case PIECE_TAKING -> {
                board.setPiece(lastMovement.getPiece(), lastMovement.getPositionOne());
                board.setPiece(lastMovement.getPieceAux(), lastMovement.getPositionTwo());
            }
            case CASTLING_UL -> {
                board.setPiece(lastMovement.getPiece(), new Position(4, 0));
                board.setPiece(lastMovement.getPieceAux(), new Position(0, 0));
            }
            case CASTLING_BL -> {
                board.setPiece(lastMovement.getPiece(), new Position(4, 7));
                board.setPiece(lastMovement.getPieceAux(), new Position(0, 7));
            }
            case CASTLING_UR -> {
                board.setPiece(lastMovement.getPiece(), new Position(4, 0));
                board.setPiece(lastMovement.getPieceAux(), new Position(7, 0));
            }
            case CASTLING_BR -> {
                board.setPiece(lastMovement.getPiece(), new Position(4, 7));
                board.setPiece(lastMovement.getPieceAux(), new Position(7, 7));
            }
            case PAWN_PROMOTION_QUEEN -> {
                board.setPiece(lastMovement.getPiece(), lastMovement.getPositionOne());
                isPawnPromotion = true;
            }
            case PAWN_PROMOTION_BISHOP -> {
                board.setPiece(lastMovement.getPiece(), lastMovement.getPositionOne());
                isPawnPromotion = true;
            }
            case PAWN_PROMOTION_KNIGHT -> {
                board.setPiece(lastMovement.getPiece(), lastMovement.getPositionOne());
                isPawnPromotion = true;
            }
            case PAWN_PROMOTION_ROOK -> {
                board.setPiece(lastMovement.getPiece(), lastMovement.getPositionOne());
                isPawnPromotion = true;
            }
            default -> { } // VOID
        }
        movements.remove(movements.size()-1);
        if (isPawnPromotion)
            undoLastMove();
    }

    private static void recordMovement(Position positionOne, Position positionTwo, ActionEnum action) {
        switch (action) {
            case MOVE -> movements.add(new Movement(positionOne, positionTwo, action));
            case PIECE_TAKING -> {
                movements.add(new Movement(positionOne, positionTwo, action));
                if (board.getPiece(positionOne).getColorOfPiece().equals(ColorEnum.BLACK))
                    whitePiecesTaken.add(board.getPiece(positionTwo));
                else
                    blackPiecesTaken.add(board.getPiece(positionTwo));
            }
            case CASTLING_UL -> movements.add(new Movement(new Position(4, 0), new Position(2, 0), action));
            case CASTLING_BL -> movements.add(new Movement(new Position(4, 7), new Position(2, 7), action));
            case CASTLING_UR -> movements.add(new Movement(new Position(4, 0), new Position(6, 0), action));
            case CASTLING_BR -> movements.add(new Movement(new Position(4, 7), new Position(6, 7), action));
            case PAWN_PROMOTION_QUEEN -> movements.add(new Movement(positionOne, null, action));
            case PAWN_PROMOTION_BISHOP -> movements.add(new Movement(positionOne, null, action));
            case PAWN_PROMOTION_KNIGHT -> movements.add(new Movement(positionOne, null, action));
            case PAWN_PROMOTION_ROOK -> movements.add(new Movement(positionOne, null, action));
            default -> { } // VOID
        }
    }

    /*
     * se encarga de realizar el enroque de torre y rey entre las posiciones que se pasan por parametro del metodo
     * se da por supuesto que las posiciones pasadas por parametro son una torre y un rey,
     * y que se cumplen todas las condiciones previas para realizar el enroque
     */
    public static void performCastling(Position positionOne, Position positionTwo, ActionEnum corner) {
        if (board.getPiece(positionOne).getNameOfPiece().equals(PieceEnum.ROOK)) {
            Position positionAux = positionOne;
            positionOne = positionTwo;
            positionTwo = positionAux;
        }
        switch (corner) {
            case CASTLING_UL -> {
                board.movePiece(positionTwo, new Position(3, 0));
                board.movePiece(positionOne, new Position(2, 0));
            }
            case CASTLING_BL -> {
                board.movePiece(positionTwo, new Position(3, 7));
                board.movePiece(positionOne, new Position(2, 7));
            }
            case CASTLING_UR -> {
                board.movePiece(positionTwo, new Position(5, 0));
                board.movePiece(positionOne, new Position(6, 0));
            }
            case CASTLING_BR -> {
                board.movePiece(positionTwo, new Position(5, 7));
                board.movePiece(positionOne, new Position(6, 7));
            }
            default -> { } // VOID
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
