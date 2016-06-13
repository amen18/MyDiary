package com.DAO.model;

import java.io.Serializable;

/**
 * Created by Lilit on 26.04.2016.
 */
public class NoteListViewModel implements Serializable{
    private int id;
    private String title;
    private long date;
    private boolean isLocked;
    private int mood;

    public int getMood() {
        return mood;
    }

    public NoteListViewModel setMood(int mood) {
        this.mood = mood;
        return this;
    }

    public long getDate() {
        return date;
    }

    public NoteListViewModel setDate(long date) {
        this.date = date;
        return this;
    }

    public int getId() {
        return id;
    }

    public NoteListViewModel setId(int id) {
        this.id = id;
        return this;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public NoteListViewModel setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoteListViewModel setTitle(String title) {
        this.title = title;
        return this;
    }
}
