package com.example.androidcourseproject;

import com.example.androidcourseproject.kal.Photos;
import com.example.androidcourseproject.kal.PseudoApartmentTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class RequestsSQL {

    public static ResultSet applyTargetFilters(Connection conn, String type, Date b1, Date b2, int bp, int tp) throws SQLException //Зарефакторить SQL запросы в этом методе
    {
        String query = "";
        if (type != null) {
            //command = new SqlCommand($"SELECT a.number, a.type, a.price, l.settling, l.eviction FROM Apartments a, Living l, Booking b WHERE (a.type={type} AND b.settling>'{b2}' AND l.eviction<'{b1}' AND a.price>{bp} AND a.price<{tp})", connection);
            query = "SELECT a.apartment_id, a.number, a.`type`, a.price FROM courseprojectschema.Apartment a WHERE a.`type` = '" + type + "' AND a.price > " + bp + " AND a.apartment_id < " + tp + " AND ((a.apartment_id IN (SELECT number FROM courseprojectschema.Living WHERE eviction < '" + b1 + "') AND NOT EXISTS(SELECT apartment_id FROM courseprojectschema.Booking WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Booking))) OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE settling > '" + b2 + "') OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction < '" + b1 + "'))) AND NOT EXISTS(SELECT apartment_id FROM courseprojectschema.Living WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living)) OR ((a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction<'" + b1 + "')) AND (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE settling>'" + b2 + "') OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction<'" + b1 + "')))) OR (a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Living) AND a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Booking)))";
        } else {
            //command = new SqlCommand($"SELECT a.number, a.type, a.price, l.settling, l.eviction FROM Apartments a, Living l, Booking b WHERE (b.settling>'{b2}' AND l.eviction<'{b1}' AND a.price>{bp} AND a.price<{tp})", connection);
            query = "SELECT a.apartment_id, a.number, a.`type`, a.price FROM courseprojectschema.Apartment a WHERE a.price > " + bp + " AND a.price < " + tp + " AND ((a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction < '" + b1 + "') AND NOT EXISTS(SELECT number FROM courseprojectschema.Booking WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Booking))) OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE settling > '" + b2 + "') OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction < '" + b1 + "'))) AND NOT EXISTS(SELECT apartment_id FROM courseprojectschema.Living WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living)) OR ((a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction<'" + b1 + "')) AND (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE settling>'" + b2 + "') OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction<'" + b1 + "')))) OR (a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Living) AND a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Booking)))";
        }
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static Boolean isThereNoBookingsAndLivingsOnApartment(Connection conn, int apartmentId) throws SQLException {
        String query = "SELECT a.apartment_id FROM courseprojectschema.Apartment a WHERE a.apartment_id not in (SELECT b.apartment_id FROM courseprojectschema.Booking b) AND a.apartment_id not in (SELECT l.apartment_id FROM courseprojectschema.Living l) AND a.apartment_id = " + apartmentId;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set.next() != false;
    }

    public static Boolean isNumberFreeForSetDate(Connection conn, Date b1, Date b2, int numberId) throws SQLException {
        String query = "SELECT a.apartment_id FROM courseprojectschema.Apartment a WHERE ((a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction < '" + b1 + "') AND NOT EXISTS(SELECT number FROM courseprojectschema.Booking WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Booking))) OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE settling > '" + b2 + "') OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction < '" + b1 + "'))) AND NOT EXISTS(SELECT apartment_id FROM courseprojectschema.Living WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living)) OR ((a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction<'" + b1 + "')) AND (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE settling>'" + b2 + "') OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction<'" + b1 + "')))) OR (a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Living) AND a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Booking)))";
        ResultSet set = conn.createStatement().executeQuery(query);
        while (set.next()) {
            if (numberId == set.getInt(1)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> getTypesOfApartments(Connection conn) throws SQLException // Получаем все реально имеющиеся типы номеров для данной гостинницы для последующего использоваия в ComboBox
    {
        ArrayList<String> currentTypesOfApartments = new ArrayList<>();
        String query = "SELECT DISTINCT type FROM courseprojectschema.Apartment";
        ResultSet set = conn.createStatement().executeQuery(query);
        while (set.next()) {
            currentTypesOfApartments.add(set.getString(1));
        }
        return currentTypesOfApartments;
    }

    //Тут получение листа картинок заданного номера. Пока не знаю как его правильно переписать =(
    public static java.util.List<Photos> collectImagesByApartmentId(Connection connection, int idOA) throws SQLException {
        String query = "SELECT photo_id, path, apartment_id FROM courseprojectschema.Photo WHERE apartment_id = " + idOA;
        ResultSet set = connection.createStatement().executeQuery(query);
        java.util.List<Photos> listOfImages = new ArrayList<Photos>();
        while (set.next()) {
            Photos localPhotos = new Photos(
                    set.getInt(1),
                    set.getString(2),
                    set.getInt(3)
            );
            listOfImages.add(localPhotos);
        }
        return listOfImages;
    }

    public static boolean selectSetValueOfNumberFromApartments(Connection conn, int n) throws SQLException {
        String query = "SELECT DISTINCT number FROM courseprojectschema.Apartments WHERE number = " + n;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set.next() != false;
    }

    public static boolean typeOfApartmentsIsCorrect(Connection conn, String cb) {
        PseudoApartmentTypes pAT = PseudoApartmentTypes.getApartmentsTypes();
        for (int i = 0; i < pAT.getTypesArray().length; i++) {
            if (pAT.getTypesArray()[i].equals(cb)) {
                return true;
            }
        }
        return false;
    }

    public static ResultSet selectAllFromClient(Connection conn) throws SQLException {
        String query = "SELECT client_id, passport_series, passport_number, name, surname, patronymic, birthday, tel_number FROM courseprojectschema.Client";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet selectAllFromClientWithSetSurname(Connection conn, String surname) throws SQLException {
        String query = "SELECT passport_series, passport_number, name, surname, patronymic, birthday, tel_number FROM courseprojectschema.Client WHERE surname LIKE '" + surname + "%'";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static int selectNthIdFromClientWherePassportDataDefinedToStringALTERNATIVE(Connection conn, int ps, int pn) throws SQLException {
        String query = "SELECT DISTINCT client_id FROM courseprojectschema.Client WHERE passport_series = " + ps + " AND passport_number = " + pn;
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set.next() == false) {
            return -1;
        } else {
            return set.getInt(1);
        }
    }

    public static String selectNthIdFromClientWherePassportDataDefinedToString(Connection conn, int ps, int pn) throws SQLException { //Очень странный метод, не знаю как я его вообще придумал. Не уверен, что смогу его заменить, поэтому выше напишу его альтернативу
        String query = "SELECT DISTINCT client_id FROM courseprojectschema.Client WHERE passport_series = " + ps + " AND passport_number = " + pn;
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set.next() == false) {
            return "null";
        } else {
            return set.getString(1);
        }
    }

    public static ResultSet selectAllFromClientByClientId(Connection conn, int iOC) throws SQLException {
        String query = "SELECT client_id, passport_series, passport_number, name, surname, patronymic, birthday, tel_number FROM courseprojectschema.Client WHERE client_id = " + iOC;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet selectAllFromLiving(Connection conn) throws SQLException {
        String query = "SELECT l.living_id, c.client_id, c.name, c.surname, c.patronymic, l.settling, l.eviction, a.number, l.value_of_guests, l.value_of_kids, a.apartment_id, l.as_id FROM courseprojectschema.Client c, Living l, Apartment a WHERE c.client_id = l.client_id AND l.apartment_id = a.apartment_id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet selectAllFromLivingByClientId(Connection conn, int iOC) throws SQLException {
        String query = "SELECT l.living_id, c.client_id, c.name, c.surname, c.patronymic, l.settling, l.eviction, a.number, l.value_of_guests, l.value_of_kids, a.apartment_id, l.as_id FROM courseprojectschema.Client c, courseprojectschema.Living l, courseprojectschema.Apartment a WHERE l.client_id = " + iOC + " AND c.client_id = " + iOC + " AND l.apartment_id = a.apartment_id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet selectAllFromBooking(Connection conn) throws SQLException {
        String query = "SELECT b.booking_id, b.client_id, c.name, c.surname, c.patronymic, b.settling, b.eviction, a.number, b.value_of_guests, b.value_of_kids, a.apartment_id FROM courseprojectschema.Client c, courseprojectschema.Booking b, courseprojectschema.Apartment a WHERE c.client_id = b.client_id AND b.apartment_id = a.apartment_id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet selectRelatedDataFromBookingById(Connection conn, Integer bookingId) throws SQLException {
        String query = "SELECT settling, eviction, value_of_guests, value_of_kids, apartment_id FROM courseprojectschema.Booking WHERE booking_id = " + bookingId;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet selectAllFromBookingByClientId(Connection conn, int iOC) throws SQLException {
        String query = "SELECT b.booking_id, b.client_id, c.name, c.surname, c.patronymic, b.settling, b.eviction, a.number, b.value_of_guests, b.value_of_kids, a.apartment_id FROM courseprojectschema.Client c, courseprojectschema.Booking b, courseprojectschema.Apartment a WHERE b.client_id = " + iOC + " AND c.client_id = " + iOC + " AND b.apartment_id = a.apartment_id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet selectAllFromApartments(Connection conn) throws SQLException {
        String query = "SELECT apartment_id, number, type, price FROM courseprojectschema.Apartment";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet selectAllFromApartmentsWhereNumberIsSet(Connection conn, int n) throws SQLException {
        String query = "SELECT number, type, price FROM courseprojectschema.Apartments WHERE number = " + n;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet selectAllFromApartmentsWhereApartmentIdIsSet(Connection conn, int apartmentId) throws SQLException {
        String query = "SELECT apartment_id, number, type, price FROM courseprojectschema.Apartment WHERE apartment_id = " + apartmentId;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static boolean isNumberOfApartmentsFreeForToday(Connection conn, int nOA) throws SQLException {
        String query = "SELECT a.apartment_id FROM courseprojectschema.Apartment a WHERE (a.apartment_id = " + nOA + ") AND ((a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction < '" + LocalDate.now() + "') AND NOT EXISTS(SELECT apartment_id FROM courseprojectschema.Booking WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Booking))) OR ((a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction < '" + LocalDate.now() + "'))) AND NOT EXISTS(SELECT apartment_id FROM courseprojectschema.Living WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living)) OR ((a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction < '" + LocalDate.now() + "')) AND ((a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction < '" + LocalDate.now() + "')))) OR (a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Living) AND a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Booking)))";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set.next() != false;
    }

    public static boolean isNumberOfApartmentsUnique(Connection conn, int nOA) throws SQLException {
        String query = "SELECT DISTINCT number FROM courseprojectschema.Apartment WHERE number = " + nOA;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set.next() == false;
    }

    public static int valueOfClientLivingsAndBookingsForToday(Connection conn, int iOC) throws SQLException {
        LocalDate.now();
        String query1 = "SELECT COUNT(l.living_id) FROM courseprojectschema.Living l WHERE l.eviction > '" + LocalDate.now() + "' AND l.client_id IN (SELECT client_id FROM courseprojectschema.client WHERE client_id = " + iOC + ")";
        ResultSet set1 = conn.createStatement().executeQuery(query1);
        String query2 = "SELECT COUNT(b.booking_id) FROM courseprojectschema.Booking b WHERE b.client_id IN (SELECT client_id FROM courseprojectschema.client WHERE client_id = " + iOC + ")";
        ResultSet set2 = conn.createStatement().executeQuery(query2);
        int l = 0;
        int b = 0;
        if (set1.next()) {
            l = set1.getInt(1);
        }
        if (set2.next()) {
            b = set2.getInt(1);
        }
        int v = l + b;
        return v;
    }

    public static Integer getCurrentDiscount(Connection conn) throws SQLException {
        String query = "SELECT DISTINCT discount FROM courseprojectschema.Discount";
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set.next() == false) {
            return 0;
        } else {
            return set.getInt(1);
        }
    }

    public static void setNewDiscount(Connection conn, int disc) throws SQLException {
        String query1 = "SELECT DISTINCT discount FROM courseprojectschema.Discount";
        ResultSet set1 = conn.createStatement().executeQuery(query1);
        if (set1.next() == false) {
            String query2 = "INSERT INTO courseprojectschema.Discount (discount) VALUES (" + disc + ")";
            conn.createStatement().executeUpdate(query2);
        } else {
            String query3 = "UPDATE courseprojectschema.Discount SET discount = " + disc;
            conn.createStatement().executeUpdate(query3);
        }
    }

    public static Integer[] selectAllFromAdditionalServicesWhereIsSetLivingID(Connection conn, int iOAS) throws SQLException {
        Integer[] v = new Integer[6];
        String query = "SELECT as_id, mini_bar, clothes_washing, telephone, intercity_telephone, food FROM courseprojectschema.Additional_services WHERE as_id = " + iOAS;
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set.next()) {
            for (int i = 0; i < v.length; i++) {
                v[i] = set.getInt(i + 1);
            }
        } else {
            for (int i = 0; i < v.length; i++) {
                v[i] = 0;
            }
        }
        return v;
    }

    public static boolean doesClientHasNoLivingsAndBookings(Connection conn, int idOfClient) throws SQLException {
        String query1 = "SELECT living_id FROM courseprojectschema.Living WHERE client_id = " + idOfClient;
        ResultSet set1 = conn.createStatement().executeQuery(query1);
        String query2 = "SELECT booking_id FROM courseprojectschema.Booking WHERE client_id = " + idOfClient;
        ResultSet set2 = conn.createStatement().executeQuery(query2);
        return set1 == null && set2 == null;
    }


    //Элементарные запросы на удаление и изменение сущностей

    public static void updateClientWithValues(Connection conn, int clientId, int passportSeries, int passportNumber, String name, String surname, String patronymic, Date birthday, String telephone) throws SQLException {
        String query = "UPDATE courseprojectschema.Client SET passport_series = " + passportSeries + ", passport_number = " + passportNumber + ", name = '" + name + "', surname = '" + surname + "', patronymic = '" + patronymic + "', birthday = '" + birthday + "', tel_number = '" + telephone + "' WHERE client_id = " + clientId;
        conn.createStatement().executeUpdate(query);
    }

    public static void deleteNullableClient(Connection conn, int clientId) throws SQLException {
        String query = "DELETE FROM courseprojectschema.Client WHERE client_id = " + clientId;
        conn.createStatement().executeUpdate(query);
    }

    public static void deleteAllInfoAboutclient(Connection conn, int clientId) throws SQLException {
        String query1 = "DELETE FROM courseprojectschema.Client WHERE client_id = " + clientId;
        conn.createStatement().executeUpdate(query1);
        String query2 = "DELETE FROM courseprojectschema.Living WHERE client_id = " + clientId;
        conn.createStatement().executeUpdate(query2);
        String query3 = "DELETE FROM courseprojectschema.Booking WHERE client_id = " + clientId;
        conn.createStatement().executeUpdate(query3);
    }

    public static void insertClientAndLivingAndAdditionalServicesEntry(Connection conn, int passportSeries, int passportNumber, String name, String surname, String patronymic, Date birthday, String telephone, int apartment_id, Date settling, Date eviction, int vog, int vok) throws SQLException {
        String query1 = "INSERT INTO courseprojectschema.Client (passport_series, passport_number, name, surname, patronymic, birthday, tel_number) VALUES (" + passportSeries + ", " + passportNumber + ", '" + name + "', '" + surname + "', '" + patronymic + "', '" + birthday + "', '" + telephone + "')";
        conn.createStatement().executeUpdate(query1);
        String query2 = "select @@IDENTITY";
        ResultSet set1 = conn.createStatement().executeQuery(query2);
        set1.next();
        int client_id = set1.getInt(1);
        String query3 = "INSERT INTO courseprojectschema.Additional_services (mini_bar, clothes_washing, telephone, intercity_telephone, food) VALUES (0, 0, 0, 0, 0)";
        conn.createStatement().executeUpdate(query3);
        ResultSet set2 = conn.createStatement().executeQuery(query2);
        set2.next();
        int as_id = set2.getInt(1);
        String query4 = "INSERT INTO courseprojectschema.Living (apartment_id, settling, eviction, value_of_guests, value_of_kids, client_id, as_id) VALUES (" + apartment_id + ", '" + settling + "', '" + eviction + "', " + vog + ", " + vok + ", " + client_id + ", " + as_id + ")";
        conn.createStatement().executeUpdate(query4);
    }

    public static void insertClientAndBookingAndEntry(Connection conn, int passportSeries, int passportNumber, String name, String surname, String patronymic, Date birthday, String telephone, int apartment_id, Date settling, Date eviction, int vog, int vok) throws SQLException {
        String query1 = "INSERT INTO courseprojectschema.Client (passport_series, passport_number, name, surname, patronymic, birthday, tel_number) VALUES (" + passportSeries + ", " + passportNumber + ", '" + name + "', '" + surname + "', '" + patronymic + "', '" + birthday + "', '" + telephone + "')";
        conn.createStatement().executeUpdate(query1);
        String query2 = "select @@IDENTITY";
        ResultSet set1 = conn.createStatement().executeQuery(query2);
        set1.next();
        int client_id = set1.getInt(1);
        String query3 = "INSERT INTO courseprojectschema.Booking (apartment_id, settling, eviction, value_of_guests, value_of_kids, client_id) VALUES ('" + apartment_id + "', '" + settling + "', '" + eviction + "', " + vog + ", " + vok + ", " + client_id + ")";
        conn.createStatement().executeUpdate(query3);
    }

    public static void changeLivingEntry(Connection conn, int livingId, int vog, int vok) throws SQLException {
        String query = "UPDATE courseprojectschema.Living SET value_of_guests = " + vog + ", value_of_kids = " + vok + " WHERE living_id = " + livingId;
        conn.createStatement().executeUpdate(query);
    }

    public static void deleteLivingEntry(Connection conn, int livingId, int asId) throws SQLException {
        String query1 = "DELETE FROM courseprojectschema.Additional_services WHERE as_id = " + asId;
        conn.createStatement().executeUpdate(query1);
        String query2 = "DELETE FROM courseprojectschema.Living WHERE living_id = " + livingId;
        conn.createStatement().executeUpdate(query2);
    }

    public static void insertLivingEntry(Connection conn, int client_id, int apartment_id, Date settling, Date eviction, int vog, int vok) throws SQLException {
        String query1 = "INSERT INTO courseprojectschema.Additional_services (mini_bar, clothes_washing, telephone, intercity_telephone, food) VALUES (0, 0, 0, 0, 0)";
        conn.createStatement().executeUpdate(query1);
        String query2 = "select @@IDENTITY";
        ResultSet set = conn.createStatement().executeQuery(query2);
        set.next();
        int as_id = set.getInt(1);
        String query3 = "INSERT INTO courseprojectschema.Living (number, settling, eviction, value_of_guests, value_of_kids, client_id, as_id) VALUES ('" + apartment_id + "', '" + settling + "', '" + eviction + "', '" + vog + "', '" + vok + "', '" + client_id + "', '" + as_id + "')";
        conn.createStatement().executeUpdate(query3);
    }

    public static void changeBookingEntry(Connection conn, int bookingId, int vog, int vok) throws SQLException {
        String query = "UPDATE courseprojectschema.Booking SET value_of_guests = " + vog + ", value_of_kids = " + vok + " WHERE booking_id = " + bookingId;
        conn.createStatement().executeUpdate(query);
    }

    public static void deleteBookingEntry(Connection conn, int bookingId) throws SQLException {
        String query = "DELETE FROM courseprojectschema.Booking WHERE booking_id = " + bookingId;
        conn.createStatement().executeUpdate(query);
    }

    public static void insertBookingEntry(Connection conn, int client_id, int apartment_id, Date settling, Date eviction, int vog, int vok) throws SQLException {
        String query = "INSERT INTO courseprojectschema.Booking (apartment_id, settling, eviction, value_of_guests, value_of_kids, client_id) VALUES ('" + apartment_id + "', '" + settling + "', '" + eviction + "', '" + vog + "', '" + vok + "', '" + client_id + "')";
        conn.createStatement().executeUpdate(query);
    }

    public static void changeApartmentsEntry(Connection conn, int apartmentId, int number, String type, int price) throws SQLException {
        String query = "UPDATE courseprojectschema.Apartment SET number = " + number + " , \"type\" = '" + type + "', price = " + price + " WHERE apartmentId = " + apartmentId;
        conn.createStatement().executeUpdate(query);
    }

    public static void deleteApartmentsEntry(Connection conn, int apartmentId) throws SQLException {
        String query1 = "DELETE FROM courseprojectschema.Apartment WHERE apartment_id = " + apartmentId;
        conn.createStatement().executeUpdate(query1);
        String query2 = "DELETE FROM courseprojectschema.Photo WHERE apartment_id = " + apartmentId;
        conn.createStatement().executeUpdate(query2);
    }

    public static void insertApartmentsEntry(Connection conn, int number, String type, int price) throws SQLException {
        String query = "INSERT INTO courseprojectschema.Apartment (number, `type`, price) VALUES ('" + number + "', '" + type + "', '" + price + "')";
        conn.createStatement().executeUpdate(query);
    }

    public static ResultSet selectApartmentsIdWithNumber(Connection conn, int number) throws SQLException {
        String query = "SELECT * FROM courseprojectschema.Apartment WHERE number = " + number;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static void changeAdditionalServices(Connection conn, int as_id, int mini_bar, int clothes_washing, int telephone, int intercity_telephone, int food) throws SQLException {
        String query = "UPDATE courseprojectschema.Additional_services SET mini_bar = " + mini_bar + ", clothes_washing = " + clothes_washing + ", telephone = " + telephone + ", intercity_telephone = " + intercity_telephone + ", food = " + food + " WHERE as_id = " + as_id;
        conn.createStatement().executeUpdate(query);
    }

    public static void insertPhotoEntry(Connection conn, String path, int apartmentId) throws SQLException {
        String query = "INSERT INTO courseprojectschema.Photo (path, apartment_id) VALUES ('" + path + "', " + apartmentId + ")";
        conn.createStatement().executeUpdate(query);
    }

    public static void deletePhotoEntry(Connection conn, int apartmentId) throws SQLException {
        String query = "DELETE FROM courseprojectschema.Photo WHERE photo_id = " + apartmentId;
        conn.createStatement().executeUpdate(query);
    }
}
