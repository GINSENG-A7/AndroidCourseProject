package com.example.androidcourseproject.ui.livings_and_bookings.livings;

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

import com.example.androidcourseproject.InputValidation;
import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.FragmentLivingsBinding;
import com.example.androidcourseproject.room.AdditionalServicesRoom;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.BookingRoom;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.room.LivingRoom;
import com.example.androidcourseproject.ui.MainActivity;
import com.example.androidcourseproject.ui.clients.ClientsAdapter;
import com.example.androidcourseproject.ui.clients.ClientsViewModel;
import com.example.androidcourseproject.ui.livings_and_bookings.LivingsAndBookingsViewModel;

import java.util.List;

public class LivingsFragment extends Fragment {

    private LivingsAndBookingsViewModel livingsAndBookingsViewModel;
    private FragmentLivingsBinding binding;
    private LivingsAdapter adapter;
    public static AppDatabase db;
    private ClientsViewModel clientsViewModel;
    private InputValidation inputValidation;
    private EditText etMiniBar;
    private EditText etClothesWashing;
    private EditText etTelephone;
    private EditText etIntercityTelephone;
    private EditText etFood;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        livingsAndBookingsViewModel = new ViewModelProvider(this).get(LivingsAndBookingsViewModel.class);
        binding = FragmentLivingsBinding.inflate(inflater, container, false);
        inputValidation = new InputValidation();

        db = AppDatabase.getDataBase(getContext());
        List<LivingRoom> list = db.dao().getAllLivings();
        adapter = new LivingsAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.livingsList.setLayoutManager(layoutManager);
        binding.livingsList.setAdapter(adapter);

        binding.livingsList.addItemDecoration(new DividerItemDecoration(getContext(),
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
                        if (result.equals("Livings")) {
                            int clientId = bundle.getInt("clientId");
                            List<LivingRoom> list = db.dao().getAllLivingsByClientId(clientId);
                            adapter = new LivingsAdapter(list);
                            binding.livingsList.setAdapter(adapter);
                        }
                    }
                });

        binding.goToClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LivingRoom living = adapter.getSelected();
                if(living != null) {
                    Bundle result = new Bundle();
                    result.putInt("clientId", living.client_id);
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
                LivingRoom living = adapter.getSelected();
                if (living != null) {
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
                            AdditionalServicesRoom additionalService = db.dao().getAdditionalServiceByLivingId(living.living_id);
                            db.dao().deleteAdditionalService(additionalService);
                            db.dao().deleteLiving(living);
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

        binding.showAdditionalServicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LivingRoom living = adapter.getSelected();
                if(living != null) {
                    // clienteate an alert builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Name");

                    // set the custom layout
                    final View customLayout = getLayoutInflater().inflate(R.layout.edit_additional_services,
                            null);
                    builder.setView(customLayout);

                    // add a button
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(inputValidation.isRowConsistsOfNumbers(etMiniBar.getText().toString()) &&
                                    inputValidation.isRowConsistsOfNumbers(etClothesWashing.getText().toString()) &&
                                    inputValidation.isRowConsistsOfNumbers(etTelephone.getText().toString()) &&
                                    inputValidation.isRowConsistsOfNumbers(etIntercityTelephone.getText().toString()) &&
                                    inputValidation.isRowConsistsOfNumbers(etFood.getText().toString())) {
                                if(Integer.valueOf(etMiniBar.getText().toString()) >= 0 &&
                                        Integer.valueOf(etClothesWashing.getText().toString()) >= 0 &&
                                        Integer.valueOf(etTelephone.getText().toString()) >= 0 &&
                                        Integer.valueOf(etIntercityTelephone.getText().toString()) >= 0 &&
                                        Integer.valueOf(etFood.getText().toString()) >= 0) {
                                    AdditionalServicesRoom additionalService = db.dao().getAdditionalServiceByLivingId(living.living_id);
                                    additionalService.mini_bar = Integer.valueOf(etMiniBar.getText().toString());
                                    additionalService.clothes_washing = Integer.valueOf(etClothesWashing.getText().toString());
                                    additionalService.telephone = Integer.valueOf(etTelephone.getText().toString());
                                    additionalService.intercity_telephone = Integer.valueOf(etIntercityTelephone.getText().toString());
                                    additionalService.food = Integer.valueOf(etFood.getText().toString());

                                    db.dao().updateAdditionalService(additionalService);
                                    showLongToastWithText(getContext(), "Запись успешно обновлена");
                                }
                                else {
                                    showLongToastWithText(getContext(), "Изменения отменены: некорректно введённые данные");
                                }
                            }
                            else {
                                showLongToastWithText(getContext(), "Изменения отменены: все поля обязательны к заполнению");
                            }

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

                    etMiniBar = customLayout.findViewById(R.id.etEditMiniBar);
                    etClothesWashing = customLayout.findViewById(R.id.etEditClothesWashing);
                    etTelephone = customLayout.findViewById(R.id.etTelephone);
                    etIntercityTelephone = customLayout.findViewById(R.id.etEditIntercityTelephone);
                    etFood = customLayout.findViewById(R.id.etEditFood);
                }
                else {
                    Toast.makeText(getContext(), "Запись не выбрана", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
