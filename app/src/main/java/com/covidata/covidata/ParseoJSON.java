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


    public DatoGlobal parsearJSON(String JSON)throws JSONException {

        JSONObject objeto_total = new JSONObject(JSON);
        DatoGlobal datoglobal=null;

            try{

                JSONObject result = objeto_total.getJSONObject("result");
                String date = objeto_total.getString("date");
                int confirmed = result.getInt("confirmed");
                int deaths = result.getInt("deaths");
                int recovered = result.getInt("recovered");
                Log.e("recuperados", ""+recovered);

                datoglobal = new DatoGlobal(date, confirmed, deaths, recovered);


            }catch (JSONException e){
                Log.d("location","error en el elemento: "+e.getLocalizedMessage());
            }

        return datoglobal;
    }


    public DatoGlobal parsearJSONFechaPais(String JSON, String fecha)throws JSONException {

        JSONObject objeto_total = new JSONObject(JSON);
        DatoGlobal datoglobal=null;

        try{

            JSONObject result = objeto_total.getJSONObject("result");
            JSONObject objetoFecha = result.getJSONObject(fecha);
            int confirmed = objetoFecha.getInt("confirmed");
            int deaths = objetoFecha.getInt("deaths");
            int recovered = objetoFecha.getInt("recovered");

            datoglobal = new DatoGlobal(fecha, confirmed, deaths, recovered);

        }catch (JSONException e){
            Log.d("location","error en el elemento: "+e.getLocalizedMessage());
        }

        return datoglobal;
    }

    public String parsearJSONNombre(String JSON)throws JSONException {

        JSONObject objeto_total = new JSONObject(JSON);
        String nombre="";

        try{

            JSONObject translations = objeto_total.getJSONObject("translations");
            nombre = translations.getString("es");


        }catch (JSONException e){
            Log.d("location","error en el elemento: "+e.getLocalizedMessage());
        }

        return nombre;
    }



}
