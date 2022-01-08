package com.example.androidcourseproject.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "living",
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
                ),
                @ForeignKey(
                        entity = AdditionalServicesRoom.class,
                        parentColumns = "as_id",
                        childColumns = "as_id"
                )
        })

public class LivingRoom {
    @PrimaryKey(autoGenerate = true)
    public int living_id;
    @ColumnInfo(name = "client_id")
    public int client_id;
    @ColumnInfo(name = "settling")
    public String settling;
    @ColumnInfo(name = "eviction")
    public String eviction;
    @ColumnInfo(name = "value_of_guests")
    public int value_of_guests;
    @ColumnInfo(name = "value_of_kids")
    public int value_of_kids;
    @ColumnInfo(name = "apartment_id")
    public int apartment_id;
    @ColumnInfo(name = "as_id")
    public int as_id;

    public LivingRoom(int client_id, String settling, String eviction, int value_of_guests, int value_of_kids, int apartment_id, int as_id) {
        this.client_id = client_id;
        this.settling = settling;
        this.eviction = eviction;
        this.value_of_guests = value_of_guests;
        this.value_of_kids = value_of_kids;
        this.apartment_id = apartment_id;
        this.as_id = as_id;
    }
}
