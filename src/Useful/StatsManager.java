package Useful;

import MyObjects.Request;

import java.util.ArrayList;

public class StatsManager {

    public static void getStats(ArrayList<Request> requestsList, int time, int cylinderChangeMoves,
                                int blockChangeMoves, int platterChangesMoves) {

        int numberOfProcesses = requestsList.size();
        long wholeWaitingTime = 0;
        int servedBeforeDeadline = 0;

        for (Request rqst: requestsList) {
            wholeWaitingTime += rqst.getWaitingTime();
            int momentOfResponse = rqst.getMomentOfNotification() + rqst.getWaitingTime();
            if(momentOfResponse <= rqst.getDeadline())
                servedBeforeDeadline++;
        }

        System.out.println("RESULTS:");
        System.out.println("Total time: " + time/100_000f);
        System.out.println("Average waiting for response time: " + (wholeWaitingTime/numberOfProcesses)/100_000f);
        System.out.println("Percentage of processes served before deadline: " + servedBeforeDeadline*100f/numberOfProcesses + "%");
        System.out.println("Moves in order to change platter: " + platterChangesMoves);
        System.out.println("Moves in order to change cylinder: " + cylinderChangeMoves);
        System.out.println("Moves in order to change block: " + blockChangeMoves);
    }
}