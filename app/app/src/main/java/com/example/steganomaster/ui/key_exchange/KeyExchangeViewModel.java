package com.example.steganomaster.ui.key_exchange;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KeyExchangeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public KeyExchangeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is key exchange fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}