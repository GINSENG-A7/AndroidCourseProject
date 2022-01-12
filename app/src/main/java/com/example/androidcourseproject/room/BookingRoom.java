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
    public long settling;
    @ColumnInfo(name = "eviction")
    public long eviction;
    @ColumnInfo(name = "booking_apartment_number")
    public int booking_apartment_number;
    @ColumnInfo(name = "value_of_guests")
    public int value_of_guests;
    @ColumnInfo(name = "value_of_kids")
    public int value_of_kids;
    @ColumnInfo(name = "apartment_id")
    public int apartment_id;

    public BookingRoom(int client_id, long settling, long eviction, int value_of_guests, int value_of_kids, int apartment_id) {
        this.client_id = client_id;
        this.settling = settling;
        this.eviction = eviction;
        this.value_of_guests = value_of_guests;
        this.value_of_kids = value_of_kids;
        this.apartment_id = apartment_id;
    }
}
