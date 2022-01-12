package com.example.androidcourseproject.ui.apartments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidcourseproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterClientApartmentPicker} factory method to
 * create an instance of this fragment.
 */
public class RegisterClientApartmentPicker extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getSupportFragmentManager()
                .setFragmentResultListener("newClientKey", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                        String result = String.valueOf(bundle.getInt("passportSeriesKey"));
                        Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();
                    }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_client_apartment_picker, container, false);
    }
}