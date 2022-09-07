package com.example.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.termscheduler.Database.Repository;
import com.example.termscheduler.Entity.Course;
import com.example.termscheduler.Entity.Term;
import com.example.termscheduler.R;

import java.util.List;

public class TermDetail extends AppCompatActivity {
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    String title;
    String start;
    String end;
    Repository repository;
    int termID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        editTitle = findViewById(R.id.editTermTitle);
        editStart = findViewById(R.id.editTermStart);
        editEnd = findViewById(R.id.editTermEnd);
        termID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start_date");
        end = getIntent().getStringExtra("end_date");
        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);
        repository = new Repository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.activityDetailRecyclerView);
        Repository repository = new Repository(getApplication());
        List<Course> courses = repository.getAllFilteredCourses(title);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);
    }

    public void saveTerm(View view) {
        Term term;
        if(TermAdd.isDate(editStart.getText().toString()) && TermAdd.isDate(editEnd.getText().toString())) {
            term = new Term(termID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repository.update(term);

            Intent intent = new Intent(TermDetail.this, TermList.class);
            startActivity(intent);
        } else {
            Toast.makeText(TermDetail.this, "Invalid Date", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteTerm(View view) {
        Term term;
        term = new Term(termID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());

        if(repository.getAllFilteredCourses(editTitle.getText().toString()).isEmpty()) {
            repository.delete(term);
            Intent intent = new Intent(TermDetail.this, TermList.class);
            startActivity(intent);
        } else {
            Toast.makeText(TermDetail.this, "Can not delete term with courses associated", Toast.LENGTH_LONG).show();
        }
    }
}