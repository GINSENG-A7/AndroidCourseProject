package com.example.androidcourseproject.ui.apartments;

import static com.example.androidcourseproject.ui.MainActivity.showLongToastWithText;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.FragmentApartmentsBinding;
import com.example.androidcourseproject.room.ApartmentRoom;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.BookingRoom;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.room.PhotoRoom;
import com.example.androidcourseproject.ui.MainActivity;
import com.example.androidcourseproject.ui.clients.ClientsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApartmentsFragment extends Fragment {

    private ApartmentsViewModel apartmentsViewModel;
    private FragmentApartmentsBinding binding;
    private ApartmentsAdapter adapter;
    public static AppDatabase db;
    private TextView etNumber;
    private Spinner spinnerType;
    private TextView etPrice;
    public String[] apartmentTypesList = new String[] {"Люкс", "Полулюкс", "Одноместный", "Двуместный", "Трёхместный", "Четырёхместный", "Пятиместный"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        apartmentsViewModel =
                new ViewModelProvider(this).get(ApartmentsViewModel.class);

        binding = FragmentApartmentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        apartmentsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        db = AppDatabase.getDataBase(getContext());
        List<ApartmentRoom> list = db.dao().getAllApartments();
        adapter = new ApartmentsAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.apartmentsList.setLayoutManager(layoutManager);
        binding.apartmentsList.setAdapter(adapter);

        binding.apartmentsList.addItemDecoration(new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.showPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApartmentRoom apartment = adapter.getSelected();
                if(apartment != null) {
                    Bundle result = new Bundle();
                    result.putInt("apartmentId", apartment.apartment_id);
                    requireActivity().getSupportFragmentManager().setFragmentResult("relatedApartmentKey", result);
                    MainActivity.navigateToPhotos();
                }
                else {
                    Toast.makeText(getContext(), "Запись не выбрана", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.editApartmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApartmentRoom apartment = adapter.getSelected();
                if(apartment != null) {
                    // clienteate an alert builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Name");

                    // set the custom layout
                    final View customLayout = getLayoutInflater().inflate(R.layout.edit_apartment_layout,
                            null);
                    builder.setView(customLayout);

                    // add a button
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // send data from the AlertDialog to the Activity

                            if (spinnerType.getSelectedItem() != null &&
                                    !etNumber.getText().toString().equals("") &&
                                    !etPrice.getText().toString().equals("")) {

                                ApartmentRoom checkedApartment = db.dao().getApartmentByNumber(
                                        Integer.valueOf(etNumber.getText().toString()));

                                apartment.number = Integer.valueOf(etNumber.getText().toString());
                                apartment.type = spinnerType.getSelectedItem().toString();
                                apartment.price = Integer.valueOf(etPrice.getText().toString());

                                if(checkedApartment.number != apartment.number) {
                                    db.dao().updateApartment(apartment);

                                    adapter.setApartments(db.dao().getAllApartments());
                                    adapter.notifyDataSetChanged();

                                    showLongToastWithText(getContext(), "Запись успешно обновлена");
                                }
                                else {
                                    showLongToastWithText(getContext(), "Апартаменты с таким номером уже добавлены");
                                }
                            }
                            else {
                                showLongToastWithText(getContext(), "Все поля обязательны к заполнению");
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

                    //заполняем данные записи в соответствующие поля
                    etNumber = customLayout.findViewById(R.id.tvEditNumber);
                    spinnerType = customLayout.findViewById(R.id.spinnerType);
                    etPrice = customLayout.findViewById(R.id.tvEditPrice);

                    etNumber.setText(String.valueOf(apartment.number));
                    etPrice.setText(String.valueOf(apartment.price));

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, apartmentTypesList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerType.setAdapter(arrayAdapter);

                }
                else {
                    Toast.makeText(getContext(), "Запись не выбрана", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.deleteApartmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApartmentRoom apartment = adapter.getSelected();
                if(apartment != null) {
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
                            // send data from the AlertDialog to the Activity
                            List<PhotoRoom> apartmentPhotos = db.dao().getAllPhotosByApartmentId(apartment.apartment_id);
                            db.dao().deletePhotos(apartmentPhotos);
                            db.dao().deleteApartment(apartment);

                            adapter.setApartments(db.dao().getAllApartments());
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

        binding.addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clienteate an alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Name");

                // set the custom layout
                final View customLayout = getLayoutInflater().inflate(R.layout.edit_apartment_layout,
                        null);
                builder.setView(customLayout);

                // add a button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // send data from the AlertDialog to the Activity

                        if (spinnerType.getSelectedItem() != null &&
                                !etNumber.getText().toString().equals("") &&
                                !etPrice.getText().toString().equals("")) {

                            ApartmentRoom checkedApartment = db.dao().getApartmentByNumber(
                                    Integer.valueOf(etNumber.getText().toString()));

                            ApartmentRoom apartment = new ApartmentRoom(
                                    Integer.valueOf(etNumber.getText().toString()),
                                    spinnerType.getSelectedItem().toString(),
                                    Integer.valueOf(etPrice.getText().toString())
                            );

                            if(checkedApartment.number != apartment.number) {
                                db.dao().insertApartment(apartment);

                                adapter.setApartments(db.dao().getAllApartments());
                                adapter.notifyDataSetChanged();

                                showLongToastWithText(getContext(), "Запись успешно добавлена");
                            }
                            else {
                                showLongToastWithText(getContext(), "Апартаменты с таким номером уже добавлены");
                            }
                        }
                        else {
                            showLongToastWithText(getContext(), "Все поля обязательны к заполнению");
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

                //заполняем данные записи в соответствующие поля
                etNumber = customLayout.findViewById(R.id.tvEditNumber);
                spinnerType = customLayout.findViewById(R.id.spinnerType);
                etPrice = customLayout.findViewById(R.id.tvEditPrice);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, apartmentTypesList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(arrayAdapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}