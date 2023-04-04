package Main;

import DiscAlgorithms.*;
import MyObjects.*;

import java.util.Random;

public class Main {

    private static final int REQUEST_LIFETIME = 1;
    private static final int PLATTER_CHANGE_TIME = 1;
    private static final int BLOCK_CHANGE_TIME = 3;
    private static final int CYLINDER_CHANGE_TIME = 10;
    private static final int BLOCKS_PER_CYLINDER = 100;
    private static final int CYLINDERS_PER_PLATTER = 100;
    private static final int NUMBER_OF_PLATTERS = 10;
    private static final int NUMBER_OF_REQUESTS = 1500;
    private static final boolean ARE_REQUESTS_COMING_SIMULTANEOUSLY = false;
    private static final boolean IS_DEADLINE_IMPORTANT = true;
    private static Disc disc;

    public static void main(String[] args) {

        disc = new Disc(new Request[CYLINDERS_PER_PLATTER][BLOCKS_PER_CYLINDER][NUMBER_OF_PLATTERS]);

        generateRequests();

        new FCFS(disc, CYLINDER_CHANGE_TIME, BLOCK_CHANGE_TIME, PLATTER_CHANGE_TIME, REQUEST_LIFETIME);
        new SSTF(disc, CYLINDER_CHANGE_TIME, BLOCK_CHANGE_TIME, PLATTER_CHANGE_TIME, REQUEST_LIFETIME);
        new SCAN(disc, CYLINDER_CHANGE_TIME, BLOCK_CHANGE_TIME, PLATTER_CHANGE_TIME, REQUEST_LIFETIME);
        new C_SCAN(disc, CYLINDER_CHANGE_TIME, BLOCK_CHANGE_TIME, PLATTER_CHANGE_TIME, REQUEST_LIFETIME);
        new EDF(disc, CYLINDER_CHANGE_TIME, BLOCK_CHANGE_TIME, PLATTER_CHANGE_TIME, REQUEST_LIFETIME);
        new FD_SCAN(disc, CYLINDER_CHANGE_TIME, BLOCK_CHANGE_TIME, PLATTER_CHANGE_TIME, REQUEST_LIFETIME);
    }

    private static void generateRequests () {

        Random rng = new Random();

        int numberOfSegments = NUMBER_OF_PLATTERS * BLOCKS_PER_CYLINDER * CYLINDERS_PER_PLATTER;
        int address = rng.nextInt(numberOfSegments-1);

        for (int rID = 0; rID < NUMBER_OF_REQUESTS; rID++) {

            while (disc.getRequest(address) != null)
                address = rng.nextInt(numberOfSegments-1);

            int momentOfNotification;
            if (ARE_REQUESTS_COMING_SIMULTANEOUSLY)
                momentOfNotification = 0;
            else
                momentOfNotification = rng.nextInt(numberOfSegments - numberOfSegments/3);

            double deadline;
            if (!IS_DEADLINE_IMPORTANT)
                deadline = Double.POSITIVE_INFINITY;
            else
//                deadline = (rng.nextBoolean()) ? Double.POSITIVE_INFINITY :
//                    momentOfNotification + rng.nextInt(numberOfSegments/3);
                deadline = momentOfNotification + rng.nextInt(numberOfSegments/3);

            int[] position = disc.getCylinderBlockAndPlatterOfGivenAddress(address);

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
}