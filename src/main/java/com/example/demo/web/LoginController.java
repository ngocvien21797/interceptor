package com.example.demo.web;

import com.example.demo.security.UserSession;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session) {
        if ("admin".equals(username) && "admin".equals(password)) {
            session.setAttribute("USER", new UserSession(1L, "admin", "ADMIN"));
            return "redirect:/admin/dashboard";
        }
        if ("user".equals(username) && "user".equals(password)) {
            session.setAttribute("USER", new UserSession(2L, "user", "USER"));
            return "redirect:/home";
        }
        return "redirect:/login?error";
    }

    @GetMapping("/403")
    public String denied() {
        return "403";
    }
}
