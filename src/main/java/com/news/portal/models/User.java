package com.news.portal.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "password")
    private String password;

    @JsonBackReference
    @OneToMany(mappedBy = "author")
    private Set<Comment> comments;
}
