package ca.mohawkcollege.da_silva_moraes.project;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by da-silva-moraes on 11-Apr-17.
 */

public class DownloadCourseInformation extends AsyncTask<String, Void, String> {

    private Activity myActivity;

    public DownloadCourseInformation(Activity inActivity){
        myActivity = inActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        String results = "";
        int statusCode = 404;

        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            statusCode = connection.getResponseCode();

            if(statusCode == 200){
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    results += line;
                }
            }
        } catch(IOException e){
            Log.d("Http status: ", statusCode + "");
        }
        return results;
    }

    @Override
    protected void onPostExecute(String result){
        Gson gson = new Gson();
        CourseList courses = gson.fromJson(result, CourseList.class);

        if(courses != null){
            DbHelper dbHelper = new DbHelper(Project.getContext());
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            for(Courses course : courses){
                ContentValues values = new ContentValues();
                values.put("_id", course.get_id());
                values.put("program", course.getProgram());
                values.put("semesterNum", course.getSemesterNum());
                values.put("courseCode", course.getCourseCode());
                values.put("courseTitle", course.getCourseTitle());
                values.put("courseDescription", course.getCourseDescription());
                values.put("courseOwner", course.getCourseOwner());
                values.put("optional", course.getOptional());
                values.put("hours", course.getHours());

                long newRowId = database.replace("courses", null, values);

                Log.d("log", "New ID " + newRowId);
            }
        }

    }
}
