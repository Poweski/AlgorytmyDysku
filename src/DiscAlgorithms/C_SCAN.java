package DiscAlgorithms;

import MyObjects.Disc;
import MyObjects.Request;
import Useful.DistanceCalculator;

import java.util.ArrayList;

public class C_SCAN {

    private final Disc disc;
    private final int cylinderChangeTime;
    private final int blockChangeTime;
    private final int platterChangeTime;
    private final int requestLifetime;
    private int time = 0;
    private Request lastlyExecutedRequest = null;
    private ArrayList<Request> listOfDeadRequests = new ArrayList<>();

    public C_SCAN (Disc disc, int cylChangeTime, int blkChangeTime, int pltChangeTime, int reqLifetime) {
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

        while (nextRequest != null) {

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

    private Request findNextRequest () {

        int lastCylinderID = 0;
        int lastBlockID = 0;
        int lastPlatterID = 0;
        int tempTime = time;

        if (lastlyExecutedRequest != null) {
            lastCylinderID = lastlyExecutedRequest.getCylinderID();
            lastBlockID = lastlyExecutedRequest.getBlockID();
            lastPlatterID = lastlyExecutedRequest.getPlatterID();
        }

        int actualAddress = disc.getAddress(lastPlatterID, lastCylinderID, lastBlockID);

        while (actualAddress++ <= disc.getLastAddress()) {
            int potentialAddress = actualAddress++;
            Request potentialRequest = disc.getRequest(potentialAddress);
            if (potentialRequest != null && potentialRequest.getMomentOfNotification() < tempTime) {
                time += DistanceCalculator.getDifferenceInTimeBetweenTwoSegments
                        (actualAddress, potentialAddress,disc,platterChangeTime,cylinderChangeTime,blockChangeTime);
                disc.addRequest(potentialAddress, null);
                return potentialRequest;
            }
            time += DistanceCalculator.getDifferenceInTimeBetweenTwoSegments
                    (actualAddress, potentialAddress,disc,platterChangeTime,cylinderChangeTime,blockChangeTime);
        }

        return null;
    }
}