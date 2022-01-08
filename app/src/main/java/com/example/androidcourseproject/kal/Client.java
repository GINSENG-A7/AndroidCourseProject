package com.example.androidcourseproject.kal;

public class Client {
    private final int client_id;
    private final int passport_series;
    private final int passport_number;
    private final String name;
    private final String surname;
    private final String patronymic;
    private final String birthday;
    private final String telephone;

    public Client(int client_id, int passport_series, int passport_number, String name, String surname, String patronymic, String birthday, String telephone) {
        this.client_id = client_id;
        this.passport_series = passport_series;
        this.passport_number = passport_number;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.telephone = telephone;
    }

    public int getClient_id() {
        return client_id;
    }

    public int getPassport_series() {
        return passport_series;
    }

    public int getPassport_number() {
        return passport_number;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getTelephone() {
        return telephone;
    }
}
