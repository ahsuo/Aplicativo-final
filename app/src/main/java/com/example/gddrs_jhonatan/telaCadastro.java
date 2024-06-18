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

public class telaCadastro extends AppCompatActivity {

    private EditText editTextEmail2;
    private EditText editTextSenha2;
    private Button buttonCadastrar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        // Inicializando os componentes da UI
        editTextEmail2 = findViewById(R.id.editTextEmail2);
        editTextSenha2 = findViewById(R.id.editTextSenha2);
        buttonCadastrar = findViewById(R.id.buttonCadstrar);

        // Configurando o listener para o botão de cadastro
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail2.getText().toString().trim();
                String senha = editTextSenha2.getText().toString().trim();

                // Verifica se os campos estão preenchidos
                if (email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(telaCadastro.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Lógica de cadastro aqui (ex: salvar no banco de dados)
                    registerUser(email, senha);

                    Toast.makeText(telaCadastro.this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();

                    // Navega para a tela de cadastro do usuário (CadastroDetrito)
                    Intent intent = new Intent(telaCadastro.this, tela_principal.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(telaCadastro.this, "Registro bem-sucedido.", Toast.LENGTH_SHORT).show();
                        navigateToNextActivity(user);
                    } else {
                        Toast.makeText(telaCadastro.this, "Falha no registro.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void navigateToNextActivity(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(telaCadastro.this, tela_principal.class);
            startActivity(intent);
            finish();
        }
    }

}
