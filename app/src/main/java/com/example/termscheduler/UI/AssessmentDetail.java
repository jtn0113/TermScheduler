package com.example.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.termscheduler.Database.Repository;
import com.example.termscheduler.Entity.Assessment;
import com.example.termscheduler.Entity.Course;
import com.example.termscheduler.R;

import java.util.List;

public class AssessmentDetail extends AppCompatActivity {
    EditText editAssessmentTitle;
    EditText editAssessmentStart;
    EditText editAssessmentEnd;
    EditText editAssessmentType;
    Spinner editAssessmentCourse;

    int id;
    String title;
    String start;
    String end;
    String type;
    String assessmentCourse;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        repository = new Repository(getApplication());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        editAssessmentTitle = findViewById(R.id.editAssessmentTitle);
        editAssessmentStart = findViewById(R.id.editAssessmentStart);
        editAssessmentEnd = findViewById(R.id.editAssessmentEnd);
//        editAssessmentType = findViewById(R.id.editAssessmentRadio);
        editAssessmentCourse = findViewById(R.id.editAssessmentSpinner);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start_date");
        end = getIntent().getStringExtra("end_date");

        editAssessmentTitle.setText(title);
        editAssessmentStart.setText(start);
        editAssessmentEnd.setText(end);
//        Still need to figure out radio and spinner types

        Spinner spinner = findViewById(R.id.editAssessmentSpinner);
        Repository repository = new Repository(getApplication());
        List<String> termCourses = repository.getCourseTitles();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, termCourses);
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

    public void saveAssessment(View view) {
        Assessment assessment;
        assessment = new Assessment(id, editAssessmentTitle.getText().toString(), editAssessmentStart.getText().toString(), editAssessmentEnd.getText().toString(), null, null);
        repository.update(assessment);
    }
}