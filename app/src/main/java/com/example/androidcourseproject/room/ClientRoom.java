package com.example.androidcourseproject.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "client")
public class ClientRoom {
    @PrimaryKey(autoGenerate = true)
    public int client_id;
    @ColumnInfo(name = "passport_series")
    public int passport_series;
    @ColumnInfo(name = "passport_number")
    public int passport_number;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "surname")
    public String surname;
    @ColumnInfo(name = "patronymic")
    public String patronymic;
    @ColumnInfo(name = "birthday")
    public String birthday;
    @ColumnInfo(name = "telephone")
    public String telephone;

    public ClientRoom(int passport_series, int passport_number, String name, String surname, String patronymic, String birthday, String telephone) {
        this.passport_series = passport_series;
        this.passport_number = passport_number;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.telephone = telephone;
    }
}
