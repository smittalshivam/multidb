package com.example.multidb2;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Post {
    @Id
    private long id;
    private String name;

    public Post(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Post() {
    }

    public Post(String name) {
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
}