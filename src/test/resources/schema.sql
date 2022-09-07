create table movies.academy_award
(
    id              bigint auto_increment
        primary key,
    created_date    datetime(6)  null,
    updated_date    datetime(6)  null,
    additional_info varchar(255) null,
    category        varchar(255) null,
    nominee         text         null,
    won             bit          null,
    year            varchar(255) null
);

create table movies.movie
(
    id           bigint auto_increment
        primary key,
    created_date datetime(6)    null,
    updated_date datetime(6)    null,
    box_office   decimal(19, 2) null,
    title        varchar(255)   null,
    year         int            null
);

create table movies.user
(
    id           bigint auto_increment
        primary key,
    created_date datetime(6)  null,
    updated_date datetime(6)  null,
    email        varchar(255) null,
    token        text         null
);

create table movies.rating
(
    id           bigint auto_increment
        primary key,
    created_date datetime(6) null,
    updated_date datetime(6) null,
    rating       double      null,
    movie_id     bigint      not null,
    user_id      bigint      not null,
    constraint FKlqsvmdlh3ep1boo7in23xe86y
        foreign key (movie_id) references movies.movie (id),
    constraint FKpn05vbx6usw0c65tcyuce4dw5
        foreign key (user_id) references movies.user (id)
);

