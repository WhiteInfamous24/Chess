package srs;

import java.util.Scanner;

import srs.exception.InvalidPositionException;
import srs.userinterface.UserInterface;
import srs.util.ChessUtilities;
import srs.util.Movement;
import srs.util.Position;
import srs.util.enums.ActionEnum;
import srs.util.factory.impl.UserInterfaceFactory;

public class Controller {

    private static Controller instance;
    private static UserInterface userInterface;

    private Controller() {
        DataLogger.logProgramStartUp_CONTROLLER();; // DATALOG
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
            case "1" -> userInterface = UserInterfaceFactory.getInstance().build("CONSOLE");
            case "2" -> userInterface = UserInterfaceFactory.getInstance().build("WINDOWS");
            default -> userInterface = UserInterfaceFactory.getInstance().build("");
        }
        DataLogger.logUserInterface_CONTROLLER(); // DATALOG
    }

    /*
     * es el metodo que se encarga de recopilar lo necesario para realizar el movimiento, validarlo
     * y en caso de cumplir con un movimiento valido, ejecutarlo
     */
    public static void movePiece() {
        ActionEnum action;
        Movement movement;
        do {
            action = null;
            movement = movementRequest();
            if (Game.getBoard().getPiece(movement.getPositionTwo()) != null) {
                action = ChessUtilities.couldCastling(movement.getPositionOne(), movement.getPositionTwo());
                if (action == null && ChessUtilities.couldTakeAPiece(movement.getPositionOne(), movement.getPositionTwo()))
                    action = ActionEnum.PIECE_TAKING;
            }
            else if (ChessUtilities.isValidMovement(movement.getPositionOne(), movement.getPositionTwo()))
                action = ActionEnum.MOVE;
            if (action != null) // si es valido realiza el movimiento solicitado
                Game.movePiece(movement.getPositionOne(), movement.getPositionTwo(), action);
            if (ChessUtilities.isCheck()) { // si se pone al propio rey en jaque se elimina el movimiento
                userInterface.checkMessage();
                Game.undoLastMove();
                action = null;
            }
            if (action == null)
                userInterface.invalidMovementMessage();
        } while (action == null);
        DataLogger.logMovement_GAME(); // DATALOG
        if (ChessUtilities.isPawnPromotion()) {
            Game.pawnPromotion(userInterface.choosePieceRequest(), movement.getPositionTwo());
            DataLogger.logPawnPromotion_GAME(); // DATALOG
        }
    }

    /*
     * pide al usuario que ingrese 2 posiciones del tablero, y en caso de no cumplir, se volveran a pedir 2 posiciones nuevamente
     * hasta que se cumplan los requisitos y luego devuelve el movimiento como return el metodo
     */
    private static Movement movementRequest() {
        Position positionOne = null;
        Position positionTwo = null;
        do {
            try {
                positionOne = firstPositionRequest();
                positionTwo = secondPositionRequest();
            } catch (InvalidPositionException e) {
                // System.out.println("-InvalidPositionException-");
                userInterface.invalidPositionMessage();
            } catch (IndexOutOfBoundsException e) {
                // System.out.println("-IndexOutOfBoundsException-");
                userInterface.invalidPositionMessage();
            }
        } while (positionOne == null || positionTwo == null);
        return new Movement(positionOne, positionTwo, null);
    }

    /*
     * pide por interfaz de usuario que se le ingrese una posicion del tablero que indique una pieza propia que sera la pieza a mover
     * puede arrojar excepcion por:
     * -ser una posicion fuera de los limites del tablero
     * -por no haber elegido una pieza propia
     * -haber elegido una casilla vacia
     */
    private static Position firstPositionRequest() throws IndexOutOfBoundsException, InvalidPositionException {
        Position position = userInterface.firstPositionRequestMessage();
        if (Game.getBoard().getPiece(position) != null)
            if (Game.getBoard().getPiece(position).getColorOfPiece().equals(Game.getPlayer()))
                return position;    
        throw new InvalidPositionException();
    }

    /*
     * pide por interfaz de usuario que se le ingrese una posicion del tablero que sera la posicion a mover la pieza seleccionada anteriormente
     * o en caso de que sea otra pieza se analiza si es propia para realizar enroque de torre y rey, o si es rival se analiza si se puede comer
     * puede arrojar excepcion por:
     * -elegir una posicion vacia y la pieza seleccionada anteriormente no pueda realizar esa trayectoria
     * -no se seleccionaron 2 piezas propias que cumplan con los requisitos para realizar el enroque de torre y rey
     * -se selecciono una posicion con una pieza enemiga, pero la pieza propia no puede realizar una trayectoria permitida para comerla
     */
    private static Position secondPositionRequest() throws IndexOutOfBoundsException, InvalidPositionException {
        Position position = userInterface.secondPositionRequestMessage();
        Game.getBoard().getPiece(position); // linea para corroborar que la posicion existe en el tablero o que el metodo tire excepcion
        return position;
    }

    // GETTERS & SETTERS

    public static UserInterface getUserInterface() {
        return userInterface;
    }
}
