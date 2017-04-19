package ca.mohawkcollege.da_silva_moraes.project;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity
        implements ExpandableListView.OnChildClickListener, ListView.OnItemClickListener{

    private ExpandableListAdapter listAdapter;
    private ArrayAdapter<String> adapter;
    private int selectedProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!databaseExists(this, DbHelper.DATABASE_NAME)){
            refreshDatabase(new View(this));
        } else
            createExpandableList();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        selectedProgram = listAdapter.getHeaderTitle(groupPosition);
        int semester = listAdapter.getChildText(groupPosition, childPosition);
        ListView listView = (ListView) findViewById(R.id.coursesList);

        List<String> courseNames = DatabaseQueries.getCourseNames(selectedProgram, semester);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String courseDescription = adapter.getItem(position);

        Intent intent = new Intent(this, CourseDescription.class);
        intent.putExtra("courseDescription", courseDescription);
        intent.putExtra("program", selectedProgram);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Find out the current state of the drawer (open or closed)
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        boolean isOpen = drawerLayout.isDrawerOpen(GravityCompat.START);

        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // Home button - open or close the drawer
                if (isOpen) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refreshDatabase(View view) {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);

        DownloadCourseInformation dl = new DownloadCourseInformation();
        String response;
        String uri = "https://csunix.mohawkcollege.ca/~geczy/mohawkprograms.php";
        try {
            response = dl.execute(uri).get();
            Log.i("Response: ", response);

            if (response != null) {
                Log.i("Creating List", "list");
                createExpandableList();
            }
        } catch (InterruptedException | ExecutionException  e) {
            e.printStackTrace();
        }
    }

    public void createExpandableList(){
        HashMap<String, List<String>> listDataChild = ExpandableListDataPump.getData();
        List<String> listDataHeader = new ArrayList<>(listDataChild.keySet());

        ExpandableListView programList = (ExpandableListView) findViewById(R.id.programList);
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        programList.setAdapter(listAdapter);
        programList.setOnChildClickListener(this);
    }

    private static boolean databaseExists(Context context, String dbName){
        File dbFile = context.getDatabasePath(dbName);
        Log.i("Database exists: ", dbFile.exists() + "");
        return dbFile.exists();
    }
}