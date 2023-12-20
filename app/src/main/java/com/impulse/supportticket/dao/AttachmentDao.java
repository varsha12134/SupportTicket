package com.impulse.supportticket.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.impulse.supportticket.model.AttachmentModel;

@Dao
public interface AttachmentDao {
    @Insert
    void insertAttachment(AttachmentModel attachmentModel);

    @Query("select * from attachment where ticketId == :ticketId")
    LiveData<AttachmentModel> fetchAttachmentList(String ticketId);
}
