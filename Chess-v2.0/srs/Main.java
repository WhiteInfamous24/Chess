import java.util.Scanner;

import model.Board;
import userInterface.UserInterface;
import userInterface.impl.ConsoleUI;
import utils.patterns.factory.impl.UIFactory;

public class Main {
    public static void main(String[] args) {
        UIFactory uiFactory = UIFactory.getInstance();
        System.out.println("Como desea visualizar la aplicacion?");
        Scanner scanner = new Scanner(System.in);
        UserInterface userInterface = uiFactory.build(scanner.nextLine());
        Game game = Game.getInstance();
        game.attach(userInterface);
        do {
            userInterface.showTurn();
            userInterface.movePiece();
        } while (game.isCheckmate());
        System.out.println(UserInterface.CHECKMATE_MESSAGE);
    }
}
