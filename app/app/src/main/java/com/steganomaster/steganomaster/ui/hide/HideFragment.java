package com.steganomaster.steganomaster.ui.hide;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.steganomaster.steganomaster.R;
import com.steganomaster.steganomaster.databinding.FragmentHideBinding;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.text.DecimalFormat;

public class HideFragment extends Fragment {

    private FragmentHideBinding binding;
    String secretFileType = "text", targetFileType ="text";
    Uri secretFile = null, targetFile = null;
    double secretFileSizeInMB = 0;

    ActivityResultLauncher<Intent> getSecretFileUri = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    assert result.getData() != null;
                    secretFile = result.getData().getData();
                    if(secretFile != null) {
                        secretFileSizeInMB = getFileSizeFromUri(secretFile);
                        TextView secretFileName = binding.secretUploadName;
                        secretFileName.setText(getFileNameFromUri(secretFile));
                    }
                }
            });

    ActivityResultLauncher<Intent> getTargetFileUri = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    assert result.getData() != null;
                    targetFile = result.getData().getData();
                    if (targetFile != null) {
                        Double targetFileSizeInMB = getFileSizeFromUri(targetFile);
                        if(targetFileSizeInMB > secretFileSizeInMB) {
                            TextView targetFileName = binding.targetUploadName;
                            targetFileName.setText(getFileNameFromUri(targetFile));
                        }
                        else {
                            Toast.makeText(getContext(), "Please, upload file of size greater than " + String.valueOf(secretFileSizeInMB), Toast.LENGTH_SHORT).show();
                            targetFile = null;
                        }
                    }
                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHideBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final String[] secret_type_array = getResources().getStringArray(R.array.secret_type_array);

        final Spinner secret_type_spinner = binding.secretTypeSpinner;
        final MaterialButton secretFileUpload = binding.secretUpload;
        final Spinner target_type_spinner = binding.targetTypeSpinner;
        final MaterialButton targetFileUpload = binding.targetUpload;
        RadioButton publicKeyRadio = binding.publicKey;
        LinearLayout searchPublicKey = binding.usePublicKey;
        LinearLayout importantSection = binding.important;
        TextView secretUploadName = binding.secretUploadName;
        TextView importantText = binding.importantMessage;

//        Secret Message Choose
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
                secretFileType = secret_type_array[position].toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                secretFileType = secret_type_array[0].toLowerCase();
            }
        });


//        Secret File upload
        secretFileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSecretFileChooser();
            }
        });

//        Target File Choose
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
                targetFileType = secret_type_array[position].toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                targetFileType = secret_type_array[0].toLowerCase();
            }
        });


//        Target File upload
        targetFileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(secretUploadName.getText().toString().equals("No File Selected")) {
                    Toast.makeText(getContext(), "Please, upload secret file first.", Toast.LENGTH_SHORT).show();
                }
                else {
                    showTargetFileChooser();
                }
            }
        });


//        Choose Encryption method
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
        secretUploadName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("No File Selected")) {
                    if(importantSection.getVisibility() != View.VISIBLE) {
                        importantText.setText("Target file should have size greater than " + secretFileSizeInMB + " MB.");
                        importantSection.setVisibility(View.VISIBLE);
                    }
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

    private void showSecretFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(secretFileType + "/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            getSecretFileUri.launch(Intent.createChooser(intent, "Select Secret File to Upload"));
        } catch (android.content.ActivityNotFoundException ex) {
            // Handle exception
            Toast.makeText(getContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showTargetFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(targetFileType + "/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            getTargetFileUri.launch(Intent.createChooser(intent, "Select Target File to Upload"));
        } catch (android.content.ActivityNotFoundException ex) {
            // Handle exception
            Toast.makeText(getContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        ContentResolver contentResolver = requireContext().getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

            fileName = cursor.getString(nameIndex);
            cursor.close();
        }
        if (fileName == null) {
            String path = uri.getPath();
            if (path != null) {
                fileName = new File(path).getName();
            }
        }
        return fileName;
    }

    private Double getFileSizeFromUri(Uri uri) {
        Double fileSize = 0.0;
        ContentResolver contentResolver = requireContext().getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            fileSize = cursor.getDouble(sizeIndex);
            cursor.close();
        }
        fileSize = fileSize / (1000 * 1000);
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        fileSize = Double.valueOf(decimalFormat.format(fileSize));
        return fileSize;
    }
}