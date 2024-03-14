package com.example.steganomaster.ui.expose;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExposeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ExposeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is expose fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}