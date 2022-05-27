package srs.userinterface;

import java.util.ArrayList;
import srs.Board;
import srs.Position;
import srs.enums.ColorEnum;
import srs.pieces.Piece;

public class UserInterfaceConsole implements UserInterface {
    public UserInterfaceConsole() {

    }
    
    public void playerTurnMessage(ColorEnum c) {
        System.out.println("TURNO DE " + c);
    }

    public void invalidPositionMessage() {
        System.out.println("POSICION NO VALIDA");
    }

    public void requestFirstPositionMessage() {

    }

    public void requestSecondPositionMessage() {

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
}
