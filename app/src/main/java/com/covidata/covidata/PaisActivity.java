package com.covidata.covidata;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaisActivity extends AppCompatActivity {

    String fecha;
    String nombrePais;
    String iso;
    ImageView imagen_pais;
    String nombreConTodo;
    ActionBar actionBar;
    TextView cajaConfirmadosHoy, cajaConfirmadosAyer, cajaFallecidosHoy, cajaFallecidosAyer, cajaRecuperadosHoy, cajaRecuperadosAyer, cajatexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);

        cajaConfirmadosHoy=findViewById(R.id.confirmadosHoy);
        cajaConfirmadosAyer=findViewById(R.id.confirmadosAyer);
        cajaFallecidosHoy=findViewById(R.id.fallecidosHoy);
        cajaFallecidosAyer=findViewById(R.id.fallecidosAyer);
        cajaRecuperadosHoy=findViewById(R.id.recuperadosHoy);
        cajaRecuperadosAyer=findViewById(R.id.recuperadosAyer);
        cajatexto=findViewById(R.id.texto);

        nombrePais = getIntent().getStringExtra("Nombre");
        iso = getIntent().getStringExtra("ISO");
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        imagen_pais=findViewById(R.id.imagen_pais);
        Map<String, Integer> mapaPaises = listaPaises();


        if(mapaPaises.get(nombrePais)==null){
            imagen_pais.setImageResource(R.drawable.generico);
        }else{
            int imagen = mapaPaises.get(nombrePais);
            imagen_pais.setImageResource(imagen);
        }

        hacerPeticionNombre(iso);
        hacerPeticionFecha();

    }

    public void crearGrafico(DatoGlobal datoGlobal){

        AnyChartView anyChartView = findViewById(R.id.graficoPais);

        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Pie pie = AnyChart.pie();
        String colores[] = {"#BF8A26", "#591E3A", "#30728C"};

        List<DataEntry> data = new ArrayList<>();

        data.add(new ValueDataEntry("Confirmados", datoGlobal.getConfirmados()));
        data.add(new ValueDataEntry("Fallecidos", datoGlobal.getFallecidos()));
        data.add(new ValueDataEntry("Recuperados", datoGlobal.getRecuperados()));

        pie.data(data);

        pie.labels().enabled(false);
        pie.selected().explode("0%");
        pie.palette(colores);
        pie.tooltip().enabled(false);


        pie.legend()
                .enabled(false);

        anyChartView.setChart(pie);
    }

    private void hacerPeticion() {

        String url = "https://covidapi.info/api/v1/country/"+iso+"/latest";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // por aquí recibo el XML o JSON
                        ParseoJSON p=new ParseoJSON();
                        Log.d("Response", response);

                        try {
                            DatoGlobal datoGlobal=p.parsearJSONFechaPais(response,fecha);
                            String fechaActual = datoGlobal.getFecha();
                            cajatexto.setText("*Datos actualizados a día "+fechaActual);
                            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date date=null;

                            try {
                                date = formatter.parse(fechaActual);
                            }catch (ParseException e ){
                                e.printStackTrace();
                            }

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);

                            calendar.add(Calendar.DAY_OF_YEAR, -1);
                            Date diaAnterior=calendar.getTime();
                            DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                            String diaAnteriorString = formato.format(diaAnterior);
                            hacerPeticionDiaAnterior(diaAnteriorString, datoGlobal);

                            crearGrafico(datoGlobal);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders(){
                Map<String, String>  params = new HashMap<>();
                params.put("Accept","application/xml");
                return params;
            }

        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void hacerPeticionFecha() {

        String url = "https://covidapi.info/api/v1/latest-date";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         fecha=response;
                         Log.e("fecha", fecha);
                         hacerPeticion();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders(){
                Map<String, String>  params = new HashMap<>();
                params.put("Accept","application/xml");
                return params;
            }

        };
        Volley.newRequestQueue(this).add(postRequest);
    }


    private void hacerPeticionDiaAnterior(final String fechaAnterior, final DatoGlobal datoGlobalHoy) {

        String url = "https://covidapi.info/api/v1/country/"+iso+"/"+fechaAnterior;

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // por aquí recibo el XML o JSON
                        ParseoJSON p=new ParseoJSON();
                        Log.d("Response", response);

                        try {
                            DatoGlobal datoGlobal=p.parsearJSONFechaPais(response,fechaAnterior);
                            int confirmados = datoGlobal.getConfirmados();
                            int fallecidos = datoGlobal.getFallecidos();
                            int recuperados = datoGlobal.getRecuperados();
                            String fecha = datoGlobal.getFecha();

                            cajaConfirmadosAyer.setText(NumberFormat.getInstance().format(confirmados));
                            cajaFallecidosAyer.setText(NumberFormat.getInstance().format(fallecidos));
                            cajaRecuperadosAyer.setText(NumberFormat.getInstance().format(recuperados));


                            int calculoConfirmados=datoGlobalHoy.getConfirmados()-confirmados;
                            int calculoFallecidos=datoGlobalHoy.getFallecidos()-fallecidos;
                            int calculoRecuerados=datoGlobalHoy.getRecuperados()-recuperados;


                            cajaConfirmadosHoy.setText(NumberFormat.getInstance().format(datoGlobalHoy.getConfirmados()));
                            cajaFallecidosHoy.setText(NumberFormat.getInstance().format(datoGlobalHoy.getFallecidos()));
                            cajaRecuperadosHoy.setText(NumberFormat.getInstance().format(datoGlobalHoy.getRecuperados()));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders(){
                Map<String, String>  params = new HashMap<>();
                params.put("Accept","application/xml");
                return params;
            }

        };
        Volley.newRequestQueue(this).add(postRequest);
    }


    private void hacerPeticionNombre(String iso) {

        String url = "https://restcountries.eu/rest/v2/alpha/"+iso.toLowerCase();

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ParseoJSON p=new ParseoJSON();
                        Log.d("Response", response);

                        try {
                            nombreConTodo=p.parsearJSONNombre(response);
                            actionBar.setTitle(nombreConTodo);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders(){
                Map<String, String>  params = new HashMap<>();
                params.put("Accept","application/json");
                return params;
            }

        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private static Map<String,Integer> listaPaises(){
        Map<String, Integer> paises = new HashMap<>();

        paises.put("alemania",R.drawable.alemania);
        paises.put("arabia saudi",R.drawable.arabia);
        paises.put("argelia",R.drawable.argelia);
        paises.put("australia",R.drawable.australia);
        paises.put("canada",R.drawable.canada);
        paises.put("china",R.drawable.china);
        paises.put("colombia",R.drawable.colombia);
        paises.put("estados unidos",R.drawable.estadosunidos);
        paises.put("francia",R.drawable.francia);
        paises.put("reino unido",R.drawable.granbretana);
        paises.put("grecia",R.drawable.grecia);
        paises.put("india",R.drawable.india);
        paises.put("islandia",R.drawable.islandia);
        paises.put("italia",R.drawable.italia);
        paises.put("rusia",R.drawable.rusia);
        paises.put("sudafrica",R.drawable.sudafrica);
        paises.put("tanzania",R.drawable.tanzania);
        paises.put("japon",R.drawable.japon);
        paises.put("espana",R.drawable.espanacanariasno);
        paises.put("brasil",R.drawable.brasil);
        paises.put("nigeria",R.drawable.nigeria);

        return paises;
    }

}
