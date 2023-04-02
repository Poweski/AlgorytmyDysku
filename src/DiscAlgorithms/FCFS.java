package DiscAlgorithms;

import Comparators.SortByMomentOfNotification;
import MyObjects.Disc;
import MyObjects.Request;
import Useful.TableManager;

import java.util.ArrayList;
import java.util.Arrays;

public class FCFS {

    private ArrayList<Request> queueOfRequests = new ArrayList<>();
    private ArrayList<Request> listOfDeadRequests = new ArrayList<>();

    private final int platterChangeTime;
    private final int cylinderChangeTime;
    private final int segmentChangeTime;
    private final int requestLifetime;
    private int time = 0;
    private Request lastlyExecutedRequest = null;

    public FCFS (Disc disc, int pltChangeTime, int cylChangeTime, int segChangeTime, int reqLifetime) {
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
        return queueOfRequests.remove(0);
    }
}