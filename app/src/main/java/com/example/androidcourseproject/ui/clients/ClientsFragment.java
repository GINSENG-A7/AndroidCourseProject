package com.example.androidcourseproject.ui.clients;

import static com.example.androidcourseproject.ui.MainActivity.showLongToastWithText;

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
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.FragmentClientsBinding;
import com.example.androidcourseproject.room.AdditionalServicesRoom;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.BookingRoom;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.room.LivingRoom;
import com.example.androidcourseproject.ui.MainActivity;
import com.example.androidcourseproject.ui.livings_and_bookings.livings.LivingsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClientsFragment extends Fragment {

    private ClientsViewModel clientsViewModel;
    private FragmentClientsBinding binding;
    private ClientsAdapter adapter;
    public static AppDatabase db;
    private EditText etEditPassportSeries;
    private EditText etEditPassportNumber;
    private EditText etEditName;
    private EditText etEditSurname;
    private EditText etEditPatronymic;
    private EditText etEditBirthday;
    private EditText etEditTelephone;

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
        requireActivity().getSupportFragmentManager()
                .setFragmentResultListener("relocatedDataKey", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                        int clientId = bundle.getInt("clientId");
                        if (clientId != 0) {
                            ClientRoom relatedClient = db.dao().getClientById(clientId);
                            List<ClientRoom> relatedClientsList = new ArrayList<ClientRoom>();
                            relatedClientsList.add(relatedClient);
                            adapter = new ClientsAdapter(relatedClientsList);
                            binding.clientsList.setAdapter(adapter);
                        }
                    }
                });

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
                            showLongToastWithText(getContext(), editText.getText().toString());

                            client.passport_series = Integer.valueOf(etEditPassportSeries.getText().toString());
                            client.passport_number = Integer.valueOf(etEditPassportNumber.getText().toString());
                            client.name = etEditName.getText().toString();
                            client.surname = etEditSurname.getText().toString();
                            client.patronymic = etEditPatronymic.getText().toString();
                            client.birthday = Long.valueOf(etEditBirthday.getText().toString());
                            client.telephone = etEditTelephone.getText().toString();
                            db.dao().updateClient(client);

                            adapter.setClients(db.dao().getAllClients());
                            adapter.notifyDataSetChanged();

//                            db.dao().updateClient(new ClientRoom(
//                                    Integer.valueOf(etEditPassportSeries.getText().toString()),
//                                    Integer.valueOf(etEditPassportNumber.getText().toString()),
//                                    etEditName.getText().toString(),
//                                    etEditSurname.getText().toString(),
//                                    etEditPatronymic.getText().toString(),
//                                    Long.valueOf(etEditBirthday.getText().toString()),
//                                    etEditTelephone.getText().toString()
//                            ));

                            showLongToastWithText(getContext(), "Запись успешно обновлена");
                        }
                    });
                    builder.setNegativeButton("CANCEL",  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showLongToastWithText(getContext(), "Изменения отменены");
                        }
                    });

                    // clienteate and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    //заполняем данные записи в соответствующие поля
                    etEditPassportSeries = customLayout.findViewById(R.id.tvEditPassportSeries);
                    etEditPassportNumber = customLayout.findViewById(R.id.tvEditPassportNumber);
                    etEditName = customLayout.findViewById(R.id.tvEditName);
                    etEditSurname = customLayout.findViewById(R.id.tvEditSurname);
                    etEditPatronymic = customLayout.findViewById(R.id.tvEditPatronymic);
                    etEditBirthday = customLayout.findViewById(R.id.tvEditBirthday);
                    etEditTelephone = customLayout.findViewById(R.id.etEditTelephone);
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
                if (client != null) {
                    MainActivity.navigateToLivingsAndBookings();
                    Bundle result = new Bundle();
                    result.putString("tabNameKey", "Livings");
                    result.putInt("clientId", client.client_id);
                    requireActivity().getSupportFragmentManager().setFragmentResult("requestKey", result);
                }
                else {
                    Toast.makeText(getContext(), "Запись не выбрана", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.goToBookingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientRoom client = adapter.getSelected();
                if (client != null) {
                    MainActivity.navigateToLivingsAndBookings();
                    Bundle result = new Bundle();
                    result.putString("tabNameKey", "Booking");
                    result.putInt("clientId", client.client_id);
                    requireActivity().getSupportFragmentManager().setFragmentResult("requestKey", result);
                }
                else {
                    Toast.makeText(getContext(), "Запись не выбрана", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.deleteClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientRoom client = adapter.getSelected();
                if(client != null) {
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
                            List<BookingRoom> bookings = db.dao().getAllBookingsByClientId(client.client_id);
                            db.dao().deleteBookings(bookings);

                            List<LivingRoom> livings = db.dao().getAllLivingsByClientId(client.client_id);
                            for (LivingRoom living: livings) {
                                AdditionalServicesRoom additionalServices = db.dao().getAdditionalServiceByLivingId(living.living_id);
                                db.dao().deleteAdditionalService(additionalServices);
                                db.dao().deleteLiving(living);
                            }

                            db.dao().deleteClient(client);
                            showLongToastWithText(getContext(), "Запись успешно удалена");

                            adapter.setClients(db.dao().getAllClients());
                            adapter.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("NO",  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showLongToastWithText(getContext(), "Изменения отменены");
                        }
                    });

                    // clienteate and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(getContext(), "Запись не выбрана", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.registerNewClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getSelected() == null) {
                    MainActivity.navigateToClientRegistrationDataInputting();
                }
                else {
                    ClientRoom clientRoom = adapter.getSelected();
                    Bundle result = new Bundle();
                    result.putInt("clientIdKey", clientRoom.client_id);
                    requireActivity().getSupportFragmentManager().setFragmentResult("relatedClientKey", result);
                    MainActivity.navigateToClientRegistrationDataInputting();
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        List<ClientRoom> clientList = MainActivity.db.dao().getAllClients();
        if (clientList.size() > 0) {
            setArrayAdapter(clientList);
        }
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