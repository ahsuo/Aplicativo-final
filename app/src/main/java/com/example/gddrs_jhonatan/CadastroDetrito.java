package com.example.gddrs_jhonatan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class CadastroDetrito extends AppCompatActivity {

    private static final String TAG = "CadastroDetrito";
    private EditText editTextNomePessoa;
    private EditText editTextData;
    private EditText editTextEndereco;
    private EditText editTextTipoDetrito;
    private EditText editTextTelefone;
    private Button buttonSalvar;
    private Button buttonVoltar;
    private Button buttonCapturarLocalizacao;
    private DatabaseReference databaseDetritos;
    private double latitude;
    private double longitude;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

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
        buttonCapturarLocalizacao = findViewById(R.id.buttonCapturarLocalizacao);

        // Inicializar referência do Firebase Realtime Database
        databaseDetritos = FirebaseDatabase.getInstance().getReference("detritos");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Definir ação do botão "Capturar Localização"
        buttonCapturarLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarLocalizacao();
            }
        });

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

        // Solicitar permissão de localização se ainda não concedida
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void capturarLocalizacao() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                Log.d(TAG, "Localização capturada: Latitude = " + latitude + ", Longitude = " + longitude);
                                Toast.makeText(CadastroDetrito.this, "Localização capturada!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "Localização é nula");
                                Toast.makeText(CadastroDetrito.this, "Não foi possível obter a localização.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Erro ao tentar obter a localização", e);
                            Toast.makeText(CadastroDetrito.this, "Erro ao tentar obter a localização.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    // Método para salvar os dados do detrito
    private void salvarDetrito() {
        String nomePessoa = editTextNomePessoa.getText().toString();
        String data = editTextData.getText().toString();
        String endereco = editTextEndereco.getText().toString();
        String tipoDetrito = editTextTipoDetrito.getText().toString();
        String telefone = editTextTelefone.getText().toString();

        if (nomePessoa.isEmpty() || data.isEmpty() || endereco.isEmpty() || tipoDetrito.isEmpty() || telefone.isEmpty() || latitude == 0.0 || longitude == 0.0) {
            Toast.makeText(this, "Por favor, preencha todos os campos e capture a localização.", Toast.LENGTH_SHORT).show();
        } else {
            String id = databaseDetritos.push().getKey();
            Detrito detrito = new Detrito(id, nomePessoa, data, endereco, tipoDetrito, telefone, latitude, longitude);

            if (id != null) {
                databaseDetritos.child(id).setValue(detrito).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(CadastroDetrito.this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
                        // Limpar os campos após salvar
                        editTextNomePessoa.setText("");
                        editTextData.setText("");
                        editTextEndereco.setText("");
                        editTextTipoDetrito.setText("");
                        editTextTelefone.setText("");

                        // Navegar para MapActivity
                        Intent intent = new Intent(CadastroDetrito.this, tela_principal.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CadastroDetrito.this, "Erro ao salvar dados!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                capturarLocalizacao();
            } else {
                Toast.makeText(this, "Permissão de localização negada", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
