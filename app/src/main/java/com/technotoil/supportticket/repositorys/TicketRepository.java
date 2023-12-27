package com.technotoil.supportticket.repositorys;

import com.technotoil.supportticket.model.TicketModel;

import java.util.List;

public class TicketRepository {
   /* Context context;
    RoomDB roomDB;
    private LiveData<List<TicketModel>> listTicket;
    private LiveData<TicketModel> ticketLiveData;

    public TicketRepository(Context context) {
        this.context = context;
        roomDB = (RoomDB) RoomDB.getInstance(context);
        listTicket = new MutableLiveData<>();
        ticketLiveData = new MutableLiveData<>();

    }

    public void insert(TicketModel ticketModel) {
//        roomDB.ticketDao().insertTicketData(ticketModel);
        new InsertTicketAsyncTask(roomDB.ticketDao()).execute(ticketModel);
    }

    public LiveData<List<TicketModel>> getActiveData( ) {
        listTicket = roomDB.ticketDao().fetchTicketActiveData();
//         new FetchTicketAsyncTask(roomDB.ticketDao()).execute();
        return listTicket;
    }
    public LiveData<TicketModel> getData(int id ) {
        ticketLiveData = roomDB.ticketDao().ActiveData(id);
//         new FetchTicketAsyncTask(roomDB.ticketDao()).execute();
        return ticketLiveData;
    }
    public void updateTicketStatus(int id, int status) {
        roomDB.ticketDao().updateStatusForTicket(id, status);
    }

    public LiveData<TicketModel> getTicketData(int id) {
        ticketLiveData = roomDB.ticketDao().fetchTicketDataById(id);
        return ticketLiveData;
    }

    public LiveData<List<TicketModel>> getClosedData() {
        listTicket = roomDB.ticketDao().fetchTicketClosedData();
        return listTicket;
    }

    public LiveData<List<TicketModel>> getAwaitingData() {
        listTicket = roomDB.ticketDao().fetchTicketAwaitingData();
        return listTicket;
    }
    public LiveData<List<TicketModel>> getOpenData() {
        listTicket = roomDB.ticketDao().fetchTicketOpenData();
        return listTicket;
    }



    public static class InsertTicketAsyncTask extends AsyncTask<TicketModel, Void, Void> {
        private TicketDao ticketDao;

        private InsertTicketAsyncTask(TicketDao ticketDao) {
            this.ticketDao = ticketDao;
        }

        @Override
        protected Void doInBackground(TicketModel... ticketModels) {
            ticketDao.insertTicketData(ticketModels[0]);
            return null;
        }
    }*/
    /*public static class    UpdateTicketAsyncTask extends AsyncTask<Objects, Void,Void> {
        private TicketDao ticketDao;
        private UpdateTicketAsyncTask(TicketDao ticketDao){
            this.ticketDao = ticketDao;
        }
        @Override
        protected Void doInBackground(Objects... params) {
//            int id = (int) params[0];
//            ticketDao.updateStatusForTicket(id);
            return null;
        }

    }*/

//    public static class FetchTicketAsyncTask extends AsyncTask<Void, Void, List<TicketModel>> {
//        private TicketDao ticketDao;
//        private MutableLiveData<LiveData<List<TicketModel>>> liveTicketData;
//
//        public FetchTicketAsyncTask(TicketDao ticketDao) {
//            this.ticketDao = ticketDao;
////            this.liveTicketData = liveTicketData;
//        }
//
//        @Override
//        protected List<TicketModel> doInBackground(Void... Void) {
//            return  ticketDao.fetchTicketActiveData();
//        }
//
//       /* @Override
//        protected void onPostExecute(LiveData<List<TicketModel>> listLiveData) {
//            super.onPostExecute(listLiveData);
//            if (OnDataFetchedListener != null) {
//                OnDataFetchedListener.onDataFetched(listLiveData);
//            }
//        }*/
//    }
    public interface OnDataFetchedListener{
        void onDataFetched(List<TicketModel> ticketModels);

    }
}


