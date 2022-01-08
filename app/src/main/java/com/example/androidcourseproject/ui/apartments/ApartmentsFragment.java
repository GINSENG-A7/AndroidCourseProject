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

import com.example.androidcourseproject.databinding.FragmentApartmentsBinding;

public class ApartmentsFragment extends Fragment {

    private ApartmentsViewModel apartmentsViewModel;
    private FragmentApartmentsBinding binding;

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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}