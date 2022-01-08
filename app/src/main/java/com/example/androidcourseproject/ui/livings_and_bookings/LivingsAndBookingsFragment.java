package com.example.androidcourseproject.ui.livings_and_bookings;

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

import com.example.androidcourseproject.databinding.FragmentLivingsAndBookingsBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class LivingsAndBookingsFragment extends Fragment {

    private LivingsAndBookingsViewModel livingsAndBookingsViewModel;
    private FragmentLivingsAndBookingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        livingsAndBookingsViewModel =
                new ViewModelProvider(this).get(LivingsAndBookingsViewModel.class);

        binding = FragmentLivingsAndBookingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        livingsAndBookingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//            }
//        });
        binding.livingBookingContainer.setAdapter(new PagerAdapter(this));
        new TabLayoutMediator(binding.topTabs, binding.livingBookingContainer, (tab, position) -> {
            if (position == 0) {
                tab.setText("Livings");
            } else {
                tab.setText("Bookings");
            }
        }).attach();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}