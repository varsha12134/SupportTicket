package com.impulse.supportticket.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.impulse.supportticket.dao.AttachmentDao;
import com.impulse.supportticket.dao.MessageDao;
import com.impulse.supportticket.dao.TicketDao;
import com.impulse.supportticket.dao.UserDao;

public abstract class RoomDB extends RoomDatabase {

    public static final String Db_Name ="DataBase";
    public static RoomDatabase instance;

    public static synchronized RoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, Db_Name)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }
    public abstract UserDao userDao();
    public abstract AttachmentDao attachmentDao();
    public abstract TicketDao ticketDao();
    public abstract MessageDao messageDao();
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };

}
