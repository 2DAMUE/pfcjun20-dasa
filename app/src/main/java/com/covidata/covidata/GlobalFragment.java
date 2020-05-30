package com.covidata.covidata;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.CircularGauge;
import com.anychart.core.axes.Circular;
import com.anychart.core.gauge.pointers.Bar;
import com.anychart.enums.Anchor;
import com.anychart.graphics.vector.Fill;
import com.anychart.graphics.vector.SolidFill;
import com.anychart.graphics.vector.text.HAlign;
import com.anychart.graphics.vector.text.VAlign;


import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class GlobalFragment extends Fragment {

    AnyChartView anyChartView;
    ImageButton busqueda;
    EditText busqueda_pais;
    View view;
    TextView cajaConfirmadosHoy, cajaConfirmadosAyer, cajaFallecidosHoy, cajaFallecidosAyer, cajaRecuperadosHoy, cajaRecuperadosAyer,
    cajaActivosHoy, cajaActivosAyer, cajaFechaAyer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_global, container, false);

        anyChartView=view.findViewById(R.id.grafico);
        busqueda=view.findViewById(R.id.busqueda);
        busqueda_pais=view.findViewById(R.id.busqueda_pais);

        cajaConfirmadosHoy=view.findViewById(R.id.confirmadosHoy);
        cajaConfirmadosAyer=view.findViewById(R.id.confirmadosAyer);
        cajaFallecidosHoy=view.findViewById(R.id.fallecidosHoy);
        cajaFallecidosAyer=view.findViewById(R.id.fallecidosAyer);
        cajaRecuperadosHoy=view.findViewById(R.id.recuperadosHoy);
        cajaRecuperadosAyer=view.findViewById(R.id.recuperadosAyer);
        cajaActivosHoy=view.findViewById(R.id.activosHoy);
        cajaActivosAyer=view.findViewById(R.id.activosAyer);
        cajaFechaAyer=view.findViewById(R.id.fecha);

        final Map<String,String> paises = crearMapaPaises();

        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                String textoPais = busqueda_pais.getText().toString().toLowerCase();
                String paisSinAcentos = limpiarAcentos(textoPais);
                String iso= paises.get(paisSinAcentos);

                if(iso==null){
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Pais no encontrado", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                }else{
                    if(textoPais.equals("españa")){
                        Intent intent2 = new Intent(getActivity(),MainActivity.class);
                        intent2.putExtra("dato", "1");
                        startActivity(intent2);

                    }else{
                        intent = new Intent(getActivity(), PaisActivity.class);
                        intent.putExtra("Nombre", paisSinAcentos);
                        intent.putExtra("ISO", iso);
                        startActivity(intent);
                    }

                }



            }
        });

        hacerPeticion();

        return view;
    }


    private void hacerPeticion() {

        String url = "https://covidapi.info/api/v1/global";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // por aquí recibo el XML o JSON
                        ParseoJSON p=new ParseoJSON();
                        Log.d("Response", response);

                        try {
                            DatoGlobal datoGlobal =p.parsearJSON(response);//Este response es el String JSON que le pasamos al metodo
                            crearGrafico(datoGlobal);
                            String fechaActual = datoGlobal.getFecha();
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
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(postRequest);
    }


    private void hacerPeticionDiaAnterior(String fecha, final DatoGlobal datoGlobalHoy) {

        String url = "https://covidapi.info/api/v1/global/"+fecha;

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // por aquí recibo el XML o JSON
                        ParseoJSON p=new ParseoJSON();
                        Log.d("Response", response);

                        try {
                            DatoGlobal datoGlobal =p.parsearJSON(response);//Este response es el String JSON que le pasamos al metodo
                            int confirmados = datoGlobal.getConfirmados();
                            int fallecidos = datoGlobal.getFallecidos();
                            int recuperados = datoGlobal.getRecuperados();
                            int activos = (confirmados-(fallecidos+recuperados));
                            String fecha = datoGlobal.getFecha();

                            cajaFechaAyer.setText(fecha);
                            cajaConfirmadosAyer.setText(NumberFormat.getInstance().format(confirmados));
                            cajaFallecidosAyer.setText(NumberFormat.getInstance().format(fallecidos));
                            cajaRecuperadosAyer.setText(NumberFormat.getInstance().format(recuperados));
                            cajaActivosAyer.setText(NumberFormat.getInstance().format(activos));

                            int calculoConfirmados=datoGlobalHoy.getConfirmados()-confirmados;
                            int calculoFallecidos=datoGlobalHoy.getFallecidos()-fallecidos;
                            int calculoRecuerados=datoGlobalHoy.getRecuperados()-recuperados;
                            int calculoActivos=((datoGlobalHoy.getConfirmados()-(datoGlobalHoy.getFallecidos()+datoGlobalHoy.getRecuperados()))-activos);


                            cajaConfirmadosHoy.setText("+ "+NumberFormat.getInstance().format(calculoConfirmados));
                            cajaFallecidosHoy.setText("+ "+NumberFormat.getInstance().format(calculoFallecidos));
                            cajaRecuperadosHoy.setText("+ "+NumberFormat.getInstance().format(calculoRecuerados));
                            cajaActivosHoy.setText("+ "+NumberFormat.getInstance().format(calculoActivos));


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
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(postRequest);
    }


    public void crearGrafico(DatoGlobal datoGlobal){

        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));

        int activos = datoGlobal.getConfirmados()-(datoGlobal.getFallecidos()+datoGlobal.getRecuperados());
        String confirmados = NumberFormat.getInstance().format(datoGlobal.getConfirmados());
        String fallecidos = NumberFormat.getInstance().format(datoGlobal.getFallecidos());
        String recuperados = NumberFormat.getInstance().format(datoGlobal.getRecuperados());
        String activosString = NumberFormat.getInstance().format(activos);

        CircularGauge circularGauge = AnyChart.circular();
        circularGauge.height("410px");
        circularGauge.width("410px");

        circularGauge.data(new SingleValueDataSet(new String[] { String.valueOf(datoGlobal.getConfirmados()), String.valueOf(datoGlobal.getFallecidos())
                , String.valueOf(datoGlobal.getRecuperados()), String.valueOf(activos), "0", String.valueOf(datoGlobal.getConfirmados())}));
        circularGauge.fill("#fff")
                .stroke(null)
                .padding(0d, 0d, 0d, 0d)
                .margin(100d, 100d, 100d, 100d);
        circularGauge.startAngle(0d);
        circularGauge.sweepAngle(270d);

        Circular xAxis = circularGauge.axis(0)
                .radius(100d)
                .width(1d)
                .fill((Fill) null);
        xAxis.scale()
                .minimum(0d)
                .maximum(6000000d);
        xAxis.ticks("{ interval: 1 }")
                .minorTicks("{ interval: 1 }");
        xAxis.labels().enabled(false);
        xAxis.ticks().enabled(false);
        xAxis.minorTicks().enabled(false);

        circularGauge.label(0d)
                .text("Confirmados - <span style=\"\">"+confirmados +"</span>")
                .fontColor("#BF8A26")
                .fontWeight("bold")
                .fontSize(14)
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.MIDDLE);
        circularGauge.label(0d)
                .anchor(Anchor.RIGHT_CENTER)
                .padding(0d, 10d, 0d, 0d)
                .height(17d / 2d + "%")
                .offsetY(100d + "%")
                .offsetX(0d);
        Bar bar0 = circularGauge.bar(0d);
        bar0.dataIndex(0d);
        bar0.radius(100d);
        bar0.width(17d);
        bar0.fill(new SolidFill("#BF8A26", 1d));
        bar0.stroke(null);
        bar0.zIndex(5d);
        Bar bar100 = circularGauge.bar(100d);
        bar100.dataIndex(5d);
        bar100.radius(100d);
        bar100.width(17d);
        bar100.fill(new SolidFill("#F5F4F4", 1d));
        bar100.stroke("1 #e5e4e4");
        bar100.zIndex(4d);

        circularGauge.label(1d)
                .text("Fallecidos - <span style=\"\">"+fallecidos+"</span>")
                .fontColor("#591E3A")
                .fontWeight("bold")
                .fontSize(14)
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.MIDDLE);
        circularGauge.label(1d)
                .anchor(Anchor.RIGHT_CENTER)
                .padding(0d, 10d, 0d, 0d)
                .height(17d / 2d + "%")
                .offsetY(80d + "%")
                .offsetX(0d);
        Bar bar1 = circularGauge.bar(1d);
        bar1.dataIndex(1d);
        bar1.radius(80d);
        bar1.width(17d);
        bar1.fill(new SolidFill("#591E3A", 1d));
        bar1.stroke(null);
        bar1.zIndex(5d);
        Bar bar101 = circularGauge.bar(101d);
        bar101.dataIndex(5d);
        bar101.radius(80d);
        bar101.width(17d);
        bar101.fill(new SolidFill("#F5F4F4", 1d));
        bar101.stroke("1 #e5e4e4");
        bar101.zIndex(4d);

        circularGauge.label(2d)
                .text("Recuperados - <span style=\"\">"+recuperados+"</span>")
                .fontColor("#30728C")
                .fontWeight("bold")
                .fontSize(14)
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.MIDDLE);
        circularGauge.label(2d)
                .anchor(Anchor.RIGHT_CENTER)
                .padding(0d, 10d, 0d, 0d)
                .height(17d / 2d + "%")
                .offsetY(60d + "%")
                .offsetX(0d);
        Bar bar2 = circularGauge.bar(2d);
        bar2.dataIndex(2d);
        bar2.radius(60d);
        bar2.width(17d);
        bar2.fill(new SolidFill("#30728C", 1d));
        bar2.stroke(null);
        bar2.zIndex(5d);
        Bar bar102 = circularGauge.bar(102d);
        bar102.dataIndex(5d);
        bar102.radius(60d);
        bar102.width(17d);
        bar102.fill(new SolidFill("#F5F4F4", 1d));
        bar102.stroke("1 #e5e4e4");
        bar102.zIndex(4d);

        circularGauge.label(3d)
                .text("Activos - <span style=\"\">"+activosString+"</span>")
                .fontColor("#6E6E6E")
                .fontWeight("bold")
                .fontSize(14)
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.MIDDLE);
        circularGauge.label(3d)
                .anchor(Anchor.RIGHT_CENTER)
                .padding(0d, 10d, 0d, 0d)
                .height(17d / 2d + "%")
                .offsetY(40d + "%")
                .offsetX(0d);
        Bar bar3 = circularGauge.bar(3d);
        bar3.dataIndex(3d);
        bar3.radius(40d);
        bar3.width(17d);
        bar3.fill(new SolidFill("#6E6E6E", 1d));
        bar3.stroke(null);
        bar3.zIndex(5d);
        Bar bar103 = circularGauge.bar(103d);
        bar103.dataIndex(5d);
        bar103.radius(40d);
        bar103.width(17d);
        bar103.fill(new SolidFill("#F5F4F4", 1d));
        bar103.stroke("1 #e5e4e4");
        bar103.zIndex(4d);


        circularGauge.margin(50d, 50d, 50d, 50d);
        circularGauge.tooltip().format("{%value}{groupsSeparator:.}");

        anyChartView.setChart(circularGauge);
    }


    public static Map<String, String> crearMapaPaises(){
        Map<String, String> paises = new HashMap<>();
        for (String iso: Locale.getISOCountries()){
            Locale locale = new Locale("", iso);
            String codigoIso = locale.getISO3Country();
            String nombre = locale.getDisplayCountry().toLowerCase();
            String nombreSinAcento = limpiarAcentos(nombre);

            paises.put(nombreSinAcento,codigoIso);

        }
        return paises;
    }

    public static String limpiarAcentos(String cadena){
        String cadenaNormalize = Normalizer.normalize(cadena, Normalizer.Form.NFD);
        String cadenaSinAcentos = cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
        return cadenaSinAcentos;
    }



}