package MyObjects;

import java.util.Objects;
import java.util.Random;

public class Request { //implements Comparable<Request> {

    private final int platterID;
    private final int cylinderID;
    private final int segmentID;
    private final int momentOfNotification;
    private final int deadline;
    private int waitingTime;

    public Request(int platterID, int cylinderID, int segmentID, int momentOfNotification, int deadline) {
        this.platterID = platterID;
        this.cylinderID = cylinderID;
        this.segmentID = segmentID;
        this.momentOfNotification = momentOfNotification;
        this.deadline = deadline;
        this.waitingTime = 0;
    }

    public Request(Request request) {
        this.platterID = request.getPlatterID();
        this.cylinderID = request.getCylinderID();
        this.segmentID = request.getSegmentID();
        this.momentOfNotification = request.getMomentOfNotification();
        this.deadline = request.getDeadline();
        this.waitingTime = request.getWaitingTime();
    }


    public int getPlatterID() {
        return platterID;
    }

    public int getCylinderID() {
        return cylinderID;
    }

    public int getSegmentID() {
        return segmentID;
    }

    public int getMomentOfNotification() {
        return momentOfNotification;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int value) {
        this.waitingTime = value;
    }

//    public static MyProcess generateProcess(int processNumber, int stringNumber, int range,
//                                            int threshold, int numberOfProcesses) {
//        Random generator = new Random();
//        int length = generatePhaseLength(range,threshold);
//        int moment = generator.nextInt(numberOfProcesses*(range+threshold)/4);
//        return new MyProcess(processNumber,stringNumber,length,moment);
//    }
//    public static int generatePhaseLength(int range, int threshold) {
//        Random generator = new Random();
//        int ratio = generator.nextInt(4);
//        int limit = threshold + Math.abs(range-threshold)/5;
//
//        if (ratio == 0)
//            return generator.nextInt(limit, range);
//        else
//            return generator.nextInt(threshold, limit);
//    }
//
//    public static int generatePhaseLength2(int range, int threshold, int limit) {
//        Random generator = new Random();
//
//        int ratio = generator.nextInt(4);
//
//        if (ratio == 0)
//            return generator.nextInt(limit, range);
//        else
//            return generator.nextInt(threshold, limit);
//    }

//    @Override
//    public int compareTo(Request other) {
//        return Integer.compare(other.getMomentOfNotification(), momentOfNotification);
//    }
//    @Override
//    public boolean equals(Object other) {
//        if (this == other) return true;
//        if (other == null || getClass() != other.getClass())
//            return false;
//        Request otherRequest = (Request) other;
//        return momentOfNotification == otherRequest.getMomentOfNotification()
//                && position == otherRequest.getPosition()
//                && deadline == otherRequest.getDeadline();
//    }
//    @Override
//    public int hashCode() {
//        return Objects.hash(momentOfNotification,position,deadline);
//    }
//    @Override
//    public String toString() {
//        return String.format("%d-%d-%d-%d", position,momentOfNotification,deadline,waitingTime);
//    }
}
