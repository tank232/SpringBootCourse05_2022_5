package com.av.dao;

import com.av.domain.Author;

public interface AuthorDao extends GenericDomainCrud<Long, Author> {
    Author findByName(String authorName);
}
