package com.av.cli;

import com.av.dao.AuthorDao;
import com.av.domain.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.MessageFormat;

@Slf4j
@ShellComponent
public class AuthorDefinerCommand {

    private final AuthorDao authorDao;

    public AuthorDefinerCommand(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @ShellMethod("set locale fo session")
    public void addAuthor(String name) {
        log.debug(String.format("addAuthor with name:%s", name));
        var author = authorDao.add(new Author(name));
        log.debug("new AuthorId=" + author.getId());
    }

    @ShellMethod("show authors")
    public void showAuthors() {
        log.info("ho ho");
        var authorList = authorDao.getAll();

        if (authorList.size() != 0) {
            authorDao.getAll().forEach(a -> log.info(MessageFormat.format("author:{0}", a.toString())));
        } else {
            log.debug("No author in db");
        }
    }
}
