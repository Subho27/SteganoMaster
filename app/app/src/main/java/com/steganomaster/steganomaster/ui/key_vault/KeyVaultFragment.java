package com.steganomaster.steganomaster.ui.key_vault;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.steganomaster.steganomaster.databinding.FragmentKeyExchangeBinding;
import com.steganomaster.steganomaster.databinding.FragmentKeyVaultBinding;

public class KeyVaultFragment extends Fragment {

    private FragmentKeyVaultBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        KeyVaultViewModel keyVaultViewModel =
                new ViewModelProvider(this).get(KeyVaultViewModel.class);

        binding = FragmentKeyVaultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textKeyVault;
        keyVaultViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}