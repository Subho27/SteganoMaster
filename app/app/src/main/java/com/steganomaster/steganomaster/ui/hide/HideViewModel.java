package com.steganomaster.steganomaster.ui.hide;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HideViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HideViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is hide fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}