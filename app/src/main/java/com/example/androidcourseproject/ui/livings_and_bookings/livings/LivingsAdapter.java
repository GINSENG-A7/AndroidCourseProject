package com.example.androidcourseproject.ui.livings_and_bookings.livings;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcourseproject.databinding.CustomLivingsAndBookingsListItemLayoutBinding;
import com.example.androidcourseproject.room.ApartmentRoom;
import com.example.androidcourseproject.room.BookingRoom;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.room.LivingRoom;
import com.example.androidcourseproject.ui.Actionable;
import com.example.androidcourseproject.ui.livings_and_bookings.bookings.BookingsAdapter;

import java.util.List;

/**
 * Livings RecyclerView handler
 */
public class LivingsAdapter extends RecyclerView.Adapter<LivingsAdapter.ViewHolder> implements Actionable {
    public static List<LivingRoom> livings;
    public static List<ClientRoom> clients;
    public static List<ApartmentRoom> apartments;
    public static int checkedPosition = -1;
    public static int preCheckedPosition = -2;


    public LivingsAdapter(List<LivingRoom> livings) {
        this.livings = livings;
    }

    @NonNull
    @Override
    public LivingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomLivingsAndBookingsListItemLayoutBinding binding = CustomLivingsAndBookingsListItemLayoutBinding.inflate(inflater, parent, false);
        return new LivingsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LivingsAdapter.ViewHolder holder, int position) {
        holder.bind(livings.get(position), this);
//        holder.binding.getRoot().setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public int getItemCount() {
        return livings.size();
    }

    /**
     * Allows to set chosen item in Livings RecyclerView
     * @param position
     */
    @Override
    public void updateItem(int position) {
        if(checkedPosition != -1 && checkedPosition != position) {
            notifyItemChanged(checkedPosition);
        }
        checkedPosition = position;
    }

    public void setLivings(List<LivingRoom> livings) {
        LivingsAdapter.livings = livings;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CustomLivingsAndBookingsListItemLayoutBinding binding;

        public ViewHolder(CustomLivingsAndBookingsListItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * fills data fields parameters if living entry is selected and mark selected entry with color
         * @param living
         * @param actionable
         */
        public void bind(LivingRoom living, Actionable actionable) {
            ClientRoom client = LivingsFragment.db.dao().getClientById(living.client_id);
            ApartmentRoom apartment = LivingsFragment.db.dao().getApartmentById(living.apartment_id);;

            binding.tvName.setText(client.name);
            binding.tvSurname.setText(client.surname);
            binding.tvPatronymic.setText(client.patronymic);
            binding.tvSettling.setText(String.valueOf(living.settling));
            binding.tvEviction.setText(String.valueOf(living.eviction));
            binding.tvNumber.setText(String.valueOf(apartment.number));
            binding.tvValueOfGuests.setText(String.valueOf(living.value_of_guests));
            binding.tvValueOfKids.setText(String.valueOf(living.value_of_kids));

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
                        Log.d("zxc", "???????????? ???? ??????????????");
                        if (preCheckedPosition != checkedPosition) {
                            preCheckedPosition = checkedPosition;
                        }
                        checkedPosition = getAdapterPosition();
                    }
                }

            });
        }
    }

    /**
     * returns selected item in Livings RecyclerView
     * @return
     */
    public LivingRoom getSelected() {
        if (checkedPosition != -1) {
            return livings.get(checkedPosition);
        }
        return null;
    }
}
