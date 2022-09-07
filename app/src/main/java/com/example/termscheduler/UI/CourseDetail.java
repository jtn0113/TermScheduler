package com.example.termscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    SimpleDateFormat sdf;
    String myFormat;

    String title;
    String start;
    String end;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String courseTerm;
    String courseNotes;
    int selectedPosition;
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
        myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        editCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editCourseStart.getText().toString();
                if(info.equals("")) {
                    info = "02/10/22";
                }
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info = editCourseEnd.getText().toString();
                if(info.equals("")) {
                    info = "02/10/22";
                }
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this, endDate, myCalendarStart.get(Calendar.YEAR),
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
        courseTerm = repository.getCourseTermTitle(courseID);
        selectedPosition = arrayAdapter.getPosition(courseTerm);
        spinner.setSelection(selectedPosition);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.courseDetailRecyclerView);
        List<Assessment> assessments = repository.getAllFilteredAssessments(title);
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssessments(assessments);
    }

    private void updateLabelStart() {
        editCourseStart.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        editCourseEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_coursedetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, courseNotes);
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Notes for " + title);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifyStart:
                String dateFromScreen = editCourseStart.getText().toString();
                Date myDate = null;
                try{
                    myDate = sdf.parse(dateFromScreen);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(CourseDetail.this, MyReceiver.class);
                intent.putExtra("key", title + " starts today");
                PendingIntent sender = PendingIntent.getBroadcast(CourseDetail.this, MainActivity.numAlert++, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.notifyEnd:
                String endDateFromScreen = editCourseEnd.getText().toString();
                Date myEndDate = null;
                try{
                    myEndDate = sdf.parse(endDateFromScreen);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                Long endTrigger = myEndDate.getTime();
                Intent newIntent = new Intent(CourseDetail.this, MyReceiver.class);
                newIntent.putExtra("key", title + " ends today");
                PendingIntent newSender = PendingIntent.getBroadcast(CourseDetail.this, MainActivity.numAlert++, newIntent, 0);
                AlarmManager newAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                newAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, newSender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveCourse(View view) {
        Spinner spinner = findViewById(R.id.editCourseTerm);
        Course course;
        if(TermAdd.isDate(editCourseStart.getText().toString()) && TermAdd.isDate(editCourseEnd.getText().toString())) {
            course = new Course(courseID, editCourseTitle.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editCourseStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), spinner.getSelectedItem().toString(), editCourseNotes.getText().toString());
            repository.update(course);

            Intent intent = new Intent(CourseDetail.this, CourseList.class);
            startActivity(intent);
        } else {
            Toast.makeText(CourseDetail.this, "Invalid Date", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteCourse(View view) {
        Spinner spinner = findViewById(R.id.editCourseTerm);
        Course course;
        course = new Course(courseID, editCourseTitle.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editCourseStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), spinner.getSelectedItem().toString(), editCourseNotes.getText().toString());
        repository.delete(course);

        Intent intent = new Intent(CourseDetail.this, CourseList.class);
        startActivity(intent);
    }
}