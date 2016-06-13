package com.DAO.executors.functionality;


import com.DAO.model.NoteMedia;

/**
 * Created by AMEN on 26.04.2016.
 */
public interface Media_Functionality {
    public NoteMedia addMedia(NoteMedia media);
    public void  deleteMedia (int mediaId);
    public NoteMedia getMediaById(int mediaId);
    public void deleteMediaByNoteId(int noteId);
    public void deleteAllMedia();
}
