package com.steganomaster.steganomaster.ui.key_exchange;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.steganomaster.steganomaster.databinding.FragmentKeyExchangeBinding;

public class KeyExchangeFragment extends Fragment {

    private FragmentKeyExchangeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        KeyExchangeViewModel keyExchangeViewModel =
                new ViewModelProvider(this).get(KeyExchangeViewModel.class);

        binding = FragmentKeyExchangeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textKeyExchange;
        keyExchangeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}