package com.news.portal.dto;

import com.news.portal.models.role.ERole;
import com.news.portal.models.role.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class UserDataRequest {

    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Set<ERole> roles;

    public Set<Role> convertToRoleSet() {
        return roles.stream().map(r -> new Role(r)).collect(Collectors.toSet());
    }
}
