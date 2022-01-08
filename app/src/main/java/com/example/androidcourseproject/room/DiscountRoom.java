package com.example.androidcourseproject.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "discount")
public class DiscountRoom {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "discount")
    public int discount;
}
