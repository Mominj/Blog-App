
CREATE TABLE IF NOT EXISTS role
(
    id   INTEGER PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS users
(
    id    INTEGER PRIMARY KEY AUTO_INCREMENT,
    username   varchar(50)           not null unique,
    password   varchar(250)          not null,
    enabled    boolean default false not null
);

CREATE TABLE IF NOT EXISTS posts
(
    id            INTEGER      not null AUTO_INCREMENT,
    title         varchar(255) not null,
    body          TEXT         not null,
    creation_date timestamp    ,
    user_id       INTEGER      not null,
    is_approved   boolean not null ,
    primary key (id),
    constraint FK5lidm6cqbc7u4xhqpxm898qme
        foreign key (user_id)
            references users
);



CREATE TABLE IF NOT EXISTS user_role
(
    user_id      INTEGER not null,
    role_id INTEGER not null,
    primary key (user_id, role_id),
    constraint FKdsfxx5g8x8mnxne1fe0yxhjhq
        foreign key (user_id)
            references users,
    constraint FKq3lq694rr66e6kpo2h84ad92q
        foreign key (role_id)
            references role
);
create table comments
(
    id            INTEGER    not null AUTO_INCREMENT,
    body          TEXT      not null,
    creation_date timestamp not null,
    post_id       INTEGER    not null,
    user_id       INTEGER    not null,
    primary key (id),
    constraint FKh4c7lvsc298whoyd4w9ta25cr
        foreign key (post_id)
            references posts,
    constraint FK8omq0tc18jd43bu5tjh6jvraq
        foreign key (user_id)
            references users
);
