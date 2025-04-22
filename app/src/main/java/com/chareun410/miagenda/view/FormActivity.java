package com.chareun410.miagenda.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chareun410.miagenda.R;
import com.chareun410.miagenda.data.ContactsRepository;
import com.chareun410.miagenda.databinding.ActivityFormBinding;
import com.chareun410.miagenda.domain.Contact;
import com.chareun410.miagenda.domain.Gender;

public class FormActivity extends AppCompatActivity {

    private ActivityFormBinding binding;
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View's Binding
        binding = ActivityFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageButton doneButton = binding.doneButton;
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.formInputName.getText().toString();
                String phone = binding.formInputPhone.getText().toString();

                // Required fields validation
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(view.getContext(), "ERROR: ingrese nombre y teléfono", Toast.LENGTH_SHORT).show();
                    return;
                }

                String lastName = binding.formInputLastName.getText().toString();
                String address = binding.formInputAddress.getText().toString();
                int genderId = binding.radioGroupGender.getCheckedRadioButtonId();

                Gender gender;
                if(genderId == R.id.genderFemale) {
                    gender = Gender.FEMALE;
                } else {
                    gender = Gender.MALE;
                }

                Bundle extras = getIntent().getExtras();
                boolean isEditing = extras.getBoolean("isEditing");

                getContactAdapter().save(name, lastName, phone, address, gender, isEditing, view);

                // Eliminate Activity and return to previous activity (ContactsActivity)
                finish();
            }
        });
    }

    private ContactAdapter getContactAdapter() {
        if(contactAdapter == null) {
            contactAdapter = new ContactAdapter(this, ContactsRepository.getList());
        }
        return contactAdapter;
    }
}