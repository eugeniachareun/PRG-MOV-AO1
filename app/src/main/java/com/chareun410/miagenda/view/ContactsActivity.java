package com.chareun410.miagenda.view;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chareun410.miagenda.data.ContactsRepository;
import com.chareun410.miagenda.databinding.ActivityContactsBinding;
import com.chareun410.miagenda.domain.Contact;
import com.chareun410.miagenda.interactor.ContactsInteractor;

import java.util.List;

public class ContactsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ActivityContactsBinding binding;
    private ContactsInteractor contactsInteractor;
    private ContactAdapter contactAdapter;
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
        contactAdapter = new ContactAdapter(this, contacts);
        recyclerView.setAdapter(contactAdapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Contacts Interactor
        contactsInteractor = new ContactsInteractor(this);

        // SearchView
        SearchView searchView = binding.searchView;
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        contactAdapter.buscar(s);
        return false;
    }
}