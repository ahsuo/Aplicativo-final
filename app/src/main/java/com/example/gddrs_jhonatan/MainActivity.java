package com.example.gddrs_jhonatan;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        EditText emailEditText = findViewById(R.id.editTextEmail);
        EditText passwordEditText = findViewById(R.id.editTextSenha);
        Button registerButton = findViewById(R.id.buttonCrieMinhaConta);
        Button buttonAcessar = findViewById(R.id.buttonAcessar);

        buttonAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para o botão "Acessar"
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                signInWithEmail(email,password);
                // Aqui você pode adicionar a lógica de login
                //Intent intent = new Intent(MainActivity.this, tela_principal.class);
                //startActivity(intent);
            }
        });

        // Botão "Crie minha conta"
        Button buttonCrieMinhaConta = findViewById(R.id.buttonCrieMinhaConta);
        buttonCrieMinhaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para o botão "Crie minha conta"
                // Aqui você pode adicionar a lógica de cadastro
                Intent intent = new Intent(MainActivity.this, telaCadastro.class);
                startActivity(intent);
            }
        });
    }
    private void signInWithEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        navigateToNextActivity(user);
                    } else {
                        Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void navigateToNextActivity(FirebaseUser user) {
        if (user != null) {
            String userType = getIntent().getStringExtra("userType");
            /*if ("help_seeker".equals(userType)) {
                Intent intent = new Intent(LoginActivity.this, HelpSeekerActivity.class);
                startActivity(intent);
            } else if ("helper".equals(userType)) {
                Intent intent = new Intent(LoginActivity.this, HelperActivity.class);
                startActivity(intent);
            }*/
            Intent intent = new Intent(MainActivity.this, tela_principal.class);
            startActivity(intent);
        }
    }
}


