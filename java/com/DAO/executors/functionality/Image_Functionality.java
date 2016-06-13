package com.DAO.executors.functionality;


import com.DAO.model.NoteImage;

/**
 * Created by AMEN on 26.04.2016.
 */
public interface Image_Functionality {
    public NoteImage addImage(NoteImage image);
    public void  deleteImage (int imageId);
    public NoteImage getImageById(int imageId);
    public void deleteImageByNoteId(int noteId);
    public void deleteAllImages();
}
