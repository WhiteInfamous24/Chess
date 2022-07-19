package srs.userinterface;

import java.util.ArrayList;
import java.util.Scanner;

import srs.Game;
import srs.pieces.Bishop;
import srs.pieces.Knight;
import srs.pieces.Piece;
import srs.pieces.Queen;
import srs.pieces.Rook;
import srs.util.Board;
import srs.util.Position;
import srs.util.enums.ColorEnum;

public class UserInterfaceConsole implements UserInterface {
    
    public UserInterfaceConsole() {

    }
    
    public void playerTurnMessage() {
        System.out.println("TURNO DE " + Game.getPlayer());
    }

    public void checkMessage() {
        System.out.println("HAY JAQUE AL REY");
    }

    public void winnerMessage() {
        System.out.println("||===||==========================||===||");
        System.out.println("||   ||   HAY JAQUEMATE AL REY   ||   ||");
        System.out.println("||   ||      GANADOR: " + Game.getOpponent() + "      ||   ||");
        System.out.println("||===||==========================||===||");
    }

    public void invalidPositionMessage() {
        System.out.println("POSICION NO VALIDA");
    }

    public void invalidMovementMessage() {
        System.out.println("MOVIMIENTO NO VALIDO");
    }

    public void invalidInputMessage() {
        System.out.println("ENTRADA NO VALIDA");
    }

    public Position requestFirstPositionMessage() {
        System.out.print("Pieza a mover: ");
        String input = new Scanner(System.in).nextLine();
        return new Position(input.charAt(0)-97, 7-(input.charAt(1)-49));
    }

    public Position requestSecondPositionMessage() {
        System.out.print("Mover a casilla: ");
        String input = new Scanner(System.in).nextLine();
        return new Position(input.charAt(0)-97, 7-(input.charAt(1)-49));
    }

    public Piece requestToChoosePiece() {
        Piece piece;
        System.out.println("'1' ----> Bishop");
        System.out.println("'2' ----> Knight");
        System.out.println("'3' ----> Rook");
        System.out.println("'4' ----> Queen");
        System.out.println("(default) Queen\n");
        System.out.print("Seleccione una pieza: ");
        String input = new Scanner(System.in).nextLine();
        switch (input) {
            case "1" -> piece = new Bishop(Game.getPlayer());
            case "2" -> piece = new Knight(Game.getPlayer());
            case "3" -> piece = new Rook(Game.getPlayer());
            case "4" -> piece = new Queen(Game.getPlayer());
            default -> piece = new Queen(Game.getPlayer());
        }
        return piece;
    }

    public void showBoard() {
        Board board = Game.getBoard();
        int row = 8;
        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                System.out.println("||===||===============================================================================================||===||");
                System.out.println("||   ||     A     |     B     |     C     |     D     |     E     |     F     |     G     |     H     ||   ||");
                System.out.println("||===||===============================================================================================||===||");
            }
            else
                System.out.println("||---||-----------|-----------|-----------|-----------|-----------|-----------|-----------|-----------||---||");
            System.out.print("||   |");
            for (int j = 0; j < 8; j++) { 
                System.out.print("|");
                if (board.getPiece(new Position(j, i)) != null)
                    System.out.printf(" %-10s", board.getPiece(new Position(j, i)).getNameOfPiece());
                else
                    System.out.printf("%11s", " ");
            }
            System.out.println("||   ||");
            System.out.print("|| " + row +" |");
            for (int j = 0; j < 8; j++) {
                System.out.print("|");
                if (board.getPiece(new Position(j, i)) != null)
                    System.out.printf(" %-10s", board.getPiece(new Position(j, i)).getColorOfPiece());
                else
                    System.out.printf("%11s", " ");
            }
            System.out.println("|| " + row + " ||");
            System.out.println("||   ||           |           |           |           |           |           |           |           ||   ||");
            row--;
        }
        System.out.println("||===||===============================================================================================||===||");
        System.out.println("||   ||     A     |     B     |     C     |     D     |     E     |     F     |     G     |     H     ||   ||");
        System.out.println("||===||===============================================================================================||===||");
    }

    public void showPiecesTaken() {
        ArrayList<Piece> blackPiecesTaken = Game.getBlackPiecesTaken();
        ArrayList<Piece> whitePiecesTaken = Game.getWhitePiecesTaken();
        System.out.print("||====================|");
        for (Piece piece : blackPiecesTaken)
            System.out.print("|===========");
        if (blackPiecesTaken.size() == 0)
            System.out.print("|");
        else
            System.out.print("||");
        System.out.print("\n|| BLACK PIECES TAKEN |");
        for (Piece piece : blackPiecesTaken)
            System.out.printf("| %-10s", piece.getNameOfPiece());
        if (blackPiecesTaken.size() == 0)
            System.out.print("|");
        else
            System.out.print("||");
        System.out.print("\n||--------------------|");
        for (Piece piece : blackPiecesTaken)
            System.out.print("|-----------");
        if (blackPiecesTaken.size() == 0)
            System.out.print("|");
        else
            System.out.print("||");
        System.out.print("\n||--------------------|");
        for (Piece piece : whitePiecesTaken)
            System.out.print("|-----------");
        if (whitePiecesTaken.size() == 0)
            System.out.print("|");
        else
            System.out.print("||");
        System.out.print("\n|| WHITE PIECES TAKEN |");
        for (Piece piece : whitePiecesTaken)
            System.out.printf("| %-10s", piece.getNameOfPiece());
        if (whitePiecesTaken.size() == 0)
            System.out.print("|");
        else
            System.out.print("||");
        System.out.print("\n||====================|");
        for (Piece piece : whitePiecesTaken)
            System.out.print("|===========");
        if (whitePiecesTaken.size() == 0)
            System.out.print("|");
        else
            System.out.print("||");
    }

    public void cleanScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void insertVoidLine(int n) {
        for(int i = 0; i < n; i++)
            System.out.println("");
    }
}
