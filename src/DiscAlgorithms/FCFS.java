package DiscAlgorithms;

import Comparators.SortByMomentOfNotification;
import MyObjects.Disc;
import MyObjects.Request;
import Useful.DistanceCalculator;
import Useful.StatsManager;
import Useful.TableManager;

import java.util.ArrayList;

public class FCFS {

    private Request lastlyExecutedRequest = null;
    private final ArrayList<Request> listOfDeadRequests = new ArrayList<>();
    private final ArrayList<Request> queueOfRequests;
    private final int requestLifetime;
    private final int cylinderChangeTime;
    private final int blockChangeTime;
    private final int platterChangeTime;
    private int cylinderChangingNumberOfMoves = 0;
    private int platterChangingNumberOfMoves = 0;
    private int blockChangingNumberOfMoves = 0;
    private int time = 0;

    public FCFS (Disc disc, int cylChangeTime, int blkChangeTime, int pltChangeTime, int reqLifetime) {

        queueOfRequests = TableManager.convert3DRequestTableTo1DArrayList(disc.getDisc());
        queueOfRequests.sort(new SortByMomentOfNotification());

        blockChangeTime = blkChangeTime;
        cylinderChangeTime = cylChangeTime;
        platterChangeTime = pltChangeTime;
        requestLifetime = reqLifetime;

        System.out.print("\nFCFS ");
        carryOutTheSimulation();
        StatsManager.getStats(listOfDeadRequests, time, cylinderChangingNumberOfMoves,
                blockChangingNumberOfMoves, platterChangingNumberOfMoves);
    }

    private void carryOutTheSimulation () {

        Request nextRequest = findNextRequest();

        while (nextRequest != null) {

            if (nextRequest.getMomentOfNotification() > time)
                time = nextRequest.getMomentOfNotification();

            if (lastlyExecutedRequest != null) {
                time += DistanceCalculator.getDifferenceInTimeBetweenTwoRequests
                        (lastlyExecutedRequest,nextRequest,platterChangeTime,cylinderChangeTime,blockChangeTime);
                cylinderChangingNumberOfMoves += Math.abs(lastlyExecutedRequest.getCylinderID() - nextRequest.getCylinderID());
                platterChangingNumberOfMoves += Math.abs(lastlyExecutedRequest.getPlatterID() - nextRequest.getPlatterID());
                blockChangingNumberOfMoves += Math.abs(lastlyExecutedRequest.getBlockID() - nextRequest.getBlockID());
            }

            nextRequest.setWaitingTime(time-nextRequest.getMomentOfNotification());

            time += requestLifetime;
            lastlyExecutedRequest = nextRequest;
            listOfDeadRequests.add(nextRequest);

            nextRequest = findNextRequest();
        }
    }

    private Request findNextRequest () {
        if (queueOfRequests.size() == 0)
            return null;
        return queueOfRequests.remove(0);
    }
}