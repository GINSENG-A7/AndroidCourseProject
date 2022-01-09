package com.example.androidcourseproject.ui.livings_and_bookings.bookings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidcourseproject.databinding.FragmentBookingsBinding;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.BookingRoom;
import com.example.androidcourseproject.room.LivingRoom;
import com.example.androidcourseproject.ui.livings_and_bookings.LivingsAndBookingsViewModel;
import com.example.androidcourseproject.ui.livings_and_bookings.livings.LivingsAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookingsFragment extends Fragment {
    private LivingsAndBookingsViewModel livingsAndBookingsViewModel;
    private FragmentBookingsBinding binding;
    private BookingsAdapter adapter;
    public static AppDatabase db;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        livingsAndBookingsViewModel = new ViewModelProvider(this).get(LivingsAndBookingsViewModel.class);
        binding = FragmentBookingsBinding.inflate(inflater, container, false);

        db = AppDatabase.getDataBase(getContext());
        List<BookingRoom> list = db.dao().getAllBookings();
        adapter = new BookingsAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.bookingsList.setLayoutManager(layoutManager);
        binding.bookingsList.setAdapter(adapter);

        binding.bookingsList.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL));

        return binding.getRoot();
    }
}
