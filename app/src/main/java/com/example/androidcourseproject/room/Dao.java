package com.example.androidcourseproject.room;


import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Query("SELECT * FROM living l WHERE l.client_id = :clientId")
    List<LivingRoom> getLivings(int clientId);

    @Query("SELECT * FROM client")
    List<ClientRoom> getAllClients();

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
