package com.chareun410.miagenda.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

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

        checkIfLogged();
    }

    private void onLoginClick() {
        String username = binding.editTextUsuarioLogin.getText().toString();
        String password = binding.editTextPasswordLogin.getText().toString();

        boolean isValid = loginInteractor.validateCredentials(username, password);

        if(isValid) {
            SharedPreferences preferencias = getSharedPreferences("agenda", Context.MODE_PRIVATE);
            preferencias.edit().putString("auth","OK").apply();
            goToContactsActivity();
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }

    }

    private void goToContactsActivity() {
        Intent i = new Intent(getApplicationContext(), ContactsActivity.class);
        startActivity(i);
    }

    private void checkIfLogged() {
        SharedPreferences preferencias = getSharedPreferences("agenda", Context.MODE_PRIVATE);
        String auth = preferencias.getString("auth", "NO");
        if (auth.equals("OK")) {
            goToContactsActivity();
        }
    }
}