package com.covidata.covidata;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class ArticulosFragment extends Fragment {

    private RecyclerView recyclerView;
    private Adapter adapter;
    ArrayList<Articulo> lista;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_articulos, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        lista= crearListaArticulos();
        adapter = new Adapter(lista);
        recyclerView.setAdapter((adapter));
        initListener();

        return view;
    }

    private void initListener(){

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ArticulosActivity.class);
                Articulo articulo = lista.get(position);
                intent.putExtra("url", articulo.getUrl());
                intent.putExtra("titulo", articulo.getTitulo());
                intent.putExtra("autor",  articulo.getAutor());
                startActivity(intent);

            }
        });

    }


    private ArrayList<Articulo> crearListaArticulos() {
        ArrayList<Articulo> listaArticulos= new ArrayList<>();
        listaArticulos.add(new Articulo("Mathematical modeling of the spread of the coronavirus disease 2019 (COVID-19) taking into account the undetected infections. The case of China.",
                "B.Ivorra, M.R.Ferrández, M.Vela-Pérez, A.M.Ramos","https://www.sciencedirect.com/science/article/pii/S1007570420301350",R.drawable.articulo1));
        listaArticulos.add(new Articulo("",
                "Próximamente más articulos...","https://www.miatechaust.com.au/wp-content/uploads/2016/05/COMING-SOON-LOGO.png",0));
        return listaArticulos;

    }
}
