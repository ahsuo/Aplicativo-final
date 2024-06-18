package com.example.gddrs_jhonatan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DetritoAdapter extends RecyclerView.Adapter<DetritoAdapter.ViewHolder> {

    private List<Detrito> detritos;
    private Context context;

    public DetritoAdapter(List<Detrito> detritos, Context context) {
        this.detritos = detritos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detrito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Detrito detrito = detritos.get(position);
        holder.nomeTextView.setText("Nome: " + detrito.getNomePessoa());
        holder.enderecoTextView.setText("Endereço: " + detrito.getEndereco());
        holder.tipoDetritoTextView.setText("Tipo de Detrito: " + detrito.getTipoDetrito());
        holder.telefoneTextView.setText("Telefone: " + detrito.getTelefone());
        holder.latitudeTextView.setText("Latitude: " + detrito.getLatitude());
        holder.longitudeTextView.setText("Longitude: " + detrito.getLongitude());

        holder.buttonExibirLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("latitude", detrito.getLatitude());
                intent.putExtra("longitude", detrito.getLongitude());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return detritos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeTextView;
        public TextView enderecoTextView;
        public TextView tipoDetritoTextView;
        public TextView telefoneTextView;
        public TextView latitudeTextView;
        public TextView longitudeTextView;
        public Button buttonExibirLocalizacao;

        public ViewHolder(View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.textViewNome);
            enderecoTextView = itemView.findViewById(R.id.textViewEndereco);
            tipoDetritoTextView = itemView.findViewById(R.id.textViewTipoDetrito);
            telefoneTextView = itemView.findViewById(R.id.textViewTelefone);
            latitudeTextView = itemView.findViewById(R.id.textViewLatitude);
            longitudeTextView = itemView.findViewById(R.id.textViewLongitude);
            buttonExibirLocalizacao = itemView.findViewById(R.id.buttonExibirLocalizacao);
        }
    }
}
