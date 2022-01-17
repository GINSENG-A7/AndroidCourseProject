package com.example.androidcourseproject.ui.apartments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.androidcourseproject.room.BookingRoom;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.ui.MainActivity;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.showPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApartmentRoom apartment = adapter.getSelected();
                if(apartment != null) {
                    Bundle result = new Bundle();
                    result.putInt("apartmentId", apartment.apartment_id);
                    requireActivity().getSupportFragmentManager().setFragmentResult("relatedApartmentKey", result);
                    MainActivity.navigateToPhotos();
                }
                else {
                    Toast.makeText(getContext(), "Запись не выбрана", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.editApartmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.deleteApartmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}