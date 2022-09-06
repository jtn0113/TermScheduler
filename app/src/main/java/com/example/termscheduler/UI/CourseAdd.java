package com.example.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.termscheduler.DAO.TermDAO;
import com.example.termscheduler.Database.Repository;
import com.example.termscheduler.Entity.Course;
import com.example.termscheduler.Entity.Term;
import com.example.termscheduler.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdd extends AppCompatActivity {
    Repository repository;
    EditText title;
    EditText start;
    EditText end;
    EditText status;
    EditText instructorName;
    EditText instructorPhone;
    EditText instructorEmail;
    String courseTerm;
    EditText notes;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        repository = new Repository(getApplication());

        Spinner spinner = findViewById(R.id.addCourseTerm);
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

        spinner = (Spinner) findViewById(R.id.addCourseTerm);

        title = findViewById(R.id.addCourseTitle);
        start = findViewById(R.id.addCourseStartDate);
        end = findViewById(R.id.addCourseEndDate);
        status = findViewById(R.id.addCourseStatus);
        instructorName = findViewById(R.id.addCourseInstructorName);
        instructorPhone = findViewById(R.id.addCourseInstructorPhone);
        instructorEmail = findViewById(R.id.addCourseInstructorEmail);
        courseTerm = spinner.getSelectedItem().toString();
        notes = findViewById(R.id.addCourseNotes);

        int newID = repository.getAllCourses().get(repository.getAllCourses().size() -1).getCourseID() + 1;
        course = new Course(newID, title.getText().toString(), start.getText().toString(), end.getText().toString(), status.getText().toString(), instructorName.getText().toString(), instructorPhone.getText().toString(), instructorEmail.getText().toString(), courseTerm, notes.getText().toString());
        repository.insert(course);

        Intent intent = new Intent(CourseAdd.this, CourseList.class);
        startActivity(intent);

    }
}