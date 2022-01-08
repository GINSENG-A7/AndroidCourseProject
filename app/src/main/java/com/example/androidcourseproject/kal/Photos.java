package com.example.androidcourseproject.kal;

public class Photos {
    private final int photo_id;
    private final String path;
    private final int apartment_id;

    public Photos(int photo_id, String path, int apartment_id) {
        this.photo_id = photo_id;
        this.path = path;
        this.apartment_id = apartment_id;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public String getPath() {
        return path;
    }

    public int getApartment_id() {
        return apartment_id;
    }
}
