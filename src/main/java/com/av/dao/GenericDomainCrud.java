package com.av.dao;

import java.util.List;

public interface GenericDomainCrud<K, T> {
    T add(T domain);

    void update(T domain);

    void delete(T domain);

    List<T> getAll();

    void deleteById(K id);
}
