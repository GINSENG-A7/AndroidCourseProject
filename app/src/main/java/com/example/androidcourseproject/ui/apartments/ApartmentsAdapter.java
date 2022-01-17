package com.example.androidcourseproject.ui.apartments;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcourseproject.databinding.CustomApartmentsListItemLayoutBinding;
import com.example.androidcourseproject.room.ApartmentRoom;
import com.example.androidcourseproject.room.ClientRoom;
import com.example.androidcourseproject.ui.Actionable;

import java.util.List;

/**
 * Apartments RecyclerView handler
 */
public class ApartmentsAdapter extends RecyclerView.Adapter<ApartmentsAdapter.ViewHolder> implements Actionable {

    public static List<ApartmentRoom> apartments;
    public static int checkedPosition = -1;
    public static int preCheckedPosition = -2;

    public ApartmentsAdapter(List<ApartmentRoom> apartments) {
        this.apartments = apartments;
    }

    @NonNull
    @Override
    public ApartmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomApartmentsListItemLayoutBinding binding = CustomApartmentsListItemLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ApartmentsAdapter.ViewHolder holder, int position) {
        holder.bind(apartments.get(position), this);
    }

    @Override
    public int getItemCount() {
        return apartments.size();
    }

    /**
     * Allows to set chosen item in Apartments RecyclerView
     * @param position
     */
    @Override
    public void updateItem(int position) {
        if(checkedPosition != -1 && checkedPosition != position) {
            notifyItemChanged(checkedPosition);
        }
        checkedPosition = position;
    }

    public void setApartments(List<ApartmentRoom> apartments) {
        ApartmentsAdapter.apartments = apartments;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CustomApartmentsListItemLayoutBinding binding;
        public ViewHolder(CustomApartmentsListItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * fills data fields parameters if apartment entry is selected and mark selected entry with color
         * @param apartment
         * @param actionable
         */
        public void bind(ApartmentRoom apartment, Actionable actionable) {
            binding.tvNumber.setText(String.valueOf(apartment.number));
            binding.tvType.setText(apartment.type);
            binding.tvPrice.setText(String.valueOf(apartment.price));

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

    /**
     * returns selected item in Apartments RecyclerView
     * */
    public ApartmentRoom getSelected() {
        if (checkedPosition != -1) {
            return apartments.get(checkedPosition);
        }
        return null;
    }
}
