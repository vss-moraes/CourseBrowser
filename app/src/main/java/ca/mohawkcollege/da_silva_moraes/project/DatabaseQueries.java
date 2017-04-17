package ca.mohawkcollege.da_silva_moraes.project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


class DatabaseQueries {

    static List<String> getProgramCodes(){
        List<String> programs = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(Project.getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        final String GET_PROGRAMS = "SELECT DISTINCT program FROM courses ORDER BY program;";

        Cursor cursor = database.rawQuery(GET_PROGRAMS, null);
        while (cursor.moveToNext())
            programs.add(cursor.getString(cursor.getColumnIndex("program")));
        cursor.close();

        return programs;
    }

    static List<String> getProgramSemesters(String program){
        List<String> semesters = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(Project.getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        final String GET_SEMESTERS =
                "SELECT DISTINCT semesterNum " +
                        "FROM courses WHERE " +
                        "program = ?" +
                        "ORDER BY semesterNum;";

        Cursor cursor = database.rawQuery(GET_SEMESTERS, new String[] {program});
        while (cursor.moveToNext())
            semesters.add(cursor.getString(cursor.getColumnIndex("semesterNum")));
        cursor.close();
        return semesters;
    }

    static List<String> getCourseNames(int program, int semester){
        List<String> courseNames = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(Project.getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        final String GET_COURSES_NAME =
                "SELECT courseCode, courseTitle " +
                        "FROM courses " +
                        "WHERE program = ? " +
                        "AND semesterNum = ? ";

        Cursor cursor = database.rawQuery(GET_COURSES_NAME, new String[] {program + "", semester + ""});
        while(cursor.moveToNext()){
            String courseCode = cursor.getString(cursor.getColumnIndex("courseCode"));
            String courseTitle = cursor.getString(cursor.getColumnIndex("courseTitle"));

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
                        "FROM courses " +
                        "WHERE courseCode = ? " +
                        "AND courseTitle = ? " +
                        "AND program = ?;";

        Cursor cursor = database.rawQuery(GET_COURSES, new String[] {courseInfo[0], courseInfo[1], program + ""});
        while (cursor.moveToNext()){
            course.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            course.setProgram(cursor.getInt(cursor.getColumnIndex("program")));
            course.setSemesterNum(cursor.getInt(cursor.getColumnIndex("semesterNum")));
            course.setCourseCode(cursor.getString(cursor.getColumnIndex("courseCode")));
            course.setCourseTitle(cursor.getString(cursor.getColumnIndex("courseTitle")));
            course.setCourseDescription(cursor.getString(cursor.getColumnIndex("courseDescription")));
            course.setCourseOwner(cursor.getString(cursor.getColumnIndex("courseOwner")));
            course.setOptional(cursor.getInt(cursor.getColumnIndex("optional")));
            course.setHours(cursor.getInt(cursor.getColumnIndex("hours")));
        }
        cursor.close();

        return course;
    }
}
