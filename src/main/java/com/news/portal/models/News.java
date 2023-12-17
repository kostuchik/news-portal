package com.news.portal.models;

import java.time.LocalDateTime;
import java.util.List;

public class News {
    private Long id;
    private String title;
    private final LocalDateTime publicationDate = LocalDateTime.now();
    private String description;
    private String text;
    private List<Comment> comments;
}
