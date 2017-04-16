package ca.mohawkcollege.da_silva_moraes.project;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity
        implements ExpandableListView.OnChildClickListener, ExpandableListView.OnItemClickListener{

    private ExpandableListView programList;
    private ExpandableListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!databaseExists(this, DbHelper.DATABASE_NAME)){
            refreshDatabase(new View(this));
        }

        HashMap<String, List<String>> listDataChild = ExpandableListDataPump.getData();
        List<String> listDataHeader = new ArrayList<>(listDataChild.keySet());

        programList = (ExpandableListView) findViewById(R.id.programList);
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        programList.setAdapter(listAdapter);
        programList.setOnChildClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//        int program = listAdapter.getHeaderTitle(groupPosition);
//        int semester = listAdapter.getChildText(groupPosition, childPosition);
//
//        List<Courses> coursesList = DbHelper.getCoursesInformation(program, semester);

        String toastText = listAdapter.getChildText(groupPosition, childPosition) + " " +
                listAdapter.getHeaderTitle(groupPosition);
        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DrawerLayout mydrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mydrawerlayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Find out the current state of the drawer (open or closed)
        DrawerLayout mydrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        boolean isOpen = mydrawerlayout.isDrawerOpen(GravityCompat.START);

        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // Home button - open or close the drawer
                if (isOpen == true) {
                    mydrawerlayout.closeDrawer(GravityCompat.START);
                } else {
                    mydrawerlayout.openDrawer(GravityCompat.START);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}