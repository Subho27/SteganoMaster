package com.example.steganomaster.ui.hide;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.steganomaster.HomeActivity;
import com.example.steganomaster.R;
import com.example.steganomaster.databinding.FragmentHideBinding;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class HideFragment extends Fragment {

    private FragmentHideBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHideBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final String[] secret_type_array = getResources().getStringArray(R.array.secret_type_array);


//        Secret Message Choose
        final Spinner secret_type_spinner = binding.secretTypeSpinner;
        ArrayAdapter<CharSequence> secretAdapter = ArrayAdapter.createFromResource(
                requireActivity(),
                R.array.secret_type_array,
                android.R.layout.simple_spinner_item
        );
        secretAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secret_type_spinner.setAdapter(secretAdapter);

        secret_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), secret_type_array[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), secret_type_array[0], Toast.LENGTH_SHORT).show();
            }
        });


//        Secret File upload
        final MaterialButton secretFileUpload = binding.secretUpload;
        secretFileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

//        Target File Choose
        final Spinner target_type_spinner = binding.targetTypeSpinner;
        ArrayAdapter<CharSequence> targetAdapter = ArrayAdapter.createFromResource(
                requireActivity(),
                R.array.secret_type_array,
                android.R.layout.simple_spinner_item
        );
        targetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        target_type_spinner.setAdapter(targetAdapter);

        target_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), secret_type_array[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), secret_type_array[0], Toast.LENGTH_SHORT).show();
            }
        });


//        Choose Encryption method
        RadioButton publicKeyRadio = binding.publicKey;
        LinearLayout searchPublicKey = binding.usePublicKey;
        publicKeyRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(publicKeyRadio.isChecked()) {
                    if(searchPublicKey.getVisibility() != View.VISIBLE) {
                        searchPublicKey.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    if(searchPublicKey.getVisibility() != View.GONE) {
                        searchPublicKey.setVisibility(View.GONE);
                    }
                }
            }
        });


//        No secret file is chosen yet
        LinearLayout importantSection = binding.important;
        TextView secretUploadName = binding.secretUploadName;
        secretUploadName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != "No File Selected") {
                    if(importantSection.getVisibility() != View.VISIBLE)
                        importantSection.setVisibility(View.VISIBLE);
                }
                else {
                    if(importantSection.getVisibility() != View.GONE)
                        importantSection.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Handle exception
            Toast.makeText(getContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }
}