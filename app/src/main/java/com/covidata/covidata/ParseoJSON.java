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

    public ArrayList<DatoCCAA> parsearJSONCCAA(String JSON)throws JSONException {

        JSONObject objeto_total = new JSONObject(JSON);
        ArrayList<DatoCCAA> lista = new ArrayList<>();
        int confirmados=0;
        int fallecidos=0;
        int recuperados=0;
        int confirmadosDiario=0;
        int fallecidosDiario=0;
        int recuperadosDiario=0;

        try{
            JSONArray lista_timeline= objeto_total.getJSONArray("timeline");
            for (int indice = 0; indice < lista_timeline.length(); indice++) {
                JSONObject objeto = (JSONObject) lista_timeline.get(indice);
                String fecha= objeto.getString("fecha");
                Log.e("fechaccaa",fecha);
                JSONArray lista_regiones=objeto.getJSONArray("regiones");
                for (int indice1 = 0; indice1 < lista_regiones.length(); indice1++) {
                    JSONObject objeto_region = (JSONObject) lista_regiones.get(indice1);
                    JSONObject data = objeto_region.getJSONObject("data");
                    confirmados = data.getInt("casosConfirmados");
                    Log.e("casosC",""+confirmados);
                    fallecidos = data.getInt("casosFallecidos");
                    recuperados = data.getInt("casosRecuperados");
                    confirmadosDiario = data.getInt("casosConfirmadosDiario");
                    fallecidosDiario = data.getInt("casosFallecidosDiario");
                    recuperadosDiario = data.getInt("casosRecuperadosDiario");
                }
                DatoCCAA da= new DatoCCAA(fecha,confirmados,fallecidos,recuperados,confirmadosDiario,fallecidosDiario,recuperadosDiario);
                lista.add(da);
            }


        }catch (JSONException e){
            Log.d("location","error en el elemento: "+e.getLocalizedMessage());
        }

        return lista;
    }



}
