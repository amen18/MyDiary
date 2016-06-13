package com.DAO.model;

/**
 * Created by AMEN on 26.04.2016.
 */
public class NoteImage {
    private int id;
    private String imagePath;
    private int noteID;



    public int getId() {
        return id;
    }

    public NoteImage setId(int id) {
        this.id = id;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public NoteImage setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public int getNoteID() {
        return noteID;
    }

    public NoteImage setNoteID(int noteID) {
        this.noteID = noteID;
        return this;
    }
}
