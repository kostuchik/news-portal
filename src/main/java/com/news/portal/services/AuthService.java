package com.news.portal.services;


import com.news.portal.dto.LoginRequest;
import com.news.portal.dto.LoginResponse;
import com.news.portal.dto.UserDataRequest;
import com.news.portal.models.User;
import com.news.portal.models.role.Role;
import com.news.portal.repositories.RoleRepository;
import com.news.portal.repositories.UserRepository;
import com.news.portal.util.JwtTokenUtils;
import com.news.portal.util.exceptions.AuthError;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;

    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<?> createAuthToken(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AuthError(HttpStatus.UNAUTHORIZED.value(), "Incorrect login or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token, userDetails.getUsername()));
    }

    public User register(UserDataRequest registrationRequest) {
        User user = new User(registrationRequest);

        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new EntityExistsException("Пользователь " + registrationRequest.getUsername() + " уже существует!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = registrationRequest.getRoles().stream()
                .map(r -> roleRepository.findByRoleName(r)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Роль " + r.name() + " не найдена!")))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }
}
