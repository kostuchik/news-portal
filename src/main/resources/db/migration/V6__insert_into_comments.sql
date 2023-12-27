INSERT INTO COMMENTS (comment_id, date, text, user_id, parent_id, news_id)
VALUES
    (1, CURRENT_TIMESTAMP, 'This first comment', 2, null, 1),
    (2, CURRENT_TIMESTAMP, 'Hello', 1, 1, 1),
    (3, CURRENT_TIMESTAMP, 'Bye!', 3, 2, 1),
    (4, CURRENT_TIMESTAMP, 'Okay.', 2, null, 2),
    (5, CURRENT_TIMESTAMP, 'Wow', 3, 4, 2);