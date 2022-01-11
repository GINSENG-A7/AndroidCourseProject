package com.example.androidcourseproject.ui.clients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClientsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ClientsViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is dashboard fragment");
    }

    public void setData(String text) {
        mText.setValue(text);
    }

    public LiveData<String> getText() {
        return mText;
    }
}