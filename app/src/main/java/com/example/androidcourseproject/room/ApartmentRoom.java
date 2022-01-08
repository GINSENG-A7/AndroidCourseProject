package com.example.androidcourseproject.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "apartment")
public class ApartmentRoom {
    @PrimaryKey(autoGenerate = true)
    public int apartment_id;
    @ColumnInfo(name = "number")
    public int number;
    @ColumnInfo(name = "type")
    public String type;
    @ColumnInfo(name = "price")
    public int price;

    public ApartmentRoom( int number, String type, int price) {
        this.number = number;
        this.type = type;
        this.price = price;
    }
}
