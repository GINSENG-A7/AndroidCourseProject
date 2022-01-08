package com.example.androidcourseproject.kal;

public class Apartments {
    private final int apartment_id;
    private final int number;
    private final String type;
    private final int price;

    public Apartments(int apartment_id, int number, String type, int price) {
        this.apartment_id = apartment_id;
        this.number = number;
        this.type = type;
        this.price = price;
    }
    public int getApartment_id() {
        return apartment_id;
    }

    public int getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

}
