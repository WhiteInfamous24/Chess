package srs;

import java.util.Scanner;

import srs.exception.InvalidPositionException;
import srs.userinterface.UserInterface;
import srs.userinterface.UserInterfaceConsole;
import srs.userinterface.UserInterfaceWindows;
import srs.util.ChessUtilities;
import srs.util.Movement;
import srs.util.Position;
import srs.util.ValidateMovement;
import srs.util.enums.CastlingCornerEnum;
import srs.util.enums.ColorEnum;

public class Controller {

    private static Controller instance;
    private static UserInterface userInterface;

    private Controller() {
        askForUserInterface();
        Game.getInstance();
        Game.initializePieces();
    }

    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }

    /*
     * pide por consola al usuario que ingrese que metodo de visualizacion quiere ejecutar,
     * luego instancia la "userInterface" en "console" o en "windows"
     */
    private static void askForUserInterface() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("||===||=======================||===||");
        System.out.println("||   ||                       ||   ||");
        System.out.println("||   ||   INTERFAZ GRAFICA:   ||   ||");
        System.out.println("||   ||                       ||   ||");
        System.out.println("||   ||   '1' ----> Consola   ||   ||");
        System.out.println("||   ||   '2' ----> Ventana   ||   ||");
        System.out.println("||   ||   (default) Consola   ||   ||");
        System.out.println("||   ||                       ||   ||");
        System.out.println("||===||=======================||===||");
        System.out.print("\nSeleccion: ");
        String input = new Scanner(System.in).nextLine();
        switch (input) {
            case "1" -> userInterface = new UserInterfaceConsole();
            case "2" -> userInterface = new UserInterfaceWindows();
            default -> userInterface = new UserInterfaceConsole();
        }
    }

    /*
     * es el metodo que se encarga de recopilar lo necesario para realizar el movimiento, validarlo
     * y en caso de cumplir con un movimiento valido, ejecutarlo
     */
    public static void movePiece() {
        boolean takePiece;
        boolean isValidMovement;
        do {
            takePiece = false;
            isValidMovement = false;
            Movement movement = requestMovement();
            Position positionOne = movement.getPositionOne();
            Position positionTwo = movement.getPositionTwo();
            if (Game.getBoard().getPiece(positionTwo) != null) {
                CastlingCornerEnum couldCastling = ValidateMovement.couldCastling(positionOne, positionTwo);
                if (couldCastling != null) {
                    Game.performCastling(positionOne, positionTwo, couldCastling);
                    isValidMovement = true;
                }
                else if (ValidateMovement.couldTakeAPiece(positionOne, positionTwo)) {
                    if (Game.getPlayer().equals(ColorEnum.BLACK))
                        Game.getWhitePiecesTaken().add(Game.getBoard().getPiece(positionTwo));
                    else
                        Game.getBlackPiecesTaken().add(Game.getBoard().getPiece(positionTwo));
                    Game.getMovements().add(movement);
                    Game.getBoard().movePiece(positionOne, positionTwo);
                    takePiece = true;
                    isValidMovement = true;
                }
            }
            else if (ValidateMovement.isValidMovement(positionOne, positionTwo)) {
                Game.getMovements().add(movement);
                Game.getBoard().movePiece(positionOne, positionTwo);
                isValidMovement = true;
            }
            if (ChessUtilities.isCheck()) {
                userInterface.checkMessage();
                Game.undoLastMove();
                isValidMovement = false;
            }
            if (!isValidMovement) {
                userInterface.invalidMovementMessage();
                if (takePiece)
                    if (Game.getPlayer().equals(ColorEnum.BLACK))
                        Game.getWhitePiecesTaken().remove(Game.getWhitePiecesTaken().size()-1);
                    else
                        Game.getBlackPiecesTaken().remove(Game.getBlackPiecesTaken().size()-1);
            }
        } while (!isValidMovement);
    }

    /*
     * pide al usuario que ingrese 2 posiciones del tablero, y en caso de no cumplir, se volveran a pedir 2 posiciones nuevamente
     * hasta que se cumplan los requisitos y luego devuelve el movimiento como return el metodo
     */
    private static Movement requestMovement() {
        Position positionOne = null;
        Position positionTwo = null;
        do {
            try {
                positionOne = requestFirstPosition();
                positionTwo = requestSecondPosition();
            } catch (InvalidPositionException e) {
                //System.out.println("-InvalidPositionException-");
                userInterface.invalidPositionMessage();
            } catch (IndexOutOfBoundsException e) {
                //System.out.println("-IndexOutOfBoundsException-");
                userInterface.invalidPositionMessage();
            }
        } while (positionOne == null || positionTwo == null);
        return new Movement(positionOne, positionTwo, Game.getBoard().getPiece(positionOne).getWasMoved());
    }

    /*
     * pide por interfaz de usuario que se le ingrese una posicion del tablero que indique una pieza propia que sera la pieza a mover
     * puede arrojar excepcion por:
     * -ser una posicion fuera de los limites del tablero
     * -por no haber elegido una pieza propia
     * -haber elegido una casilla vacia
     */
    private static Position requestFirstPosition() throws IndexOutOfBoundsException, InvalidPositionException {
        Position position = userInterface.requestFirstPositionMessage();
        if (Game.getBoard().getPiece(position) != null) {
            if (!Game.getBoard().getPiece(position).getColorOfPiece().equals(Game.getPlayer()))
                throw new InvalidPositionException();
        }
        else
            throw new InvalidPositionException();
        return position;
    }

    /*
     * pide por interfaz de usuario que se le ingrese una posicion del tablero que sera la posicion a mover la pieza seleccionada anteriormente
     * o en caso de que sea otra pieza se analiza si es propia para realizar enroque de torre y rey, o si es rival se analiza si se puede comer
     * puede arrojar excepcion por:
     * -elegir una posicion vacia y la pieza seleccionada anteriormente no pueda realizar esa trayectoria
     * -no se seleccionaron 2 piezas propias que cumplan con los requisitos para realizar el enroque de torre y rey
     * -se selecciono una posicion con una pieza enemiga, pero la pieza propia no puede realizar una trayectoria permitida para comerla
     */
    private static Position requestSecondPosition() throws IndexOutOfBoundsException, InvalidPositionException {
        Position position = userInterface.requestSecondPositionMessage();
        Game.getBoard().getPiece(position); // linea para corroborar que la posicion existe en el tablero o que el metodo tire excepcion
        return position;
    }

    // GETTERS & SETTERS

    public static UserInterface getUserInterface() {
        return userInterface;
    }
}
