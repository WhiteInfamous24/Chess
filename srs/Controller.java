package srs;

import java.util.Scanner;

import srs.exception.InvalidPositionException;
import srs.userinterface.UserInterface;
import srs.userinterface.UserInterfaceConsole;
import srs.userinterface.UserInterfaceWindows;
import srs.util.Movement;
import srs.util.Position;

public class Controller {

    private static Controller instance;
    private static Game game;
    private static UserInterface userInterface;

    private Controller() {
        askForUserInterface();
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
    public void askForUserInterface() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("'1' ----> Console");
        System.out.println("'2' ----> Windows");
        System.out.println("(default) Console\n");
        System.out.print("Seleccione interfaz grafica deseada: ");
        String input = new Scanner(System.in).nextLine();
        switch (input) {
            case "1" -> userInterface = new UserInterfaceConsole();
            case "2" -> userInterface = new UserInterfaceWindows();
            default -> userInterface = new UserInterfaceConsole();
        }
    }

    /*
     * pide al usuario que ingrese 2 posiciones del tablero, y en caso de no cumplir, se volveran a pedir 2 posiciones nuevamente
     * hasta que se cumplan los requisitos y luego devuelve el movimiento como return el metodo
     */
    private Movement requestMovement() {
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
        return new Movement(positionOne, positionTwo, board.getPiece(positionOne).getWasMoved());
    }

    /*
     * pide por interfaz de usuario que se le ingrese una posicion del tablero que indique una pieza propia que sera la pieza a mover
     * puede arrojar excepcion por:
     * -ser una posicion fuera de los limites del tablero
     * -por no haber elegido una pieza propia
     * -haber elegido una casilla vacia
     */
    private Position requestFirstPosition() throws IndexOutOfBoundsException, InvalidPositionException {
        Position position = userInterface.requestFirstPositionMessage();
        if (board.getPiece(position) != null)
            if (!board.getPiece(position).getColorOfPiece().equals(player))
                throw new InvalidPositionException();
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
    private Position requestSecondPosition() throws IndexOutOfBoundsException, InvalidPositionException {
        Position position = userInterface.requestSecondPositionMessage();
        board.getPiece(position); // linea para corroborar que la posicion existe en el tablero o que el metodo tire excepcion
        return position;
    }

    /*
     * es el metodo que se encarga de recopilar lo necesario para realizar el movimiento, validarlo
     * y en caso de cumplir con algun movimiento valido, ejecutarlo
     */
    public void movePiece() {
        /*
        boolean takePiece;
        boolean isValidMovement;
        do {
            takePiece = false;
            isValidMovement = false;
            Movement movement = requestMovement();
            Position position_1 = movement.getPosition1();
            Position position_2 = movement.getPosition2();
            if (board.getPiece(position_2) != null) {
                String could_make_castling = couldMakeCastling(position_1, position_2);
                if (could_make_castling != null) {
                    makeCastling(position_1, position_2, could_make_castling);
                    isValidMovement = true;
                }
                else if (couldTakeAPiece(position_1, position_2)) {
                    if (player.equals(ColorEnum.BLACK))
                        whitePiecesTaken.add(board.getPiece(position_2));
                    else
                        blackPiecesTaken.add(board.getPiece(position_2));
                    movements.add(movement);
                    board.movePiece(position_1, position_2);
                    take_piece = true;
                    isValidMovement = true;
                }
            }
            else if (isValidMovement(position_1, position_2)) {
                movements.add(movement);
                board.movePiece(position_1, position_2);
                isValidMovement = true;
            }
            if (thereIsCheck()) {
                userInterface.checkMessage();
                returnMovementBackwards();
                isValidMovement = false;
            }
            if (!isValidMovement) {
                userInterface.invalidMovementMessage();
                if (take_piece)
                    if (player.equals(ColorEnum.BLACK))
                        whitePiecesTaken.remove(whitePiecesTaken.size()-1);
                    else
                        blackPiecesTaken.remove(blackPiecesTaken.size()-1);
            }
        } while (!isValidMovement);
        */
    }
}
