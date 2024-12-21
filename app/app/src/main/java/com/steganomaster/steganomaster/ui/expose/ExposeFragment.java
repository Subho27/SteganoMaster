package com.steganomaster.steganomaster.ui.expose;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.steganomaster.steganomaster.databinding.FragmentExposeBinding;
import com.google.android.material.button.MaterialButton;

import java.io.File;

public class ExposeFragment extends Fragment {

    private FragmentExposeBinding binding;
    Uri embeddedFile = null;

    ActivityResultLauncher<Intent> getEmbeddedFileUri = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    assert result.getData() != null;
                    embeddedFile = result.getData().getData();
                    if(embeddedFile != null) {
                        TextView embeddedFileName = binding.embeddedUploadName;
                        embeddedFileName.setText(getFileNameFromUri(embeddedFile));
                    }
                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExposeViewModel exposeViewModel =
                new ViewModelProvider(this).get(ExposeViewModel.class);

        binding = FragmentExposeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final MaterialButton embeddedFileUpload = binding.embeddedUpload;
        RadioButton decryptPublicKeyRadio = binding.decryptPublicKey;
        LinearLayout decryptSearchPublicKey = binding.decryptUsePublicKey;

//        Secret File upload
        embeddedFileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmbeddedFileChooser();
            }
        });

//        Choose Decryption method
        decryptPublicKeyRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(decryptPublicKeyRadio.isChecked()) {
                    if(decryptSearchPublicKey.getVisibility() != View.VISIBLE) {
                        decryptSearchPublicKey.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    if(decryptSearchPublicKey.getVisibility() != View.GONE) {
                        decryptSearchPublicKey.setVisibility(View.GONE);
                    }
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showEmbeddedFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        String[] allowedMIMETypes = {"text/*", "image/*", "audio/*", "video/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, allowedMIMETypes);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            getEmbeddedFileUri.launch(Intent.createChooser(intent, "Select Embedded File to Upload"));
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
}