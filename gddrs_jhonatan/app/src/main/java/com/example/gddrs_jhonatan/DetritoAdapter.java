package com.example.gddrs_jhonatan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DetritoAdapter extends RecyclerView.Adapter<DetritoAdapter.ViewHolder> {

    private List<Detrito> detritos;

    public DetritoAdapter(List<Detrito> detritos) {
        this.detritos = detritos;
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
        holder.nomeTextView.setText("Nome: " + detrito.getNome());
        holder.enderecoTextView.setText("Endereço: " + detrito.getEndereco());
        holder.tipoDetritoTextView.setText("Tipo de Detrito: " + detrito.getTipoDetrito());
        holder.telefoneTextView.setText("Telefone: " + detrito.getTelefone()); // Exibir o telefone
    }

    @Override
    public int getItemCount() {
        return detritos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeTextView;
        public TextView enderecoTextView;
        public TextView tipoDetritoTextView;
        public TextView telefoneTextView; // Novo campo de telefone

        public ViewHolder(View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.textViewNome);
            enderecoTextView = itemView.findViewById(R.id.textViewEndereco);
            tipoDetritoTextView = itemView.findViewById(R.id.textViewTipoDetrito);
            telefoneTextView = itemView.findViewById(R.id.textViewTelefone); // Inicializar o campo de telefone
        }
    }
}
