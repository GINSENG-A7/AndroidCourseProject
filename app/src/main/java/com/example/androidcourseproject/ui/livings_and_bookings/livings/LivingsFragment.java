package com.example.androidcourseproject.ui.livings_and_bookings.livings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidcourseproject.databinding.FragmentLivingsBinding;

public class LivingsFragment extends Fragment {
    private FragmentLivingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLivingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
