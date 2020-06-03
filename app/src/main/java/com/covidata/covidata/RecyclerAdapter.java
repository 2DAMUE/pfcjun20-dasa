package com.covidata.covidata;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private static final String TAG="RecyclerAdapter";

    ArrayList<String> lista;

    public RecyclerAdapter(ArrayList<String> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = lista.get(position);
        holder.nombretv.setText(nombre);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombretv;
        ImageView imagenMas;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            nombretv = itemView.findViewById((R.id.nombre));
            imagenMas = itemView.findViewById((R.id.imagenMas));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), CAActivity.class);
                    intent.putExtra("nombre", nombretv.getText());
                    itemView.getContext().startActivity(intent);
                }
            });

        }

    }
}
