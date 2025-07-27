package com.detai.StuSmart.controller;

import com.detai.StuSmart.model.Student;
import com.detai.StuSmart.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentLoginController {

    @Autowired
    private StudentService studentService;

    // Hiển thị form login
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-login"; // HTML file ở /templates/student-login.html
    }

    // Xử lý khi submit form bằng POST
    @PostMapping("/login")
    public String login(@ModelAttribute("student") Student student,
                        HttpSession session,
                        Model model) {
        Student authenticated = studentService.authentication(student.getUsername(), student.getPassword());

        if (authenticated != null) {

            session.setAttribute("loggedInStudent", authenticated);
            return "redirect:/student/home";
        } else {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
            return "student-login";
        }
    }

    // Trang sau khi đăng nhập thành công
    @GetMapping("/home")
    public String studentHome(HttpSession session) {
        if (session.getAttribute("loggedInStudent") == null)
        {
            return "redirect:/student/login";
        }
        return "student-home"; // File student-home.html trong /templates/
    }
}
