package com.DAO.model;

/**
 * Created by AMEN on 26.04.2016.
 */
public class NoteMedia {
    private int id;
    private byte[] mediaContent;
    private int noteID;

    public int getNoteID() {
        return noteID;
    }

    public NoteMedia setNoteID(int noteID) {
        this.noteID = noteID;
        return this;
    }

    public byte[] getMediaContent() {
        return mediaContent;
    }

    public NoteMedia setMediaContent(byte[] mediaContent) {
        this.mediaContent = mediaContent;
        return this;
    }

    public int getId() {
        return id;
    }

    public NoteMedia setId(int id) {
        this.id = id;
        return this;
    }
}
