package srs;

public class Movement {
    private Position position_1;
    private Position position_2;
    private boolean was_moved_old;

    public Movement(Position pos_1, Position pos_2, boolean w_m_o) {
        position_1 = pos_1;
        position_2 = pos_2;
        was_moved_old = w_m_o;
    }

    public Position getPosition1() {
        return position_1;
    }

    public Position getPosition2() {
        return position_2;
    }

    public boolean getWasMovedOld() {
        return was_moved_old;
    }
}
