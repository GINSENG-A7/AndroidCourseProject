package com.example.androidcourseproject.kal;

public class Living {
    private final int client_id;
    private final String living_client_name; //
    private final String living_client_surname; //
    private final String living_client_patronymic; //
    private final String settling;
    private final String eviction;
    private final int living_apartment_number; //
    private final int value_of_guests;
    private final int value_of_kids;
    private final int apartment_id;
    private final int as_id;
    private final int living_id;

    public Living(int living_id, int client_id, String living_client_name, String living_client_surname, String living_client_patronymic, String settling, String eviction, int living_apartment_number, int value_of_guests, int value_of_kids, int apartment_id, int as_id) {
        this.living_id = living_id;
        this.client_id = client_id;
        this.living_client_name = living_client_name;
        this.living_client_surname = living_client_surname;
        this.living_client_patronymic = living_client_patronymic;
        this.settling = settling;
        this.eviction = eviction;
        this.living_apartment_number = living_apartment_number;
        this.value_of_guests = value_of_guests;
        this.value_of_kids = value_of_kids;
        this.apartment_id = apartment_id;
        this.as_id = as_id;
    }

    public int getLiving_id() {
        return living_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public String getLiving_client_name() {
        return living_client_name;
    }

    public String getLiving_client_surname() {
        return living_client_surname;
    }

    public String getLiving_client_patronymic() {
        return living_client_patronymic;
    }

    public String getSettling() {
        return settling;
    }

    public String getEviction() {
        return eviction;
    }

    public int getLiving_apartment_number() {
        return living_apartment_number;
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

    public int getAs_id() {
        return as_id;
    }
}
