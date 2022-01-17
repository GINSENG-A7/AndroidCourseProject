package com.example.androidcourseproject.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "photo",
        foreignKeys = {
                @ForeignKey(
                        entity = ApartmentRoom.class,
                        parentColumns = "apartment_id",
                        childColumns = "apartment_id"
                )
})
public class PhotoRoom {
    @PrimaryKey(autoGenerate = true)
    public int photo_id;
    @ColumnInfo(name = "path")
    public String path;
    @ColumnInfo(name = "apartment_id")
    public int apartment_id;
}
