package srs;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        //System.out.print("\033[H\033[2J");
        //System.out.flush();
        //game.showBoard();
        game.initializePieces();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        game.showBoard();
        System.out.println("");
        game.showPiecesTaken();
        System.out.println("");
        while (true) {
            game.movePiece();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            game.showBoard();
            System.out.println("");
            game.showPiecesTaken();
            System.out.println("\n");
            game.analizePawnPromotion();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            game.showBoard();
            System.out.println("");
            game.showPiecesTaken();
            System.out.println("");
            game.changePlayer();
        }
    }

    public static void pause() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press ENTER To Continue...");
        scanner.nextLine();
        scanner.close();
    }
}
