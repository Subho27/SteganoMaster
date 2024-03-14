package com.example.steganomaster.ui.expose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.steganomaster.databinding.FragmentExposeBinding;

public class ExposeFragment extends Fragment {

    private FragmentExposeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExposeViewModel exposeViewModel =
                new ViewModelProvider(this).get(ExposeViewModel.class);

        binding = FragmentExposeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textExpose;
        exposeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}