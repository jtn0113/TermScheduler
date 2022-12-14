package com.example.termscheduler.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.termscheduler.Entity.Course;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses ORDER BY courseID ASC")
    List<Course> getAllCourses();

    @Query("SELECT courseTitle FROM courses")
    List<String> getAllCourseTitles();

    @Query("SELECT courseTermTitle FROM courses WHERE courseID = :courseID")
    String getCourseTermTitle(int courseID);

    @Query("SELECT * FROM courses WHERE courseTermTitle = :termTitle")
    List<Course> getCoursesForTerm(String termTitle);
}
