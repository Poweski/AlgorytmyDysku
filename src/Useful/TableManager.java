package Useful;

import MyObjects.Request;

import java.util.ArrayList;

public class TableManager {

    @SuppressWarnings("unchecked")
    public static ArrayList<Request> convert3DRequestTableTo1DArrayList(Request[][][] tab) {
        ArrayList<Request> resultsArrayList = new ArrayList<>();
        for (Request[][] platter : tab) {
            for (Request[] cylinder : platter) {
                for (Request segment : cylinder) {
                    if (segment != null) {
                        resultsArrayList.add(new Request(segment));
                    }
                }
            }
        }
        return resultsArrayList;
    }

}
