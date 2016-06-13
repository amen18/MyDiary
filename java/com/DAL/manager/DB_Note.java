package com.DAL.manager;

import android.content.Context;

import com.DAO.executors.Image_Queries;
import com.DAO.executors.Media_Queries;
import com.DAO.executors.Note_Queries;
import com.DAO.executors.functionality.Image_Functionality;
import com.DAO.executors.functionality.Media_Functionality;
import com.DAO.executors.functionality.Note_Functionality;
import com.DAO.model.Note;
import com.DAO.model.NoteImage;
import com.DAO.model.NoteListViewModel;
import com.DAO.model.NoteMedia;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


/**
 * Created by AMEN on 26.04.2016.
 */
public class DB_Note  implements Note_Functionality,Image_Functionality,Media_Functionality {
    private static Note_Queries n_q;
    private static Image_Queries im_q;
    private static Media_Queries md_q;

    private static DB_Note db_n;

    private  DB_Note(Context context){
        n_q = new Note_Queries(context);
        im_q = new Image_Queries(context);
        md_q = new Media_Queries(context);
    };

    public static DB_Note getInstance(Context context){
        if(db_n == null){
            db_n = new DB_Note(context);
        }
        return db_n;
    }


    @Override
    public Note addNote(Note note) {
        try {
            n_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Note n = n_q.addNote(note);
        n_q.close();
        return n;
    }

    @Override
    public void deleteNote(int noteId) {
        try {
            n_q.open();
            im_q.open();
            md_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        n_q.deleteNote(noteId);
        im_q.deleteImageByNoteId(noteId);
        md_q.deleteMediaByNoteId(noteId);
        im_q.close();
        md_q.close();
        n_q.close();
    }

    @Override
    public Note getNoteById(int noteId) {
        try {
            n_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Note n = n_q.getNoteById(noteId);
        n_q.close();
        return n;
    }

    @Override
    public List<NoteListViewModel> getAllNotes() {
        try {
            n_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<NoteListViewModel> noteList = n_q.getAllNotes();
        n_q.close();
        Collections.reverse(noteList);
        return noteList;
    }

    @Override
    public List<NoteImage> getAllImagesByNoteId(int noteId) {
        try {
            n_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<NoteImage> imageList = n_q.getAllImagesByNoteId(noteId);
        n_q.close();
        return imageList;
    }

    @Override
    public List<NoteMedia> getAllMediaByNoteId(int noteid) {
        try {
            n_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<NoteMedia> mediaList = n_q.getAllMediaByNoteId(noteid);
        n_q.close();
        return mediaList;
    }

    @Override
    public NoteImage addImage(NoteImage image) {
        try {
            im_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        NoteImage noteImage = im_q.addImage(image);
        im_q.close();
        return noteImage;
    }

    @Override
    public void deleteImage(int imageId) {
        try {
            im_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        im_q.deleteImage(imageId);
        im_q.close();
    }

    @Override
    public NoteImage getImageById(int imageId) {
        try {
            im_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        NoteImage nm = im_q.getImageById(imageId);
        im_q.close();
        return nm;
    }

    @Override
    public void deleteImageByNoteId(int noteId) {

    }

    @Override
    public void deleteAllImages() {

    }

    @Override
    public NoteMedia addMedia(NoteMedia media) {
        try {
            md_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        NoteMedia noteMedia = md_q.addMedia(media);
        md_q.close();
        return noteMedia;
    }

    @Override
    public void deleteMedia(int mediaId) {
        try {
            md_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        md_q.deleteMedia(mediaId);
        md_q.close();
    }

    @Override
    public NoteMedia getMediaById(int mediaId) {
        try {
            md_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        NoteMedia nm = md_q.getMediaById(mediaId);
        return nm;
    }

    @Override
    public void deleteMediaByNoteId(int noteId) {

    }

    @Override
    public void deleteAllMedia() {

    }

    @Override
    public String getMemoryTextByNoteId(int nodeId) {
        try {
            n_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String s = n_q.getMemoryTextByNoteId(nodeId);
        n_q.close();
        return s;
    }

    @Override
    public List<NoteListViewModel> getNotes(int locked) {
        try {
            n_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<NoteListViewModel> noteList = n_q.getNotes(locked);
        n_q.close();
        Collections.reverse(noteList);
        return noteList;
    }

    @Override
    public List<NoteListViewModel> getFilteredNotes(long date1, long date2)
    {
        try {
            n_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<NoteListViewModel> noteList = n_q.getFilteredNotes(date1, date2);
        n_q.close();
        return noteList;
    }

    @Override
    public List<NoteListViewModel> getDateFilteredNotes(long date1, long date2, int locked) {
        try {
            n_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<NoteListViewModel> noteList = n_q.getDateFilteredNotes(date1, date2, locked);
        n_q.close();
        Collections.reverse(noteList);
        return noteList;
    }

    @Override
    public void deleteAllNotes() {
        try{
            n_q.open();
            md_q.open();
            im_q.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        n_q.deleteAllNotes();
        md_q.deleteAllMedia();
        im_q.deleteAllImages();
        n_q.close();
        md_q.close();
        im_q.close();
    }
}
