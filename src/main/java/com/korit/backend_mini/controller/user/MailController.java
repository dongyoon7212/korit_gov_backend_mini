package com.korit.backend_mini.controller.user;

import com.korit.backend_mini.security.model.PrincipalUser;
import com.korit.backend_mini.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(mailService.sendMail(principalUser));
    }

    @GetMapping("/verify")
    public String verify(Model model, @RequestParam String token) {
        Map<String, Object> resultMap = mailService.verify(token);
        model.addAllAttributes(resultMap);
        return "result_page";
    }
}
