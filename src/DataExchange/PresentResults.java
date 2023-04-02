//package DataExchange;
//
//public class PresentResults {
//    public static double[] collectWaitingTime(Database database) {
//        double[] results = new double[database.getNumberOfStrings()];
//        for (int i=0; i<database.getNumberOfStrings(); i++) {
//            int collectedTotalWaitingTime = 0;
//            for (int j=0; j<database.getNumberOfProcesses(); j++)
//                collectedTotalWaitingTime += database.getMainArray()[i][j].getWaitingTime();
//            results[i] = ((double)collectedTotalWaitingTime)/((double)database.getNumberOfProcesses());
//        }
//        return results;
//    }
//    public static double collectAverage(double[] array) {
//        double sum = 0;
//        for (double value : array) sum += value;
//        return sum/array.length;
//    }
//    public static double[] collectPhaseLength(Database database) {
//        double[] results = new double[database.getNumberOfStrings()];
//        for (int i=0; i<database.getNumberOfStrings(); i++) {
//            int collectedTotalPhaseLength = 0;
//            for (int j=0; j<database.getNumberOfProcesses(); j++)
//                collectedTotalPhaseLength += database.getMainArray()[i][j].getPhaseLength();
//            results[i] = ((double)collectedTotalPhaseLength)/((double)database.getNumberOfProcesses());
//        }
//        return results;
//    }
//    //the ratio of short to long processes
//    public static double[] collectProportion(double[] averagePhaseLength, int range, int threshold, Database database) {
//        double[] results = new double[averagePhaseLength.length];
//        for (int i=0; i<averagePhaseLength.length; i++) {
//            double limit = threshold+((double)Math.abs(range-threshold))/5;
//            double sum = 0;
//            for (int j=0; j<database.getNumberOfProcesses(); j++)
//                if (database.getMainArray()[i][j].getPhaseLength() > limit)
//                    sum++;
//            results[i] = ((double)database.getNumberOfProcesses()-sum)/sum;
//        }
//        return results;
//    }
//
//    public static void presentResults(int timeQuantum, int range, int threshold, Database database, Database databaseFCFS,
//                                      Database databaseSJF, Database databaseSRTF, Database databaseRR) {
//
//        double[] averagePhaseLength = collectPhaseLength(database);
//        double averagePL = collectAverage(averagePhaseLength);
//        double[] averageWaitingTimeFCFS = collectWaitingTime(databaseFCFS);
//        double averageFCFS = collectAverage(averageWaitingTimeFCFS);
//        double[] averageWaitingTimeSJF = collectWaitingTime(databaseSJF);
//        double averageSJF = collectAverage(averageWaitingTimeSJF);
//        double[] averageWaitingTimeSRTF = collectWaitingTime(databaseSRTF);
//        double averageSRTF = collectAverage(averageWaitingTimeSRTF);
//        double[] averageWaitingTimeRR = collectWaitingTime(databaseRR);
//        double averageRR = collectAverage(averageWaitingTimeRR);
//        double[] proportion = collectProportion(averagePhaseLength,range,threshold,database);
//        double averageProp = collectAverage(proportion);
//
//        System.out.format("%s %d %65s %n","Time quantum length used: ", timeQuantum, "Average waiting time for:");
//        System.out.format("%s %25s %15s %10s %10s %10s %10s %n", "String number:",
//                "Average phase length:", "Proportion:","FCFS", "SJF", "SRTF", "RR");
//        for (int i=0; i<database.getNumberOfStrings(); i++) {
//            System.out.format("%8d %22.2f %21.2f %13.2f %10.2f %10.2f %10.2f %n", i+1,
//                    averagePhaseLength[i],proportion[i], averageWaitingTimeFCFS[i],
//                    averageWaitingTimeSJF[i], averageWaitingTimeSRTF[i], averageWaitingTimeRR[i]);
//        }
//        System.out.format("%n %19s %10.2f %21.2f %13.2f %10.2f %10.2f %10.2f %n", "Average:",
//                averagePL, averageProp, averageFCFS, averageSJF, averageSRTF, averageRR);
//    }
//}
