package com.av.dao;

import com.av.domain.Book;

public interface BookDao<L extends Number, B> extends GenericDomainCrud<Long, Book> {}
