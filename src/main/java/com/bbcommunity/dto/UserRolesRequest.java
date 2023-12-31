package com.bbcommunity.dto;

import com.bbcommunity.role.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRolesRequest {
	private String email;
    private Role role;
}
