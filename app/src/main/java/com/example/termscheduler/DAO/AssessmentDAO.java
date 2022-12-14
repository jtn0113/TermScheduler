package com.example.termscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.termscheduler.Entity.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentID ASC")
    List<Assessment> getAllAssessments();

    @Query("SELECT assessmentCourseTitle from assessments WHERE assessmentID = :assessmentID")
    String getAssessmentCourseTitle(int assessmentID);

    @Query("SELECT assessmentType FROM assessments WHERE assessmentID = :assessmentID")
    String getAssessmentType(int assessmentID);

    @Query("SELECT * FROM assessments WHERE assessmentCourseTitle = :courseTitle")
    List<Assessment> getAssessmentsForCourse(String courseTitle);
}
