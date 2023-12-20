package com.impulse.supportticket.repositorys;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.impulse.supportticket.dao.AttachmentDao;
import com.impulse.supportticket.db.RoomDB;
import com.impulse.supportticket.model.AttachmentModel;

public class AttachmentRepository {
    Context context;
    RoomDB roomDB;
    private LiveData<AttachmentModel> listTicket;

    public AttachmentRepository(Context context) {
        this.context = context;
         roomDB = (RoomDB) RoomDB.getInstance(context);
    }
    public void insertAttachmentDataInRoom(AttachmentModel attachmentModel){
//        roomDB.attachmentDao().insertAttachment(attachmentModel);
        new InsertAttachmentAsyncTask(roomDB.attachmentDao()).doInBackground(attachmentModel);
    }

    public LiveData<AttachmentModel> getActiveData( String ticketID) {
        listTicket = roomDB.attachmentDao().fetchAttachmentList(ticketID);
        return listTicket;
    }

    public static class InsertAttachmentAsyncTask extends AsyncTask<AttachmentModel, Void, Void> {
        private AttachmentDao attachmentDao;

        private InsertAttachmentAsyncTask(AttachmentDao attachmentDao) {
            this.attachmentDao = attachmentDao;
        }
        @Override
        protected Void doInBackground(AttachmentModel... attachmentModels) {
            attachmentDao.insertAttachment(attachmentModels[0]);
            return null;
        }
    }
}
