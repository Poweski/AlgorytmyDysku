package Main;

import DataExchange.*;
import DiscAlgorithms.FCFS;
import DiscAlgorithms.SCAN;
import DiscAlgorithms.SSTF;
import MyObjects.*;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    private static final int REQUEST_LIFETIME = 1;
    private static final int SEGMENT_CHANGE_TIME = 1;
    private static final int CYLINDER_CHANGE_TIME = 3;
    private static final int PLATTER_CHANGE_TIME = 1;

    private static final int SEGMENTS_PER_CYLINDER = 5;
    private static final int CYLINDERS_PER_PLATTER = 2;
    private static final int NUMBER_OF_PLATTERS = 3;

    private static final int NUMBER_OF_REQUESTS = 5;

    private static Disc disc;


    private static final boolean ARE_REQUESTS_COMING_SIMULTANEOUSLY = false;

    public static void main(String[] args) {

        createDisk();
        generateRequests();

        FCFS fcfs = new FCFS(disc, PLATTER_CHANGE_TIME, CYLINDER_CHANGE_TIME, SEGMENT_CHANGE_TIME, REQUEST_LIFETIME);
        SSTF sstf = new SSTF(disc, PLATTER_CHANGE_TIME, CYLINDER_CHANGE_TIME, SEGMENT_CHANGE_TIME, REQUEST_LIFETIME);
        //TODO
        SCAN scan = new SCAN(disc, PLATTER_CHANGE_TIME, CYLINDER_CHANGE_TIME, SEGMENT_CHANGE_TIME, REQUEST_LIFETIME);

    }

    private static void createDisk () {
        disc = new Disc(new Request[NUMBER_OF_PLATTERS][CYLINDERS_PER_PLATTER][SEGMENTS_PER_CYLINDER]);
    }


    //TODO można pomyśleć nad innym sposobem generowania procesów (konkretniej wybierania adresów)
    private static void generateRequests () {

        Random rng = new Random(42);

        int numberOfSegments = NUMBER_OF_PLATTERS*CYLINDERS_PER_PLATTER*SEGMENTS_PER_CYLINDER;
        int address = rng.nextInt(numberOfSegments-1);
        for (int rID = 0; rID < NUMBER_OF_REQUESTS; rID++) {
            while (disc.getRequest(address) != null) {
                address = rng.nextInt(numberOfSegments-1);
            }

            int momentOfNotification = rng.nextInt(numberOfSegments - numberOfSegments/3);
            int deadline = (rng.nextBoolean())? -1 : momentOfNotification + rng.nextInt(numberOfSegments/3);

            int[] position = disc.getPlatterCylinderAndSegmentOfGivenAddress(address);

            Request newRequest = new Request(
                                        position[0],
                                        position[1],
                                        position[2],
                                        momentOfNotification,
                                        deadline
                                    );
            disc.addRequest(address, newRequest);
        }
    }

    public static void printDisc () {
//        for ()
    }
}