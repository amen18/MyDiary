package com.DAO.executors.functionality;

import com.DAO.model.Note;
import com.DAO.model.NoteImage;
import com.DAO.model.NoteListViewModel;
import com.DAO.model.NoteMedia;

import java.util.List;
/**
 * Created by AMEN on 26.04.2016.
 */
public interface Note_Functionality {
    public Note addNote(Note note);
    public void  deleteNote (int noteId);
    public Note getNoteById(int noteId);
    public List<NoteListViewModel> getAllNotes();
    public List<NoteImage> getAllImagesByNoteId(int noteId);
    public List<NoteMedia> getAllMediaByNoteId(int noteid);
    public String getMemoryTextByNoteId(int noteId);
    public List<NoteListViewModel> getNotes(int locked);
    public List<NoteListViewModel> getFilteredNotes(long date1,long date2);
    public List<NoteListViewModel> getDateFilteredNotes(long date1,long date2,int locked);
    public void deleteAllNotes();
}
