package com.chareun410.miagenda.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chareun410.miagenda.data.sqlite.ContactsRepository;
import com.chareun410.miagenda.databinding.ActivityContactsBinding;
import com.chareun410.miagenda.domain.Contact;

public class ContactsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String LOG_TAG = ContactsActivity.class.getSimpleName();
    private ActivityContactsBinding binding;
    private ContactAdapter contactAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ContactsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = new ContactsRepository(this);

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
        // Mostrar/ocultar boton de cerrar sesión al abrir/cerrar la búsqueda
        searchView.setOnSearchClickListener(view -> onSearchClick());
        searchView.setOnCloseListener(this::onSearchClose);


        // Close Button
        ImageButton closeButton = binding.closeButton;
        closeButton.setOnClickListener(view -> onCloseClick());

    }

    private ContactAdapter getContactAdapter() {
        if(contactAdapter == null) {
            contactAdapter = new ContactAdapter(this, repository);
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
        getContactAdapter().contactsList = repository.getAll();
        recyclerView.setAdapter(getContactAdapter());
        binding.searchView.onActionViewCollapsed();
    }

    private void onCloseClick() {
        SharedPreferences preferencias = getSharedPreferences("agenda", Context.MODE_PRIVATE);
        preferencias.edit().putString("auth","NO").apply();
        goToLoginActivity();
    }

    private void goToLoginActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    private void onSearchClick() {
        ImageButton closeButton = binding.closeButton;
        closeButton.setVisibility(View.GONE);
    }

    private boolean onSearchClose() {
        ImageButton closeButton = binding.closeButton;
        closeButton.setVisibility(View.VISIBLE);
        return  false;
    }


}