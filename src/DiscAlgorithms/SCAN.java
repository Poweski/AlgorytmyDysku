package DiscAlgorithms;

import Comparators.SortByMomentOfNotification;
import MyObjects.Disc;
import MyObjects.Request;
import Useful.TableManager;

import java.util.ArrayList;

public class SCAN {

    private final Disc disc;
    private final int platterChangeTime;
    private final int cylinderChangeTime;
    private final int segmentChangeTime;
    private final int requestLifetime;
    private int time = 0;
    private Request lastlyExecutedRequest = null;
    private ArrayList<Request> listOfDeadRequests = new ArrayList<>();

    public SCAN (Disc disc, int pltChangeTime, int cylChangeTime, int segChangeTime, int reqLifetime) {
        this.disc = disc;

        platterChangeTime = pltChangeTime;
        segmentChangeTime = segChangeTime;
        cylinderChangeTime = cylChangeTime;
        requestLifetime = reqLifetime;
        System.out.println();
        carryOutTheSimulation();
    }

    private void carryOutTheSimulation () {
        Request nextRequest = findNextRequest();
        int platterJump;
        int cylinderJump;
        int segmentJump;
        while (nextRequest != null){

            if (nextRequest.getMomentOfNotification() > time) {
                time = nextRequest.getMomentOfNotification();
            }

            platterJump = 0;
            cylinderJump = 0;
            segmentJump = 0;
            if (lastlyExecutedRequest != null) {
                platterJump = Math.abs(lastlyExecutedRequest.getPlatterID() - nextRequest.getPlatterID());
                cylinderJump = Math.abs(lastlyExecutedRequest.getCylinderID() - nextRequest.getCylinderID());
                segmentJump = Math.abs(lastlyExecutedRequest.getSegmentID() - nextRequest.getSegmentID());
            }

            time += platterJump*platterChangeTime;
            time += cylinderJump*cylinderChangeTime;
            time += segmentJump*segmentChangeTime;
            nextRequest.setWaitingTime(time-nextRequest.getMomentOfNotification());
            time += requestLifetime;
            lastlyExecutedRequest = nextRequest;
            listOfDeadRequests.add(nextRequest);
            nextRequest = findNextRequest();
        }
    }

    private Request findNextRequest () {

        int lastPlatterID = 0;
        int lastCylinderID = 0;
        int lastSegmentID = 0;
        int tempTime = time;

        if (lastlyExecutedRequest != null) {
            lastPlatterID = lastlyExecutedRequest.getPlatterID();
            lastCylinderID = lastlyExecutedRequest.getCylinderID();
            lastSegmentID = lastlyExecutedRequest.getSegmentID();
        }

        int actualAddress = disc.getAddress(lastPlatterID, lastCylinderID, lastSegmentID);

        while (getClosestSegmentAddressFromRight(actualAddress) != -1) {
            int potentialAddress = getClosestSegmentAddressFromRight(actualAddress);
            Request potentialRequest = disc.getRequest(potentialAddress);
            if (potentialRequest != null && potentialRequest.getMomentOfNotification() < tempTime) {
                disc.addRequest(potentialAddress, null);
                return potentialRequest;
            }
            tempTime += getDifferenceInTimeBetweenTwoSegments(actualAddress, potentialAddress);
        }

        while (getClosestSegmentAddressFromLeft(actualAddress) != -1) {
            int potentialAddress = getClosestSegmentAddressFromLeft(actualAddress);
            Request potentialRequest = disc.getRequest(potentialAddress);
            if (potentialRequest != null && potentialRequest.getMomentOfNotification() < tempTime) {
                disc.addRequest(potentialAddress, null);
                return potentialRequest;
            }
            tempTime += getDifferenceInTimeBetweenTwoSegments(actualAddress, potentialAddress);
        }


        return null;
    }

    private int getClosestSegmentAddressFromRight (int address) {
        int[] position = disc.getPlatterCylinderAndSegmentOfGivenAddress(address);
        int platter = position[0];
        int cylinder = position[1];
        int segment = position[2];


        if (platter+1 == disc.getPlattersNumber()) {
            if(segment+1 == disc.getSegmentsPerCylinderNumber()) {
                segment
            }
            else {
                segment++;
            }
        }
        else {
            platter++;
        }
//        if (++segment == disc.getSegmentsPerCylinderNumber()) {
//            segment = 0;
//            if (++cylinder == disc.getCylindersPerPlatterNumber()) {
//                cylinder = 0;
//                if (++platter == disc.getPlattersNumber()) {
//                    return -1;
//                }
//            }
//        }

        return disc.getAddress(platter, cylinder, segment);
    }

    private int getClosestSegmentAddressFromLeft (int address) {
        int[] position = disc.getPlatterCylinderAndSegmentOfGivenAddress(address);
        int platter = position[0];
        int cylinder = position[1];
        int segment = position[2];

        if (--segment == -1) {
            segment = disc.getSegmentsPerCylinderNumber()-1;
            if (--cylinder == -1) {
                cylinder = disc.getCylindersPerPlatterNumber()-1;
                if (--platter == -1) {
                    return -1;
                }
            }
        }

        return disc.getAddress(platter, cylinder, segment);
    }

    private int getDifferenceInTimeBetweenTwoSegments (int address1, int address2) {
        int[] pos1 = disc.getPlatterCylinderAndSegmentOfGivenAddress(address1);
        int platterID1 = pos1[0];
        int cylinderID1 = pos1[1];
        int segmentID1 = pos1[2];
        int[] pos2 = disc.getPlatterCylinderAndSegmentOfGivenAddress(address2);
        int platterID2 = pos2[0];
        int cylinderID2 = pos2[1];
        int segmentID2 = pos2[2];
        return Math.abs(platterID1 - platterID2)*platterChangeTime +
                + Math.abs(cylinderID1-cylinderID2)*cylinderChangeTime +
                + Math.abs(segmentID1-segmentID2)*segmentChangeTime;
    }

}
