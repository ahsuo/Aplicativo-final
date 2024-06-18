package com.example.gddrs_jhonatan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class listaDetritos extends AppCompatActivity {

    private RecyclerView recyclerViewDetritos;
    private DetritoAdapter detritoAdapter;
    private List<Detrito> detritoList;
    private DatabaseReference databaseDetritos;
    private Button buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_detritos);

        recyclerViewDetritos = findViewById(R.id.recyclerViewDetritos);
        recyclerViewDetritos.setLayoutManager(new LinearLayoutManager(this));

        detritoList = new ArrayList<>();
        detritoAdapter = new DetritoAdapter(detritoList);
        recyclerViewDetritos.setAdapter(detritoAdapter);

        // Inicializar referência do Firebase Realtime Database
        databaseDetritos = FirebaseDatabase.getInstance().getReference("detritos");

        // Carregar dados do Firebase
        databaseDetritos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detritoList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Detrito detrito = postSnapshot.getValue(Detrito.class);
                    detritoList.add(detrito);
                }
                detritoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Tratar erro de carregamento
            }
        });

        // Inicializar botão Voltar
        buttonVoltar = findViewById(R.id.buttonVoltar);
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finaliza a atividade atual e retorna para a anterior
            }
        });
    }
}
