package com.chareun410.miagenda.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
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

        String title = getIntent().getStringExtra("title");
        TextView formTitle = binding.formTitle;
        formTitle.setText(title);

        // If editing, fill fields with previous data
        Contact contact = (Contact) getIntent().getSerializableExtra("contact");
        loadPreviousData(contact);

        doneButton.setOnClickListener(this::onDoneClick);
    }

    private void onDoneClick(View view) {
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

        Contact contact = (Contact) getIntent().getSerializableExtra("contact");
        getContactAdapter().save(name, lastName, phone, address, gender, contact, view);

        // Eliminate current activity and return to previous activity (ContactsActivity)
        finish();
    }

    private void loadPreviousData(Contact contact) {
        if (contact == null) {
            return;
        }
        binding.formInputName.setText(contact.getName());
        binding.formInputLastName.setText(contact.getLastName());
        binding.formInputPhone.setText(contact.getPhone());
        binding.formInputAddress.setText(contact.getAddress());

        Gender gender = contact.getGender();
        if (gender == Gender.FEMALE) {
            binding.genderFemale.setChecked(true);
        } else {
            binding.genderMale.setChecked(true);
        }
    }

    private ContactAdapter getContactAdapter() {
        if(contactAdapter == null) {
            contactAdapter = new ContactAdapter(this, ContactsRepository.getList());
        }
        return contactAdapter;
    }
}