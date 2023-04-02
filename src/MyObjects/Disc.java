package MyObjects;

public class Disc {

    private final Request[][][] disc;

    public Disc (Request[][][] requests) {
        this.disc = requests;
    }

    public Request[][][] getDisc() {
        return disc;
    }

    public int getPlattersNumber () {
        return disc.length;
    }

    public int getCylindersPerPlatterNumber () {
        return disc[0].length;
    }

    public int getSegmentsPerCylinderNumber () {
        return disc[0][0].length;
    }

    public int[] getPlatterCylinderAndSegmentOfGivenAddress (int address) {
        int platter = address/(getCylindersPerPlatterNumber()*getSegmentsPerCylinderNumber());
        int cylinder = (address%(getCylindersPerPlatterNumber()*getSegmentsPerCylinderNumber()))/getSegmentsPerCylinderNumber();
        int segment = (address%(getCylindersPerPlatterNumber()*getSegmentsPerCylinderNumber()))%getSegmentsPerCylinderNumber();
        return new int[]{platter, cylinder, segment};
    }

    public Request getRequest (int address) {
        int[] position = getPlatterCylinderAndSegmentOfGivenAddress(address);
        return disc[position[0]][position[1]][position[2]];
    }

    public int getAddress (int platter, int cylinder, int addres) {
        return platter*getCylindersPerPlatterNumber() + cylinder*getSegmentsPerCylinderNumber() + addres;
    }

    public int getAddress (Request request) {
        return getAddress(request.getPlatterID(), request.getCylinderID(), request.getSegmentID());
    }

    public int getLastAddress () {
        return getPlattersNumber()*getCylindersPerPlatterNumber()*getSegmentsPerCylinderNumber() - 1;
    }

    public void addRequest(int address, Request rqst) {
        int[] position = getPlatterCylinderAndSegmentOfGivenAddress(address);
        disc[position[0]][position[1]][position[2]] = rqst;
    }


}