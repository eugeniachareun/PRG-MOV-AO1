package com.chareun410.miagenda.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chareun410.miagenda.data.ContactsRepository;
import com.chareun410.miagenda.databinding.ActivityContactsBinding;
import com.chareun410.miagenda.domain.Contact;
import com.chareun410.miagenda.interactor.ContactsInteractor;

import java.util.Collections;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private ActivityContactsBinding binding;
    private ContactsInteractor contactsInteractor;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View's Binding
        binding = ActivityContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain Contact list
        List<Contact> contacts = ContactsRepository.getList();

        // RecyclerView
        recyclerView = binding.contactsRecyclerView;
        recyclerView.setAdapter(new ContactAdapter(this, contacts));

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Contacts Interactor
        contactsInteractor = new ContactsInteractor(this);

        // Search Button
        ImageButton searchButton = binding.searchButton;
        searchButton.setOnClickListener(view -> onSearchClick());

    }

    private void onSearchClick() {
        String searchText = binding.searchText.getText().toString();
        contactsInteractor.searchContact(searchText);

    }
}