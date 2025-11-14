package com.example.demo.controller;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
public class LoginController {
	@Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("loginID") String loginID,
                        @RequestParam("loginPass") String loginPass,
                        Model model,
                        HttpSession session) {
    	User authenticatedUser = userService.authenticate(loginID, loginPass);
        if (authenticatedUser != null) {
        	//String encodedNickname = authenticatedUser.getNickname();
        	session.setAttribute("nickname", authenticatedUser.getNickname());
            session.setAttribute("loginID", authenticatedUser.getLoginID());
        	return "redirect:/home";
        	//?loginId=" + authenticatedUser.getLoginID() + "&nickname=" + encodedNickname;
        } else {
            model.addAttribute("error", "ユーザー名またはパスワードが間違っています");
            return "login";
        }
    }
}
