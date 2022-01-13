package com.example.androidcourseproject.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.FragmentLivingsBinding;
import com.example.androidcourseproject.kal.DbHelper;
import com.example.androidcourseproject.room.AdditionalServicesRoom;
import com.example.androidcourseproject.room.ApartmentRoom;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.room.LivingRoom;
import com.example.androidcourseproject.ui.clients.ClientsFragment;
import com.example.androidcourseproject.ui.clients.ClientsViewModel;
import com.example.androidcourseproject.ui.livings_and_bookings.LivingsAndBookingsFragment;
import com.example.androidcourseproject.ui.livings_and_bookings.PagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androidcourseproject.databinding.ActivityMainBinding;

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


//
//        dbHelper = new DbHelper(getApplicationContext());
//        database = dbHelper.getWritableDatabase();
        db = AppDatabase.getDataBase(getBaseContext());
//        db.dao().insertClient(new ClientRoom(123, 123, "asd", "asd", "zxc", "zxc", "zxc"));
//        db.dao().insertApartment(new ApartmentRoom(1, "aboba", 123));
//        db.dao().insertAdditionalService(new AdditionalServicesRoom(1, 1, 1, 1, 1));
//        db.dao().insertLiving(new LivingRoom(1, "zxc", "zxc", 1, 1, 1, 1));
//        ClientRoom client = db.dao().getClientsBySurname("ebooka");
//        Log.d("zxc", String.valueOf(client.client_id));
        clientsViewModel = new ViewModelProvider(this).get(ClientsViewModel.class);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

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

    public static void navigateToClient() {
        navController.navigate(R.id.navigation_clients);
    }
}