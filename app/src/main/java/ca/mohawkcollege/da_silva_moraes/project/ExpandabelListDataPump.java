package ca.mohawkcollege.da_silva_moraes.project;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vsant on 16-Apr-17.
 */

public class ExpandabelListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> programCodes = DbHelper.getProgramCodes();

        for (String program : programCodes){
            List<String> semestersList = DbHelper.getProgramSemesters(program);

            expandableListDetail.put(program, semestersList);
        }

        return expandableListDetail;

    }
}
