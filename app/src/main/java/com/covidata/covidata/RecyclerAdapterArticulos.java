package com.covidata.covidata;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterArticulos extends RecyclerView.Adapter<RecyclerAdapterArticulos.ViewHolder>{

    private static final String TAG="RecyclerAdapter";

    ArrayList<Articulo> lista;

    public RecyclerAdapterArticulos(ArrayList<Articulo> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item2, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.nombre.setText(lista.get(position).getNombre());
        holder.nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        ImageView imagen;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            nombre = itemView.findViewById((R.id.nombre));
                    /*
            imagen = itemView.findViewById((R.id.imagen));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

                     */

        }

    }






}
