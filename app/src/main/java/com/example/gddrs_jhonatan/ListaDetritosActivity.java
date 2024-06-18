package com.example.gddrs_jhonatan;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
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

public class ListaDetritosActivity extends AppCompatActivity {

    private static final String TAG = "ListaDetritosActivity";
    private RecyclerView recyclerView;
    private DetritoAdapter detritoAdapter;
    private List<Detrito> detritoList;
    private DatabaseReference databaseDetritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_detritos);

        recyclerView = findViewById(R.id.recyclerViewDetritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        detritoList = new ArrayList<>();
        detritoAdapter = new DetritoAdapter(detritoList, this);
        recyclerView.setAdapter(detritoAdapter);

        databaseDetritos = FirebaseDatabase.getInstance().getReference("detritos");

        loadDetritos();
    }

    private void loadDetritos() {
        databaseDetritos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                detritoList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Detrito detrito = postSnapshot.getValue(Detrito.class);
                    if (detrito != null) {
                        detritoList.add(detrito);
                    }
                }
                detritoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Failed to load detritos", databaseError.toException());
            }
        });
    }
}
