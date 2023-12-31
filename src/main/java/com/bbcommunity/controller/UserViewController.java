package com.bbcommunity.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbcommunity.dto.UserRegisterDto;
import com.bbcommunity.dto.UserRolesRequest;
import com.bbcommunity.entity.User;
import com.bbcommunity.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserViewController {
	private final UserService userService;
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new UserRegisterDto());
		return "user/register";
	}
	
	@GetMapping("/login")
	public String login() {
	    return "user/login";
	}
	
	
	@GetMapping("/myInfo")
	public String myInfo(Model model) {
        // 현재 로그인 중인 사용자의 Member 엔티티를 가져옴
        User loggedInUser = userService.getCurrentLoggedInMember();
        if (loggedInUser != null) {
            model.addAttribute("name", loggedInUser.getName());
            model.addAttribute("email", loggedInUser.getEmail());
            model.addAttribute("gender", loggedInUser.getGender());
            model.addAttribute("nickname", loggedInUser.getNickname());
            model.addAttribute("regdate", loggedInUser.getRegdate());
            model.addAttribute("role", loggedInUser.getRole());
        } else {
            // 로그인 안됐을때 로그인 페이지로 이동시키기
        	return "user/login";
        }

        return "user/myInfo";
    }
	
	@GetMapping("/changePw")
	public String changePw() {
	    return "user/changePw";
	}
	
	@GetMapping("/changeInfo")
	public String changeInfo(Model model) {
		// 현재 로그인 중인 사용자의 Member 엔티티를 가져옴
        User loggedInUser = userService.getCurrentLoggedInMember();
        if (loggedInUser != null) {
            model.addAttribute("name", loggedInUser.getName());
            model.addAttribute("email", loggedInUser.getEmail());
            model.addAttribute("gender", loggedInUser.getGender());
            model.addAttribute("nickname", loggedInUser.getNickname());
            model.addAttribute("regdate", loggedInUser.getRegdate());
            model.addAttribute("role", loggedInUser.getRole());
            model.addAttribute("nickname", loggedInUser.getNickname());
        } else {
            // 로그인 안됐을때 로그인 페이지로 이동시키기
        	return "user/login";
        }
	    return "user/changeInfo";
	}
	
	@GetMapping("/resign")
	public String resignPage() {
	    return "user/resign";
	}
	
	@GetMapping("/userManagement")
	public String userManagement(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "user/userManagement";
	}
	
	@PostMapping("/userManagement")
	public ResponseEntity<?> updateRoles(@RequestBody UserRolesRequest request) {
	    userService.updateRoles(request.getEmail(), request.getRole());
	    return ResponseEntity.ok("Roles updated successfully");
	}
}
