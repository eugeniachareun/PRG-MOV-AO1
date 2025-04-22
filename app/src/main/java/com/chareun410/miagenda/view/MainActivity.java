package com.chareun410.miagenda.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.chareun410.miagenda.databinding.ActivityMainBinding;
import com.chareun410.miagenda.interactor.LoginInteractor;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LoginInteractor loginInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View's Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Login Interactor
        loginInteractor = new LoginInteractor(this);

        // Login Button
        Button loginButton = binding.loginButton;
        loginButton.setOnClickListener(view -> onLoginClick());
    }

    private void onLoginClick() {
        String username = binding.editTextUsuarioLogin.getText().toString();
        String password = binding.editTextPasswordLogin.getText().toString();

        boolean isValid = loginInteractor.validateCredentials(username, password);

        //TODO descomentar esto, es solo para pruebas
        //if(isValid) {
            Intent i = new Intent(getApplicationContext(), ContactsActivity.class);
            startActivity(i);
        //} else {
          //  Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        //}

    }
}