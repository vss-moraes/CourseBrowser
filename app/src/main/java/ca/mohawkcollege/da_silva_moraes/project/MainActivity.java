package ca.mohawkcollege.da_silva_moraes.project;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.deleteDatabase(DbHelper.DATABASE_NAME);

        if(!databaseExists(this, DbHelper.DATABASE_NAME)){
            refreshDatabase(new View(this));
        }
    }

    public void refreshDatabase(View view) {
        DownloadCourseInformation dl = new DownloadCourseInformation(this);
        String uri = "https://csunix.mohawkcollege.ca/~geczy/mohawkprograms.php";
        try {
            String response = dl.execute(uri).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    private static boolean databaseExists(Context context, String dbName){
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}