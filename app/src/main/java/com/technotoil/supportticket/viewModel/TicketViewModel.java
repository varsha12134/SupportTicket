package com.technotoil.supportticket.viewModel;

public class TicketViewModel {
   /* TicketRepository repository;
//    private LiveData<List<TicketModel>> listTicket;
    private LiveData<TicketModel> listTicketLiveData;

    public TicketViewModel(@NonNull Application application) {
        super(application);
        repository = new TicketRepository(application);
    }
    public void insertTicketDataInRoom(TicketModel ticketModel){
        repository.insert(ticketModel);
    }
    public LiveData<List<TicketModel>> fetchActiveDataFromRoom() {
        return repository.getActiveData();
    }
    public LiveData<TicketModel> fetchDataFromRoom(int id) {
        return repository.getData(id);
    }
    public void updateStatus(int id , int status){
        repository.updateTicketStatus(id, status);
    }

    public LiveData<TicketModel> fetchClosedDataById(int id) {
        listTicketLiveData = repository.getTicketData(id);
        return listTicketLiveData;
    }
    public LiveData<List<TicketModel>> fetchClosedList() {
        return  repository.getClosedData();
    }
    public LiveData<List<TicketModel>> fetchAwaitingList() {
        return repository.getAwaitingData();
    }
    public LiveData<List<TicketModel>> fetchOpenList() {
        return repository.getOpenData();
    }*/
}
