package DiscAlgorithms;

import Comparators.SortByMomentOfNotification;
import MyObjects.Disc;
import MyObjects.Request;
import Useful.DistanceCalculator;
import Useful.StatsManager;
import Useful.TableManager;

import java.util.ArrayList;

public class SSTF {

    private final ArrayList<Request> queueOfRequests;
    private ArrayList<Request> listOfDeadRequests = new ArrayList<>();
    private Request lastlyExecutedRequest = null;
    private final int cylinderChangeTime;
    private final int blockChangeTime;
    private final int platterChangeTime;
    private final int requestLifetime;
    private int time = 0;

    private int cylinderChangingNumberOfMoves = 0;
    private int platterChangingNumberOfMoves = 0;
    private int blockChangingNumberOfMoves = 0;

    public SSTF (Disc disc, int cylChangeTime, int blkChangeTime, int pltChangeTime, int reqLifetime) {
        queueOfRequests = TableManager.convert3DRequestTableTo1DArrayList(disc.getDisc());
        queueOfRequests.sort(new SortByMomentOfNotification());
        cylinderChangeTime = cylChangeTime;
        blockChangeTime = blkChangeTime;
        platterChangeTime = pltChangeTime;
        requestLifetime = reqLifetime;
        System.out.println();
        carryOutTheSimulation();
        StatsManager.getStats(listOfDeadRequests, time, cylinderChangingNumberOfMoves,
                blockChangingNumberOfMoves, platterChangingNumberOfMoves);
    }

    private void carryOutTheSimulation () {

        Request nextRequest = findNextRequest();

        while (nextRequest != null) {

            if (nextRequest.getMomentOfNotification() > time)
                time = nextRequest.getMomentOfNotification();

            if (lastlyExecutedRequest != null)
            {
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

        if (lastlyExecutedRequest == null)
            return queueOfRequests.remove(0);

        Request nearestRequest = queueOfRequests.get(0);
        int bestDifferenceInTime = DistanceCalculator.getDifferenceInTimeBetweenTwoRequests
                (lastlyExecutedRequest,nearestRequest,platterChangeTime,cylinderChangeTime,blockChangeTime);

        int numberOfProcessesComingBeforeActualTime = 1;

        while (numberOfProcessesComingBeforeActualTime < queueOfRequests.size() &&
                queueOfRequests.get(numberOfProcessesComingBeforeActualTime).getMomentOfNotification() <=
                        Math.max(nearestRequest.getMomentOfNotification(), time)) {

            Request potentialRequest = queueOfRequests.get(numberOfProcessesComingBeforeActualTime);
            int potentialDifferenceInTime = DistanceCalculator.getDifferenceInTimeBetweenTwoRequests
                    (lastlyExecutedRequest,potentialRequest,platterChangeTime,cylinderChangeTime,blockChangeTime);


            if (potentialDifferenceInTime < bestDifferenceInTime) {
                nearestRequest = potentialRequest;
                bestDifferenceInTime = potentialDifferenceInTime;
            }

            numberOfProcessesComingBeforeActualTime++;
        }
        queueOfRequests.remove(nearestRequest);
        return nearestRequest;
    }
}