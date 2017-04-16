package ca.mohawkcollege.da_silva_moraes.project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!databaseExists(this, DbHelper.DATABASE_NAME)){
            refreshDatabase(new View(this));
        }

        HashMap<String, List<String>> listDataChild = ExpandabelListDataPump.getData();
        List<String> listDataHeader = new ArrayList<>(listDataChild.keySet());

        ExpandableListView programList = (ExpandableListView) findViewById(R.id.programList);
        ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        programList.setAdapter(listAdapter);

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