package com.example.androidcourseproject.ui.apartments;

import static com.example.androidcourseproject.ui.MainActivity.showLongToastWithText;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.androidcourseproject.InputValidation;
import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.FragmentApartmentsBinding;
import com.example.androidcourseproject.databinding.FragmentRegisterClientApartmentPickerBinding;
import com.example.androidcourseproject.databinding.FragmentRegisterClientDataInputsBinding;
import com.example.androidcourseproject.room.AdditionalServicesRoom;
import com.example.androidcourseproject.room.ApartmentRoom;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.room.LivingRoom;
import com.example.androidcourseproject.ui.MainActivity;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterClientApartmentPicker} factory method to
 * create an instance of this fragment.
 */
public class RegisterClientApartmentPicker extends Fragment {
    private ApartmentsViewModel apartmentsViewModel;
    private FragmentRegisterClientApartmentPickerBinding binding;
    private ApartmentsAdapter adapter;
    public static AppDatabase db;
    protected CalendarView calendarView;
    private long chosenSettlingDate;
    private long chosenEvictionDate;
    private ClientRoom client;
    private LivingRoom booking;
    private AdditionalServicesRoom additionalServices;
    private int valueOfGuests = 0;
    private int valueOfKids = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterClientApartmentPickerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = AppDatabase.getDataBase(getContext());
        List<ApartmentRoom> list = db.dao().getAllApartments();
        adapter = new ApartmentsAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.apartmentsList.setLayoutManager(layoutManager);
        binding.apartmentsList.setAdapter(adapter);

        binding.apartmentsList.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL));

        requireActivity().getSupportFragmentManager()
                .setFragmentResultListener("newClientKey", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                        valueOfGuests = bundle.getInt("valueOfGuests");
                        valueOfKids = bundle.getInt("valueOfKids");
                        client = new ClientRoom(bundle.getInt("passportSeriesKey"),
                                bundle.getInt("passportNumberKey"),
                                bundle.getString("nameKey"),
                                bundle.getString("surnameKey"),
                                bundle.getString("patronymicKey"),
                                bundle.getLong("birthdayKey"),
                                bundle.getString("telephoneKey")
                        );
                    }
        });

        binding.setSettlingDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clienteate an alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Birthday");

                // set the custom layout
                final View customLayout = getLayoutInflater().inflate(R.layout.date_picker_layout,
                        null);
                builder.setView(customLayout);

                // add a button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chosenSettlingDate = calendarView.getDate();
                        binding.tvSettlingDate.setText(new Date(chosenSettlingDate).toString());
                    }
                });
                builder.setNegativeButton("CANCEL",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.tvSettlingDate.setText(R.string.newClient_noDate_dateTextView);
                    }
                });

                // clienteate and show the alert dialog
                AlertDialog dialog = builder.create();
                calendarView = customLayout.findViewById(R.id.calendarView);

                dialog.show();
            }
        });

        binding.setEvictionDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clienteate an alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Birthday");

                // set the custom layout
                final View customLayout = getLayoutInflater().inflate(R.layout.date_picker_layout,
                        null);
                builder.setView(customLayout);

                // add a button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chosenEvictionDate = calendarView.getDate();
                        binding.tvEvictionDate.setText(new Date(chosenEvictionDate).toString());
                    }
                });
                builder.setNegativeButton("CANCEL",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.tvEvictionDate.setText(R.string.newClient_noDate_dateTextView);
                    }
                });

                // clienteate and show the alert dialog
                AlertDialog dialog = builder.create();
                calendarView = customLayout.findViewById(R.id.calendarView);

                dialog.show();
            }
        });

        binding.registerNewLivingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long lastInsertedClientId = db.dao().insertClient(client);
                long lastInsertedAdditionalServicesId = db.dao().insertAdditionalService(
                        new AdditionalServicesRoom(0, 0, 0, 0, 0));
                db.dao().insertLiving(new LivingRoom((int)lastInsertedClientId,
                        chosenSettlingDate,
                        chosenEvictionDate,
                        valueOfGuests,
                        valueOfKids,
                        adapter.getSelected().apartment_id,
                        (int)lastInsertedAdditionalServicesId
                        ));

                showLongToastWithText(getContext(), "Проживание успешно зарегистрированно!");
                MainActivity.navigateToClient();
            }
        });

        binding.registerNewBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

}