package srs;

import java.io.FileWriter;

import srs.util.Movement;

public class DataLogger {
    
    private static DataLogger instance;

    private DataLogger() { } // VOID

    public DataLogger getInstance() {
        if (instance == null)
            instance = new DataLogger();
        return instance;
    }

    public static void logProgramStartUp_CONTROLLER() {
        writeLogTXT("--- PROGRAM START-UP --- \n");
    }

    public static void logUserInterface_CONTROLLER() {
        writeLogTXT("INITIAL USER INTERFACE: " + Controller.getUserInterface().getClass().getSimpleName() + "\n");
    }

    public static void logMovement_GAME() {
        Movement movementToLog = Game.getMovements().get(Game.getMovements().size()-1);
        DataLogger.writeLogTXT(String.format("%5s | %21s | %6s | [%d][%d] -> [%d][%d]\n",
            movementToLog.getPlayer(),
            movementToLog.getAction(),
            movementToLog.getPiece().getNameOfPiece(),
            movementToLog.getPositionOne().getX(),
            movementToLog.getPositionOne().getY(),
            movementToLog.getPositionTwo().getX(),
            movementToLog.getPositionTwo().getY()));
    }

    public static void logPawnPromotion_GAME() {
        Movement movementToLog = Game.getMovements().get(Game.getMovements().size()-1);
        DataLogger.writeLogTXT(String.format("%5s | %21s | %6s | [%d][%d]\n",
            movementToLog.getPlayer(),
            movementToLog.getAction(),
            movementToLog.getPiece().getNameOfPiece(),
            movementToLog.getPositionOne().getX(),
            movementToLog.getPositionOne().getY()));
    }

    private static void writeLogTXT(String toLog) {
        try {
            FileWriter file = new FileWriter("logs\\log.txt", true);
            file.write(toLog);
            file.close();
        } catch (Exception e) { } // VOID
    }

    private static void readLogTXT() {
        try {
            
        } catch (Exception e) { } // VOID
    }
}
