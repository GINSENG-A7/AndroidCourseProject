package com.example.androidcourseproject.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.FragmentLivingsBinding;
import com.example.androidcourseproject.kal.DbHelper;
import com.example.androidcourseproject.room.AdditionalServicesRoom;
import com.example.androidcourseproject.room.ApartmentRoom;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.room.DiscountRoom;
import com.example.androidcourseproject.room.LivingRoom;
import com.example.androidcourseproject.ui.clients.ClientsFragment;
import com.example.androidcourseproject.ui.clients.ClientsViewModel;
import com.example.androidcourseproject.ui.livings_and_bookings.LivingsAndBookingsFragment;
import com.example.androidcourseproject.ui.livings_and_bookings.PagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androidcourseproject.databinding.ActivityMainBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ActivityMainBinding binding;
    SQLiteDatabase database;
    DbHelper dbHelper;
    SimpleCursorAdapter simpleCursorAdapter;
    public static AppDatabase db;
    static NavController navController;
    static PagerAdapter pagerAdapter;
    private static ClientsViewModel clientsViewModel;
    public static boolean clientsListItemsDecorated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

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

    public static void navigateToClientRegistrationDataInputting() {
        navController.navigate(R.id.registerClientDataInputs);
    }

    public static void navigateToClientRegistrationApartmentPicking() {
        navController.navigate(R.id.registerClientApartmentPicker);
    }

    public static void showLongToastWithText(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void navigateToClients() {
        navController.navigate(R.id.navigation_clients);
    }

    public static void navigateToPhotos() {
        navController.navigate(R.id.photosFragment);
    }

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