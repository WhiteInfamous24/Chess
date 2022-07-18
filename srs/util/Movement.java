package srs.util;

public class Movement {
    
    private Position positionOne;
    private Position positionTwo;
    private boolean wasMovedOld;

    public Movement(Position positionOne, Position positionTwo, boolean wasMovedOld) {
        this.positionOne = positionOne;
        this.positionTwo = positionTwo;
        this.wasMovedOld = wasMovedOld;
    }

    // GETTERS & SETTERS

    public Position getPositionOne() {
        return positionOne;
    }

    public Position getPositionTwo() {
        return positionTwo;
    }

    public boolean getWasMovedOld() {
        return wasMovedOld;
    }
}
