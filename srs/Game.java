package srs;

import java.util.ArrayList;
import java.util.Scanner;

import srs.enums.ColorEnum;
import srs.enums.PieceEnum;
import srs.exception.InvalidPositionException;
import srs.pieces.Bishop;
import srs.pieces.King;
import srs.pieces.Knight;
import srs.pieces.Pawn;
import srs.pieces.Piece;
import srs.pieces.Queen;
import srs.pieces.Rook;
import srs.userinterface.UserInterface;
import srs.userinterface.UserInterfaceConsole;
import srs.userinterface.UserInterfaceWindows;

public class Game {
    private Board board;
    private ColorEnum player;
    private ArrayList<Piece> black_pieces_taken;
    private ArrayList<Piece> white_pieces_taken;
    private ArrayList<Movement> movements;
    private UserInterface user_interface;

    /*
    constructor de la clase
    */
    public Game() {
        board = new Board();
        player = ColorEnum.WHITE;
        black_pieces_taken = new ArrayList<>();
        white_pieces_taken = new ArrayList<>();
        movements = new ArrayList<>();
        askForUserInterface();
    }

    /*
    pide por consola al usuario que ingrese que metodo de visualizacion quiere ejecutar,
    luego instancia la "user_interface" en "console" o en "windows"
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
            case "1":
                user_interface = new UserInterfaceConsole();
                break;
            case "2":
                user_interface = new UserInterfaceWindows();
                break;
            default:
                user_interface = new UserInterfaceConsole();
                break;
        }
    }

    /*
    inicializa todas las piezas de ajedrez en sus posiciones
    */
    public void initializePieces() {
        for (int i = 0; i < 8; i++) {
            board.setPiece(new Pawn(ColorEnum.BLACK), new Position(i, 1));
            board.setPiece(new Pawn(ColorEnum.WHITE), new Position(i, 6));
        }
        board.setPiece(new Rook(ColorEnum.BLACK), new Position(0, 0));
        board.setPiece(new Rook(ColorEnum.BLACK), new Position(7, 0));
        board.setPiece(new Rook(ColorEnum.WHITE), new Position(0, 7));
        board.setPiece(new Rook(ColorEnum.WHITE), new Position(7, 7));
        board.setPiece(new Knight(ColorEnum.BLACK), new Position(1, 0));
        board.setPiece(new Knight(ColorEnum.BLACK), new Position(6, 0));
        board.setPiece(new Knight(ColorEnum.WHITE), new Position(1, 7));
        board.setPiece(new Knight(ColorEnum.WHITE), new Position(6, 7));
        board.setPiece(new Bishop(ColorEnum.BLACK), new Position(2, 0));
        board.setPiece(new Bishop(ColorEnum.BLACK), new Position(5, 0));
        board.setPiece(new Bishop(ColorEnum.WHITE), new Position(2, 7));
        board.setPiece(new Bishop(ColorEnum.WHITE), new Position(5, 7));
        board.setPiece(new Queen(ColorEnum.BLACK), new Position(4, 0));
        board.setPiece(new Queen(ColorEnum.WHITE), new Position(3, 7));
        board.setPiece(new King(ColorEnum.BLACK), new Position(3, 0));
        board.setPiece(new King(ColorEnum.WHITE), new Position(4, 7));
    }

    /*
    alterna entre jugador de color "white" y "black"
    */
    public void changePlayer() {
        if (player.equals(ColorEnum.WHITE))
            player = ColorEnum.BLACK;
        else
            player = ColorEnum.WHITE;
    }

    /*
    es el metodo que se encarga de recopilar lo necesario para realizar el movimiento, validarlo
    y en caso de cumplir con algun movimiento valido, ejecutarlo
    */
    public void movePiece() {
        boolean take_piece;
        boolean valid_movement;
        do {
            take_piece = false;
            valid_movement = false;
            Movement movement = requestMovement();
            Position position_1 = movement.getPosition1();
            Position position_2 = movement.getPosition2();
            if (board.getPiece(position_2) != null) {
                if (couldMakeCastling(position_1, position_2)) {
                    makeCastling(position_1, position_2);
                    valid_movement = true;
                }
                else if (couldTakeAPiece(position_1, position_2)) {
                    if (player.equals(ColorEnum.BLACK))
                        white_pieces_taken.add(board.getPiece(position_2));
                    else
                        black_pieces_taken.add(board.getPiece(position_2));
                    movements.add(movement);
                    board.movePiece(position_1, position_2);
                    take_piece = true;
                    valid_movement = true;
                }
            }
            else if (isValidMovement(position_1, position_2)) {
                movements.add(movement);
                board.movePiece(position_1, position_2);
                valid_movement = true;
            }
            if (thereIsCheck()) {
                user_interface.stillInCheckMessage();
                returnMovementBackwards();
                valid_movement = false;
            }
            if (!valid_movement) {
                user_interface.invalidMovementMessage();
                if (take_piece)
                    if (player.equals(ColorEnum.BLACK))
                        white_pieces_taken.remove(white_pieces_taken.size()-1);
                    else
                        black_pieces_taken.remove(black_pieces_taken.size()-1);
            }
        } while (!valid_movement);
    }

    /*
    pide al usuario que ingrese 2 posiciones del tablero, y en caso de no cumplir, se volveran a pedir 2 posiciones nuevamente
    hasta que se cumplan los requisitos y luego devuelve el movimiento como return el metodo
    */
    private Movement requestMovement() {
        Position position_1 = null;
        Position position_2 = null;
        do {
            try {
                position_1 = requestFirstPosition();
                position_2 = requestSecondPosition();
            } catch (InvalidPositionException e) {
                //System.out.println("-InvalidPositionException-");
                user_interface.invalidPositionMessage();
            } catch (IndexOutOfBoundsException e) {
                //System.out.println("-IndexOutOfBoundsException-");
                user_interface.invalidPositionMessage();
            }
        } while (position_1 == null || position_2 == null);
        return new Movement(position_1, position_2, board.getPiece(position_1).getWasMoved());
    }

    /*
    deshace el ultimo movimiento que se ejecuto en la partida
    */
    public void returnMovementBackwards() {
        Movement movement = movements.get(movements.size()-1);
        board.undoMovement(movement.getPosition2(), movement.getPosition1(), movement.getWasMovedOld());
        movements.remove(movements.size()-1);
    }

    /*
    pide por interfaz de usuario que se le ingrese una posicion del tablero que indique una pieza propia que sera la pieza a mover
    puede arrojar excepcion por:
    -ser una posicion fuera de los limites del tablero
    -por no haber elegido una pieza propia
    -haber elegido una casilla vacia
    */
    private Position requestFirstPosition() throws IndexOutOfBoundsException, InvalidPositionException {
        Position position = user_interface.requestFirstPositionMessage();
        if (board.getPiece(position) != null) {
            if (!board.getPiece(position).getColorOfPiece().equals(player))
                throw new InvalidPositionException();
        }
        else
            throw new InvalidPositionException();
        return position;
    }

    /*
    pide por interfaz de usuario que se le ingrese una posicion del tablero que sera la posicion a mover la pieza seleccionada anteriormente
    o en caso de que sea otra pieza se analiza si es propia para realizar enroque de torre y rey, o si es rival se analiza si se puede comer
    puede arrojar excepcion por:
    -elegir una posicion vacia y la pieza seleccionada anteriormente no pueda realizar esa trayectoria
    -no se seleccionaron 2 piezas propias que cumplan con los requisitos para realizar el enroque de torre y rey
    -se selecciono una posicion con una pieza enemiga, pero la pieza propia no puede realizar una trayectoria permitida para comerla
    */
    private Position requestSecondPosition() throws IndexOutOfBoundsException, InvalidPositionException {
        Position position = user_interface.requestSecondPositionMessage();
        board.getPiece(position); //linea para corroborar que la posicion existe en el tablero o que el metodo tire excepcion
        return position;
    }

    /*
    se encarga de realizar el enroque de torre y rey entre las posiciones que se pasan por parametro del metodo
    se da por supuesto que las posiciones pasadas por parametro son una torre y un rey,
    y que se cumplen todas las condiciones previas para realizar el enroque
    */
    private void makeCastling(Position pos_1, Position pos_2) {
        if (board.getPiece(pos_1).getNameOfPiece().equals(PieceEnum.ROOK)) {
            Position aux = pos_1;
            pos_1 = pos_2;
            pos_2 = aux;
        }
        Piece king = board.getPiece(pos_1);
        Piece rook = board.getPiece(pos_2);
        if (pos_2.getX() == 0)
            if (player.equals(ColorEnum.BLACK)) {
                movements.add(new Movement(pos_2, new Position(2, 0), rook.getWasMoved()));
                board.movePiece(pos_2, new Position(2, 0));
                movements.add(new Movement(pos_1, new Position(1, 0), king.getWasMoved()));
                board.movePiece(pos_1, new Position(1, 0));
            }
            else {
                movements.add(new Movement(pos_2, new Position(3, 7), rook.getWasMoved()));
                board.movePiece(pos_2, new Position(3, 7));
                movements.add(new Movement(pos_1, new Position(2, 7), king.getWasMoved()));
                board.movePiece(pos_1, new Position(2, 7));
            }
        else
            if (player.equals(ColorEnum.BLACK)) {
                movements.add(new Movement(pos_2, new Position(4, 0), rook.getWasMoved()));
                board.movePiece(pos_2, new Position(4, 0));
                movements.add(new Movement(pos_1, new Position(5, 0), king.getWasMoved()));
                board.movePiece(pos_1, new Position(5, 0));
            }
            else {
                movements.add(new Movement(pos_2, new Position(5, 7), rook.getWasMoved()));
                board.movePiece(pos_2, new Position(5, 7));
                movements.add(new Movement(pos_1, new Position(6, 7), king.getWasMoved()));
                board.movePiece(pos_1, new Position(6, 7));
            }
    }

    /*
    analiza si el movimiento que se desea realizar cumple con los posibles requisitos
    -en caso de que la posicion final sea valida para tomar una pieza rival, y efectivamente haya una pieza para poder tomar,
    se procede a verificar que en la trayectoria no haya obstaculos y si cumple, se captura la pieza
    -en caso de que la posicion final se encuentre vacia, se procede a verificar que no haya obstaculos en el camino
    */
    private boolean isValidMovement(Position pos_1, Position pos_2) {
        ArrayList<Position> possible_movements = board.getPiece(pos_1).possibleMovements(pos_1);
        ArrayList<Position> possible_takes = board.getPiece(pos_1).possibleTakes(pos_1);
        if (searchPositionInArray(possible_takes, pos_2) && board.getPiece(pos_2) != null) //veo si la posicion final se encuentra en una posicion de toma valido de la pieza
            return analizeTrajectory(pos_1, pos_2);
        else if (searchPositionInArray(possible_movements, pos_2) && board.getPiece(pos_2) == null) //veo si la posicion final se encuentra en una posicion de movimiento valido de la pieza
            return analizeTrajectory(pos_1, pos_2);
        else
            return false;
    }

    /*
    verifica que, si la ficha realiza una trayectoria larga, no colisione con otras piezas en el trayecto,
    a excepcion de la posicion final donde puede llegar a haber una pieza cualquiera
    */
    private boolean analizeTrajectory(Position pos_1, Position pos_2) {
        if (board.getPiece(pos_1).getLongMovement()) { //veo si la pieza realiza movimientos de trayectoria
            boolean no_obstruction = true;
            int x_1 = pos_1.getX();
            int y_1 = pos_1.getY();
            int x_2 = pos_2.getX();
            int y_2 = pos_2.getY();
            int MAX;
            if (x_2 == x_1) { //verifico si me muevo unicamente sobre el eje Y
                if (y_2 > y_1) { //analizo movimiento para +Y
                    MAX = y_2-y_1; //seteo el limite de iteraciones para no salirme del tablero para +Y
                    for (int i = 1; i < MAX; i++) { //analizo movimiento para +Y
                        if (board.getPiece(new Position(x_1, y_1+i)) != null && y_1+i != y_2)
                            no_obstruction = false;
                        if (y_1+i == y_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                            return no_obstruction;
                    }
                }
                else { //analizo movimiento para -Y
                    MAX = y_1-y_2; //seteo el limite de iteraciones para no salirme del tablero para -Y
                    for (int i = 1; i < MAX; i++) {
                        if (board.getPiece(new Position(x_1, y_1-i)) != null && y_1-i != y_2)
                            no_obstruction = false;
                        if (y_1-i == y_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                            return no_obstruction;
                    }
                }
            }
            else if (y_2 == y_1) { //verifico si me muevo unicamente sobre el eje X
                if (x_2 > x_1) { //analizo movimiento para +X
                    MAX = x_2-x_1; //seteo el limite de iteraciones para no salirme del tablero para +X
                    for (int i = 1; i < MAX; i++) { //analizo movimiento para +X
                        if (board.getPiece(new Position(x_1+i, y_1)) != null && x_1+i != x_2)
                            no_obstruction = false;
                        if (x_1+i == x_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                            return no_obstruction;
                    }
                }
                else { //analizo movimiento para -X
                    MAX = x_1-x_2; //seteo el limite de iteraciones para no salirme del tablero para -X
                    for (int i = 1; i < MAX; i++) {
                        if (board.getPiece(new Position(x_1-i, y_1)) != null && x_1-i != x_2)
                            no_obstruction = false;
                        if (x_1-i == x_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                            return no_obstruction;
                    }
                }
            }
            else if (x_2 > x_1 && y_2 > y_1) { //verifico si me muevo unicamente en direccion +X+Y
                if (8-x_1 < 8-y_1) //seteo el limite de iteraciones para no salirme del tablero buscando el eje mas chico
                    MAX = x_2-x_1;
                else
                    MAX = y_2-y_1;
                for (int i = 1; i < MAX; i++) { //analizo movimiento
                    if (board.getPiece(new Position(x_1+i, y_1+i)) != null && y_1+i != y_2)
                        no_obstruction = false;
                    if (y_1+i == y_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                        return no_obstruction;
                }
            }
            else if (x_2 > x_1 && y_2 < y_1) { //verifico si me muevo unicamente en el eje +X-Y
                if (8-x_1 < y_1+1) //seteo el limite de iteraciones para no salirme del tablero buscando el eje mas chico
                    MAX = x_2-x_1;
                else
                    MAX = y_1-y_2;
                for (int i = 1; i < MAX; i++) { //analizo movimiento
                    if (board.getPiece(new Position(x_1+i, y_1-i)) != null && y_1-i != y_2)
                        no_obstruction = false;
                    if (y_1-i == y_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                        return no_obstruction;
                }
            }
            else if (x_2 < x_1 && y_2 > y_1) { //verifico si me muevo unicamente en el eje -X+Y
                if (8-x_1 < 8-y_1) //seteo el limite de iteraciones para no salirme del tablero buscando el eje mas chico
                    MAX = x_1-x_2;
                else
                    MAX = y_2-y_1;
                for (int i = 1; i < MAX; i++) { //analizo movimiento
                    if (board.getPiece(new Position(x_1-i, y_1+i)) != null && y_1+i != y_2)
                        no_obstruction = false;
                    if (y_1+i == y_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                        return no_obstruction;
                }
            }
            else if (x_2 < x_1 && y_2 < y_1) { //verifico si me muevo unicamente en el eje -X-Y
                if (8-x_1 < 8-y_1) //seteo el limite de iteraciones para no salirme del tablero buscando el eje mas chico
                    MAX = x_1-x_2;
                else
                    MAX = y_1-y_2;
                for (int i = 1; i < MAX; i++) { //analizo movimiento
                    if (board.getPiece(new Position(x_1-i, y_1-i)) != null && y_1-i != y_2)
                        no_obstruction = false;
                    if (y_1-i == y_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                        return no_obstruction;
                }
            }
            return no_obstruction;
        }
        else
            return true;
    }

    /*
    verifica si hay una pieza en la posicion final, donde si esta es igual a la del jugador o es una casilla nula, no habra opcion de comer pieza,
    y en caso de ser una pieza rival, se analizaran los movimientos predefinidos de cada pieza para ver si puede comer
    */
    private boolean couldTakeAPiece(Position pos_1, Position pos_2) {
        if (board.getPiece(pos_2) != null)
            if (!board.getPiece(pos_2).getColorOfPiece().equals(player))
                if (searchPositionInArray(board.getPiece(pos_1).possibleTakes(pos_1), pos_2))
                    return isValidMovement(pos_1, pos_2);
        return false;
    }

    /*
    -ni el rey ni la torre deben haber sido movidas,
    -el rey no puede estar en jaque,
    -ninguna casilla de la trayectoria que recorrera el rey puede estar atacada,
    -no deben haber piezas entre el rey y la torre,
    el enrroque es moviendo el rey 2 casillas hacia la derecha o izquierda, y la torre, del lado al que se movio, salta sobre el rey
    */
    private boolean couldMakeCastling(Position pos_1, Position pos_2) {
        Piece piece_1 = board.getPiece(pos_1);
        Piece piece_2 = board.getPiece(pos_2);
        if (piece_1.getNameOfPiece().equals(PieceEnum.ROOK)) {
            Position aux_position = pos_1;
            pos_1 = pos_2;
            pos_2 = aux_position;
            Piece aux_piece = piece_1;
            piece_1 = piece_2;
            piece_2 = aux_piece;
        }
        if (piece_2.getColorOfPiece() == player) {
            if (piece_1.getNameOfPiece().equals(PieceEnum.KING) && piece_2.getNameOfPiece().equals(PieceEnum.ROOK))
                if (analizeTrajectory(pos_2, pos_1))
                    if (!piece_1.getWasMoved() && !piece_2.getWasMoved()) {
                        ArrayList<Position> positions = new ArrayList<>();
                        if (pos_2.getX() == 0)
                            if (player.equals(ColorEnum.BLACK)) {
                                positions.add(new Position(1, 0));
                                positions.add(new Position(2, 0));
                                positions.add(new Position(3, 0));
                            }
                            else {
                                positions.add(new Position(2, 7));
                                positions.add(new Position(3, 7));
                                positions.add(new Position(4, 7));
                            }
                        else
                            if (player.equals(ColorEnum.BLACK)) {
                                positions.add(new Position(3, 0));
                                positions.add(new Position(4, 0));
                                positions.add(new Position(5, 0));
                            }
                            else {
                                positions.add(new Position(4, 7));
                                positions.add(new Position(5, 7));
                                positions.add(new Position(6, 7));
                            }
                        return !itsAttacked(positions);
                    }
        }
        return false;
    }

    /*
    retorna la posicion del rey del jugador que fue pasado por argumento del metodo
    */
    private Position searchKing(ColorEnum c) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board.getPiece(new Position(i, j)) != null)
                    if (board.getPiece(new Position(i, j)).getNameOfPiece().equals(PieceEnum.KING) && board.getPiece(new Position(i, j)).getColorOfPiece().equals(player))
                        return new Position(i, j);
        return null;
    }

    private boolean thereIsCheck() {
        return itsAttacked(searchKing(player));
    }

    /*
    se le pasa una posicion que debe ser la del rey, y retorna un arraylist con las posiciones que no se ven atacadas alrrededor del rey
    */
    private ArrayList<Position> notAttackedPositionsInCheck(Position pos) {
        ArrayList<Position> output = new ArrayList<>();
        ArrayList<Position> around_positions = board.getPiece(pos).possibleMovements(pos);
        for (Position position : around_positions)
            if (!itsAttacked(position))
                output.add(position);
        return output;
    }

    /*
    analiza si el rey se ve acorralado, viendo si en caso de haber jaque, el rey tiene movimientos posibles,
    incluso en el caso de que pueda llegar a comer una pieza rival
    */
    public boolean thereIsCheckmate() {
        if (thereIsCheck()) {
            ArrayList<Position> tentative_movements = notAttackedPositionsInCheck(searchKing(player));
            ArrayList<Position> to_remove = new ArrayList<>();
            for (Position position : tentative_movements)
                if (board.getPiece(position) != null)
                    if (board.getPiece(position).getColorOfPiece().equals(player))
                        to_remove.add(position);
                    else
                        if (itsAttacked(position))
                            to_remove.add(position);
            tentative_movements.removeAll(to_remove);
            if (tentative_movements.size() == 0)
                return true;
        }
        return false;
    }

    /*
    analiza si existen peones que hayan completado todo su recorrido y, en caso de haber,
    se pide por interfaz de usuario que se elija en que pieza desea que se convierta,
    para luego ser reemplazada en el tablero
    */
    public void analizePawnPromotion() {
        Position position = null;
        for (int i = 0; i < 8; i++) {
            if (player.equals(ColorEnum.BLACK))
                if (board.getPiece(new Position(i, 7)) != null)
                    if (board.getPiece(new Position(i, 7)).getNameOfPiece().equals(PieceEnum.PAWN))
                        position = new Position (i, 7);
            if (player.equals(ColorEnum.WHITE))
                if (board.getPiece(new Position(i, 0)) != null)
                    if (board.getPiece(new Position(i, 0)).getNameOfPiece().equals(PieceEnum.PAWN))
                        position = new Position(i, 0);
        }
        if (position != null)
            board.setPiece(user_interface.requestToChoosePiece(player), position);
    }

    /*
    se le pasa una posicion por argumento y devuelve un booleano en el caso de
    que esa posicion se vea atacada por alguna pieza enemiga
    */
    private boolean itsAttacked(Position pos) {
        ColorEnum opponent;
        ArrayList<Position> positions = new ArrayList<>();
        if (player.equals(ColorEnum.BLACK))
            opponent = ColorEnum.WHITE;
        else
            opponent = ColorEnum.BLACK;
        for (int i = 0; i < 8; i++) //cargo todas las piezas rivales del tablero
            for (int j = 0; j < 8; j++)
                if (board.getPiece(new Position(i, j)) != null)
                    if (board.getPiece(new Position(i, j)).getColorOfPiece().equals(opponent))
                        positions.add(new Position(i, j));
        for (Position rival_piece : positions) //analizo los movimientos de todas las fichas enemigas recopiladas y debo corroborar si atacan las posiciones pasadas por parametro
            if (isValidMovement(rival_piece, pos))
                return true;
        return false;
    }

    /*
    se le pasa un arraylist de posiciones por argumento y devuelve un booleano en el caso de
    que alguna de esas posiciones se vea atacada po alguna pieza enemiga
    */
    private boolean itsAttacked(ArrayList<Position> pos) {
        ColorEnum opponent;
        ArrayList<Position> positions = new ArrayList<>();
        if (player.equals(ColorEnum.BLACK))
            opponent = ColorEnum.WHITE;
        else
            opponent = ColorEnum.BLACK;
        for (int i = 0; i < 8; i++) //cargo todas las piezas rivales del tablero
            for (int j = 0; j < 8; j++)
                if (board.getPiece(new Position(i, j)) != null)
                    if (board.getPiece(new Position(i, j)).getColorOfPiece().equals(opponent))
                        positions.add(new Position(i, j));
        for (Position position_to_analize : pos) //analizo los movimientos de todas las fichas enemigas recopiladas y debo corroborar si atacan las posiciones pasadas por parametro
            for (Position rival_piece : positions)
                if (isValidMovement(rival_piece, position_to_analize))
                    return true;
        return false;
    }

    /*
    se le pasa por argumento un arraylist de posiciones y una posicion, y el metodo retornara un booleano
    en el caso de que exista, o no, alguna posicion en el arraylist con las mismas coordenadas que la posicion
    que se paso por argumento de la funcion
    */
    private boolean searchPositionInArray(ArrayList<Position> array_pos, Position pos) {
        for (Position position : array_pos)
            if (position.getX() == pos.getX() && position.getY() == pos.getY())
                return true;
        return false;
    }

    public UserInterface getUserInterface() {
        return user_interface;
    }

    public void playerTurnMessage() {
        user_interface.playerTurnMessage(player);
    }

    public void showIfThereIsCheck() {
        if (thereIsCheck()) {
            user_interface.checkMessage();
            user_interface.insertVoidLine(1);
        }
    }

    public void winnerMessage() {
        user_interface.winnerMessage(player);
    }

    public void showBoard() {
        user_interface.showBoard(board);
    }

    public void showPiecesTaken() {
        user_interface.showPiecesTaken(black_pieces_taken, white_pieces_taken);
    }

    public void insertVoidLine(int n) {
        user_interface.insertVoidLine(n);
    }
}
