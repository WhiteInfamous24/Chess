package srs;

import java.util.ArrayList;
import java.util.Scanner;

import srs.enums.ColorEnum;
import srs.enums.PieceEnum;
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
    private UserInterface user_interface;

    public Game() {
        board = new Board();
        player = ColorEnum.WHITE;
        black_pieces_taken = new ArrayList<>();
        white_pieces_taken = new ArrayList<>();
        askForUserInterface();
    }

    public void askForUserInterface() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Select desired user interface:");
        System.out.println("\n'1' ----> Console");
        System.out.println("'2' ----> Windows\n");
        System.out.println("(default) Console\n");
        char input = new Scanner(System.in).nextLine().charAt(0);
        switch (input) {
            case '1':
                user_interface = new UserInterfaceConsole();
                break;
            case '2':
                user_interface = new UserInterfaceWindows();
                break;
            default:
                user_interface = new UserInterfaceConsole();
                break;
        }
    }

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

    public void changePlayer() {
        if (player.equals(ColorEnum.WHITE))
            player = ColorEnum.BLACK;
        else
            player = ColorEnum.WHITE;
    }

    public void movePiece() throws IndexOutOfBoundsException {
        Position position_1;
        Position position_2;
        position_1 = requestFirstPosition();
        position_2 = requestSecondPosition(position_1);
        board.movePiece(position_1, position_2);
    }

    private Position requestFirstPosition() {
        Position position;
        boolean valid_position;
        do {
            position = user_interface.requestFirstPositionMessage();
            try {
                if (board.getPiece(position) != null)
                    valid_position = board.getPiece(position).getColorOfPiece().equals(player);
                else
                    valid_position = false;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("-IndexOutOfBoundsException-");
                valid_position = false;
            }
            if (!valid_position)
                user_interface.invalidPositionMessage();
        } while (!valid_position);
        return position;
    }

    private Position requestSecondPosition(Position pos) {
        Position position;
        boolean valid_position;
        do {
            position = user_interface.requestSecondPositionMessage();
            try {
                if (board.getPiece(position) != null)
                    if (couldMakeCastling(pos, position)) {
                        if (board.getPiece(pos).getNameOfPiece().equals(PieceEnum.ROOK)) {
                            Position aux = pos;
                            pos = position;
                            position = aux;
                        }
                        if (position.getX() == 0)
                            if (player.equals(ColorEnum.BLACK)) {
                                board.movePiece(position, new Position(2, 0));
                                position = new Position(1, 0);
                            }
                            else {
                                board.movePiece(position, new Position(3, 7));
                                position = new Position(2, 7);
                            }
                        else
                            if (player.equals(ColorEnum.BLACK)) {
                                board.movePiece(position, new Position(4, 0));
                                position = new Position(5, 0);
                            }
                            else {
                                board.movePiece(position, new Position(5, 7));
                                position = new Position(6, 7);
                            }
                        valid_position = true;
                    }
                    else if (couldTakeAPiece(pos, position)) {
                        valid_position = isValidMovement(pos, position);
                        if (valid_position)
                            if (player.equals(ColorEnum.BLACK))
                                white_pieces_taken.add(board.getPiece(position));
                            else
                                black_pieces_taken.add(board.getPiece(position));
                    }
                    else
                        valid_position = false;
                else
                    valid_position = isValidMovement(pos, position);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("-IndexOutOfBoundsException-");
                valid_position = false;
            }
            if (!valid_position)
                user_interface.invalidPositionMessage();
        } while (!valid_position);
        return position;
    }

    private boolean isValidMovement(Position pos_1, Position pos_2) {
        ArrayList<Position> possible_movements = board.getPiece(pos_1).possibleMovements(pos_1);
        ArrayList<Position> possible_takes = board.getPiece(pos_1).possibleTakes(pos_1);
        if (searchPositionInArray(possible_takes, pos_2) && board.getPiece(pos_2) != null) //veo si la posicion final se encuentra en una posicion de toma valido de la pieza
            return analizeTrajectory(pos_1, pos_2);
        else if (searchPositionInArray(possible_movements, pos_2)) //veo si la posicion final se encuentra en una posicion de movimiento valido de la pieza
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
                return searchPositionInArray(board.getPiece(pos_1).possibleTakes(pos_1), pos_2);
        return false;
    }

    /*
    ni el rey ni la torre deben haber sido movidas,
    el rey no puede estar en jaque,
    ninguna casilla de la trayectoria que recorrera el rey puede estar atacada,
    no deben haber piezas en entre el rey y la torre,
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
                        return !analizeIfItsAttacked(positions);
                    }
        }
        return false;
    }

    private boolean thereIsCheck(Position pos_1) {
        ArrayList<Position> position = new ArrayList<>();
        position.add(pos_1);
        return analizeIfItsAttacked(position);
    }

    private Position searchKing(ColorEnum c) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board.getPiece(new Position(i, j)).getNameOfPiece().equals(PieceEnum.KING) && board.getPiece(new Position(i, j)).getColorOfPiece().equals(player))
                    return new Position(i, j);
        return null;
    }

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

    private boolean analizeIfItsAttacked(ArrayList<Position> pos) {
        ColorEnum player_aux;
        ArrayList<Position> positions = new ArrayList<>();
        if (player.equals(ColorEnum.BLACK))
            player_aux = ColorEnum.WHITE;
        else
            player_aux = ColorEnum.BLACK;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board.getPiece(new Position(i, j)) != null)
                    if (board.getPiece(new Position(i, j)).getColorOfPiece().equals(player_aux))
                        positions.add(new Position(i, j));
        for (Position rival_position : positions) //analizo los movimientos de todas las fichas enemigas recopiladas y debo corroborar si atacan las posiciones pasadas por parametro
            for (Position position_to_analize : pos)
                if (isValidMovement(rival_position, position_to_analize))
                    return true;
        return false;
    }

    private boolean searchPositionInArray(ArrayList<Position> array_pos, Position pos) {
        boolean output = false;
        for (Position position : array_pos)
            if (position.getX() == pos.getX() && position.getY() == pos.getY())
                output = true;
        return output;
    }

    public UserInterface getUserInterface() {
        return user_interface;
    }

    public void playerTurnMessage() {
        user_interface.playerTurnMessage(player);
    }

    public void showBoard() {
        user_interface.showBoard(board);
    }

    public void showPiecesTaken() {
        user_interface.showPiecesTaken(black_pieces_taken, white_pieces_taken);
    }
}
