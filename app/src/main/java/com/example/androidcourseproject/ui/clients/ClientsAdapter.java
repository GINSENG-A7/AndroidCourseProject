package com.example.androidcourseproject.ui.clients;

import static java.security.AccessController.getContext;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.CustomClientsListItemLayoutBinding;
import com.example.androidcourseproject.room.ClientRoom;

import java.util.List;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ViewHolder> {
    public static List<ClientRoom> clients;
    public static int checkedPosition = -1;
    public static int preCheckedPosition = -2;


    public ClientsAdapter(List<ClientRoom> clients) {
        this.clients = clients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomClientsListItemLayoutBinding binding = CustomClientsListItemLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(clients.get(position));
//        holder.binding.getRoot().setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CustomClientsListItemLayoutBinding binding;

        public ViewHolder(CustomClientsListItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ClientRoom client) {
            binding.tvName.setText(client.name);
            binding.tvSurname.setText(client.surname);
            binding.tvBirthday.setText(client.birthday);
            binding.tvPassportNumber.setText(String.valueOf(client.passport_number));
            binding.tvPassportSeries.setText(String.valueOf(client.passport_series));
            binding.tvPatronymicLabel.setText(client.patronymic);
            binding.tvTelephone.setText(client.telephone);

            if(checkedPosition == -1) {
                binding.getRoot().setBackgroundColor(Color.WHITE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    if (preCheckedPosition != getAdapterPosition()) {
                        binding.getRoot().setBackgroundColor(Color.YELLOW);
                    }
                }
                else {
                    binding.getRoot().setBackgroundColor(Color.WHITE);
                }
            }
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    binding.getRoot().setBackgroundColor(Color.YELLOW);
                    if (checkedPosition != getAdapterPosition()) {
                        Log.d("zxc", "Запись не выбрана");
                        if (preCheckedPosition != checkedPosition) {
                            preCheckedPosition = checkedPosition;
                        }
                        checkedPosition = getAdapterPosition();
                    }
                }

            });
        }
    }

    public ClientRoom getSelected() {
        if (checkedPosition != -1) {
            return clients.get(checkedPosition);
        }
        return null;
    }

}
