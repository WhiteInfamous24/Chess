package srs;

import java.io.FileWriter;

import srs.util.Movement;
import srs.util.enums.InputRequestEnum;

public class DataLogger {
    
    private static DataLogger instance;

    private DataLogger() { } // VOID

    public DataLogger getInstance() {
        if (instance == null)
            instance = new DataLogger();
        return instance;
    }

    public static void logProgramStartUp_CONTROLLER() {
        writeControllerLogTXT("\n--- PROGRAM START-UP --- \n");
    }

    public static void logUserInterface_CONTROLLER() {
        writeControllerLogTXT("USER INTERFACE: " + Controller.getUserInterface().getUserInterfaceType() + "\n");
    }

    public static void logInputRequest_CONTROLLER(InputRequestEnum input) {
        writeControllerLogTXT(String.format("-> %-10s | ", input));
    }

    public static void logMovement_CONTROLLER() {
        Movement movementToLog = Game.getMovements().get(Game.getMovements().size()-1);
        if (movementToLog.getPieceAux() != null) {
            DataLogger.writeControllerLogTXT(String.format("%-5s | %-21s | %-6s | %-6s | [%d][%d] -> [%d][%d]\n",
                movementToLog.getPlayer(),
                movementToLog.getAction(),
                movementToLog.getPiece().getNameOfPiece(),
                movementToLog.getPieceAux().getNameOfPiece(),
                movementToLog.getPositionOne().getX(),
                movementToLog.getPositionOne().getY(),
                movementToLog.getPositionTwo().getX(),
                movementToLog.getPositionTwo().getY()));
        }
        else {
            DataLogger.writeControllerLogTXT(String.format("%-5s | %-21s | %-6s | null   | [%d][%d] -> [%d][%d]\n",
                movementToLog.getPlayer(),
                movementToLog.getAction(),
                movementToLog.getPiece().getNameOfPiece(),
                movementToLog.getPositionOne().getX(),
                movementToLog.getPositionOne().getY(),
                movementToLog.getPositionTwo().getX(),
                movementToLog.getPositionTwo().getY()));
        }
    }

    public static void logPawnPromotion_CONTROLLER() {
        Movement movementToLog = Game.getMovements().get(Game.getMovements().size()-1);
        DataLogger.writeControllerLogTXT(String.format("%5s | %21s | %6s | [%d][%d]\n",
            movementToLog.getPlayer(),
            movementToLog.getAction(),
            movementToLog.getPiece().getNameOfPiece(),
            movementToLog.getPositionOne().getX(),
            movementToLog.getPositionOne().getY()));
    }

    private static void writeControllerLogTXT(String toLog) {
        try {
            FileWriter file = new FileWriter("logs\\controller-log.txt", true);
            file.write(toLog);
            file.close();
        } catch (Exception e) { } // VOID
    }

    private static void writeGameLogTXT(String toLog) {
        try {
            FileWriter file = new FileWriter("logs\\game-log.txt", true);
            file.write(toLog);
            file.close();
        } catch (Exception e) { } // VOID
    }

    private static String readControllerLogTXT() {
        try {
            
        } catch (Exception e) { } // VOID
        return null;
    }
}
