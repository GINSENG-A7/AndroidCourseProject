package com.example.androidcourseproject.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "booking",
        foreignKeys = {
                @ForeignKey(
                        entity = ClientRoom.class,
                        parentColumns = "client_id",
                        childColumns = "client_id"
                ),
                @ForeignKey(
                        entity = ApartmentRoom.class,
                        parentColumns = "apartment_id",
                        childColumns = "apartment_id"
                )
        })

public class BookingRoom {
    @PrimaryKey(autoGenerate = true)
    public int booking_id;
    @ColumnInfo(name = "client_id")
    public int client_id;
    @ColumnInfo(name = "settling")
    public String settling;
    @ColumnInfo(name = "eviction")
    public String eviction;
    @ColumnInfo(name = "booking_apartment_number")
    public int booking_apartment_number;
    @ColumnInfo(name = "value_of_guests")
    public int value_of_guests;
    @ColumnInfo(name = "value_of_kids")
    public int value_of_kids;
    @ColumnInfo(name = "apartment_id")
    public int apartment_id;
}
