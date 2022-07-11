package com.av.dao;

import com.av.domain.Genre;

public interface GenreDao extends GenericDomainCrud<Long, Genre> {
    Genre findByName(String genreName);
}
