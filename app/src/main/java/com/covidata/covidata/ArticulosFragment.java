package com.covidata.covidata;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class ArticulosFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_articulos, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        ArrayList<Articulo> lista=crearLista();

        webView = view.findViewById(R.id.web);
        webView.loadUrl(lista.get(0).getPdf());
        webView.getSettings().setJavaScriptEnabled(true);

        return view;
    }

    public static ArrayList<Articulo> crearLista(){
        ArrayList<Articulo> lista=new ArrayList<>();
        lista.add(new Articulo("Articulo 1",R.drawable.canada,
                "https://www.sciencedirect.com/science/article/pii/S1007570420301350"));
        return lista;
    }


}
