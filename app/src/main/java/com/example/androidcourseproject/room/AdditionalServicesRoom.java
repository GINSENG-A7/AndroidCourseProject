package com.example.androidcourseproject.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "additional_services")
public class AdditionalServicesRoom {
    @PrimaryKey(autoGenerate = true)
    public int as_id;
    @ColumnInfo(name = "mini_bar")
    public int mini_bar;
    @ColumnInfo(name = "clothes_washing")
    public int clothes_washing;
    @ColumnInfo(name = "telephone")
    public int telephone;
    @ColumnInfo(name = "intercity_telephone")
    public int intercity_telephone;
    @ColumnInfo(name = "food")
    public int food;

    public AdditionalServicesRoom(int mini_bar, int clothes_washing, int telephone, int intercity_telephone, int food) {
        this.mini_bar = mini_bar;
        this.clothes_washing = clothes_washing;
        this.telephone = telephone;
        this.intercity_telephone = intercity_telephone;
        this.food = food;
    }

}
