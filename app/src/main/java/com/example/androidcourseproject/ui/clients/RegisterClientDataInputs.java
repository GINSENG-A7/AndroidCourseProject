package com.example.androidcourseproject.ui.clients;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterClientDataInputs} factory method to
 * create an instance of this fragment.
 */
public class RegisterClientDataInputs extends Fragment {
    private FragmentRegisterClientDataInputsBinding binding;
    private long chosenBirthdayDate;
    protected CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_register_client_data_inputs, container, false);
        binding = FragmentRegisterClientDataInputsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                dialog.show();
            }
        });

        binding.nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navigateToClientRegistrationApartmentPicking();
                Bundle result = new Bundle();
                ClientRoom newClient = new ClientRoom(
                        Integer.valueOf(binding.etPassportSeries.getText().toString()),
                        Integer.valueOf(binding.etPassportNumber.getText().toString()),
                        binding.etName.getText().toString(),
                        binding.etSurname.getText().toString(),
                        binding.etPatronymic.getText().toString(),
                        chosenBirthdayDate,
                        binding.etTelephone.getText().toString()
                        );
                result.putInt("passportSeriesKey", newClient.passport_series);
                result.putInt("passportNumberKey", newClient.passport_number);
                result.putString("nameKey", newClient.name);
                result.putString("surnameKey", newClient.surname);
                result.putString("patronymicKey", newClient.patronymic);
                result.putLong("birthdayKey", newClient.birthday);
                result.putString("telephoneKey", newClient.telephone);
                requireActivity().getSupportFragmentManager().setFragmentResult("newClientKey", result);
            }
        });
    }
}