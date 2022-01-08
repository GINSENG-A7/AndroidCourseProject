package com.example.androidcourseproject.ui.apartments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ApartmentsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ApartmentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}