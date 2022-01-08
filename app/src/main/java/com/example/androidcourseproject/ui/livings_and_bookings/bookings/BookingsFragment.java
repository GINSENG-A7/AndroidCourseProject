package com.example.androidcourseproject.ui.livings_and_bookings.bookings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidcourseproject.databinding.FragmentBookingsBinding;

import java.util.ArrayList;
import java.util.List;

public class BookingsFragment extends Fragment {
    private FragmentBookingsBinding binding;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
