package com.impulse.supportticket.repositorys;

public class MessageRepository {
    /*Context context;
    RoomDB roomDB;
    private LiveData<List<MessageModel>> liveData;

    public MessageRepository(Context context) {
        this.context = context;
        roomDB = (RoomDB) RoomDB.getInstance(context);
    }
    public void insertMessageDataInRoom(MessageModel messageModel){
//        roomDB.messageDao().insertMessagesData(messageModel);
        new InsertMessageAsyncTask(roomDB.messageDao()).execute(messageModel);
    }
    public LiveData<List<MessageModel>> getData(int ticketID) {
        liveData = roomDB.messageDao().fetchMessageData(ticketID);
        return liveData;
    }

    public static class InsertMessageAsyncTask extends AsyncTask<MessageModel, Void, Void> {
        private MessageDao messageDao;

        private InsertMessageAsyncTask(MessageDao messageDao) {
            this.messageDao = messageDao;
        }
        @Override
        protected Void doInBackground(MessageModel... messageModels) {
            messageDao.insertMessagesData(messageModels[0]);
            return null;
        }*/


}
