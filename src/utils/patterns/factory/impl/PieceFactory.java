package utils.patterns.factory.impl;

import model.pieces.Piece;
import model.pieces.impl.Bishop;
import model.pieces.impl.King;
import model.pieces.impl.Knight;
import model.pieces.impl.Pawn;
import model.pieces.impl.Queen;
import model.pieces.impl.Rook;
import utils.enums.ColorEnum;
import utils.patterns.factory.Factory;

public class PieceFactory extends Factory<Piece> {
    private static PieceFactory instance;

    private PieceFactory() {}

    public static PieceFactory getInstance() {
        if(instance == null)
            instance = new PieceFactory();
        return instance;
    }

    public final Piece build(String type) {
        return switch (type) {
            case "bishop_b" -> new Bishop(ColorEnum.BLACK);
            case "king_b" -> new King(ColorEnum.BLACK);
            case "knight_b" -> new Knight(ColorEnum.BLACK);
            case "pawn_b" -> new Pawn(ColorEnum.BLACK);
            case "queen_b" -> new Queen(ColorEnum.BLACK);
            case "rook_b" -> new Rook(ColorEnum.BLACK);
            case "bishop_w" -> new Bishop(ColorEnum.WHITE);
            case "king_w" -> new King(ColorEnum.WHITE);
            case "knight_w" -> new Knight(ColorEnum.WHITE);
            case "pawn_w" -> new Pawn(ColorEnum.WHITE);
            case "queen_w" -> new Queen(ColorEnum.WHITE);
            case "rook_w" -> new Rook(ColorEnum.WHITE);
            default -> null;
        };
    }
}
