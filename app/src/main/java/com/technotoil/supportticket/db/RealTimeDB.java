package com.technotoil.supportticket.db;

import com.google.firebase.database.FirebaseDatabase;

public class RealTimeDB {
    private static FirebaseDatabase firebaseDatabase;

    private RealTimeDB() {
        // Private constructor to prevent instantiation
    }

    public static synchronized FirebaseDatabase getInstance() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
        return firebaseDatabase;
    }
}
