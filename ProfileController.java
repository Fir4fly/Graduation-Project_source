package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.MedalService; // MedalServiceも利用
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private MedalService medalService; // MedalServiceをDI

    @PostMapping("/update-nickname")
    public String updateNickname(
            @RequestParam("newNickname") String newNickname,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        String loginID = (String) session.getAttribute("loginID");

        if (loginID == null) {
            // ログインしていない場合はログイン画面へリダイレクト
            return "redirect:/login";
        }
        
        // ニックネームのバリデーション（例: null, 空白チェック）
        if (newNickname == null || newNickname.trim().isEmpty()) {
             redirectAttributes.addFlashAttribute("updateError", "ニックネームを入力してください。");
             return "redirect:/home"; // または元の画面へ戻る
        }
        
        // 1. Userテーブルの更新
        boolean userUpdateSuccess = userService.updateNickname(loginID, newNickname);
        
        // 2. Medalテーブルの更新（Medalテーブルにもnicknameがあるため）
        try {
            medalService.updateNickname(loginID, newNickname); // MedalServiceに新規メソッドが必要です
        } catch (Exception e) {
            // エラーハンドリング
            System.err.println("Medalテーブルのニックネーム更新に失敗しました: " + e.getMessage());
            userUpdateSuccess = false; // どちらかの更新が失敗したら全体を失敗と見なす
        }
        
        if (userUpdateSuccess) {
            // セッション情報の更新
            session.setAttribute("nickname", newNickname);
            
            // 成功メッセージをリダイレクト先に渡す
            redirectAttributes.addFlashAttribute("updateSuccess", true);
            
        } else {
            // 失敗メッセージ
            redirectAttributes.addFlashAttribute("updateError", "ニックネームの更新に失敗しました。");
        }

        // Home画面にリダイレクトして、更新後の情報を再表示
        return "redirect:/home";
    }
}