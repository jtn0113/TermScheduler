package com.example.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.termscheduler.Database.Repository;
import com.example.termscheduler.Entity.Assessment;
import com.example.termscheduler.Entity.Course;
import com.example.termscheduler.Entity.Term;
import com.example.termscheduler.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewTerms(View view) {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);

//        Repository repo = new Repository(getApplication());
//        Term term = new Term(1, "Term 1", "01/01/22", "06/01/22");
//        Term term2 = new Term(2, "Term 2", "05/01/22", "09/01/22");
//        repo.insert(term);
//        repo.insert(term2);
    }

    public void viewCourses(View view) {
        Intent intent = new Intent(MainActivity.this, CourseList.class);
        startActivity(intent);

//        Repository repo = new Repository(getApplication());
//        Course course = new Course(1, "Mobile Application Development", "02/02/22", "03/03/22", "Currently working", "Brenda Jones", "888-888-8888", "brenda@jones.com", "Term 1", "Currently working on this course");
//        Course course2 = new Course(2, "Software I", "08/02/22", "12/03/22", "Not started", "Jim Bob", "888-888-7777", "jim@bob.com", "Term 2", "Need notes");
//        repo.insert(course);
//        repo.insert(course2);
    }

    public void viewAssessments(View view) {
        Intent intent = new Intent(MainActivity.this, AssessmentList.class);
        startActivity(intent);

//        Repository repo = new Repository(getApplication());
//        Assessment assessment = new Assessment(1, "Mobile PA", "02/22/22", "02/28/22", "PA", "Mobile Application Development");
//        Assessment assessment2 = new Assessment(2, "Software OA", "05/22/22", "07/28/22", "OA", "Software I");
//        repo.insert(assessment);
//        repo.insert(assessment2);
    }
}