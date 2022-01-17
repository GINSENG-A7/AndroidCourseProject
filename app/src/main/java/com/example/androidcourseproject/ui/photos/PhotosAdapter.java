package com.example.androidcourseproject.ui.photos;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidcourseproject.PictureHandler;
import com.example.androidcourseproject.databinding.CustomPhotosListItemsLayoutBinding;
import com.example.androidcourseproject.databinding.FragmentPhotosBinding;
import com.example.androidcourseproject.room.ApartmentRoom;
import com.example.androidcourseproject.room.PhotoRoom;
import com.example.androidcourseproject.ui.Actionable;

import java.util.List;

/**
 * Photos RecyclerView handler
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> implements Actionable{
    public static List<PhotoRoom> photos;
    public static List<ApartmentRoom> apartments;
    public static int checkedPosition = -1;
    public static int preCheckedPosition = -2;


    public PhotosAdapter(List<PhotoRoom> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomPhotosListItemsLayoutBinding binding = CustomPhotosListItemsLayoutBinding.inflate(inflater, parent, false);

        return new PhotosAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosAdapter.ViewHolder holder, int position) {
        holder.bind(photos.get(position), this);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    /**
     * Allows to set chosen item in Photos RecyclerView
     * @param position
     */
    @Override
    public void updateItem(int position) {
        if(checkedPosition != -1 && checkedPosition != position) {
            notifyItemChanged(checkedPosition);
        }
        checkedPosition = position;
    }

    public void setPhotos(List<PhotoRoom> photos) {
        PhotosAdapter.photos = photos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CustomPhotosListItemsLayoutBinding binding;
        private PictureHandler pictureHandler = new PictureHandler();

        public ViewHolder(CustomPhotosListItemsLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * fills data fields parameters if photo entry is selected and mark selected entry with color
         * @param photo
         * @param actionable
         */
        public void bind(PhotoRoom photo, Actionable actionable) {
            binding.imageView.setImageBitmap(pictureHandler.ConvertPathToBitmap(photo.path));

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
     * returns selected item in Photos RecyclerView
     * */
    public PhotoRoom getSelected() {
        if (checkedPosition != -1) {
            return photos.get(checkedPosition);
        }
        return null;
    }
}
