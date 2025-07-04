package com.major_project.NDMS_MajorProject.Controller;

import com.major_project.NDMS_MajorProject.Entity.Student;
import com.major_project.NDMS_MajorProject.Repository.AdminRepository;
import com.major_project.NDMS_MajorProject.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private StudentRepository studentRepo;

    // Show login page (for both admin and students)
    @GetMapping("/")
    public String showLoginPage() {
        return "login"; // View name: login.html
    }
    @GetMapping("/admin-login-page")
    public String showAdminLoginPage() {
        return "admin_login";  // This should match your admin login HTML file name (admin_login.html)
    }


    @PostMapping("/admin/login")
    public String loginAdmin(@RequestParam String username,
                             @RequestParam String password,
                             Model model) {
        // Fixed credentials check
        if (("Radharaman".equals(username) && "Radharaman@12".equals(password)) ||
                ("RGI".equals(username) && "RGI@12".equals(password))) {
            return "admin_dashboard";  // ✅ go to the correct admin dashboard page
        } else {
            model.addAttribute("error", "Invalid admin credentials");
            return "login"; // back to main login
        }
    }

    @GetMapping("/student/profile")
    public String viewStudentProfile(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("loggedInStudent");
        if (student == null) {
            return "redirect:/"; // not logged in
        }
        model.addAttribute("student", student);
        return "student_profile";
    }

//    @GetMapping("/student/dashboard")
//    public String showStudentDashboard(HttpSession session, Model model) {
//        Student student = (Student) session.getAttribute("loggedInStudent");
//        if (student == null) {
//            return "redirect:/"; // not logged in
//        }
//        model.addAttribute("student", student);
//        return "student_dashboard";
//    }


    // Student login using roll number and password
    @PostMapping("/student/login")
    public String loginStudent(@RequestParam String rollNumber,
                               @RequestParam String password,
                               Model model,
                               HttpSession session) {
        Student student = studentRepo.findByRollNumberAndPassword(rollNumber, password);
        if (student != null) {
            session.setAttribute("loggedInStudent", student);
            session.setAttribute("studentName", student.getName());              // ✅ added
            session.setAttribute("studentRollNumber", student.getRollNumber()); // ✅ added
            model.addAttribute("student", student);
            return "redirect:/student/dashboard"; // ✅ better to redirect
        } else {
            model.addAttribute("error", "Invalid student credentials");
            return "login";
        }
    }

    @GetMapping("/student/login")
    public String showStudentLoginPage() {
        return "login";
    }


    // Show registration page
    @GetMapping("/student/register-page")
    public String showRegisterPage() {
        return "student_register"; // View name: student_register.html
    }

    // Register a new student
    @PostMapping("/student/register")
    public String registerStudent(@RequestParam String name,
                                  @RequestParam String course,
                                  @RequestParam String branch,
                                  @RequestParam String rollNumber,
                                  @RequestParam String password,
                                  Model model) {
        try {
            if (studentRepo.findByRollNumber(rollNumber) != null) {
                model.addAttribute("error", "Roll number already registered");
                return "student_register";
            }

            Student student = new Student();
            student.setName(name);
            student.setCourse(course);
            student.setBranch(branch);
            student.setRollNumber(rollNumber);
            student.setPassword(password);
            studentRepo.save(student);

            model.addAttribute("success", "Registered successfully! Please log in.");
            return "redirect:/"; // Redirect to login page after registration

        } catch (Exception e) {
            e.printStackTrace(); // logs error in console
            model.addAttribute("error", "Something went wrong during registration.");
            return "student_register";
        }
    }

}
