package ca.mohawkcollege.da_silva_moraes.project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static ca.mohawkcollege.da_silva_moraes.project.FeedReaderContract.*;


class DatabaseQueries {

    static List<String> getProgramCodes(){
        List<String> programs = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(Project.getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        final String GET_PROGRAMS =
                "SELECT DISTINCT " + FeedEntry.COLUMN_NAME_PROGRAM +
                        " FROM " + FeedEntry.TABLE_NAME +
                        " ORDER BY " + FeedEntry.COLUMN_NAME_PROGRAM + ";";

        Cursor cursor = database.rawQuery(GET_PROGRAMS, null);
        while (cursor.moveToNext())
            programs.add(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_PROGRAM)));
        cursor.close();

        return programs;
    }

    static List<String> getProgramSemesters(String program){
        List<String> semesters = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(Project.getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        final String GET_SEMESTERS =
                "SELECT DISTINCT " + FeedEntry.COLUMN_NAME_SEMESTER +
                        " FROM " + FeedEntry.TABLE_NAME +
                        " WHERE " + FeedEntry.COLUMN_NAME_PROGRAM + " = ?" +
                        " ORDER BY " + FeedEntry.COLUMN_NAME_SEMESTER +";";

        Cursor cursor = database.rawQuery(GET_SEMESTERS, new String[] {program});
        while (cursor.moveToNext())
            semesters.add(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_SEMESTER)));
        cursor.close();
        return semesters;
    }

    static List<String> getCourseNames(int program, int semester){
        List<String> courseNames = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(Project.getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        final String GET_COURSES_NAME =
                "SELECT " + FeedEntry.COLUMN_NAME_CODE + ", " + FeedEntry.COLUMN_NAME_TITLE +
                        " FROM " + FeedEntry.TABLE_NAME +
                        " WHERE " + FeedEntry.COLUMN_NAME_PROGRAM + " = ?" +
                        " AND " + FeedEntry.COLUMN_NAME_SEMESTER + " = ?;";

        Cursor cursor = database.rawQuery(GET_COURSES_NAME, new String[] {program + "", semester + ""});
        while(cursor.moveToNext()){
            String courseCode = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_CODE));
            String courseTitle = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_TITLE));

            courseNames.add(courseCode + ": " + courseTitle);
        }
        cursor.close();

        return courseNames;
    }

    static Courses getCourseInformation(String[] courseInfo, int program){
        Courses course = new Courses();

        DbHelper dbHelper = new DbHelper(Project.getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        final String GET_COURSES =
                "SELECT DISTINCT * " +
                        "FROM " + FeedEntry.TABLE_NAME +
                        " WHERE " + FeedEntry.COLUMN_NAME_CODE + " = ?" +
                        " AND " + FeedEntry.COLUMN_NAME_TITLE + " = ?" +
                        " AND " + FeedEntry.COLUMN_NAME_PROGRAM + " = ?;";

        Cursor cursor = database.rawQuery(GET_COURSES, new String[] {courseInfo[0], courseInfo[1], program + ""});
        while (cursor.moveToNext()){
            course.set_id(cursor.getInt(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_ID)));
            course.setProgram(cursor.getInt(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_PROGRAM)));
            course.setSemesterNum(cursor.getInt(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_SEMESTER)));
            course.setCourseCode(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_CODE)));
            course.setCourseTitle(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_TITLE)));
            course.setCourseDescription(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_DESCRIPTION)));
            course.setCourseOwner(cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_OWNER)));
            course.setOptional(cursor.getInt(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_OPTIONAL)));
            course.setHours(cursor.getInt(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_HOURS)));
        }
        cursor.close();

        return course;
    }
}
