package com.news.portal.dto;

import com.news.portal.models.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class LoginResponse {
    private String token;
    private final String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private Set<Role> role;
}
