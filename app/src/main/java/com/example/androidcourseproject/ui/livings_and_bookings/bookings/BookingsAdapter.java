package com.example.androidcourseproject.ui.livings_and_bookings.bookings;

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
import com.example.androidcourseproject.ui.Actionable;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> implements Actionable {
    public static List<BookingRoom> bookings;
    public static List<ClientRoom> clients;
    public static List<ApartmentRoom> apartments;
    public static int checkedPosition = -1;
    public static int preCheckedPosition = -2;


    public BookingsAdapter(List<BookingRoom> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomLivingsAndBookingsListItemLayoutBinding binding = CustomLivingsAndBookingsListItemLayoutBinding.inflate(inflater, parent, false);
        return new BookingsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsAdapter.ViewHolder holder, int position) {
        holder.bind(bookings.get(position), this);
//        holder.binding.getRoot().setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    @Override
    public void updateItem(int position) {
        if(checkedPosition != -1 && checkedPosition != position) {
            notifyItemChanged(checkedPosition);
        }
        checkedPosition = position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CustomLivingsAndBookingsListItemLayoutBinding binding;

        public ViewHolder(CustomLivingsAndBookingsListItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(BookingRoom booking, Actionable actionable) {
            ClientRoom client = BookingsFragment.db.dao().getClientById(booking.client_id).get(0);
            ApartmentRoom apartment = BookingsFragment.db.dao().getApartmentById(booking.apartment_id).get(0);;

            binding.tvName.setText(client.name);
            binding.tvSurname.setText(client.surname);
            binding.tvPatronymic.setText(client.patronymic);
            binding.tvSettling.setText(booking.settling);
            binding.tvEviction.setText(booking.eviction);
            binding.tvNumber.setText(String.valueOf(apartment.number));
            binding.tvValueOfGuests.setText(String.valueOf(booking.value_of_guests));
            binding.tvValueOfKids.setText(String.valueOf(booking.value_of_kids));

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
    }

    public BookingRoom getSelected() {
        if (checkedPosition != -1) {
            return bookings.get(checkedPosition);
        }
        return null;
    }
}
