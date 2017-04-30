/*
 * I, Vinicius da Silva Moraes, 000745570 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 */

package ca.mohawkcollege.da_silva_moraes.project;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static ca.mohawkcollege.da_silva_moraes.project.FeedReaderContract.*;


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

    /*
     * Creates a list on the main activity when a semester from a program is selected
     */
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

    /*
     * Creates the Course Description Activity when a course is selected
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String courseDescription = adapter.getItem(position);

        Intent intent = new Intent(this, CourseDescription.class);
        intent.putExtra(FeedEntry.COLUMN_NAME_DESCRIPTION, courseDescription);
        intent.putExtra(FeedEntry.COLUMN_NAME_PROGRAM, selectedProgram);
        startActivity(intent);
    }

    /*
     * Handles the navigation drawer opening and closing when home button is clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        boolean isOpen = drawerLayout.isDrawerOpen(GravityCompat.START);

        switch (item.getItemId()) {
            case android.R.id.home:
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

    /*
     * Creates or refreshed the database, pulling data from the webservice
     */
    public void refreshDatabase(View view) {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);

        DownloadCourseInformation dl = new DownloadCourseInformation();
        String response;
        String uri = "https://csunix.mohawkcollege.ca/~geczy/mohawkprograms.php";
        try {
            response = dl.execute(uri).get();

            if (response != null) {
                createExpandableList();
            }
        } catch (InterruptedException | ExecutionException  e) {
            e.printStackTrace();
        }
    }

    /*
     * Populates the Expandable List based on data from the database
     */
    public void createExpandableList(){
        HashMap<String, List<String>> listDataChild = ExpandableListDataPump.getData();
        List<String> listDataHeader = new ArrayList<>(listDataChild.keySet());

        ExpandableListView programList = (ExpandableListView) findViewById(R.id.programList);
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        programList.setAdapter(listAdapter);
        programList.setOnChildClickListener(this);
    }

    /*
     * Checks if the database exists before creating the expandable list on the Navigation Drawer
     */
    private static boolean databaseExists(Context context, String dbName){
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}