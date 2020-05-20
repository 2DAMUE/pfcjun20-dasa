package com.covidata.covidata;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

class ParseoJSON {


    public ArrayList<Integer> parsearJSON(String JSON)throws JSONException {

        ArrayList<Integer>listaDatosGlobales = new ArrayList<>();

        JSONObject objeto_total = new JSONObject(JSON);

            try{

                JSONObject result = objeto_total.getJSONObject("result");
                int confirmed = result.getInt("confirmed");
                int deaths = result.getInt("deaths");
                int recovered = result.getInt("recovered");


                listaDatosGlobales.add(confirmed);
                listaDatosGlobales.add(deaths);
                listaDatosGlobales.add(recovered);

            }catch (JSONException e){
                Log.d("location","error en el elemento: "+e.getLocalizedMessage());
            }

        return listaDatosGlobales;
    }

    public ArrayList<Integer> parsearJSONFechaPais(String JSON)throws JSONException {

        ArrayList<Integer>listaDatosGlobales = new ArrayList<>();
        Calendar fecha = Calendar.getInstance();
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        fecha.add(Calendar.DATE,-1);
        Date ayer = fecha.getTime();
        String fechaAyer = formato.format(ayer).toString();


        JSONObject objeto_total = new JSONObject(JSON);

        try{

            JSONObject result = objeto_total.getJSONObject("result");
            JSONObject objetoFecha = result.getJSONObject(fechaAyer);
            int confirmed = objetoFecha.getInt("confirmed");
            int deaths = objetoFecha.getInt("deaths");
            int recovered = objetoFecha.getInt("recovered");


            listaDatosGlobales.add(confirmed);
            listaDatosGlobales.add(deaths);
            listaDatosGlobales.add(recovered);

        }catch (JSONException e){
            Log.d("location","error en el elemento: "+e.getLocalizedMessage());
        }

        return listaDatosGlobales;
    }
}
