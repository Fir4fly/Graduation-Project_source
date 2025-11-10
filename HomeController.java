package com.example.demo.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Medal;
import com.example.demo.service.MedalService;

@Controller
public class HomeController {
	
	@Autowired
	private MedalService medalService;
	
	@PostMapping("/home")
	public String showHomePage(
			//@RequestParam(name = "loginID") String loginID,
			//@RequestParam(name = "nickname") String nickname,
			Model model
		){
		String loginID = "1";
		String nickname = "2";
		
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
