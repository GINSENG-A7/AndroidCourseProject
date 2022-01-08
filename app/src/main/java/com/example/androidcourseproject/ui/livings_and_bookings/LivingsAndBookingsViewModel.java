package com.example.androidcourseproject.ui.livings_and_bookings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LivingsAndBookingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LivingsAndBookingsViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}