import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        //game.showBoard();
        game.initializePieces();
        game.showBoard();
        game.movePiece();
        game.showBoard();
    }

    public static void pause() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press ENTER To Continue...");
        scanner.nextLine();
        scanner.close();
    }
}
