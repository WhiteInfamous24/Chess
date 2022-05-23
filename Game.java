import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Board board;
    private ColorEnum player;

    public Game() {
        board = new Board();
        player = ColorEnum.WHITE;
    }

    public void initializePieces() {
        for(int i = 0; i < 8; i++) {
            board.setPiece(new Pawn(ColorEnum.BLACK), new Position(i, 1));
            board.setPiece(new Pawn(ColorEnum.WHITE), new Position(i, 6));
        }
        board.setPiece(new Rook(ColorEnum.BLACK), new Position(0, 0));
        board.setPiece(new Rook(ColorEnum.WHITE), new Position(0, 7));
        board.setPiece(new Rook(ColorEnum.BLACK), new Position(7, 0));
        board.setPiece(new Rook(ColorEnum.WHITE), new Position(7, 7));
        board.setPiece(new Knight(ColorEnum.BLACK), new Position(1, 0));
        board.setPiece(new Knight(ColorEnum.WHITE), new Position(1, 7));
        board.setPiece(new Knight(ColorEnum.BLACK), new Position(6, 0));
        board.setPiece(new Knight(ColorEnum.WHITE), new Position(6, 7));
        board.setPiece(new Bishop(ColorEnum.BLACK), new Position(2, 0));
        board.setPiece(new Bishop(ColorEnum.WHITE), new Position(2, 7));
        board.setPiece(new Bishop(ColorEnum.BLACK), new Position(5, 0));
        board.setPiece(new Bishop(ColorEnum.WHITE), new Position(5, 7));
        board.setPiece(new Queen(ColorEnum.BLACK), new Position(4, 0));
        board.setPiece(new King(ColorEnum.WHITE), new Position(4, 7));
        board.setPiece(new Queen(ColorEnum.WHITE), new Position(3, 7));
        board.setPiece(new King(ColorEnum.BLACK), new Position(3, 0));
    }

    public void changePlayer() {
        if(player == ColorEnum.WHITE)
            player = ColorEnum.BLACK;
        else
            player = ColorEnum.WHITE;
    }

    public void movePiece() {
        Position position_1;
        Position position_2;
        boolean invalid_movement;
        System.out.println("\nTURNO DE " + player);
        do {
            System.out.print("\nPieza a mover [fila][columna]: ");
            position_1 = requestPosition();
            if(board.getPiece(position_1) != null)
                invalid_movement = board.getPiece(position_1).getColorOfPiece() != player;
            else
                invalid_movement = true;
            if(invalid_movement)
                System.out.println("-POSICION NO VALIDA-");
        } while(invalid_movement);
        do {
            System.out.print("Mover a casilla [fila][columna]: ");
            position_2 = requestPosition();
            if(board.getPiece(position_2) != null)
                invalid_movement = board.getPiece(position_2).getColorOfPiece() == player || !isValidMovement(position_1, position_2);
            else
                invalid_movement = !isValidMovement(position_1, position_2);
            if(invalid_movement)
                System.out.println("-POSICION NO VALIDA-");
        } while(invalid_movement);
        board.movePiece(position_1, position_2);
    }

    private Position requestPosition() {
        Scanner scanner = new Scanner(System.in);
        String input = " ";
        do
            input = scanner.nextLine();
        while(input.length() != 2 || input.charAt(0) < 97 || input.charAt(0) > 104 || input.charAt(1) < 48 || input.charAt(1) > 56);
        //scanner.close();
        return new Position(input.charAt(1)-49, input.charAt(0)-97);
    }

    private boolean isValidMovement(Position pos_1, Position pos_2) {
        ArrayList<Position> possible_movements = board.getPiece(pos_1).possibleMovements(pos_1);
        if(searchPositionInArray(possible_movements, pos_2)) //veo si la posicion final se encuentra en una posicion valida de la pieza
            if(board.getPiece(pos_1).getLongMovement()) { //veo si la pieza realiza movimientos de trayectoria
                boolean no_obstruction = true;
                int x_1 = pos_1.getX();
                int y_1 = pos_1.getY();
                int x_2 = pos_2.getX();
                int y_2 = pos_2.getY();
                int MAX;
                if(x_2 == x_1) { //verifico si me muevo unicamente sobre el eje Y
                    if(y_2 > y_1) { //analizo movimiento para +Y
                        MAX = y_2-y_1; //seteo el limite de iteraciones para no salirme del tablero para +Y
                        for(int i = 1; i < MAX; i++) { //analizo movimiento para +Y
                            if(board.getPiece(new Position(x_1, y_1+i)) != null && y_1+i != y_2)
                                no_obstruction = false;
                            if(y_1+i == y_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                                return no_obstruction;
                        }
                    }
                    else { //analizo movimiento para -Y
                        MAX = y_1-y_2; //seteo el limite de iteraciones para no salirme del tablero para -Y
                        for(int i = 1; i < MAX; i++) {
                            if(board.getPiece(new Position(x_1, y_1-i)) != null && y_1-i != y_2)
                                no_obstruction = false;
                            if(y_1-i == y_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                                return no_obstruction;
                        }
                    }
                }
                else if(y_2 == y_1) { //verifico si me muevo unicamente sobre el eje X
                    if(x_2 > x_1) { //analizo movimiento para +X
                        MAX = x_2-x_1; //seteo el limite de iteraciones para no salirme del tablero para +X
                        for(int i = 1; i < MAX; i++) { //analizo movimiento para +X
                            if(board.getPiece(new Position(x_1+i, y_1)) != null && x_1+i != x_2)
                                no_obstruction = false;
                            if(x_1+i == x_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                                return no_obstruction;
                        }
                    }
                    else { //analizo movimiento para -X
                        MAX = x_1-x_2; //seteo el limite de iteraciones para no salirme del tablero para -X
                        for(int i = 1; i < MAX; i++) {
                            if(board.getPiece(new Position(x_1-i, y_1)) != null && x_1-i != x_2)
                                no_obstruction = false;
                            if(x_1-i == x_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                                return no_obstruction;
                        }
                    }
                }
                else if(x_2 > x_1 && y_2 > y_1) { //verifico si me muevo unicamente en direccion +X+Y
                    if(8-x_1 < 8-y_1) //seteo el limite de iteraciones para no salirme del tablero buscando el eje mas chico
                        MAX = x_2-x_1;
                    else
                        MAX = y_2-y_1;
                    for(int i = 1; i < MAX; i++) { //analizo movimiento
                        if(board.getPiece(new Position(x_1+i, y_1+i)) != null && y_1+i != y_2)
                            no_obstruction = false;
                        if(y_1+i == y_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                            return no_obstruction;
                    }
                }
                else if(x_2 > x_1 && y_2 < y_1) { //verifico si me muevo unicamente en el eje +X-Y
                    if(8-x_1 < y_1+1) //seteo el limite de iteraciones para no salirme del tablero buscando el eje mas chico
                        MAX = x_2-x_1;
                    else
                        MAX = y_1-y_2;
                    for(int i = 1; i < MAX; i++) { //analizo movimiento
                        if(board.getPiece(new Position(x_1+i, y_1-i)) != null && y_1-i != y_2)
                            no_obstruction = false;
                        if(y_1-i == y_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                            return no_obstruction;
                    }
                }
                else if(x_2 < x_1 && y_2 > y_1) { //verifico si me muevo unicamente en el eje -X+Y
                    if(8-x_1 < 8-y_1) //seteo el limite de iteraciones para no salirme del tablero buscando el eje mas chico
                        MAX = x_1-x_2;
                    else
                        MAX = y_2-y_1;
                    for(int i = 1; i < MAX; i++) { //analizo movimiento
                        if(board.getPiece(new Position(x_1-i, y_1+i)) != null && y_1+i != y_2)
                            no_obstruction = false;
                        if(y_1+i == y_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                            return no_obstruction;
                    }
                }
                else if(x_2 < x_1 && y_2 < y_1) { //verifico si me muevo unicamente en el eje -X-Y
                    if(8-x_1 < 8-y_1) //seteo el limite de iteraciones para no salirme del tablero buscando el eje mas chico
                        MAX = x_1-x_2;
                    else
                        MAX = y_1-y_2;
                    for(int i = 1; i < MAX; i++) { //analizo movimiento
                        if(board.getPiece(new Position(x_1-i, y_1-i)) != null && y_1-i != y_2)
                            no_obstruction = false;
                        if(y_1-i == y_2) //analizo si llego a la posicion final y se retorna si hay obstruccion
                            return no_obstruction;
                    }
                }
                return no_obstruction;
            }
            else
                return true;
        else
            return false;
    }

    private boolean searchPositionInArray(ArrayList<Position> array_pos, Position pos) {
        boolean output = false;
        for(Position position : array_pos)
            if(position.getX() == pos.getX() && position.getY() == pos.getY())
                output = true;
        return output;
    }

    public void showBoard() {
        char row = 'A';
        System.out.println("");
        for(int i = 0; i < 8; i++) {
            if(i == 0)
                System.out.println("||===||===============================================================================================||");
            else
                System.out.println("||---||-----------|-----------|-----------|-----------|-----------|-----------|-----------|-----------||");
            System.out.print("||   |");
            for(int j = 0; j < 8; j++) { 
                System.out.print("|");
                if(board.getPiece(new Position(j, i)) != null)
                    System.out.printf(" %-10s", board.getPiece(new Position(j, i)).getNameOfPiece());
                else
                    System.out.printf("%11s", " ");
            }
            System.out.println("||");
            System.out.print("|| " + row +" |");
            row++;
            for(int j = 0; j < 8; j++) {
                System.out.print("|");
                if(board.getPiece(new Position(j, i)) != null)
                    System.out.printf(" %-10s", board.getPiece(new Position(j, i)).getColorOfPiece());
                else
                    System.out.printf("%11s", " ");
            }
            System.out.println("||");
            System.out.println("||   ||           |           |           |           |           |           |           |           ||");
        }
        System.out.println("||===||===============================================================================================||");
        System.out.println("||   ||     1     |     2     |     3     |     4     |     5     |     6     |     7     |     8     ||");
        System.out.println("||===||===============================================================================================||");
    }
}
