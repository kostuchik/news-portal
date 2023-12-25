CREATE TABLE USERS (
    user_id bigserial PRIMARY KEY,
    username varchar(15) UNIQUE NOT NULL,
    email varchar(20) UNIQUE NOT NULL,
    avatar varchar(250),
    password varchar(250) NOT NULL
);

CREATE TABLE ROLES (
    role_id bigserial PRIMARY KEY,
    role_name varchar(10) UNIQUE
);

CREATE TABLE USER_ROLES (
    user_id bigint,
    role_id bigint,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id)
);

CREATE TABLE NEWS (
    news_id bigserial PRIMARY KEY,
    title varchar(40) NOT NULL UNIQUE,
    publication_date timestamp NOT NULL,
    description varchar(128) NOT NULL,
    news_text text NOT NULL
);

CREATE TABLE COMMENTS (
    comment_id bigserial PRIMARY KEY,
    date timestamp NOT NULL,
    text text NOT NULL,
    user_id bigint,
    parent_id bigint,
    news_id bigint,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (news_id) REFERENCES news(news_id)
);