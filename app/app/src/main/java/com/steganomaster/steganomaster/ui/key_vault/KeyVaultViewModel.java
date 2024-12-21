package com.steganomaster.steganomaster.ui.key_vault;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KeyVaultViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public KeyVaultViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is key vault fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}