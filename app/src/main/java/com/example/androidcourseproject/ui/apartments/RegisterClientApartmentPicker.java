package com.example.androidcourseproject.ui.apartments;

import static com.example.androidcourseproject.ui.MainActivity.convertCalendarViewDateToLong;
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

import android.util.Log;
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
import com.example.androidcourseproject.room.BookingRoom;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.room.LivingRoom;
import com.example.androidcourseproject.ui.MainActivity;

import java.util.ArrayList;
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
    private int bottomPrice = 0;
    private int topPrice = Integer.MAX_VALUE;
    boolean clientIsAlreadyRegistered = false;

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
        List<ApartmentRoom> apartmentList = new ArrayList<ApartmentRoom>();
        adapter = new ApartmentsAdapter(apartmentList);
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

                        client = db.dao().getClientsByPassportData(bundle.getInt("passportSeriesKey"),
                                bundle.getInt("passportNumberKey"));
                        clientIsAlreadyRegistered = true;

                        if (client == null) {
                            client = new ClientRoom(bundle.getInt("passportSeriesKey"),
                                    bundle.getInt("passportNumberKey"),
                                    bundle.getString("nameKey"),
                                    bundle.getString("surnameKey"),
                                    bundle.getString("patronymicKey"),
                                    bundle.getLong("birthdayKey"),
                                    bundle.getString("telephoneKey")
                            );
                            clientIsAlreadyRegistered = false;
                        }
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

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        chosenSettlingDate = convertCalendarViewDateToLong(year, month, dayOfMonth);
                        binding.tvSettlingDate.setText(new Date(
                                chosenSettlingDate).toString());
                        Log.d("zxc", String.valueOf(chosenSettlingDate));
                    }
                });

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
                        Log.d("intG", String.valueOf(chosenSettlingDate));
                        Log.d("intG", String.valueOf(chosenEvictionDate));
                        Log.d("intG", String.valueOf(bottomPrice));
                        Log.d("intG", String.valueOf(topPrice));
                        if(chosenSettlingDate < chosenEvictionDate && bottomPrice < topPrice) {
                            List<ApartmentRoom> localApartmentList = db.dao().getFilteredApartments(
                                    chosenSettlingDate,
                                    chosenEvictionDate,
                                    bottomPrice,
                                    topPrice
                            );
                            adapter.setApartments(localApartmentList);
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            showLongToastWithText(getContext(), "Даты заданы некорректно");
                        }
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

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        chosenEvictionDate = convertCalendarViewDateToLong(year, month, dayOfMonth);
                        binding.tvEvictionDate.setText(new Date(chosenEvictionDate).toString());
                        Log.d("zxc", String.valueOf(chosenEvictionDate));
                    }
                });

                dialog.show();
            }
        });

        binding.bottomPriceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false) {
                    if(!binding.bottomPriceEditText.getText().toString().equals("")) {
                        bottomPrice = Integer.valueOf(binding.bottomPriceEditText.getText().toString());
                    }
                    else {
                        bottomPrice = 0;
                    }
//                    List<ApartmentRoom> list = db.dao().getFilteredApartments(
//                            chosenSettlingDate,
//                            chosenEvictionDate,
//                            bottomPrice,
//                            topPrice
//                    );
//                    adapter = new ApartmentsAdapter(list);
                }
            }
        });

        binding.topPriceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false) {
                    if(!binding.topPriceEditText.getText().toString().equals("")) {
                        topPrice = Integer.valueOf(binding.topPriceEditText.getText().toString());
                    }
                    else {
                        topPrice = Integer.MAX_VALUE;
                    }
//                    List<ApartmentRoom> list = db.dao().getFilteredApartments(
//                            chosenSettlingDate,
//                            chosenEvictionDate,
//                            bottomPrice,
//                            topPrice
//                    );
//                    adapter = new ApartmentsAdapter(list);
                }
            }
        });

        binding.registerNewLivingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long lastInsertedClientId = -1;
                if(clientIsAlreadyRegistered == false) {
                    lastInsertedClientId = db.dao().insertClient(client);
                }
                else {
                    lastInsertedClientId = client.client_id;
                }
                long lastInsertedAdditionalServicesId = db.dao().insertAdditionalService(
                        new AdditionalServicesRoom(0, 0, 0, 0, 0));
                db.dao().insertLiving(new LivingRoom((int) lastInsertedClientId,
                        chosenSettlingDate,
                        chosenEvictionDate,
                        valueOfGuests,
                        valueOfKids,
                        adapter.getSelected().apartment_id,
                        (int) lastInsertedAdditionalServicesId
                ));

                showLongToastWithText(getContext(), "Проживание успешно зарегистрированно!");
                MainActivity.navigateToClients();
            }
        });

        binding.registerNewBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long lastInsertedClientId = -1;
                if(clientIsAlreadyRegistered == false) {
                    lastInsertedClientId = db.dao().insertClient(client);
                }
                else {
                    lastInsertedClientId = client.client_id;
                }
                db.dao().insertBooking(new BookingRoom((int) lastInsertedClientId,
                        chosenSettlingDate,
                        chosenEvictionDate,
                        valueOfGuests,
                        valueOfKids,
                        adapter.getSelected().apartment_id
                ));

                showLongToastWithText(getContext(), "Бронирование успешно зарегистрированно!");
                MainActivity.navigateToClients();
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