package com.major_project.NDMS_MajorProject.Controller;

import com.major_project.NDMS_MajorProject.Entity.Receipt;
import com.major_project.NDMS_MajorProject.Repository.ReceiptRepository;
import com.major_project.NDMS_MajorProject.Service.ReceiptService;
import com.major_project.NDMS_MajorProject.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ReceiptService receiptService;

    // Admin Dashboard
    @GetMapping("/dashboard")
    public String showAdminDashboard() {
        return "admin_dashboard"; // This should match your admin dashboard HTML filename
    }

    // Manage Students
    @GetMapping("/students")
    public String manageStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "manage_students";
    }

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam String rollNumber) {
        studentService.removeStudentByRollNumber(rollNumber);
        return "redirect:/admin/students";
    }

    // Manage Dues Section
    @GetMapping("/manage-dues")
    public String viewDues(@RequestParam(name = "rollNumber", required = false) String rollNumber, Model model) {
        List<Receipt> receipts;

        if (rollNumber != null && !rollNumber.isEmpty()) {
            receipts = receiptService.getReceiptsByRollNumber(rollNumber);
        } else {
            receipts = receiptService.getLatestReceiptsForAllStudents(); // ⬅️ Use the new method
        }

        model.addAttribute("receipts", receipts);
        model.addAttribute("rollNumber", rollNumber);
        return "manage_dues";
    }

    @GetMapping("/dues")
    public String manageDues(Model model) {
        List<Receipt> receipts = receiptService.getAllReceipts(); // Make sure this method exists
        model.addAttribute("receipts", receipts);
        return "manage-dues";
    }

    @PostMapping("/update-dues")
    public String updateDues(@RequestParam Long receiptId, @RequestParam double dueAmount) {
        receiptService.updateDues(receiptId, dueAmount);
        return "redirect:/admin/dues";
    }

    @PostMapping("/admin/clear-dues")
    public ResponseEntity<String> clearDues(@RequestParam Long receiptId) {
        receiptService.clearDues(receiptId);
        return ResponseEntity.ok("Dues cleared");
    }


    @Autowired
    private ReceiptRepository receiptRepository;
    @PostMapping("/admin/update-dues")
    public ResponseEntity<String> updateDues(@RequestParam Long receiptId,
                                             @RequestParam Double dueAmount) {
        receiptService.updateDues(receiptId, dueAmount);
        return ResponseEntity.ok("Dues updated");
    }


}
