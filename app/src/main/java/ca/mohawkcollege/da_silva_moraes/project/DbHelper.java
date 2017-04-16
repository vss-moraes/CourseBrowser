package ca.mohawkcollege.da_silva_moraes.project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by da-silva-moraes on 11-Apr-17.
 */

public class DbHelper extends SQLiteOpenHelper{

    static final String DATABASE_NAME = "MohawkCoursesList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_DATABASE =
                    "CREATE TABLE courses (" +
                    "_id INTEGER PRIMARY KEY," +
                    "program INTEGER," +
                    "semesterNum INTEGER," +
                    "courseCode TEXT," +
                    "courseTitle TEXT," +
                    "courseDescription TEXT," +
                    "courseOwner TEXT," +
                    "optional INTEGER," +
                    "hours INTEGER);";

    public DbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){

    }

    public static List<String> getProgramCodes(){
        List<String> programs = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(Project.getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        final String GET_PROGRAMS = "SELECT DISTINCT program FROM courses;";

        Cursor cursor = database.rawQuery(GET_PROGRAMS, null);
        while (cursor.moveToNext())
            programs.add(cursor.getString(cursor.getColumnIndex("program")));

        return programs;
    }

    public static List<String> getProgramSemesters(String program){
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

        return semesters;
    }

    public static List<Courses> getCoursesInformation(int program, int semester){
        List<Courses> coursesList = new ArrayList<>();

        DbHelper dbHelper = new DbHelper(Project.getContext());
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        final String GET_COURSES =
                "SELECT courseCode, courseTitle " +
                        "FROM courses " +
                        "WHERE program = ? " +
                        "AND semesterNum = ? " +
                        "ORDER BY courseCode;";

        Cursor cursor = database.rawQuery(GET_COURSES, new String[] {program + "", semester + ""});
        Courses course = new Courses();
        while (cursor.moveToNext()){
            course.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            course.setProgram(program);
            course.setSemesterNum(semester);
            course.setCourseCode(cursor.getString(cursor.getColumnIndex("courseCode")));
            course.setCourseTitle(cursor.getString(cursor.getColumnIndex("courseTitle")));
            course.setCourseDescription(cursor.getString(cursor.getColumnIndex("courseDescription")));
            course.setCourseOwner(cursor.getString(cursor.getColumnIndex("courseOwner")));
            course.setOptional(cursor.getInt(cursor.getColumnIndex("optional")));
            course.setHours(cursor.getInt(cursor.getColumnIndex("hours")));

            coursesList.add(course);
        }

        return coursesList;
    }
}
