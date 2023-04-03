package Useful;

import MyObjects.Request;

import java.util.ArrayList;

public class TableManager {

    @SuppressWarnings("unchecked")
    public static ArrayList<Request> convert3DRequestTableTo1DArrayList(Request[][][] tab) {
        ArrayList<Request> resultsArrayList = new ArrayList<>();
        for (Request[][] cylinder : tab)
            for (Request[] block : cylinder)
                for (Request platter : block)
                    if (platter != null)
                        resultsArrayList.add(new Request(platter));
        return resultsArrayList;
    }
}