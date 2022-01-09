package com.example.androidcourseproject.ui.apartments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidcourseproject.databinding.FragmentApartmentsBinding;
import com.example.androidcourseproject.room.ApartmentRoom;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.ui.clients.ClientsAdapter;

import java.util.List;

public class ApartmentsFragment extends Fragment {

    private ApartmentsViewModel apartmentsViewModel;
    private FragmentApartmentsBinding binding;
    private ApartmentsAdapter adapter;
    public static AppDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        apartmentsViewModel =
                new ViewModelProvider(this).get(ApartmentsViewModel.class);

        binding = FragmentApartmentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        apartmentsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        db = AppDatabase.getDataBase(getContext());
        List<ApartmentRoom> list = db.dao().getAllApartments();
        adapter = new ApartmentsAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.apartmentsList.setLayoutManager(layoutManager);
        binding.apartmentsList.setAdapter(adapter);

        binding.apartmentsList.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}