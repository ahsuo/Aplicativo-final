package com.example.gddrs_jhonatan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroDetrito extends AppCompatActivity {

    private EditText editTextNomePessoa;
    private EditText editTextData;
    private EditText editTextEndereco;
    private EditText editTextTipoDetrito;
    private EditText editTextTelefone;
    private Button buttonSalvar;
    private Button buttonVoltar;
    private DatabaseReference databaseDetritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_detrito);

        // Inicializar os componentes da interface do usuário
        editTextNomePessoa = findViewById(R.id.editTextNomePessoa);
        editTextData = findViewById(R.id.editTextData);
        editTextEndereco = findViewById(R.id.editTextEndereco);
        editTextTipoDetrito = findViewById(R.id.editTextTipoDetrito);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonVoltar = findViewById(R.id.buttonVoltar);

        // Inicializar referência do Firebase Realtime Database
        databaseDetritos = FirebaseDatabase.getInstance().getReference("detritos");

        // Definir ação do botão "Salvar"
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDetrito();
            }
        });

        // Definir ação do botão "Voltar"
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finaliza a atividade atual e retorna para a anterior
            }
        });
    }

    // Método para salvar os dados do detrito
    private void salvarDetrito() {
        String nomePessoa = editTextNomePessoa.getText().toString();
        String data = editTextData.getText().toString();
        String endereco = editTextEndereco.getText().toString();
        String tipoDetrito = editTextTipoDetrito.getText().toString();

        if (nomePessoa.isEmpty() || data.isEmpty() || endereco.isEmpty() || tipoDetrito.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
        } else {
            String id = databaseDetritos.push().getKey();
            Detrito detrito = new Detrito(id, nomePessoa, data, endereco, tipoDetrito);

            if (id != null) {
                databaseDetritos.child(id).setValue(detrito).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(CadastroDetrito.this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
                        // Limpar os campos após salvar
                        editTextNomePessoa.setText("");
                        editTextData.setText("");
                        editTextEndereco.setText("");
                        editTextTipoDetrito.setText("");
                    } else {
                        Toast.makeText(CadastroDetrito.this, "Erro ao salvar dados!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
