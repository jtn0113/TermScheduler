package com.example.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.termscheduler.Database.Repository;
import com.example.termscheduler.Entity.Assessment;
import com.example.termscheduler.Entity.Course;
import com.example.termscheduler.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity {
    EditText editAssessmentTitle;
    EditText editAssessmentStart;
    EditText editAssessmentEnd;
    EditText editAssessmentType;
    Spinner editAssessmentCourse;
    RadioGroup radioGroup;
    RadioButton radioButtonOA;
    RadioButton radioButtonPA;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    SimpleDateFormat sdf;
    String myFormat;

    int id;
    int selectedPosition;
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
        assessmentCourse = getIntent().getStringExtra("assessment_course");

        editAssessmentTitle.setText(title);
        editAssessmentStart.setText(start);
        editAssessmentEnd.setText(end);

        type = repository.getAssessmentType(id);
        radioGroup = findViewById(R.id.editAssessmentRadio);
        radioButtonOA = findViewById(R.id.editAssessmentOA);
        radioButtonPA = findViewById(R.id.editAssessmentPA);
        if(type.contains("P")) {
            radioButtonPA.setChecked(true);
        } else {
            radioButtonOA.setChecked(true);
        }

        Spinner spinner = findViewById(R.id.editAssessmentSpinner);
        Repository repository = new Repository(getApplication());
        List<String> termCourses = repository.getCourseTitles();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, termCourses);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        assessmentCourse = repository.getAssessmentCourseTitle(id);
        selectedPosition = arrayAdapter.getPosition(assessmentCourse);
        spinner.setSelection(selectedPosition);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editAssessmentStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editAssessmentStart.getText().toString();
                if(info.equals("")) {
                    info = "02/10/22";
                }
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editAssessmentEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editAssessmentEnd.getText().toString();
                if(info.equals("")) {
                    info = "02/10/22";
                }
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, endDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR,year);
                myCalendarStart.set(Calendar.MONTH,month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabelStart();
            }
        };

        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR,year);
                myCalendarEnd.set(Calendar.MONTH,month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabelEnd();
            }
        };
    }

    private void updateLabelStart() {
        editAssessmentStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        editAssessmentEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_assessmentdetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.notifyStart2:
                String dateFromScreen = editAssessmentStart.getText().toString();
                Date myDate = null;
                try{
                    myDate = sdf.parse(dateFromScreen);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(AssessmentDetail.this, MyReceiver.class);
                intent.putExtra("key", title + " starts today");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetail.this, MainActivity.numAlert++, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.notifyEnd2:
                String endDateFromScreen = editAssessmentEnd.getText().toString();
                Date myEndDate = null;
                try{
                    myEndDate = sdf.parse(endDateFromScreen);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                Long endTrigger = myEndDate.getTime();
                Intent newIntent = new Intent(AssessmentDetail.this, MyReceiver.class);
                newIntent.putExtra("key", title + " ends today");
                PendingIntent newSender = PendingIntent.getBroadcast(AssessmentDetail.this, MainActivity.numAlert++, newIntent, 0);
                AlarmManager newAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                newAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, newSender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveAssessment(View view) {
        Spinner spinner = findViewById(R.id.editAssessmentSpinner);
        Assessment assessment;
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.editAssessmentRadio);
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonId);
        String selectedText = (String) radioButton.getText().toString();

        assessment = new Assessment(id, editAssessmentTitle.getText().toString(), editAssessmentStart.getText().toString(), editAssessmentEnd.getText().toString(), selectedText, spinner.getSelectedItem().toString());
        repository.update(assessment);

        Intent intent = new Intent(AssessmentDetail.this, AssessmentList.class);
        startActivity(intent);
    }

    public void deleteAssessment(View view) {
        Spinner spinner = findViewById(R.id.editAssessmentSpinner);
        Assessment assessment;
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.editAssessmentRadio);
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonId);
        String selectedText = (String) radioButton.getText().toString();

        if(TermAdd.isDate(editAssessmentStart.getText().toString()) && TermAdd.isDate(editAssessmentEnd.getText().toString())) {
            assessment = new Assessment(id, editAssessmentTitle.getText().toString(), editAssessmentStart.getText().toString(), editAssessmentEnd.getText().toString(), selectedText, spinner.getSelectedItem().toString());
            repository.delete(assessment);

            Intent intent = new Intent(AssessmentDetail.this, AssessmentList.class);
            startActivity(intent);
        } else {
            Toast.makeText(AssessmentDetail.this, "Invalid Date", Toast.LENGTH_LONG).show();
        }
    }
}