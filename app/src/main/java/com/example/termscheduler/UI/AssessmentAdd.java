package com.example.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.termscheduler.Database.Repository;
import com.example.termscheduler.Entity.Assessment;
import com.example.termscheduler.R;

import java.util.List;

public class AssessmentAdd extends AppCompatActivity {

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);
        repository = new Repository(getApplication());

        Spinner spinner = findViewById(R.id.addAssessmentCourse);
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

        Spinner spinner = (Spinner) findViewById(R.id.addAssessmentCourse);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.addAssessmentRadioGroup);
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonId);
        String selectedText = (String) radioButton.getText().toString();

        int newID = repository.getAllAssessments().get(repository.getAllAssessments().size() -1).getAssessmentID() + 1;
        EditText title = findViewById(R.id.addAssessmentTitle);
        EditText start = findViewById(R.id.addAssessmentStartDate);
        EditText end = findViewById(R.id.addAssessmentEndDate);

        if(TermAdd.isDate(start.getText().toString()) && TermAdd.isDate(end.getText().toString())) {
            assessment = new Assessment(newID, title.getText().toString(), start.getText().toString(), end.getText().toString(), selectedText, spinner.getSelectedItem().toString());
            repository.insert(assessment);

            Intent intent = new Intent(AssessmentAdd.this, AssessmentList.class);
            startActivity(intent);
        } else {
            Toast.makeText(AssessmentAdd.this, "Invalid Date", Toast.LENGTH_LONG).show();
        }
    }
}