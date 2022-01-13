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

    @Query("SELECT * FROM living WHERE client_id = :clientId")
    List<LivingRoom> getAllLivingsByClientId(int clientId);

    @Query("SELECT * FROM booking WHERE client_id LIKE :clientId ")
    List<BookingRoom> getAllBookingsByClientId(int clientId);

    @Insert()
    long insertClient(ClientRoom client);

    @Insert()
    void insertLiving(LivingRoom living);

    @Insert()
    long insertAdditionalService(AdditionalServicesRoom service);

    @Insert()
    void insertApartment(ApartmentRoom apartment);
}
