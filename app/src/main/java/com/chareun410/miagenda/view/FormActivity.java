package com.chareun410.miagenda.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.chareun410.miagenda.R;
import com.chareun410.miagenda.data.ContactsRepository;
import com.chareun410.miagenda.databinding.ActivityFormBinding;
import com.chareun410.miagenda.domain.Contact;
import com.chareun410.miagenda.domain.Gender;

public class FormActivity extends AppCompatActivity {

    private ActivityFormBinding binding;
    private boolean isEditing = false;

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
                //TODO migrar esta logica

                Contact contact;
                if (isEditing) {
                    //buscar por id y asignar
                    contact = new Contact();
                } else {
                    contact = new Contact();
                }

                String name = binding.formInputName.getText().toString();
                String lastName = binding.formInputLastName.getText().toString();
                String phone = binding.formInputPhone.getText().toString();
                String address = binding.formInputAddress.getText().toString();
                int genderId = binding.radioGroupGender.getCheckedRadioButtonId();

                Gender gender;
                if(genderId == R.id.genderFemale) {
                    gender = Gender.FEMALE;
                } else {
                    gender = Gender.MALE;
                }

                contact.setName(name);
                contact.setLastName(lastName);
                contact.setPhone(phone);
                contact.setAddress(address);
                contact.setGender(gender);

                ContactsRepository.getList().add(contact);

                //TODO notifyDataSetChanged
            }
        });
    }
}