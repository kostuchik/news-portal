package com.news.portal.models;

import java.util.Date;

public class Comment {
    private Long id;
    private Date date;
    private String text;
    private User author;
    private News news;
}
