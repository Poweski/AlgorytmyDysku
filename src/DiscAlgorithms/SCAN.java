package DiscAlgorithms;

import MyObjects.Disc;
import MyObjects.Request;
import Useful.DistanceCalculator;
import Useful.StatsManager;

import java.util.ArrayList;

public class SCAN {

    private final Disc disc;
    private final ArrayList<Request> listOfDeadRequests = new ArrayList<>();
    private Request lastlyExecutedRequest = null;
    private boolean headFlag = true;
    private final int requestLifetime;
    private final int cylinderChangeTime;
    private final int blockChangeTime;
    private final int platterChangeTime;
    private int cylinderChangingNumberOfMoves = 0;
    private int platterChangingNumberOfMoves = 0;
    private int blockChangingNumberOfMoves = 0;
    private int time = 0;

    public SCAN (Disc disc, int cylChangeTime, int blkChangeTime, int pltChangeTime, int reqLifetime) {

        this.disc = disc.getSelfClone();

        cylinderChangeTime = cylChangeTime;
        blockChangeTime = blkChangeTime;
        platterChangeTime = pltChangeTime;
        requestLifetime = reqLifetime;

        System.out.println();
        carryOutTheSimulation();
        System.out.print("SCAN ");
        StatsManager.getStats(listOfDeadRequests, time, cylinderChangingNumberOfMoves,
                blockChangingNumberOfMoves, platterChangingNumberOfMoves);
    }

    private void carryOutTheSimulation () {

        Request nextRequest = findNextRequest();

        while (nextRequest != null) {

            if (time < nextRequest.getMomentOfNotification())
                time = nextRequest.getMomentOfNotification();

            time += DistanceCalculator.getDifferenceInTimeBetweenTwoRequests(lastlyExecutedRequest,
                    nextRequest, platterChangeTime, cylinderChangeTime, blockChangeTime);

            if (lastlyExecutedRequest != null) {
                cylinderChangingNumberOfMoves += Math.abs(lastlyExecutedRequest.getCylinderID() - nextRequest.getCylinderID());
                platterChangingNumberOfMoves += Math.abs(lastlyExecutedRequest.getPlatterID() - nextRequest.getPlatterID());
                blockChangingNumberOfMoves += Math.abs(lastlyExecutedRequest.getBlockID() - nextRequest.getBlockID());
            }
            else {
                cylinderChangingNumberOfMoves += nextRequest.getCylinderID();
                platterChangingNumberOfMoves += nextRequest.getPlatterID();
                blockChangingNumberOfMoves += nextRequest.getBlockID();
            }

            nextRequest.setWaitingTime(time-nextRequest.getMomentOfNotification());

            lastlyExecutedRequest = nextRequest;
            listOfDeadRequests.add(nextRequest);
            time += requestLifetime;

            nextRequest = findNextRequest();
        }
    }

    private Request findNextRequest () {

        int tempTime;
        int previousAddress = disc.getAddress(lastlyExecutedRequest);

        if (previousAddress == -1)
            previousAddress = 0;

        int lastServicedRequestAddress = previousAddress;
        int potentialAddress = previousAddress;
        int numberOfChecksForTheSameRequest = 0;
        boolean isAnyAlive = false;
        Request potentialRequest;

        while (isAnyAlive || !(numberOfChecksForTheSameRequest == 2)) {

            if(headFlag) {
                potentialAddress += 1;
                if (potentialAddress == disc.getLastAddress())
                    headFlag = false;
            }
            else {
                potentialAddress -= 1;
                if (potentialAddress == 0)
                    headFlag = true;
            }

            if (lastServicedRequestAddress == potentialAddress)
                numberOfChecksForTheSameRequest++;

            potentialRequest = disc.getRequest(potentialAddress);

            tempTime = time + DistanceCalculator.getDifferenceInTimeBetweenTwoSegments(disc.getAddress(lastlyExecutedRequest),
                    potentialAddress, disc, platterChangeTime, cylinderChangeTime, blockChangeTime);

            if (potentialRequest != null) {
                isAnyAlive = true;
                if(potentialRequest.getMomentOfNotification() <= tempTime)
                    return disc.removeRequest(potentialAddress);
            }
        }

        return null;
    }
}