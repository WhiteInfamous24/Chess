package srs.userinterface.impl;

import java.util.ArrayList;
import java.util.Scanner;

import srs.Controller;
import srs.Game;
import srs.pieces.Piece;
import srs.userinterface.UserInterface;
import srs.util.Board;
import srs.util.Position;
import srs.util.enums.InputRequestEnum;
import srs.util.factory.impl.PieceFactory;
import srs.util.factory.impl.UserInterfaceFactory;

public class UserInterfaceConsole implements UserInterface {

    private static String userInterfaceType;
    
    public UserInterfaceConsole() {
        userInterfaceType = "CONSOLE";
    }
    
    @Override
    public InputRequestEnum inputRequest() {
        System.out.println("'play' ----> Jugar turno");
        System.out.println("'menu' ----> Menu");
        System.out.println("(default) Jugar turno");
        System.out.print("\nSeleccion: ");
        String input = new Scanner(System.in).nextLine();
        System.out.println("");
        switch (input) {
            case "play": return InputRequestEnum.PLAY;
            case "menu": return InputRequestEnum.ENTER_MENU;
            default: return InputRequestEnum.PLAY;
        }
    }

    @Override
    public void mainMenu() {
        System.out.println();
    }

    @Override
    public void userInterfaceSelectionMenu() {
        System.out.println("||===||=========================||===||");
        System.out.println("||   ||                         ||   ||");
        System.out.println("||   ||    INTERFAZ GRAFICA:    ||   ||");
        System.out.println("||   ||                         ||   ||");
        System.out.println("||   ||    '1' ----> Consola    ||   ||");
        System.out.println("||   ||    '2' ----> Ventana    ||   ||");
        System.out.println("||   ||                         ||   ||");
        System.out.println("||   ||    (default) Consola    ||   ||");
        System.out.println("||   ||                         ||   ||");
        System.out.println("||===||=========================||===||");
        System.out.print("\nSeleccion: ");
        String input = new Scanner(System.in).nextLine();
        switch (input) {
            case "1" -> Controller.setUserInterface(UserInterfaceFactory.getInstance().build("CONSOLE"));
            case "2" -> Controller.setUserInterface(UserInterfaceFactory.getInstance().build("WINDOWS"));
            default -> Controller.setUserInterface(UserInterfaceFactory.getInstance().build("CONSOLE"));
        }
    }

    @Override
    public void playerTurnMessage() {
        System.out.println("TURNO DE " + Game.getPlayer());
    }

    @Override
    public void checkMessage() {
        System.out.println("HAY JAQUE AL REY");
    }

    @Override
    public void winnerMessage() {
        System.out.println("||===||============================||===||");
        System.out.println("||   ||                            ||   ||");
        System.out.println("||   ||    HAY JAQUEMATE AL REY    ||   ||");
        System.out.println("||   ||       GANADOR: " + Game.getPlayer() + "       ||   ||");
        System.out.println("||   ||                            ||   ||");
        System.out.println("||===||============================||===||");
    }

    @Override
    public void invalidPositionMessage() {
        System.out.println("POSICION NO VALIDA");
    }

    @Override
    public void invalidMovementMessage() {
        System.out.println("MOVIMIENTO NO VALIDO");
    }

    @Override
    public void invalidInputMessage() {
        System.out.println("ENTRADA NO VALIDA");
    }

    @Override
    public Position firstPositionRequestMessage() {
        System.out.print("Pieza a mover: ");
        String input = new Scanner(System.in).nextLine();
        return new Position(input.charAt(0)-97, 7-(input.charAt(1)-49));
    }

    @Override
    public Position secondPositionRequestMessage() {
        System.out.print("Mover a casilla: ");
        String input = new Scanner(System.in).nextLine();
        return new Position(input.charAt(0)-97, 7-(input.charAt(1)-49));
    }

    @Override
    public final Piece choosePieceRequest() {
        cleanScreen();
        showBoard();
        insertVoidLine(1);
        showPiecesTaken();
        insertVoidLine(1);
        playerTurnMessage();
        insertVoidLine(1);
        System.out.println("||===||===========================||===||");
        System.out.println("||   ||                           ||   ||");
        System.out.println("||   ||    CORONACION DE PEON:    ||   ||");
        System.out.println("||   ||                           ||   ||");
        System.out.println("||   ||    '1' ----> Bishop       ||   ||");
        System.out.println("||   ||    '2' ----> Knight       ||   ||");
        System.out.println("||   ||    '3' ----> Rook         ||   ||");
        System.out.println("||   ||    '4' ----> Queen        ||   ||");
        System.out.println("||   ||                           ||   ||");
        System.out.println("||   ||    (default) Queen        ||   ||");
        System.out.println("||   ||                           ||   ||");
        System.out.println("||===||===========================||===||");
        System.out.print("\nSeleccion: ");
        String input = new Scanner(System.in).nextLine();
        return switch (input) {
            case "1" -> PieceFactory.getInstance().build("BISHOP_" + Game.getPlayer().toString());
            case "2" -> PieceFactory.getInstance().build("KNIGHT_" + Game.getPlayer().toString());
            case "3" -> PieceFactory.getInstance().build("ROOK_" + Game.getPlayer().toString());
            case "4" -> PieceFactory.getInstance().build("QUEEN_" + Game.getPlayer().toString());
            default -> PieceFactory.getInstance().build("QUEEN_" + Game.getPlayer().toString());
        };
    }

    @Override
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

    @Override
    public void showPiecesTaken() {
        ArrayList<Piece> blackPiecesTaken = Game.getBlackPiecesTaken();
        ArrayList<Piece> whitePiecesTaken = Game.getWhitePiecesTaken();
        System.out.print("|| BLACK PIECES TAKEN |");
        for (Piece pieceIterator : blackPiecesTaken)
            System.out.printf("| %-10s", pieceIterator.getNameOfPiece());
        if (blackPiecesTaken.size() == 0)
            System.out.println("|");
        else
            System.out.println("||");
        System.out.print("\n|| WHITE PIECES TAKEN |");
        for (Piece pieceIterator : whitePiecesTaken)
            System.out.printf("| %-10s", pieceIterator.getNameOfPiece());
        if (whitePiecesTaken.size() == 0)
            System.out.println("|");
        else
            System.out.println("||");
    }

    @Override
    public void cleanScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void insertVoidLine(int n) {
        for(int i = 0; i < n; i++)
            System.out.println("");
    }

    // GETTERS & SETTERS

    @Override
    public String getUserInterfaceType() {
        return userInterfaceType;
    }
}
