package com.example.androidcourseproject.room;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Query("SELECT * FROM client c WHERE c.client_id = :clientId")
    ClientRoom getClientById(int clientId);

    @Query("SELECT * FROM apartment a WHERE a.apartment_id = :apartmentId")
    ApartmentRoom getApartmentById(int apartmentId);

    @Query("SELECT * FROM client")
    List<ClientRoom> getAllClients();

    @Query("SELECT * FROM living")
    List<LivingRoom> getAllLivings();

    @Query("SELECT * FROM booking")
    List<BookingRoom> getAllBookings();

    @Query("SELECT * FROM apartment")
    List<ApartmentRoom> getAllApartments();

    @Query("SELECT * FROM client WHERE passport_series = :passport_series & passport_number = :passport_number")
    ClientRoom getClientsByPassportData(int passport_series, int passport_number);

    @Query("SELECT * FROM living WHERE client_id = :clientId")
    List<LivingRoom> getAllLivingsByClientId(int clientId);

    @Query("SELECT * FROM booking WHERE client_id LIKE :clientId ")
    List<BookingRoom> getAllBookingsByClientId(int clientId);

    @Query("SELECT * FROM additional_services WHERE as_id = :livingId")
    AdditionalServicesRoom getAdditionalServiceByLivingId(int livingId);

    @Query("SELECT * FROM Apartment a WHERE a.price > :bp AND a.price < :tp AND ((a.apartment_id IN (SELECT apartment_id FROM Living WHERE eviction < :b1) AND NOT EXISTS(SELECT number FROM Booking WHERE a.apartment_id IN (SELECT apartment_id FROM Booking))) OR (a.apartment_id in (SELECT apartment_id FROM Booking WHERE settling > :b2 ) OR (a.apartment_id in (SELECT apartment_id FROM Booking WHERE eviction < :b1))) AND NOT EXISTS(SELECT apartment_id FROM Living WHERE a.apartment_id IN (SELECT apartment_id FROM Living)) OR ((a.apartment_id in (SELECT apartment_id FROM Living WHERE eviction < :b1)) AND (a.apartment_id in (SELECT apartment_id FROM Booking WHERE settling > :b2) OR (a.apartment_id in (SELECT apartment_id FROM Booking WHERE eviction < :b1)))) OR (a.apartment_id NOT IN (SELECT apartment_id FROM Living) AND a.apartment_id NOT IN (SELECT apartment_id FROM Booking)))")
    List<ApartmentRoom> getFilteredApartments(long b1, long b2, int bp, int tp);

    @Insert()
    long insertClient(ClientRoom client);

    @Insert()
    void insertLiving(LivingRoom living);

    @Insert()
    void insertBooking(BookingRoom living);

    @Insert()
    long insertAdditionalService(AdditionalServicesRoom service);

    @Insert()
    void insertApartment(ApartmentRoom apartment);

    @Update()
    void updateClient(ClientRoom updatedClient);

    @Update()
    void updateAdditionalService(AdditionalServicesRoom updatedAdditionalService);

    @Delete
    void deleteClient(ClientRoom client);

    @Delete
    void deleteLiving(LivingRoom living);

    @Delete
    void deleteBooking(BookingRoom booking);

    @Delete
    void deleteBookings(List<BookingRoom> bookingsList);

    @Delete
    void deleteAdditionalService(AdditionalServicesRoom additionalServices);

    @Delete
    void deleteApartment(ApartmentRoom apartment);

}
