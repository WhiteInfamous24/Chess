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

    public Position getPosition1() {
        return positionOne;
    }

    public Position getPosition2() {
        return positionTwo;
    }

    public boolean getWasMovedOld() {
        return wasMovedOld;
    }
}
