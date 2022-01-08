package com.example.androidcourseproject.ui.clients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.FragmentClientsBinding;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.ui.MainActivity;

import java.util.List;

public class ClientsFragment extends Fragment {

    private ClientsViewModel clientsViewModel;
    private FragmentClientsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        clientsViewModel =
                new ViewModelProvider(this).get(ClientsViewModel.class);

        binding = FragmentClientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
        clientsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });

        return root;
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
        // находим список
        ListView lvClients = (ListView)binding.clientsList;

        // создаем адаптер
//        ListAdapter<String> adapter = new ListAdapter<String>(getContext(),
//                R.layout.custom_list_item_layout, listOfClientsRooms.get(1).client_id);

        // присваиваем адаптер списку
//        lvClients.setAdapter(adapter);
    }
}