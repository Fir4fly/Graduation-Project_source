package com.example.demo.controller;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Medal;
import com.example.demo.service.MedalService;

@Controller
public class HomeController {
	
	@Autowired
	private MedalService medalService;
	
	@RequestMapping("/home")
	public String showHomePage(
			//@RequestParam(name = "loginID") String loginID,
			//@RequestParam(name = "nickname") String nickname,
			Model model,
			HttpSession session
		){
		String loginID = (String)session.getAttribute("loginID");
		String nickname = (String)session.getAttribute("nickname");
		
		Optional<Medal> myMedalOptional = medalService.getMyMedalInfo(loginID);
		
		List<Medal> topRanking = medalService.getTopRanking();
	
		model.addAttribute("nickname", nickname);
		
		model.addAttribute("myMedals", myMedalOptional.map(Medal::getMyMedal).orElse(0));
		
		model.addAttribute("rankingList", topRanking);
		
		return "home";
	}
	
	@GetMapping("/gamelist")
	public String showGameList() {
		return "gamelist";
	}
	
	@GetMapping("/shop")
	public String showShop() {
		return "shop";
	}
	
	@GetMapping("/ranking")
	public String showRanking() {
		return "ranking";
	}
	
	@GetMapping("/achievements")
	public String showAchievemets() {
		return "achievements";
	}
}
