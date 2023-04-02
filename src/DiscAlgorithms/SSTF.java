package DiscAlgorithms;

import Comparators.SortByMomentOfNotification;
import MyObjects.Disc;
import MyObjects.Request;
import Useful.TableManager;

import java.util.ArrayList;

public class SSTF {

    private ArrayList<Request> queueOfRequests;
    private ArrayList<Request> listOfDeadRequests = new ArrayList<>();
    private final int platterChangeTime;
    private final int cylinderChangeTime;
    private final int segmentChangeTime;
    private final int requestLifetime;
    private int time = 0;
    private Request lastlyExecutedRequest = null;

    public SSTF (Disc disc, int pltChangeTime, int cylChangeTime, int segChangeTime, int reqLifetime) {
        queueOfRequests = TableManager.convert3DRequestTableTo1DArrayList(disc.getDisc());
        queueOfRequests.sort(new SortByMomentOfNotification());
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
        if (queueOfRequests.size() == 0) {
            return null;
        }

        if (lastlyExecutedRequest == null) {
            return queueOfRequests.remove(0);
        }

        Request nearestRequest = queueOfRequests.get(0);
        int bestDifferenceInTime = getDifferenceInTimeBetweenTwoRequests(lastlyExecutedRequest, nearestRequest);
        int numberOfProcessesComingBeforeActualTime = 1;

        while (numberOfProcessesComingBeforeActualTime < queueOfRequests.size() &&
                queueOfRequests.get(numberOfProcessesComingBeforeActualTime).getMomentOfNotification() <=
                Math.max(nearestRequest.getMomentOfNotification(), time)) {
            Request potentialRequest = queueOfRequests.get(numberOfProcessesComingBeforeActualTime);
            int potentialDifferenceInTime = getDifferenceInTimeBetweenTwoRequests(lastlyExecutedRequest, potentialRequest);
            if (potentialDifferenceInTime < bestDifferenceInTime) {
                nearestRequest = potentialRequest;
                bestDifferenceInTime = potentialDifferenceInTime;
            }
            numberOfProcessesComingBeforeActualTime++;
        }
        queueOfRequests.remove(nearestRequest);
        return nearestRequest;
    }

    private int getDifferenceInTimeBetweenTwoRequests (Request req1, Request req2) {
        return Math.abs(req1.getPlatterID() - req2.getPlatterID())*platterChangeTime +
                + Math.abs(req1.getCylinderID()-req2.getCylinderID())*cylinderChangeTime +
                + Math.abs(req1.getSegmentID()-req2.getCylinderID())*segmentChangeTime;
    }

}
