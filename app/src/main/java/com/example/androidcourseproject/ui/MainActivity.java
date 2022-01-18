package com.example.androidcourseproject.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;


import com.example.androidcourseproject.R;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.DiscountRoom;
import com.example.androidcourseproject.ui.clients.ClientsViewModel;
import com.example.androidcourseproject.ui.livings_and_bookings.PagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidcourseproject.databinding.ActivityMainBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ActivityMainBinding binding;
    public static AppDatabase db;
    static NavController navController;
    static PagerAdapter pagerAdapter;
    private static ClientsViewModel clientsViewModel;
    public static boolean clientsListItemsDecorated = false;

    /**
     * Builds MainActivity and bottom navigation menu with 3 pages
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_clients, R.id.navigation_livings_and_bookings, R.id.navigation_apartments)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        db = AppDatabase.getDataBase(getBaseContext());

        clientsViewModel = new ViewModelProvider(this).get(ClientsViewModel.class);

        DiscountRoom discount = db.dao().getDiscount();
        if (discount == null) {
            discount = new DiscountRoom();
            discount.discount = 0;
            db.dao().insertDiscount(discount);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * directly opens LivingsAndBookingsFragment
     */
    public static void navigateToLivingsAndBookings() {
        navController.navigate(R.id.navigation_livings_and_bookings);
    }

    /**
     * directly opens RegistrationDataInputting
     */
    public static void navigateToClientRegistrationDataInputting() {
        navController.navigate(R.id.registerClientDataInputs);
    }

    /**
     * directly opens RegistrationApartmentPicking
     */
    public static void navigateToClientRegistrationApartmentPicking() {
        navController.navigate(R.id.registerClientApartmentPicker);
    }

    /**
     * directly opens ClientsFragment
     */
    public static void navigateToClients() {
        navController.navigate(R.id.navigation_clients);
    }

    /**
     * directly opens PhotosFragment
     */
    public static void navigateToPhotos() {
        navController.navigate(R.id.photosFragment);
    }

    /**
     * correctly convert CalendarView date value to long
     */
    public static long convertCalendarViewDateToLong( int year, int month, int dayOfMonth) {
        long milliseconds = 0;
        String yearStr = String.valueOf(year);
        String monthStr = String.valueOf(month + 1);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(yearStr + "-" + monthStr + "-" + dayOfMonthStr);
            milliseconds = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }
}