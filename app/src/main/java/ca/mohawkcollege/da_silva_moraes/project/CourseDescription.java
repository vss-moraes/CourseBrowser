package ca.mohawkcollege.da_silva_moraes.project;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import static ca.mohawkcollege.da_silva_moraes.project.FeedReaderContract.*;

public class CourseDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_description);

        Bundle bundle = getIntent().getExtras();
        String description = bundle.getString(FeedEntry.COLUMN_NAME_DESCRIPTION);
        int program = bundle.getInt(FeedEntry.COLUMN_NAME_PROGRAM);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(description);
        }

        String[] courseInfo = description.split(": ");

        Courses course = DatabaseQueries.getCourseInformation(courseInfo, program);

        TextView idView = (TextView) findViewById(R.id.id_content);
        TextView programView = (TextView) findViewById(R.id.program_content);
        TextView semesterView = (TextView) findViewById(R.id.semester_content);
        TextView codeView = (TextView) findViewById(R.id.code_content);
        TextView titleView = (TextView) findViewById(R.id.title_content);
        TextView descriptionView = (TextView) findViewById(R.id.description_content);
        TextView ownerView = (TextView) findViewById(R.id.owner_content);
        TextView optionalView = (TextView) findViewById(R.id.optional_content);
        TextView hoursView = (TextView) findViewById(R.id.hours_content);

        idView.setText(course.get_id() + "");
        programView.setText(course.getProgram() + "");
        semesterView.setText(course.getSemesterNum() + "");
        codeView.setText(course.getCourseCode());
        titleView.setText(course.getCourseTitle());
        descriptionView.setText(course.getCourseDescription());
        ownerView.setText(course.getCourseOwner());

        if (course.getOptional() == 0)
            optionalView.setText("No");
        else
            optionalView.setText("Yes");

        hoursView.setText(course.getHours() + "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Find out the current state of the drawer (open or closed)
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
