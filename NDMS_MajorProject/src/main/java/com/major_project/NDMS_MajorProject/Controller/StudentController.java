package com.major_project.NDMS_MajorProject.Controller;

import com.major_project.NDMS_MajorProject.Entity.Receipt;
import com.major_project.NDMS_MajorProject.Entity.Student;
import com.major_project.NDMS_MajorProject.Entity.StudentDues;
import com.major_project.NDMS_MajorProject.Repository.ReceiptRepository;
import com.major_project.NDMS_MajorProject.Repository.StudentRepository;
import com.major_project.NDMS_MajorProject.Service.ReceiptService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private ReceiptService receiptService;
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model,
                                @ModelAttribute("successMessage") String successMessage) {

        Student student = (Student) session.getAttribute("loggedInStudent");
        if (student == null) {
            return "redirect:/student/login";
        }

        model.addAttribute("student", student); // ✅ required for student.name
        model.addAttribute("studentRollNumber", student.getRollNumber()); // optional, if used in view

        if (successMessage != null && !successMessage.isEmpty()) {
            model.addAttribute("successMessage", successMessage);
        }

        return "student_dashboard";
    }
    @Autowired
    private ReceiptRepository receiptRepository;

    // StudentController.java

    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/view-dues")
    public String viewDues(HttpSession session, Model model) {
        String rollNumber = (String) session.getAttribute("studentRollNumber");

        if (rollNumber == null) {
            return "redirect:/student/login";
        }

        // ✅ Only get latest admin-updated receipt
        Receipt latestReceipt = receiptService.getLatestAdminUpdatedReceipt(rollNumber);
        double dueAmount = 0.0;

        if (latestReceipt != null) {
            dueAmount = latestReceipt.getDueAmount() != null ? latestReceipt.getDueAmount() : 0.0;
        }

        model.addAttribute("dueAmount", dueAmount);
        return "view-dues";
    }

















    @GetMapping("/upload-receipt")
    public String showUploadReceiptForm(Model model, HttpSession session) {
        String rollNumber = (String) session.getAttribute("studentRollNumber");
        String name = (String) session.getAttribute("studentName");

        if (rollNumber == null || name == null) {
            return "redirect:/student/login";
        }

        Receipt receipt = new Receipt();
        receipt.setRollNumber(rollNumber);
        receipt.setStudentName(name);

        model.addAttribute("receipt", receipt);
        return "upload-receipt";
    }



    @PostMapping("/upload-receipt")
    public String handleReceiptUpload(@ModelAttribute Receipt receipt,
                                      @RequestParam("yearlyCommitment") double yearlyCommitment,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes) {

        String rollNumber = (String) session.getAttribute("studentRollNumber");
        String name = (String) session.getAttribute("studentName");

        if (rollNumber == null || name == null) {
            return "redirect:/student/login";
        }

        receipt.setRollNumber(rollNumber);
        receipt.setStudentName(name);
        receipt.setYearlyCommitment(yearlyCommitment);

        double dueAmount = yearlyCommitment - receipt.getAmount();
        receipt.setDueAmount(dueAmount);

        receiptService.saveReceipt(receipt);

        redirectAttributes.addFlashAttribute("successMessage", "Receipt uploaded successfully!");
        return "redirect:/student/dashboard";
    }
}
