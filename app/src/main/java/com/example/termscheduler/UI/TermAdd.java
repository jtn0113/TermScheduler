package com.example.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.termscheduler.Database.Repository;
import com.example.termscheduler.Entity.Term;
import com.example.termscheduler.R;

public class TermAdd extends AppCompatActivity {
    Repository repository;
    EditText title;
    EditText startDate;
    EditText endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);
        repository = new Repository(getApplication());
    }

    public void saveTerm(View view) {
        Term term;
        title = findViewById(R.id.addTermTitle);
        startDate = findViewById(R.id.addTermStartDate);
        endDate = findViewById(R.id.addTermEndDate);

        int newID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
        term = new Term(newID, title.getText().toString(), startDate.getText().toString(), endDate.getText().toString());
        repository.insert(term);

        Intent intent = new Intent(TermAdd.this, TermList.class);
        startActivity(intent);
    }
}