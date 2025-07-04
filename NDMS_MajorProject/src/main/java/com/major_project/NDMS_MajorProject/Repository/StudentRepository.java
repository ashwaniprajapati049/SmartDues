package com.major_project.NDMS_MajorProject.Repository;

import com.major_project.NDMS_MajorProject.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByRollNumber(String rollNumber); // used for checking duplicates
    Student findByRollNumberAndPassword(String rollNumber, String password); // used for login
    void deleteByRollNumber(String rollNumber); // used in manage students
}

