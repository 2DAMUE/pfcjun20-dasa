package com.covidata.covidata;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class TareaAsincrona extends AsyncTask {

    SpainFragment obj_noticias_fragment=null;
    ArrayList<DatoSpain> datos=null;
    public interface rellenarArrayList {
         void rellenaRecycler(ArrayList<DatoSpain> lista);
    }

    public TareaAsincrona(SpainFragment c) {
        obj_noticias_fragment=c;
    }

    @Override
    protected Void doInBackground(Object[] objects) {

        datos=WebScrapping.webScrapping();
        for (DatoSpain d: datos) {
            Log.d("confir", d.getConfirmados());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        obj_noticias_fragment.rellenaRecycler(datos);
        //rellenaRecycler(noticias);

    }

}
