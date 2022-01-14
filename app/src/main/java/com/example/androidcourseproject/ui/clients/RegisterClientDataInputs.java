package com.example.androidcourseproject.ui.clients;

import static com.example.androidcourseproject.ui.MainActivity.convertCalendarViewDateToLong;
import static com.example.androidcourseproject.ui.MainActivity.db;
import static com.example.androidcourseproject.ui.MainActivity.showLongToastWithText;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.FragmentClientsBinding;
import com.example.androidcourseproject.databinding.FragmentRegisterClientDataInputsBinding;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.example.androidcourseproject.InputValidation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterClientDataInputs} factory method to
 * create an instance of this fragment.
 */
public class RegisterClientDataInputs extends Fragment {
    private FragmentRegisterClientDataInputsBinding binding;
    private long chosenBirthdayDate;
    protected CalendarView calendarView;
    protected InputValidation inputValidation;
    private int valueOfGuests = 0;
    private int valueOfKids = 0;
    private ClientRoom relatedClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterClientDataInputsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputValidation = new InputValidation();

        requireActivity().getSupportFragmentManager()
                .setFragmentResultListener("relatedClientKey", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                        int clientId = bundle.getInt("clientIdKey");
                        relatedClient = db.dao().getClientById(clientId);
                        binding.etPassportSeries.setText(String.valueOf(relatedClient.passport_series));
                        binding.etPassportNumber.setText(String.valueOf(relatedClient.passport_number));
                        binding.etName.setText(relatedClient.name);
                        binding.etSurname.setText(relatedClient.surname);
                        binding.etPatronymic.setText(relatedClient.patronymic);
                        chosenBirthdayDate = relatedClient.birthday;
                        binding.etTelephone.setText(relatedClient.telephone);

                        binding.dateTextView.setText(new Date(chosenBirthdayDate).toString());
                    }
                });

        binding.chooseBirthdayButton.setOnClickListener(new View.OnClickListener() {
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
                        chosenBirthdayDate = calendarView.getDate();
                        binding.dateTextView.setText(new Date(chosenBirthdayDate).toString());
                    }
                });
                builder.setNegativeButton("CANCEL",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.dateTextView.setText(R.string.newClient_noDate_dateTextView);
                    }
                });

                // clienteate and show the alert dialog
                AlertDialog dialog = builder.create();
                calendarView = customLayout.findViewById(R.id.calendarView);

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        chosenBirthdayDate = convertCalendarViewDateToLong(year, month, dayOfMonth);
                        binding.dateTextView.setText(new Date(chosenBirthdayDate).toString());
                        Log.d("zxc", String.valueOf(chosenBirthdayDate));
                    }
                });

                dialog.show();
            }
        });

        binding.nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();

                if(binding.etPassportSeries.getText().toString().equals("") ||
                        binding.etPassportNumber.getText().toString().equals("") ||
                        binding.etName.getText().toString().equals("") ||
                        binding.etSurname.getText().toString().equals("") ||
                        binding.etPatronymic.getText().toString().equals("") ||
                        binding.dateTextView.getText().toString().equals(R.string.newClient_noDate_dateTextView) ||
                        binding.etTelephone.getText().toString().equals("") ||
                        binding.etValueOfGuests.getText().toString().equals("") ||
                        binding.etValueOfKids.getText().toString().equals("")) {
                        showLongToastWithText(getContext(), "Все даные обязательны к заполнению");
                }
                else {
                    if(inputValidation.isRowConsistsOfNumbers(binding.etPassportSeries.getText().toString()) &&
                            inputValidation.isRowConsistsOfNumbers(binding.etPassportNumber.getText().toString()) &&
                            inputValidation.isRowConsistsOfLetters(binding.etName.getText().toString()) &&
                            inputValidation.isRowConsistsOfLetters(binding.etSurname.getText().toString()) &&
                            inputValidation.isRowConsistsOfLetters(binding.etPatronymic.getText().toString()) &&
                            inputValidation.isRowConsistsOfNumbers(binding.etTelephone.getText().toString()) &&
                            inputValidation.isRowConsistsOfNumbers(binding.etValueOfGuests.getText().toString()) &&
                            inputValidation.isRowConsistsOfNumbers(binding.etValueOfKids.getText().toString()) &&
                            inputValidation.checkLenthOfPassportSeries(binding.etPassportSeries.getText().toString()) &&
                            inputValidation.checkLenthOfPassportNumber(binding.etPassportNumber.getText().toString())
                    ) {
                        ClientRoom newClient = new ClientRoom(
                                Integer.valueOf(binding.etPassportSeries.getText().toString()),
                                Integer.valueOf(binding.etPassportNumber.getText().toString()),
                                binding.etName.getText().toString(),
                                binding.etSurname.getText().toString(),
                                binding.etPatronymic.getText().toString(),
                                chosenBirthdayDate,
                                binding.etTelephone.getText().toString()
                        );
                        valueOfGuests = Integer.valueOf(binding.etValueOfGuests.getText().toString());
                        valueOfKids = Integer.valueOf(binding.etValueOfKids.getText().toString());

                        result.putInt("passportSeriesKey", newClient.passport_series);
                        result.putInt("passportNumberKey", newClient.passport_number);
                        result.putString("nameKey", newClient.name);
                        result.putString("surnameKey", newClient.surname);
                        result.putString("patronymicKey", newClient.patronymic);
                        result.putLong("birthdayKey", newClient.birthday);
                        result.putString("telephoneKey", newClient.telephone);
                        result.putInt("valueOfGuests", valueOfGuests);
                        result.putInt("valueOfKids", valueOfKids);

                        requireActivity().getSupportFragmentManager().setFragmentResult("newClientKey", result);
                        MainActivity.navigateToClientRegistrationApartmentPicking();
                    }
                    else {
                        showLongToastWithText(getContext(), "Некоррекный формат даннных");
                    }
                }
            }
        });
    }


}