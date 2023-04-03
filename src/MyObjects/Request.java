package MyObjects;

public class Request { //implements Comparable<Request> {

    private final int cylinderID;
    private final int blockID;
    private final int platterID;
    private final int momentOfNotification;
    private final int deadline;
    private int waitingTime;
    public Request(int cylinderID, int blockID, int platterID, int momentOfNotification, int deadline) {
        this.cylinderID = cylinderID;
        this.blockID = blockID;
        this.platterID = platterID;
        this.momentOfNotification = momentOfNotification;
        this.deadline = deadline;
        this.waitingTime = 0;
    }

    public Request(Request request) {
        this.cylinderID = request.getCylinderID();
        this.blockID = request.getBlockID();
        this.platterID = request.getPlatterID();
        this.momentOfNotification = request.getMomentOfNotification();
        this.deadline = request.getDeadline();
        this.waitingTime = request.getWaitingTime();
    }


    public int getCylinderID() {
        return cylinderID;
    }

    public int getBlockID() {
        return blockID;
    }

    public int getPlatterID() {
        return platterID;
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
