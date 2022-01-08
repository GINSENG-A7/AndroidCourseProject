package com.example.androidcourseproject.kal;

public class Booking {

    private final int booking_id;
    private final int client_id;
    private final String booking_client_name; //
    private final String booking_client_surname; //
    private final String booking_client_patronymic; //
    private final String settling;
    private final String eviction;
    private final int booking_apartment_number; //
    private final int value_of_guests;
    private final int value_of_kids;
    private final int apartment_id;

    public Booking(int booking_id,
                   int client_id,
                   String booking_client_name,
                   String booking_client_surname,
                   String booking_client_patronymic,
                   String settling,
                   String eviction,
                   int booking_apartment_number,
                   int value_of_guests,
                   int value_of_kids,
                   int apartment_id) {
        this.booking_id = booking_id;
        this.client_id = client_id;
        this.booking_client_name = booking_client_name;
        this.booking_client_surname = booking_client_surname;
        this.booking_client_patronymic = booking_client_patronymic;
        this.settling = settling;
        this.eviction = eviction;
        this.booking_apartment_number = booking_apartment_number;
        this.value_of_guests = value_of_guests;
        this.value_of_kids = value_of_kids;
        this.apartment_id = apartment_id;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public String getBooking_client_name() {
        return booking_client_name;
    }

    public String getBooking_client_surname() {
        return booking_client_surname;
    }

    public String getBooking_client_patronymic() {
        return booking_client_patronymic;
    }

    public String getSettling() {
        return settling;
    }

    public String getEviction() {
        return eviction;
    }

    public int getBooking_apartment_number() {
        return booking_apartment_number;
    }

    public int getValue_of_guests() {
        return value_of_guests;
    }

    public int getValue_of_kids() {
        return value_of_kids;
    }

    public int getApartment_id() {
        return apartment_id;
    }
}
