package com.example.gddrs_jhonatan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class tela_principal extends AppCompatActivity {

    private Button buttonCadastroDet;
    private Button buttonVoluntario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        // Inicializar os componentes da interface do usuário
        buttonCadastroDet = findViewById(R.id.buttonCadastroDet);
        buttonVoluntario = findViewById(R.id.buttonVoluntario);

        // Definir ação do botão "Cadastre um detrito"
        buttonCadastroDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tela_principal.this, CadastroDetrito.class);
                startActivity(intent);
            }
        });

        // Definir ação do botão "Ver lista de detritos cadastrados"
        buttonVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aqui você pode adicionar a lógica para exibir a lista de detritos cadastrados
                Intent intent = new Intent(tela_principal.this, ListaDetritosActivity.class);
                startActivity(intent);
            }
        });
    }
}
