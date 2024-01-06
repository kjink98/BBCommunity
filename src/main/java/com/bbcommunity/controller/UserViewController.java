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
import com.bbcommunity.role.Role;
import com.bbcommunity.service.UserService;

import lombok.RequiredArgsConstructor;
/*
* 사용자 관련 기능을 제공하는 컨트롤러입니다.
* 각 사용자의 등록, 로그인, 정보 조회 및 변경, 계정 삭제, 사용자 관리(관리자 전용) 기능을 제공합니다.
*/
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserViewController {
	private final UserService userService;
	/*
	* 사용자 등록 페이지로 이동합니다.
	*/
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new UserRegisterDto());
		return "user/register";
	}
	/*
	* 로그인 페이지로 이동합니다.
	*/
	@GetMapping("/login")
	public String login() {
	    return "user/login";
	}
	
	/*
	* 현재 로그인 중인 사용자의 정보를 조회합니다.
	*/
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
            
            // 관리자인지 여부를 판단하여 모델에 추가
            boolean isAdmin = loggedInUser.getRole() == Role.ADMIN;
            model.addAttribute("isAdmin", isAdmin);
        } else {
            // 로그인 안됐을때 로그인 페이지로 이동시키기
        	return "user/login";
        }

        return "user/myInfo";
    }
	/*
	* 비밀번호 변경 페이지로 이동합니다.
	*/
	@GetMapping("/changePw")
	public String changePw() {
	    return "user/changePw";
	}
	/*
	* 사용자 정보 변경 페이지로 이동합니다.
	*/
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
	/*
	* 계정 삭제 페이지로 이동합니다.
	*/
	@GetMapping("/resign")
	public String resignPage() {
	    return "user/resign";
	}
	/*
	* 모든 사용자의 정보를 조회하는 사용자 관리 페이지로 이동합니다.
	* 이 기능은 관리자만 이용 가능합니다.
	*/
	@GetMapping("/userManagement")
	public String userManagement(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "user/userManagement";
	}
	/*
	* 사용자의 역할을 업데이트하는 기능입니다.
	* 이 기능은 관리자만 이용 가능합니다.
	*/
	@PostMapping("/userManagement")
	public ResponseEntity<?> updateRoles(@RequestBody UserRolesRequest request) {
	    userService.updateRoles(request.getEmail(), request.getRole());
	    return ResponseEntity.ok("Roles updated successfully");
	}
}
