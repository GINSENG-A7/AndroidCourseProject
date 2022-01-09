package com.example.androidcourseproject.room;


import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Query("SELECT * FROM client c WHERE c.client_id = :clientId")
    List<ClientRoom> getClientById(int clientId);

    @Query("SELECT * FROM apartment a WHERE a.apartment_id = :apartmentId")
    List<ApartmentRoom> getApartmentById(int apartmentId);

    @Query("SELECT * FROM client")
    List<ClientRoom> getAllClients();

    @Query("SELECT * FROM living")
    List<LivingRoom> getAllLivings();

    @Query("SELECT * FROM booking")
    List<BookingRoom> getAllBookings();

    @Query("SELECT * FROM apartment")
    List<ApartmentRoom> getAllApartments();

    @Query("SELECT * FROM client WHERE surname LIKE :surname ")
    ClientRoom getClientsBySurname(String surname);

    @Insert()
    void insertClient(ClientRoom client);

    @Insert()
    void insertLiving(LivingRoom living);

    @Insert()
    void insertApartment(ApartmentRoom apartment);

    @Insert()
    void insertAdditionalService(AdditionalServicesRoom service);
}
