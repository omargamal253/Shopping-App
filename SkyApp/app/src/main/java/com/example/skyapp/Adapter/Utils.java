package com.example.skyapp.Adapter;

import com.google.firebase.database.FirebaseDatabase;

public class Utils {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }


    //Utils.getDatabase()
}