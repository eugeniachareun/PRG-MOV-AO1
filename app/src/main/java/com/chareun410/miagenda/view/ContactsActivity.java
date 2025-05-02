package com.chareun410.miagenda.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chareun410.miagenda.data.ContactsRepository;
import com.chareun410.miagenda.databinding.ActivityContactsBinding;
import com.chareun410.miagenda.domain.Contact;

public class ContactsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String LOG_TAG = ContactsActivity.class.getSimpleName();
    private ActivityContactsBinding binding;
    private ContactAdapter contactAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View's Binding
        binding = ActivityContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // RecyclerView
        recyclerView = binding.contactsRecyclerView;
        contactAdapter = getContactAdapter();
        recyclerView.setAdapter(contactAdapter);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // SearchView
        SearchView searchView = binding.searchView;
        searchView.setOnQueryTextListener(this);

    }

    private ContactAdapter getContactAdapter() {
        if(contactAdapter == null) {
            contactAdapter = new ContactAdapter(this, ContactsRepository.getList());
        }
        return contactAdapter;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        getContactAdapter().buscar(s);
        return false;
    }

    public void onClickAddButton(View view) {
        Intent i = new Intent(getApplicationContext(), FormActivity.class);
        Contact contact = null;
        i.putExtra("contact", contact);
        i.putExtra("title", "Agregar");
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume - refresh contact list");
        getContactAdapter().contactsList = ContactsRepository.getList();
        recyclerView.setAdapter(getContactAdapter());
        binding.searchView.onActionViewCollapsed();
    }
}