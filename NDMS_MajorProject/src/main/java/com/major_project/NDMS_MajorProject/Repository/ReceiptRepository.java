package com.major_project.NDMS_MajorProject.Repository;

import com.major_project.NDMS_MajorProject.Entity.Receipt;
import com.major_project.NDMS_MajorProject.Entity.StudentDues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    @Query("SELECT r FROM Receipt r WHERE r.rollNumber = :rollNumber AND r.adminUpdated = true ORDER BY r.receiptDate DESC, r.id DESC")
    List<Receipt> findTopAdminUpdatedReceipt(@Param("rollNumber") String rollNumber);


    // Get all receipts by roll number
    List<Receipt> findByRollNumber(String rollNumber);

    // Fetch the latest receipt for a roll number
    Receipt findTopByRollNumberOrderByReceiptDateDesc(String rollNumber);

    // ✅ This method is fine, can keep it for other usage
    @Query("SELECT new com.major_project.NDMS_MajorProject.Entity.StudentDues(r.rollNumber, r.yearlyCommitment - r.amount) " +
            "FROM Receipt r WHERE r.rollNumber = :rollNumber " +
            "ORDER BY r.receiptDate DESC")
    StudentDues findTopDueByRollNumber(@Param("rollNumber") String rollNumber);

    // ✅ CORRECT version of findLatestDueByRollNumber (only keep this one)
    @Query("SELECT new com.major_project.NDMS_MajorProject.Entity.StudentDues(r.rollNumber, r.dueAmount) " +
            "FROM Receipt r WHERE r.rollNumber = :rollNumber " +
            "ORDER BY r.receiptDate DESC, r.id DESC")
    List<StudentDues> findLatestDuesByRollNumber(@Param("rollNumber") String rollNumber);
    void deleteByRollNumber(String rollNumber);
    @Query("SELECT DISTINCT r.rollNumber FROM Receipt r")
    List<String> findDistinctRollNumbers();

}
