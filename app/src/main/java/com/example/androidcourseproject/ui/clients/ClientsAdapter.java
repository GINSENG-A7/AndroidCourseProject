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
import com.example.androidcourseproject.ui.Actionable;

import java.util.List;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ViewHolder> implements Actionable {
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
        holder.bind(clients.get(position), this);
//        holder.binding.getRoot().setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    @Override
    public void updateItem(int position) {
        if(checkedPosition != -1 && checkedPosition != position) {
            notifyItemChanged(checkedPosition);
        }
        checkedPosition = position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CustomClientsListItemLayoutBinding binding;

        public ViewHolder(CustomClientsListItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ClientRoom client, Actionable actionable) {
            setDecorationSymbols();

            binding.tvName.setText(client.name);
            binding.tvSurname.setText(client.surname);
            binding.tvBirthday.setText(String.valueOf(client.birthday));
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
                    actionable.updateItem(getAdapterPosition());

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

        private void setDecorationSymbols() {
            binding.passportSeriesLabel.setText(binding.passportSeriesLabel.getText().toString() + ": ");
            binding.passportNumberLabel.setText(binding.passportNumberLabel.getText().toString() + ": ");
            binding.nameLabel.setText(binding.nameLabel.getText().toString() + ": ");
            binding.surnameLabel.setText(binding.surnameLabel.getText().toString() + ": ");
            binding.patronymicLabel.setText(binding.patronymicLabel.getText().toString() + ": ");
            binding.birthdayLabel.setText(binding.birthdayLabel.getText().toString() + ": ");
            binding.telephoneLabel.setText(binding.telephoneLabel.getText().toString() + ": ");
        }
    }

    public ClientRoom getSelected() {
        if (checkedPosition != -1) {
            return clients.get(checkedPosition);
        }
        return null;
    }
}

