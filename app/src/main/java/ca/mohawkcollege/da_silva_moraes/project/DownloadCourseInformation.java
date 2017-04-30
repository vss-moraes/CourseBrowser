package ca.mohawkcollege.da_silva_moraes.project;

import android.content.ContentValues;
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

import static ca.mohawkcollege.da_silva_moraes.project.FeedReaderContract.*;

class DownloadCourseInformation extends AsyncTask<String, Void, String> {

    DownloadCourseInformation(){}

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
                String line;
                while((line = bufferedReader.readLine()) != null){
                    results += line;
                }

                Gson gson = new Gson();
                CourseList courses = gson.fromJson(results, CourseList.class);

                if(courses != null){
                    DbHelper dbHelper = new DbHelper(Project.getContext());
                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    for(Courses course : courses){
                        ContentValues values = new ContentValues();
                        values.put(FeedEntry.COLUMN_NAME_ID, course.get_id());
                        values.put(FeedEntry.COLUMN_NAME_PROGRAM, course.getProgram());
                        values.put(FeedEntry.COLUMN_NAME_SEMESTER, course.getSemesterNum());
                        values.put(FeedEntry.COLUMN_NAME_CODE, course.getCourseCode());
                        values.put(FeedEntry.COLUMN_NAME_TITLE, course.getCourseTitle());
                        values.put(FeedEntry.COLUMN_NAME_DESCRIPTION, course.getCourseDescription());
                        values.put(FeedEntry.COLUMN_NAME_OWNER, course.getCourseOwner());
                        values.put(FeedEntry.COLUMN_NAME_OPTIONAL, course.getOptional());
                        values.put(FeedEntry.COLUMN_NAME_HOURS, course.getHours());

                        long newRowId = database.replace(FeedEntry.TABLE_NAME, null, values);

                        Log.d("log", "New ID " + newRowId);
                    }
                }

            }
        } catch(IOException e){
            Log.d("Http status: ", statusCode + "");
        }
        return results;
    }

    @Override
    protected void onPostExecute(String result){

    }
}
