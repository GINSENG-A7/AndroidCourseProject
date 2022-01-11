package com.example.androidcourseproject.ui.livings_and_bookings.livings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidcourseproject.databinding.FragmentLivingsBinding;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.room.LivingRoom;
import com.example.androidcourseproject.ui.clients.ClientsAdapter;
import com.example.androidcourseproject.ui.clients.ClientsViewModel;
import com.example.androidcourseproject.ui.livings_and_bookings.LivingsAndBookingsViewModel;

import java.util.List;

public class LivingsFragment extends Fragment {

    private LivingsAndBookingsViewModel livingsAndBookingsViewModel;
    private FragmentLivingsBinding binding;
    private LivingsAdapter adapter;
    public static AppDatabase db;
    private ClientsViewModel clientsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        livingsAndBookingsViewModel = new ViewModelProvider(this).get(LivingsAndBookingsViewModel.class);
        binding = FragmentLivingsBinding.inflate(inflater, container, false);

        db = AppDatabase.getDataBase(getContext());
        List<LivingRoom> list = db.dao().getAllLivings();
        adapter = new LivingsAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.livingsList.setLayoutManager(layoutManager);
        binding.livingsList.setAdapter(adapter);

        binding.livingsList.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL));

        return binding.getRoot();
    }


}
