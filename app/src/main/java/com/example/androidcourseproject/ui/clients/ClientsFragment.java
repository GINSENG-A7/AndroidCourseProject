package com.example.androidcourseproject.ui.clients;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.FragmentClientsBinding;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.ui.MainActivity;
import com.example.androidcourseproject.ui.livings_and_bookings.livings.LivingsFragment;

import java.util.List;

public class ClientsFragment extends Fragment {

    private ClientsViewModel clientsViewModel;
    private FragmentClientsBinding binding;
    private ClientsAdapter adapter;
    public static AppDatabase db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        clientsViewModel =
                new ViewModelProvider(this).get(ClientsViewModel.class);

        binding = FragmentClientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        final TextView textView = binding.textHome;
//        clientsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        db = AppDatabase.getDataBase(getContext());
        List<ClientRoom> list = db.dao().getAllClients();
        adapter = new ClientsAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.clientsList.setLayoutManager(layoutManager);
        binding.clientsList.setAdapter(adapter);

        binding.clientsList.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL));


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientRoom client = adapter.getSelected();
                if(client != null) {
                    // clienteate an alert builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Name");

                    // set the custom layout
                    final View customLayout = getLayoutInflater().inflate(R.layout.edit_client_layout,
                            null);
                    builder.setView(customLayout);

                    // add a button
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // send data from the AlertDialog to the Activity
                            EditText editText = customLayout.findViewById(R.id.tvEditPassportSeries);
                            sendDialogDataToActivity(editText.getText().toString());
                        }
                    });
                    builder.setNegativeButton("CANCEL",  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendDialogDataToActivity("Изменения отменены");
                        }
                    });

                    // clienteate and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    //заполняем данные записи в соответствующие поля
                    EditText etEditPassportSeries = customLayout.findViewById(R.id.tvEditPassportSeries);
                    EditText etEditPassportNumber = customLayout.findViewById(R.id.tvEditPassportNumber);
                    EditText etEditName = customLayout.findViewById(R.id.tvEditName);
                    EditText etEditSurname = customLayout.findViewById(R.id.tvEditSurname);
                    EditText etEditPatronymic = customLayout.findViewById(R.id.tvEditPatronymic);
                    EditText etEditBirthday = customLayout.findViewById(R.id.tvEditBirthday);
                    EditText etEditTelephone = customLayout.findViewById(R.id.tvEditTelephone);
                    etEditPassportSeries.setText(String.valueOf(client.passport_series));
                    etEditPassportNumber.setText(String.valueOf(client.passport_number));
                    etEditName.setText(client.name);
                    etEditSurname.setText(client.surname);
                    etEditPatronymic.setText(client.patronymic);
                    etEditBirthday.setText(String.valueOf(client.birthday));
                    etEditTelephone.setText(client.telephone);
                }
                else {
                    Toast.makeText(getContext(), "Запись не выбрана", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.goToLivingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientRoom client = adapter.getSelected();
                MainActivity.navigateToLivings();
//                LivingsFragment livingsFragment = new LivingsFragment();
//                FragmentManager.findFragment(livingsFragment.getView());

            }
        });

        binding.goToBookingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientRoom client = adapter.getSelected();
            }
        });

        binding.deleteClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.registerNewClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void sendDialogDataToActivity(String data) {
        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        setArrayAdapter(MainActivity.db.dao().getAllClients());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setArrayAdapter(List<ClientRoom> listOfClientsRooms) {
        if (listOfClientsRooms.size() <= 0) {
            throw new RuntimeException("Выводимые таблицы пусты");
        }
    }
}