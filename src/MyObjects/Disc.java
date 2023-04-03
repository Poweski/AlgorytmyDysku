package MyObjects;

public class Disc {

    private final Request[][][] disc;

    public Disc (Request[][][] requests) {
        this.disc = requests;
    }

    public Request[][][] getDisc() {
        return disc;
    }

    public int getCylindersPerPlatterNumber() {
        return disc.length;
    }

    public int getBlocksPerCylinderNumber() {
        return disc[0].length;
    }

    public int getPlattersNumber() {
        return disc[0][0].length;
    }

    public int[] getPlatterCylinderAndBlockOfGivenAddress(int address) {

        int cylinder = (address / (getPlattersNumber() * getBlocksPerCylinderNumber()));
        int block = ((address % (getPlattersNumber() * getBlocksPerCylinderNumber())) / getPlattersNumber());
        int platter = (address % getPlattersNumber());

        return new int[]{cylinder, block, platter};
    }

    public Request getRequest (int address) {
        int[] position = getPlatterCylinderAndBlockOfGivenAddress(address);
        return disc[position[0]][position[1]][position[2]];
    }

    public int getAddress (int cylinder, int block, int platter) {
        return cylinder * getBlocksPerCylinderNumber() * getPlattersNumber()
                + block * getPlattersNumber()
                + platter;
    }

    public int getAddress (Request request) {
        return getAddress(request.getCylinderID(), request.getBlockID(), request.getPlatterID());
    }

    public int getLastAddress () {
        return getCylindersPerPlatterNumber() * getBlocksPerCylinderNumber() * getPlattersNumber() - 1;
    }

    public void addRequest(int address, Request request) {
        int[] position = getPlatterCylinderAndBlockOfGivenAddress(address);
        disc[position[0]][position[1]][position[2]] = request;
    }
}