package com.example.termscheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;

    private String assessmentStart;
    private String assessmentEnd;
    private String assessmentType;
    private String assessmentCourseTitle;

    public Assessment(int assessmentID, String assessmentStart, String assessmentEnd, String assessmentType, String assessmentCourseTitle) {
        this.assessmentID = assessmentID;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
        this.assessmentType = assessmentType;
        this.assessmentCourseTitle = assessmentCourseTitle;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentStart() {
        return assessmentStart;
    }

    public void setAssessmentStart(String assessmentStart) {
        this.assessmentStart = assessmentStart;
    }

    public String getAssessmentEnd() {
        return assessmentEnd;
    }

    public void setAssessmentEnd(String assessmentEnd) {
        this.assessmentEnd = assessmentEnd;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentCourseTitle() {
        return assessmentCourseTitle;
    }

    public void setAssessmentCourseTitle(String assessmentCourseTitle) {
        this.assessmentCourseTitle = assessmentCourseTitle;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentID=" + assessmentID +
                ", assessmentStart=" + assessmentStart +
                ", assessmentEnd=" + assessmentEnd +
                ", assessmentType='" + assessmentType + '\'' +
                ", assessmentCourseTitle='" + assessmentCourseTitle + '\'' +
                '}';
    }
}
