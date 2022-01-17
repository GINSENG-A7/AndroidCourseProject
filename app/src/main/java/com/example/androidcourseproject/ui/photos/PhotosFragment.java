package com.example.androidcourseproject.ui.photos;

import static android.app.Activity.RESULT_OK;

import static com.example.androidcourseproject.ui.MainActivity.showLongToastWithText;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidcourseproject.InputValidation;
import com.example.androidcourseproject.PictureHandler;
import com.example.androidcourseproject.R;
import com.example.androidcourseproject.databinding.FragmentLivingsBinding;
import com.example.androidcourseproject.databinding.FragmentPhotosBinding;
import com.example.androidcourseproject.room.ApartmentRoom;
import com.example.androidcourseproject.room.AppDatabase;
import com.example.androidcourseproject.room.LivingRoom;
import com.example.androidcourseproject.room.PhotoRoom;
import com.example.androidcourseproject.ui.MainActivity;
import com.example.androidcourseproject.ui.livings_and_bookings.LivingsAndBookingsViewModel;
import com.example.androidcourseproject.ui.livings_and_bookings.livings.LivingsAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotosFragment} factory method to
 * create an instance of this fragment.
 */
public class PhotosFragment extends Fragment {
    private FragmentPhotosBinding binding;
    private PhotosAdapter adapter;
    public static AppDatabase db;
    private static ApartmentRoom apartment;
    public static String localStorageFilesDirectory;
    private static PictureHandler pictureHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhotosBinding.inflate(inflater, container, false);

        db = AppDatabase.getDataBase(getContext());

        pictureHandler = new PictureHandler();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getSupportFragmentManager()
                .setFragmentResultListener("relatedApartmentKey", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                        int apartmentId = bundle.getInt("apartmentId");
                        apartment = db.dao().getApartmentById(apartmentId);
                        List<PhotoRoom> photosList = db.dao().getAllPhotosByApartmentId(apartment.apartment_id);
                        adapter = new PhotosAdapter(photosList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                                LinearLayoutManager.VERTICAL, false);
                        binding.photosList.setLayoutManager(layoutManager);
                        binding.photosList.setAdapter(adapter);

                        binding.photosList.addItemDecoration(new DividerItemDecoration(getContext(),
                                LinearLayoutManager.VERTICAL));

                        localStorageFilesDirectory = getContext().getFilesDir().getAbsolutePath() + "/";
                    }
                });

        binding.addPhotoButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_OK);
            showLongToastWithText(getContext(), "Запись успешно добавлена");
        });

        binding.deletePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoRoom photo = adapter.getSelected();
                if (photo != null) {
                    db.dao().deletePhoto(photo);
                    adapter.setPhotos(db.dao().getAllPhotosByApartmentId(apartment.apartment_id));
                    adapter.notifyDataSetChanged();
                    showLongToastWithText(getContext(), "Запись успешно удалена");
                }
                else {
                    Toast.makeText(getContext(), "Запись не выбрана", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                String filePath = pictureHandler.SaveImage(bitmap, localStorageFilesDirectory);

//                int localApartmentId = data.getIntExtra("apartmentIntentKey", 0);
                Log.d("maApartment", String.valueOf(apartment.apartment_id));

                PhotoRoom photo = new PhotoRoom();
                photo.path = filePath;
                photo.apartment_id = apartment.apartment_id;
                db.dao().insertPhoto(photo);

                List<PhotoRoom> photosList = db.dao().getAllPhotosByApartmentId(apartment.apartment_id);
                adapter.setPhotos(photosList);
                adapter.notifyDataSetChanged();

                Log.d("SaveImage", filePath);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("SaveImage", e.getMessage());
            }
        }
    }

}