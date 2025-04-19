package com.chareun410.miagenda.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.chareun410.miagenda.databinding.ActivityContactsBinding;

public class ContactsActivity extends AppCompatActivity {

    private ActivityContactsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View's Binding
        binding = ActivityContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}