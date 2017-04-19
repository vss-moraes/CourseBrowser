package ca.mohawkcollege.da_silva_moraes.project;

import android.provider.BaseColumns;

/**
 * Created by vsant on 19-Apr-17.
 */

final class FeedReaderContract {
    private FeedReaderContract(){}

    static class FeedEntry implements BaseColumns{
        static final String TABLE_NAME = "courses";
        static final String COLUMN_NAME_ID = "_id";
        static final String COLUMN_NAME_PROGRAM = "program";
        static final String COLUMN_NAME_SEMESTER = "semesterNum";
        static final String COLUMN_NAME_CODE = "courseCode";
        static final String COLUMN_NAME_TITLE = "courseTitle";
        static final String COLUMN_NAME_DESCRIPTION = "courseDescription";
        static final String COLUMN_NAME_OWNER = "courseOwner";
        static final String COLUMN_NAME_OPTIONAL = "optional";
        static final String COLUMN_NAME_HOURS = "hours";

    }
}