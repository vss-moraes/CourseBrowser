package ca.mohawkcollege.da_silva_moraes.project;

import java.util.HashMap;
import java.util.List;


class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        List<String> programCodes = DatabaseQueries.getProgramCodes();

        for (String program : programCodes){
            List<String> semestersList = DatabaseQueries.getProgramSemesters(program);

            expandableListDetail.put(program, semestersList);
        }

        return expandableListDetail;

    }
}
