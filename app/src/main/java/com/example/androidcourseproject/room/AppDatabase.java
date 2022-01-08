package com.example.androidcourseproject.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AdditionalServicesRoom.class, ApartmentRoom.class, BookingRoom.class, ClientRoom.class, DiscountRoom.class, LivingRoom.class, PhotoRoom.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract Dao dao();
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDataBase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "database-name")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}

