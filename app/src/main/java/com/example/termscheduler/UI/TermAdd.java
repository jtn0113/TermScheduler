package com.example.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.termscheduler.Database.Repository;
import com.example.termscheduler.Entity.Term;
import com.example.termscheduler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TermAdd extends AppCompatActivity {
    Repository repository;
    EditText title;
    EditText startDate;
    EditText endDate;

    public static boolean isDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateString.trim());
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

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

        if(isDate(startDate.getText().toString()) && isDate(endDate.getText().toString())) {
            int newID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermID() + 1;
            term = new Term(newID, title.getText().toString(), startDate.getText().toString(), endDate.getText().toString());
            repository.insert(term);

            Intent intent = new Intent(TermAdd.this, TermList.class);
            startActivity(intent);
        } else {
            Toast.makeText(TermAdd.this, "Invalid Date", Toast.LENGTH_LONG).show();
        }
    }
}