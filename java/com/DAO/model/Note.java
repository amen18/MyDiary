package com.DAO.model;

import java.util.List;

/**
 * Created by AMEN on 26.04.2016.
 */
public class Note {
    private int id;
    private String title;
    private String memeryText;
    private boolean isLocked;
    private long date;
    private List<Integer> images;
    private List<Integer> medias;
    private int mood;


    public Note()
    {}

    public int getMood() {
        return mood;
    }

    public Note setMood(int mood) {
        this.mood = mood;
        return this;
    }

    public List<Integer> getImages() {
        return images;
    }

    public Note setImages(List<Integer> images) {
        this.images = images;
        return this;
    }

    public List<Integer> getMedias() {
        return medias;
    }

    public Note setMedias(List<Integer> medias) {
        this.medias = medias;
        return this;
    }

    public long getDate() {
        return date;
    }

    public Note setDate(long date) {
        this.date = date;
        return this;
    }

    public int getId() {
        return id;
    }

    public Note setId(int id) {
        this.id = id;
        return this;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public Note setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
        return this;
    }

    public String getMemeryText() {
        return memeryText;
    }

    public Note setMemeryText(String memeryText) {
        this.memeryText = memeryText;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }
}
