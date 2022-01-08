package com.example.androidcourseproject.ui.livings_and_bookings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androidcourseproject.ui.livings_and_bookings.bookings.BookingsFragment;
import com.example.androidcourseproject.ui.livings_and_bookings.livings.LivingsFragment;

public class PagerAdapter extends FragmentStateAdapter {
    public PagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new LivingsFragment();
        } else {
            return new BookingsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
