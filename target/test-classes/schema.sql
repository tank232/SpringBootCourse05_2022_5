create table if not exists authors
(
    id   serial
        constraint authors_pk
            primary key,
    name varchar(200) not null
);

create unique index if not exists authors_name_u1
    on authors (name);

create table if not exists genre
(
    id   serial
        constraint genre_pk
            primary key,
    name varchar(200) not null
);

create unique index if not exists genre_name_u1
    on genre (name);


create table if not exists  book
(
    id serial not null
        constraint book_pk
            primary key,
    title varchar(100) not null,
    isbn varchar(50) not null,
    edition smallint default 1 not null,
    publish_year smallint not null,
    genre_id integer
        constraint book_genre_id_fk
            references genre
);



create unique index  if not exists  book_title_u1
    on book (title, edition);


create unique index if not exists book_title_u1
    on book (title, edition);


create table if not exists book_author_t
(
    id serial not null
        constraint book_author_t_pk
            primary key,
    book_id integer
        constraint book_author_t_book_id_fk
            references book
            on delete no action ,
    author_id integer
        constraint book_author_a_fk
            references authors
            on delete no action
);


create unique  index if not exists  book_author_u1
    on book_author_t (book_id, author_id);
