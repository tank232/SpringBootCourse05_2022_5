package com.av.cli;

import com.av.dao.AuthorDao;
import com.av.dao.BookDao;
import com.av.dao.GenreDao;
import com.av.domain.Book;
import com.av.services.ObjectFormatter;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.MessageFormat;

@Slf4j
@ShellComponent
public class BookDefinerCommand {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final ObjectFormatter formatter;
    private final GenreDao genreDao;

    private Book newBook = new Book();

    public BookDefinerCommand(BookDao bookDao, AuthorDao authorDao, ObjectFormatter formatter, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.formatter = formatter;
        this.genreDao = genreDao;
    }

    @ShellMethod("show all books")
    public void listBooks() {
        log.info("ho ho");
        var books = bookDao.getAll();

        if (books.size() != 0) {
            books.forEach(book -> log.info(MessageFormat.format("Book:{0}", book.toString())));
        } else {
            log.debug("No author in db");
        }
    }

    @ShellMethod("show books")
    public void showBooks() {
        log.info("ho ho");
        var bookList = bookDao.getAll();

        if (bookList.size() != 0) {
            String data = null;
            try {
                data = formatter.format(bookList);
            } catch (JsonProcessingException e) {
                log.error("Ошибка форматирования", e);
            }
            log.info(data);
        } else {
            log.debug("No book in db");
        }
    }

    @ShellMethod("create new book")
    public void newBook(String title, int edition, String isbn) {
        newBook = new Book();
        newBook.setTitle(title);
        newBook.setEdition(edition);
        newBook.setIsbn(isbn);

        try {
            var data = formatter.format(newBook);
            log.info(data);
        } catch (JsonProcessingException e) {
            log.error("Ошибка форматирования", e);
        }
    }

    @ShellMethod("save new book")
    public void saveBook() {
        bookDao.add(newBook);
    }

    @ShellMethod("set author for new book")
    public void setAuthorToNewBook(String authorName) {
        if (newBook != null) {
            var author = authorDao.findByName(authorName);

            if (author != null) {
                newBook.getAuthors().add(author);
                log.info(String.format("author by name=%s added to book", authorName));
            } else {
                log.error(String.format("author by name=%s not found", authorName));
            }
        } else {
            log.error("You mast init new book first");
        }
    }

    @ShellMethod("set author for new book")
    public void setGenreToNewBook(String genreName) {
        if (newBook != null) {
            var genre = genreDao.findByName(genreName);

            if (genre != null) {
                newBook.setGenre(genre);
                log.info(String.format("genre by name=%s added to book", genre.getName()));
            } else {
                log.error(String.format("author by name=%s not found", genreName));
            }
        } else {
            log.error("You mast init new book first");
        }
    }
}
