package srs.util.factory.impl;

import srs.pieces.Piece;
import srs.pieces.impl.Bishop;
import srs.pieces.impl.King;
import srs.pieces.impl.Knight;
import srs.pieces.impl.Pawn;
import srs.pieces.impl.Queen;
import srs.pieces.impl.Rook;
import srs.util.enums.ColorEnum;
import srs.util.factory.Factory;

public class PieceFactory extends Factory<Piece> {

    private static PieceFactory instance;

    private PieceFactory() { } // VOID

    public static PieceFactory getInstance() {
        if (instance == null)
            instance = new PieceFactory();
        return instance;
    }

    @Override
    public final Piece build(String toBuild) {
        return switch (toBuild) {
            case "PAWN_BLACK" -> new Pawn(ColorEnum.BLACK);
            case "KING_BLACK" -> new King(ColorEnum.BLACK);
            case "QUEEN_BLACK" -> new Queen(ColorEnum.BLACK);
            case "BISHOP_BLACK" -> new Bishop(ColorEnum.BLACK);
            case "KNIGHT_BLACK" -> new Knight(ColorEnum.BLACK);
            case "ROOK_BLACK" -> new Rook(ColorEnum.BLACK);
            case "PAWN_WHITE" -> new Pawn(ColorEnum.WHITE);
            case "KING_WHITE" -> new King(ColorEnum.WHITE);
            case "QUEEN_WHITE" -> new Queen(ColorEnum.WHITE);
            case "BISHOP_WHITE" -> new Bishop(ColorEnum.WHITE);
            case "KNIGHT_WHITE" -> new Knight(ColorEnum.WHITE);
            case "ROOK_WHITE" -> new Rook(ColorEnum.WHITE);
            default -> null;
        };
    }
}
