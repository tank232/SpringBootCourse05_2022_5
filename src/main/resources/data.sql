insert into authors (name)
values ('Jon Daw')
;

insert into authors (name)
values ('Егор Бугаенко')
;

insert into book(title, isbn, edition, publish_year)
values ('simple book', '111111', '1', '2021');


insert into book_author_t (book_id, author_id)
select b.id, a.id
from book b
         join authors a
              on true
where a.name in ('Jon Daw', 'Егор Бугаенко')
  and b.title = 'simple book'
  and b.edition = 1

  and not exists(
        select null
        from book_author_t tt
        where tt.book_id = b.id
    )
;


insert into genre (name)
select 'computer science' as n
union
select 'technics'
union
select  'fiction';
