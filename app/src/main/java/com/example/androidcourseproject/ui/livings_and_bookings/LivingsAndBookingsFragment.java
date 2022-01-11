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
import androidx.viewpager2.widget.ViewPager2;

import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.FragmentLivingsAndBookingsBinding;
import com.example.androidcourseproject.ui.MainActivity;
import com.google.android.material.tabs.TabLayoutMediator;

public class LivingsAndBookingsFragment extends Fragment {

    private LivingsAndBookingsViewModel livingsAndBookingsViewModel;
    private FragmentLivingsAndBookingsBinding binding;
    ViewPager2 viewpager2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        livingsAndBookingsViewModel =
                new ViewModelProvider(this).get(LivingsAndBookingsViewModel.class);

        binding = FragmentLivingsAndBookingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        PagerAdapter pagerAdapter = new PagerAdapter(this);
        binding.livingBookingContainer.setAdapter(pagerAdapter);
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

    public void slideToBookings(){
        viewpager2 = binding.livingBookingContainer;
        viewpager2.setCurrentItem(2);
    }

    public void slideToLivings(){
        viewpager2 = binding.livingBookingContainer;
        viewpager2.setCurrentItem(1);
    }
}