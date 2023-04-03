package DiscAlgorithms;

import MyObjects.Disc;
import MyObjects.Request;
import Useful.DistanceCalculator;

import java.util.ArrayList;

public class SCAN {

    private final Disc disc;
    private final int cylinderChangeTime;
    private final int blockChangeTime;
    private final int platterChangeTime;
    private final int requestLifetime;
    private int time = 0;
    private boolean headFlag = true;
    private Request lastlyExecutedRequest = null;
    private ArrayList<Request> listOfDeadRequests = new ArrayList<>();

    public SCAN (Disc disc, int cylChangeTime, int blkChangeTime, int pltChangeTime, int reqLifetime) {
        this.disc = disc;
        cylinderChangeTime = cylChangeTime;
        blockChangeTime = blkChangeTime;
        platterChangeTime = pltChangeTime;
        requestLifetime = reqLifetime;
        System.out.println();
        carryOutTheSimulation();
    }

    private void carryOutTheSimulation () {

        Request nextRequest = findNextRequest();

        while (nextRequest != null | !isArrayEmpty()) {

            while (nextRequest == null) {
                time++;
                nextRequest = findNextRequest();
            }

            if (nextRequest.getMomentOfNotification() > time)
                time = nextRequest.getMomentOfNotification();

            if (lastlyExecutedRequest != null)
                time += DistanceCalculator.getDifferenceInTimeBetweenTwoRequests
                        (lastlyExecutedRequest,nextRequest,platterChangeTime,cylinderChangeTime,blockChangeTime);

            nextRequest.setWaitingTime(time-nextRequest.getMomentOfNotification());
            time += requestLifetime;

            lastlyExecutedRequest = nextRequest;
            listOfDeadRequests.add(nextRequest);

            nextRequest = findNextRequest();
        }
    }

    //TODO Naprawić zliczanie czasu.
    private Request findNextRequest () {

        int lastCylinderID = 0;
        int lastBlockID = 0;
        int lastPlatterID = 0;

        if (lastlyExecutedRequest != null) {
            lastCylinderID = lastlyExecutedRequest.getCylinderID();
            lastBlockID = lastlyExecutedRequest.getBlockID();
            lastPlatterID = lastlyExecutedRequest.getPlatterID();
        }

        int actualAddress = disc.getAddress(lastPlatterID, lastCylinderID, lastBlockID);

        if (headFlag) {
            while (actualAddress++ <= disc.getLastAddress()) {
                int potentialAddress = actualAddress++;
                Request potentialRequest = disc.getRequest(potentialAddress);
                if (potentialRequest != null && potentialRequest.getMomentOfNotification() < time) {
                    time += DistanceCalculator.getDifferenceInTimeBetweenTwoSegments
                            (actualAddress, potentialAddress,disc,platterChangeTime,cylinderChangeTime,blockChangeTime);
                    disc.addRequest(potentialAddress, null);
                    return potentialRequest;
                }
                time += DistanceCalculator.getDifferenceInTimeBetweenTwoSegments
                        (actualAddress, potentialAddress,disc,platterChangeTime,cylinderChangeTime,blockChangeTime);
                //lastlyExecutedRequest.setCylinderID();
                //moveHeadToRightEdge();
            }
            headFlag = false;
        }
        else {
            while (actualAddress-- >= 0) {
                int potentialAddress = actualAddress--;
                Request potentialRequest = disc.getRequest(potentialAddress);
                if (potentialRequest != null && potentialRequest.getMomentOfNotification() < time) {
                    time += DistanceCalculator.getDifferenceInTimeBetweenTwoSegments
                            (actualAddress, potentialAddress,disc,platterChangeTime,cylinderChangeTime,blockChangeTime);
                    disc.addRequest(potentialAddress, null);
                    return potentialRequest;
                }
                time += DistanceCalculator.getDifferenceInTimeBetweenTwoSegments
                        (actualAddress, potentialAddress,disc,platterChangeTime,cylinderChangeTime,blockChangeTime);
                //moveHeadToLeftEdge();
            }
            headFlag = true;
        }
        return null;
    }

    public boolean isArrayEmpty() {
        int tempAddress = 0;
        boolean loopFlag = false;
        while (tempAddress <= disc.getLastAddress())
            if (disc.getRequest(tempAddress) != null)
                loopFlag = true;
        return loopFlag;
    }
}