package com.DAL.manager;

import android.content.Context;

import com.DAO.model.Note;
import com.DAO.model.NoteImage;
import com.DAO.model.NoteListViewModel;
import com.DAO.model.NoteMedia;

import java.util.List;

/**
 * Created by Ed on 30.04.2016.
 */
public class NoteManager {
    private DB_Note db;

    public NoteManager(Context context)
    {
        db = DB_Note.getInstance(context);
    }

    public Note addNote(Note n)
    {
        return db.addNote(n);
    }

    public void deleteNote(int noteId)
    {
        db.deleteNote(noteId);
    }

    public Note getNoteById(int noteId)
    {
        return db.getNoteById(noteId);
    }

    public List<NoteListViewModel> getAllNotes() {
        return db.getAllNotes();
    }

    public List<NoteImage> getAllImagesByNoteId(int noteId) {
        return db.getAllImagesByNoteId(noteId);
    }

    public List<NoteMedia> getAllMediaByNoteId(int noteId) {
        return db.getAllMediaByNoteId(noteId);
    }

    public NoteImage addImage(NoteImage image) {
        return db.addImage(image);
    }

    public void deleteImage(int imageId) {
        db.deleteImage(imageId);
    }

    public NoteImage getImageById(int imageId) {
        return db.getImageById(imageId);
    }

    public NoteMedia addMedia(NoteMedia media) {
        return db.addMedia(media);
    }

    public void deleteMedia(int mediaId) {
        db.deleteMedia(mediaId);
    }

    public NoteMedia getMediaById(int mediaId) {
        return db.getMediaById(mediaId);
    }

    public String getMemoryTextByNoteId(int noteId) {
        return db.getMemoryTextByNoteId(noteId);
    }
}
