package com.av.domain;

public class Author {

    private long id;
    private String name;

    public Author(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public Author(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Author{id=%d, name='%s'}", id, name);
    }
}
