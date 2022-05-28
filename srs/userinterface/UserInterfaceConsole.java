package srs.userinterface;

import java.util.ArrayList;
import java.util.Scanner;

import srs.Board;
import srs.Position;
import srs.enums.ColorEnum;
import srs.pieces.Bishop;
import srs.pieces.Knight;
import srs.pieces.Piece;
import srs.pieces.Queen;
import srs.pieces.Rook;

public class UserInterfaceConsole implements UserInterface {
    public UserInterfaceConsole() {

    }
    
    public void playerTurnMessage(ColorEnum c) {
        System.out.println("TURNO DE " + c);
    }

    public void invalidPositionMessage() {
        System.out.println("POSICION NO VALIDA");
    }

    public void invalidInputMessage() {
        System.out.println("ENTRADA NO VALIDA");
    }

    public Position requestFirstPositionMessage() {
        String input;
        boolean valid_input;
        do {
            valid_input = true;
            System.out.print("Pieza a mover: ");
            input = new Scanner(System.in).nextLine();
            if (input.length() != 2) {
                valid_input = false;
                invalidPositionMessage();
            }
        } while (!valid_input);
        return new Position(input.charAt(0)-97, 7-(input.charAt(1)-49));
    }

    public Position requestSecondPositionMessage() {
        String input;
        boolean valid_input;
        do {
            valid_input = true;
            System.out.print("Mover a casilla: ");
            input = new Scanner(System.in).nextLine();
            if (input.length() != 2) {
                valid_input = false;
                invalidPositionMessage();
            }
        } while (!valid_input);
        return new Position(input.charAt(0)-97, 7-(input.charAt(1)-49));
    }

    public Piece requestToChoosePiece(ColorEnum c) {
        Piece piece;
        System.out.println("'1' ----> Bishop");
        System.out.println("'2' ----> Knight");
        System.out.println("'3' ----> Rook");
        System.out.println("'4' ----> Queen");
        System.out.println("(default) Queen\n");
        System.out.print("Seleccione una pieza: ");
        String input = new Scanner(System.in).nextLine();
        switch (input) {
            case "1":
                piece = new Bishop(c);
                break;
            case "2":
                piece = new Knight(c);
                break;
            case "3":
                piece = new Rook(c);
                break;
            case "4":
                piece = new Queen(c);
                break;
            default:
                piece = new Queen(c);
                break;
        }
        return piece;
    }

    public void showBoard(Board b) {
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
                if (b.getPiece(new Position(j, i)) != null)
                    System.out.printf(" %-10s", b.getPiece(new Position(j, i)).getNameOfPiece());
                else
                    System.out.printf("%11s", " ");
            }
            System.out.println("||   ||");
            System.out.print("|| " + row +" |");
            for (int j = 0; j < 8; j++) {
                System.out.print("|");
                if (b.getPiece(new Position(j, i)) != null)
                    System.out.printf(" %-10s", b.getPiece(new Position(j, i)).getColorOfPiece());
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

    public void showPiecesTaken(ArrayList<Piece> b_p_t, ArrayList<Piece> w_p_t) {
        System.out.print("||====================|");
        for (Piece piece : b_p_t)
            System.out.print("|===========");
        if (b_p_t.size() == 0)
            System.out.print("|");
        else
            System.out.print("||");
        System.out.print("\n|| BLACK PIECES TAKEN |");
        for (Piece piece : b_p_t)
            System.out.printf("| %-10s", piece.getNameOfPiece());
        if (b_p_t.size() == 0)
            System.out.print("|");
        else
            System.out.print("||");
        System.out.print("\n||--------------------|");
        for (Piece piece : b_p_t)
            System.out.print("|-----------");
        if (b_p_t.size() == 0)
            System.out.print("|");
        else
            System.out.print("||");
        System.out.print("\n||--------------------|");
        for (Piece piece : w_p_t)
            System.out.print("|-----------");
        if (w_p_t.size() == 0)
            System.out.print("|");
        else
            System.out.print("||");
        System.out.print("\n|| WHITE PIECES TAKEN |");
        for (Piece piece : w_p_t)
            System.out.printf("| %-10s", piece.getNameOfPiece());
        if (w_p_t.size() == 0)
            System.out.print("|");
        else
            System.out.print("||");
        System.out.print("\n||====================|");
        for (Piece piece : w_p_t)
            System.out.print("|===========");
        if (w_p_t.size() == 0)
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