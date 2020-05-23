package com.covidata.covidata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaisActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);

        String nombrePais = getIntent().getStringExtra("Nombre");

        hacerPeticion();

    }

    public void crearGrafico(ArrayList<Integer>lista){
        AnyChartView anyChartView = findViewById(R.id.graficoPais);
        int activos = lista.get(0)-(lista.get(1)+lista.get(2));

        Pie pie = AnyChart.pie();
        String colores[] = {"#80CBC4", "#AED581", "#E6EE9C", "#FFCC80"};


        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Confirmados", lista.get(0)));
        data.add(new ValueDataEntry("Fallecidos", lista.get(1)));
        data.add(new ValueDataEntry("Recuperados", lista.get(2)));
        data.add(new ValueDataEntry("Activos", activos));

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

        String url = "https://covidapi.info/api/v1/country/FRA/latest";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // por aquí recibo el XML o JSON
                        ParseoJSON p=new ParseoJSON();
                        Log.d("Response", response);

                        try {
                            ArrayList<Integer> lista=p.parsearJSONFechaPais(response);//Este response es el String JSON que le pasamos al metodo


                            crearGrafico(lista);
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

}
