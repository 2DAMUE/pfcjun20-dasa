package com.covidata.covidata;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;

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

public class CAActivity extends AppCompatActivity {

    ImageView imagen_pais,aclaracion;
    TextView cajaConfirmadosHoy, cajaConfirmadosAyer, cajaFallecidosHoy, cajaFallecidosAyer, cajaRecuperadosHoy, cajaRecuperadosAyer,
            cajatexto,cajaFechaAyer, cajaSubidaConfirmados, cajaSubidaFallecidos, cajaSubidaRecuerpados, cajaFechados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca);
        String nombre= getIntent().getStringExtra("nombre");
        setTitle(nombre);

        String iso="";
        int imagen=0;
        ArrayList<CCAA> lista=crearListaCCAA();
        for (CCAA ca : lista){
            if(nombre.equals(ca.getNombre())){
                iso=ca.getIso();
                imagen=ca.getImagen();
            }
        }

        imagen_pais=findViewById(R.id.imagen_ccaa );
        imagen_pais.setImageResource(imagen);

        cajaConfirmadosHoy=findViewById(R.id.confirmadosHoy);
        cajaConfirmadosAyer=findViewById(R.id.confirmadosAyer);
        cajaFallecidosHoy=findViewById(R.id.fallecidosHoy);
        cajaFallecidosAyer=findViewById(R.id.fallecidosAyer);
        cajaRecuperadosHoy=findViewById(R.id.recuperadosHoy);
        cajaRecuperadosAyer=findViewById(R.id.recuperadosAyer);
        cajatexto=findViewById(R.id.texto);
        cajaFechaAyer=findViewById(R.id.fecha);
        cajaSubidaConfirmados=findViewById(R.id.subidaConfirmados);
        cajaSubidaFallecidos=findViewById(R.id.subidaFallecidos);
        cajaSubidaRecuerpados=findViewById(R.id.subidaRecuperados);
        cajaFechados=findViewById(R.id.fecha2);
        aclaracion=findViewById(R.id.aclaracion);
        aclaracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogButtonClicked(CAActivity.this);
            }
        });

        hacerPeticion(iso);

    }
    public void showAlertDialogButtonClicked(CAActivity view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyAlertDialogTheme);

        builder.setTitle("Aclaración");
        builder.setMessage("Estos datos incluyen los casos confirmados " +
                "por PCR más los casos por AC+ publicados por el Ministerio de Sanidad.");

        builder.setNeutralButton("CERRAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        Intent intent2 = new Intent(this,MainActivity.class);
        intent2.putExtra("dato", "2");
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent2);
        finish();
        return true;
    }

    private void hacerPeticion(String iso) {

        String url = "https://covid19.secuoyas.io/api/v1/es/ccaa??ultimodia=true&codigo="+iso;

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ParseoJSON p=new ParseoJSON();

                        try {
                            ArrayList<DatoCCAA> listaCCAA=p.parsearJSONCCAA(response);
                            Log.e("fechaCCAA",""+listaCCAA.get(listaCCAA.size()-1).getConfirmados());
                            cajaFechaAyer.setText(listaCCAA.get(listaCCAA.size()-2).getFecha());
                            cajaFechados.setText("Hoy");

                            int confirmadosAyer =listaCCAA.get(listaCCAA.size()-2).getConfirmados();
                            int fallecidosAyer = listaCCAA.get(listaCCAA.size()-2).getFallecidos();
                            int recuperadosAyer = listaCCAA.get(listaCCAA.size()-2).getRecuperados();
                            int confirmadosHoy =listaCCAA.get(listaCCAA.size()-1).getConfirmados();
                            int fallecidosHoy = listaCCAA.get(listaCCAA.size()-1).getFallecidos();
                            int recuperadosHoy = listaCCAA.get(listaCCAA.size()-1).getRecuperados();

                            cajaConfirmadosAyer.setText(NumberFormat.getInstance().format(confirmadosAyer));
                            cajaFallecidosAyer.setText(NumberFormat.getInstance().format(fallecidosAyer));
                            cajaRecuperadosAyer.setText(NumberFormat.getInstance().format(recuperadosAyer));

                            int calculoConfirmados=confirmadosHoy-confirmadosAyer;
                            int calculoFallecidos=fallecidosHoy-fallecidosAyer;
                            int calculoRecuerados=recuperadosHoy-recuperadosAyer;

                            cajaConfirmadosHoy.setText(NumberFormat.getInstance().format(confirmadosHoy));
                            cajaFallecidosHoy.setText(NumberFormat.getInstance().format(fallecidosHoy));
                            cajaRecuperadosHoy.setText(NumberFormat.getInstance().format(recuperadosHoy));

                            if(calculoConfirmados<0){
                                cajaSubidaConfirmados.setText(NumberFormat.getInstance().format(calculoConfirmados));
                            }else{
                                cajaSubidaConfirmados.setText("+ "+NumberFormat.getInstance().format(calculoConfirmados));
                            }

                            if(calculoFallecidos<0){
                                cajaSubidaFallecidos.setText(NumberFormat.getInstance().format(calculoFallecidos));
                            }else{
                                cajaSubidaFallecidos.setText("+ "+NumberFormat.getInstance().format(calculoFallecidos));
                            }

                            if(calculoRecuerados<0){
                                cajaSubidaRecuerpados.setText(NumberFormat.getInstance().format(calculoRecuerados));
                            }else{
                                cajaSubidaRecuerpados.setText("+ "+NumberFormat.getInstance().format(calculoRecuerados));
                            }


                            crearGrafico(listaCCAA);

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

    public class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }

    public void crearGrafico(ArrayList<DatoCCAA> listaCCAA){

        //Primer grafico
        AnyChartView anyChartView = findViewById(R.id.grafico1);
        anyChartView.addFont("Montserrat", "file:///android_asset/montserratregular.ttf");

        APIlib.getInstance().setActiveAnyChartView(anyChartView);

        Pie pie = AnyChart.pie();
        String colores[] = {"#BF8A26", "#591E3A", "#30728C"};
        pie.bounds(65, -10, "270px", "280px");

        List<DataEntry> data = new ArrayList<>();
        int acumuladoConfirmados=listaCCAA.get(listaCCAA.size()-1).getConfirmados();
        int acumuladoFallecidos=listaCCAA.get(listaCCAA.size()-1).getFallecidos();
        int acumuladoRecuperados=listaCCAA.get(listaCCAA.size()-1).getRecuperados();

        data.add(new ValueDataEntry("Confirmados", acumuladoConfirmados));
        data.add(new ValueDataEntry("Fallecidos", acumuladoFallecidos));
        data.add(new ValueDataEntry("Recuperados", acumuladoRecuperados));

        pie.data(data);

        pie.labels().enabled(false);
        pie.selected().explode("0%");
        pie.palette(colores);
        pie.tooltip().enabled(false);

        pie.legend()
                .enabled(false);

        anyChartView.setChart(pie);


        //Segundo grafico
        AnyChartView anyChartView1 = findViewById(R.id.grafico2);
        APIlib.getInstance().setActiveAnyChartView(anyChartView1);

        anyChartView1.setProgressBar(findViewById(R.id.progress_bar));
        anyChartView1.addFont("Montserrat", "file:///android_asset/montserratregular.ttf");

        Cartesian cartesian = AnyChart.line();
        cartesian.height("300px");
        cartesian.width("390px");

        cartesian.animation(true);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.yAxis(0).labels().fontFamily("Montserrat");
        cartesian.xAxis(0).labels().fontFamily("Montserrat").fontSize(11).padding(6d);

        List<DataEntry> seriesData = new ArrayList<>();
        for(DatoCCAA ca: listaCCAA){
            seriesData.add(new CustomDataEntry(ca.getFecha(), ca.getConfirmadoDiario(), ca.getFallecidosDiario(), ca.getRecuperadosDiario()));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Confirmados").color("#BF8A26");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip().fontFamily("Montserrat")
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        series1.tooltip().title().fontFamily("Montserrat");

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Fallecidos").color("#591E3A");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip().fontFamily("Montserrat")
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        series2.tooltip().title().fontFamily("Montserrat");

        Line series3 = cartesian.line(series3Mapping);
        series3.name("Recuperados").color("#30728C");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip().fontFamily("Montserrat")
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        series3.tooltip().title().fontFamily("Montserrat");

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(12d).fontFamily("Montserrat");
        cartesian.legend().itemsLayout("horizontalExpandable").margin(3);

        anyChartView1.setChart(cartesian);

    }

    public static ArrayList<CCAA> crearListaCCAA(){
        ArrayList<CCAA> lista=new ArrayList<>();
        lista.add(new CCAA("Andalucía","es-an",0));
        lista.add(new CCAA("Aragón", "es-ar",R.drawable.aragon));
        lista.add(new CCAA("Asturias","es-as",R.drawable.asturias));
        lista.add(new CCAA("Canarias","es-ib",0));
        lista.add(new CCAA("Cantabria", "es-cb",R.drawable.cantabria));
        lista.add(new CCAA("Castilla-La Mancha","es-cm",R.drawable.castillalamancha));
        lista.add(new CCAA("Castilla León","es-cl",0));
        lista.add(new CCAA("Cataluña","es-ct",R.drawable.cataluna));
        lista.add(new CCAA("Ceuta","es-ce",0));
        lista.add(new CCAA("Comunidad de Madrid","es-md",0));
        lista.add(new CCAA("Comunidad Valenciana","es-vc",0));
        lista.add(new CCAA("Extremadura","es-ex",0));
        lista.add(new CCAA("Galicia","es-ga",R.drawable.galicia));
        lista.add(new CCAA("Islas Baleares","es-ib",0));
        lista.add(new CCAA("La Rioja","es-ri",R.drawable.larioja));
        lista.add(new CCAA("Melilla","es-ml",0));
        lista.add(new CCAA("Murcia","es-mc",0));
        lista.add(new CCAA("Navarra","es-nc",0));
        lista.add(new CCAA("Pais Vasco","es-pv",R.drawable.paisvasco));
        return lista;
    }



}
