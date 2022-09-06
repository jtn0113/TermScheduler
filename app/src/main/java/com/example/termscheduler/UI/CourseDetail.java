package com.example.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.termscheduler.Database.Repository;
import com.example.termscheduler.Entity.Course;
import com.example.termscheduler.R;

import java.util.List;

public class CourseDetail extends AppCompatActivity {
    EditText editCourseTitle;
    EditText editCourseStart;
    EditText editCourseEnd;
    EditText editCourseStatus;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    Spinner editCourseTerm;
    EditText editCourseNotes;

    String title;
    String start;
    String end;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String courseTerm;
    String courseNotes;
    int courseID;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        editCourseTitle = findViewById(R.id.editCourseTitle);
        editCourseStart = findViewById(R.id.editCourseStart);
        editCourseEnd = findViewById(R.id.editCourseEnd);
        editCourseStatus = findViewById(R.id.editCourseStatus);
        editInstructorName = findViewById(R.id.editInstructorName);
        editInstructorPhone = findViewById(R.id.editInstructorPhone);
        editInstructorEmail = findViewById(R.id.editInstructorEmail);
        editCourseTerm = findViewById(R.id.editCourseTerm);
        editCourseNotes = findViewById(R.id.editCourseNotes);

        courseID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start_date");
        end = getIntent().getStringExtra("end_date");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructor_name");
        instructorPhone = getIntent().getStringExtra("instructor_phone");
        instructorEmail = getIntent().getStringExtra("instructor_email");
        courseTerm = getIntent().getStringExtra("course_term");
        courseNotes = getIntent().getStringExtra("course_notes");

        editCourseTitle.setText(title);
        editCourseStart.setText(start);
        editCourseEnd.setText(end);
        editCourseStatus.setText(status);
        editInstructorName.setText(instructorName);
        editInstructorPhone.setText(instructorPhone);
        editInstructorEmail.setText(instructorEmail);
//        editCourseTerm.setSelection(((ArrayAdapter<String>)editCourseTerm.getAdapter()).getPosition(courseTerm));
        editCourseNotes.setText(courseNotes);
        repository = new Repository(getApplication());

        Spinner spinner = findViewById(R.id.editCourseTerm);
        Repository repository = new Repository(getApplication());
        List<String> termTitles = repository.getTermTitles();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, termTitles);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void saveCourse(View view) {
        Course course;
        course = new Course(courseID, editCourseTitle.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editCourseStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), null, editCourseNotes.getText().toString());
        repository.update(course);
    }
}