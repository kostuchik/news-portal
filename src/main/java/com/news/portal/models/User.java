package com.news.portal.models;

import java.util.Set;

public class User {
    private Long id;
    private String username;
    private String email;
    private String avatar;
    private String password;
    private Set<Comment> comments;
}
