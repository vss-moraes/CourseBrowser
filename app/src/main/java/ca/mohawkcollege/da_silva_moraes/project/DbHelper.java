package ca.mohawkcollege.da_silva_moraes.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
