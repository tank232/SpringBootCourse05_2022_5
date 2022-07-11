package com.av.dao.impl;

import com.av.dao.AuthorDao;
import com.av.dao.BookDao;
import com.av.dao.GenreDao;
import com.av.domain.Author;
import com.av.domain.Book;
import com.av.domain.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class BookDaoImpl implements BookDao<Long, Book> {

    private static final String FETCH_SQL =
            "select b.id,\n" +
                    "       title,\n" +
                    "       isbn,\n" +
                    "       edition,\n" +
                    "       publish_year,\n" +
                    "       a.name as book_name,\n" +
                    "       a.id   as book_id,\n" +
                    "       g.name as genre_name,\n" +
                    "       g.id as genre_id \n" +
                    "from book b\n" +
                    "         join book_author_t bat on b.id = bat.book_id\n" +
                    "         join authors a on a.id = bat.author_id \n" +
                    "left join genre g on b.genre_id = g.id";
    private static final String DELETE_SQL = "delete from book where id = :id";
    private static final String UPDATE_SQL =
            "update book\n" +
                    "set  title = :title, isbn = :isbn,  edition= :edition,\n" +
                    "    publish_year = :publish_year , genre_id = :genre_id\n" +
                    "where id = :id";

    private static final String INSERT_SQL =
            "insert into book ( title, isbn, edition, publish_year , genre_id)\n" +
                    "values (:title, :isbn, :edition, :publish_year, :genre_id)";
    private static final String INSERT_AUTHOR_LINK_SQL =
            "insert into book_author_t (book_id, author_id) values (:book_id, :author_id)";

    private final GenreDao genreDao;
    private final AuthorDao authorDao;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookDaoImpl(GenreDao genreDao, AuthorDao authorDao, NamedParameterJdbcTemplate jdbcTemplate) {
        this.genreDao = genreDao;
        this.authorDao = authorDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book add(Book book) {
        var holder = new GeneratedKeyHolder();

        var parameters = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("isbn", book.getIsbn())
                .addValue("edition", book.getEdition())
                .addValue("publish_year", book.getPublishYear())
                .addValue("genre_id", book.getGenre() == null ? null : book.getGenre().getId());

        jdbcTemplate.update(INSERT_SQL, parameters, holder, new String[]{"id"});
        book.setId(holder.getKey().longValue());

        book
                .getAuthors()
                .forEach(
                        a -> {
                            var linkParam = new MapSqlParameterSource()
                                    .addValue("book_id", book.getId())
                                    .addValue("author_id", a.getId());
                            jdbcTemplate.update(INSERT_AUTHOR_LINK_SQL, linkParam);
                        }
                );

        return book;
    }

    @Override
    public void update(Book book) {
        var parameters = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("title", book.getTitle())
                .addValue("isbn", book.getIsbn())
                .addValue("edition", book.getEdition())
                .addValue("publish_year", book.getPublishYear())
                .addValue("genre_id", book.getGenre() == null ? null : book.getGenre().getId());
        jdbcTemplate.update(UPDATE_SQL, parameters);
    }

    @Override
    public void delete(Book book) {
        deleteById(book.getId());
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query(FETCH_SQL, new BookListResultSetExtractor());
    }

    @Override
    public void deleteById(Long id) {
        var namedParameters = new MapSqlParameterSource("id", id);
        int status = jdbcTemplate.update(DELETE_SQL, namedParameters);
        if (status != 0) {
            log.debug("Book with id = " + id + " removed from storage");
        } else {
            log.debug("Book not found in storage");
        }
    }

    private static class BookListResultSetExtractor implements ResultSetExtractor<List<Book>> {

        @Override
        public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            var books = new HashMap<Long, Book>();
            long bookId;

            while (rs.next()) {
                bookId = rs.getLong("id");
                Book book;
                if (books.containsKey(bookId)) {
                    book = books.get(bookId);
                } else {
                    book = new Book();
                    book.setId(rs.getLong("id"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setTitle(rs.getString("title"));
                    book.setPublishYear(rs.getInt("publish_year"));
                    book.setEdition(rs.getInt("edition"));

                    books.put(bookId, book);
                }

                var genfeId = rs.getLong("genre_id");
                if (!rs.wasNull()) {
                    var genre = new Genre(genfeId, rs.getString("genre_name"));
                    book.setGenre(genre);
                }
                var a = new Author(rs.getLong("book_id"), rs.getString("book_name"));
                book.getAuthors().add(a);
            }
            return new ArrayList<Book>(books.values());
        }
    }
}
