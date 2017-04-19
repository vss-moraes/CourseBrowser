package ca.mohawkcollege.da_silva_moraes.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static ca.mohawkcollege.da_silva_moraes.project.FeedReaderContract.*;

class DbHelper extends SQLiteOpenHelper{

    static final String DATABASE_NAME = "MohawkCoursesList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_DATABASE =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + "(" +
                    FeedEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_PROGRAM + " INTEGER," +
                    FeedEntry.COLUMN_NAME_SEMESTER + " INTEGER," +
                    FeedEntry.COLUMN_NAME_CODE + " TEXT," +
                    FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FeedEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    FeedEntry.COLUMN_NAME_OWNER + " TEXT," +
                    FeedEntry.COLUMN_NAME_OPTIONAL + " INTEGER," +
                    FeedEntry.COLUMN_NAME_HOURS + " INTEGER);";

    DbHelper (Context context){
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
