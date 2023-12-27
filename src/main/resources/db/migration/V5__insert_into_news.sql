INSERT INTO NEWS (news_id, title, publication_date, description, news_text)
VALUES
(1, 'First News', CURRENT_TIMESTAMP,
'This description',
 'Here are some examples of many-to-many relationships. ' ||
    'In a hiring platform database, a single contractor ' ||
    'can work for many companies, and each different company can ' ||
    'have many contractors working for them.'),
(2, 'Second News', CURRENT_TIMESTAMP,
 'This description2',
 'A bcrypt encoder can be useful if you''re doing cross-browser testing. ' ||
 'For example, if you''re writing tests that involve hashed passwords,' ||
 ' then you can use this utility to create a lot of valid bcrypt password ' ||
 'hashes for your tests. ');



