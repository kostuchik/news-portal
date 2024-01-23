package com.news.portal.services;


import com.news.portal.dto.UserUpdateRequest;
import com.news.portal.models.User;
import com.news.portal.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().
                        map(role -> new SimpleGrantedAuthority(role.getRoleName().toString())).
                        collect(Collectors.toList())
        );
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException("Пользователь с именем " + username + " не найден!"));
    }

    public Page<User> searchUserByText(String text, Pageable pageable) {
        return userRepository.searchUserByText(text, pageable);
    }

    public User findUserById(long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id = " + id + "не найден!"));
    }


    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User saveUser(User user) {
        try {
            return userRepository.save(user);

        } catch (RuntimeException e) {
            log.error("Пользователь {} не сохранён! Error: [{}].", user.getUsername(), e);
            throw new PersistenceException(String.format("Пользователь %s не сохранён! " +
                    "Error: [%s]", user.getUsername(), e));
        }
    }

    public User updateUser(String username, UserUpdateRequest request) {
        User user = findUserByUsername(username);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return saveUser(user);
    }

    public void deleteUser(String username) {
        if (userRepository.existsByUsername(username)) {
            userRepository.deleteByUsername(username);

        } else throw new EntityNotFoundException("Пользователь с именем \"" + username + "\" не найден!");
    }

    public void loadAvatar(String username, String uri) {
        User user = findUserByUsername(username);
        user.setAvatar(uri);
        saveUser(user);
    }
}
