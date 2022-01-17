package com.example.androidcourseproject.ui.livings_and_bookings.bookings;

import static com.example.androidcourseproject.ui.MainActivity.showLongToastWithText;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidcourseproject.InputValidation;
import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.FragmentBookingsBinding;
import com.example.androidcourseproject.room.AdditionalServicesRoom;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.BookingRoom;
import com.example.androidcourseproject.room.LivingRoom;
import com.example.androidcourseproject.ui.MainActivity;
import com.example.androidcourseproject.ui.clients.ClientsViewModel;
import com.example.androidcourseproject.ui.livings_and_bookings.LivingsAndBookingsViewModel;
import com.example.androidcourseproject.ui.livings_and_bookings.livings.LivingsAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookingsFragment extends Fragment {
    private ClientsViewModel clientsViewModel;
    private LivingsAndBookingsViewModel livingsAndBookingsViewModel;
    private FragmentBookingsBinding binding;
    private BookingsAdapter adapter;
    public static AppDatabase db;
    private InputValidation inputValidation;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        livingsAndBookingsViewModel = new ViewModelProvider(this).get(LivingsAndBookingsViewModel.class);
        binding = FragmentBookingsBinding.inflate(inflater, container, false);

        db = AppDatabase.getDataBase(getContext());
        List<BookingRoom> list = db.dao().getAllBookings();
        adapter = new BookingsAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.bookingsList.setLayoutManager(layoutManager);
        binding.bookingsList.setAdapter(adapter);

        binding.bookingsList.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getSupportFragmentManager()
                .setFragmentResultListener("relocatedDataKey", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                        String result = bundle.getString("tabNameKey");
                        if (result.equals("Bookings")) {
                            int clientId = bundle.getInt("clientId");
                            List<BookingRoom> list = db.dao().getAllBookingsByClientId(clientId);
                            adapter = new BookingsAdapter(list);
                            binding.bookingsList.setAdapter(adapter);
                        }
                    }
                });

        //***************************************************

        binding.goToClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingRoom booking = adapter.getSelected();
                if(booking != null) {
                    Bundle result = new Bundle();
                    result.putInt("clientId", booking.client_id);
                    requireActivity().getSupportFragmentManager().setFragmentResult("ClientKey", result);
                    MainActivity.navigateToClients();
                }
                else {
                    Toast.makeText(getContext(), "Запись не выбрана", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.deleteEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingRoom booking = adapter.getSelected();
                if (booking != null) {
                    // clienteate an alert builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Name");

                    // set the custom layout
                    final View customLayout = getLayoutInflater().inflate(R.layout.delete_confiramation_alert,
                            null);
                    builder.setView(customLayout);

                    // add a button
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AdditionalServicesRoom additionalService = db.dao().getAdditionalServiceByLivingId(booking.booking_id);
                            db.dao().deleteBooking(booking);
                            showLongToastWithText(getContext(), "Запись успешно удалена");
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showLongToastWithText(getContext(), "Изменения отменены");
                        }
                    });

                    // clienteate and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    Toast.makeText(getContext(), "Запись не выбрана", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
