package com.major_project.NDMS_MajorProject.Service;

import com.major_project.NDMS_MajorProject.Entity.Receipt;
import com.major_project.NDMS_MajorProject.Repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ReceiptService {
    @Autowired
    private ReceiptRepository receiptRepository;
    public Receipt getLatestAdminUpdatedReceipt(String rollNumber) {
        List<Receipt> receipts = receiptRepository.findTopAdminUpdatedReceipt(rollNumber);
        return receipts != null && !receipts.isEmpty() ? receipts.get(0) : null;
    }


    public void deleteReceiptsByRollNumber(String rollNumber) {
        receiptRepository.deleteByRollNumber(rollNumber);
    }

//    @Autowired
//    private ReceiptRepository receiptRepository;

    public Receipt getLatestReceiptByRollNumber(String rollNumber) {
        return receiptRepository.findTopByRollNumberOrderByReceiptDateDesc(rollNumber);
    }

    public List<Receipt> getReceiptsByRollNumber(String rollNumber) {
        return receiptRepository.findByRollNumber(rollNumber);
    }

    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    public void saveReceipt(Receipt receipt) {
        receipt.setAdminUpdated(false); // ✅ Set default value for new student-uploaded receipts
        receiptRepository.save(receipt);
    }

    public void updateDues(Long receiptId, Double dueAmount) {
        Receipt oldReceipt = receiptRepository.findById(receiptId).orElseThrow();

        // ❗Delete the old receipt
        receiptRepository.deleteById(receiptId);

        // ✅ Save the updated receipt
        Receipt updatedReceipt = new Receipt();
        updatedReceipt.setRollNumber(oldReceipt.getRollNumber());
        updatedReceipt.setStudentName(oldReceipt.getStudentName());
        updatedReceipt.setYear(2025);
        updatedReceipt.setSemester("N/A");
        updatedReceipt.setAmount(0.0);
        updatedReceipt.setDueAmount(dueAmount);
        updatedReceipt.setYearlyCommitment(dueAmount);
        updatedReceipt.setReceiptNumber("R-" + UUID.randomUUID().toString().substring(0, 8));
        updatedReceipt.setTransactionId("T-" + UUID.randomUUID().toString().substring(0, 8));
        updatedReceipt.setReceiptDate(LocalDate.now().toString());
        updatedReceipt.setAdminUpdated(true);

        receiptRepository.save(updatedReceipt);
    }


    public void clearDues(Long receiptId) {
        Receipt oldReceipt = receiptRepository.findById(receiptId).orElseThrow();

        // ❗Delete the old receipt
        receiptRepository.deleteById(receiptId);

        // ✅ Save the cleared receipt
        Receipt updatedReceipt = new Receipt();
        updatedReceipt.setRollNumber(oldReceipt.getRollNumber());
        updatedReceipt.setStudentName(oldReceipt.getStudentName());
        updatedReceipt.setYear(2025);
        updatedReceipt.setSemester("N/A");
        updatedReceipt.setAmount(0.0);
        updatedReceipt.setDueAmount(0.0);
        updatedReceipt.setYearlyCommitment(oldReceipt.getDueAmount());
        updatedReceipt.setReceiptNumber("R-" + UUID.randomUUID().toString().substring(0, 8));
        updatedReceipt.setTransactionId("T-" + UUID.randomUUID().toString().substring(0, 8));
        updatedReceipt.setReceiptDate(LocalDate.now().toString());
        updatedReceipt.setAdminUpdated(true);

        receiptRepository.save(updatedReceipt);
    }


    public List<Receipt> getLatestReceiptsForAllStudents() {
        List<Receipt> allReceipts = receiptRepository.findAll();

        Map<String, Receipt> latestReceiptMap = new HashMap<>();

        for (Receipt r : allReceipts) {
            String roll = r.getRollNumber();
            if (!latestReceiptMap.containsKey(roll)) {
                latestReceiptMap.put(roll, r);
            } else {
                LocalDate current = LocalDate.parse(r.getReceiptDate());
                LocalDate existing = LocalDate.parse(latestReceiptMap.get(roll).getReceiptDate());
                if (current.isAfter(existing)) {
                    latestReceiptMap.put(roll, r);
                }
            }
        }

        return new ArrayList<>(latestReceiptMap.values());
    }

}
