package com.technotoil.supportticket.dao;

import androidx.room.Dao;

@Dao
public interface TicketDao {

   /* @Insert
    void insertTicketData(TicketModel ticketModel);
*/
   /* @Query("select * from Ticket where Id = :id ")
    LiveData<TicketModel> ActiveData(int id);
    @Query("select * from Ticket where status IN(1,2) ")
    LiveData<List<TicketModel>> fetchTicketActiveData();

    @Query("UPDATE ticket SET status = :status where id = :id")
    void updateStatusForTicket(int id , int status);

    @Query("select * from Ticket where id = :id")
    LiveData<TicketModel> fetchTicketDataById(int id);

    @Query("select * from Ticket where status = 0")
    LiveData<List<TicketModel>> fetchTicketClosedData();
*/
  /*  @Query("select * from Ticket where status = 2")
    LiveData<List<TicketModel>> fetchTicketAwaitingData();
    @Query("select * from Ticket where status = 1")
    LiveData<List<TicketModel>> fetchTicketOpenData();*/
}
