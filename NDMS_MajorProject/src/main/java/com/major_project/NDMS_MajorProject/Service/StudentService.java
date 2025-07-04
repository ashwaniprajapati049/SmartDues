package com.major_project.NDMS_MajorProject.Service;

import com.major_project.NDMS_MajorProject.Entity.Student;
import com.major_project.NDMS_MajorProject.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ReceiptService receiptService; // ✅ Add this

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional
    public void removeStudentByRollNumber(String rollNumber) {
        // Step 1: Delete receipts
        receiptService.deleteReceiptsByRollNumber(rollNumber);

        // Step 2: Delete student
        studentRepository.deleteByRollNumber(rollNumber);
    }
}
