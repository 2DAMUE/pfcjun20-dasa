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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_articulos, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        ArrayList<Articulo> lista=crearLista();

        AssetManager assetManager = getContext().getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(getContext().getFilesDir(), "uno.pdf");
        try {
            in = assetManager.open("uno.pdf");
            out = new BufferedOutputStream(new FileOutputStream(file));

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getContext().getFilesDir() + "/uno.pdf"),
                "application/pdf");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent1 = Intent.createChooser(intent, "Open With");
        try {
            startActivity(intent1);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);


        return view;
    }

    public static ArrayList<Articulo> crearLista(){
        ArrayList<Articulo> lista=new ArrayList<>();
        lista.add(new Articulo("Articulo 1",R.drawable.canada,
                "https://reader.elsevier.com/reader/sd/pii/S1007570420301350?token=BA387DDAC46A34ABF52C177D0FD69F78CDA6595057DA7948E4D5ED7C826C79C8136E096390805F215C2865808986FAAD"));
        return lista;
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
