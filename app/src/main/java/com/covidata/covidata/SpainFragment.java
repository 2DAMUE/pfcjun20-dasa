package com.covidata.covidata;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.Text;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

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


public class SpainFragment extends Fragment implements TareaAsincrona.rellenarArrayList{

    View view;
    TextView cajaConfirmadosHoy, cajaConfirmadosAyer, cajaFallecidosHoy, cajaFallecidosAyer, cajaRecuperadosHoy, cajaRecuperadosAyer,
            cajatexto, cajaFechaAyer;
    Button boton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_spain, container, false);
        cajatexto=view.findViewById(R.id.texto);
        boton=view.findViewById(R.id.botonGrafico);
        cajaConfirmadosHoy=view.findViewById(R.id.confirmadosHoy);
        cajaConfirmadosAyer=view.findViewById(R.id.confirmadosAyer);
        cajaFallecidosHoy=view.findViewById(R.id.fallecidosHoy);
        cajaFallecidosAyer=view.findViewById(R.id.fallecidosAyer);
        cajaRecuperadosHoy=view.findViewById(R.id.recuperadosHoy);
        cajaRecuperadosAyer=view.findViewById(R.id.recuperadosAyer);
        cajaFechaAyer=view.findViewById(R.id.fecha);

        //Obtengo la fecha actual
        Date objDate = new Date();
        System.out.println(objDate);
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
        String fecha=objSDF.format(objDate);

        TareaAsincrona simpleTask= new TareaAsincrona(this);
        simpleTask.execute();




        return view;
    }

    @Override
    public void rellenaRecycler(final ArrayList<DatoSpain> lista) {

        String recuperados="";
        for (DatoSpain ds:lista) {
            if(!ds.getRecuperados().isEmpty()){
                recuperados=ds.getRecuperados();
            }
        }

        int nRecuperados = Integer.parseInt(recuperados);
        int confirmadosAyer=Integer.parseInt(lista.get(lista.size()-2).getConfirmados());
        int fallecidosAyer=Integer.parseInt(lista.get(lista.size()-2).getFallecidos());

        cajaConfirmadosAyer.setText(NumberFormat.getInstance().format(confirmadosAyer));
        cajaFallecidosAyer.setText(NumberFormat.getInstance().format(fallecidosAyer));
        cajaRecuperadosAyer.setText(NumberFormat.getInstance().format(nRecuperados));

        int confirmadosHoy=Integer.parseInt(lista.get(lista.size()-1).getConfirmados());
        int fallecidosHoy=Integer.parseInt(lista.get(lista.size()-1).getFallecidos());
        cajaConfirmadosHoy.setText(NumberFormat.getInstance().format(confirmadosHoy));
        cajaFallecidosHoy.setText(NumberFormat.getInstance().format(fallecidosHoy));
        cajaRecuperadosHoy.setText(NumberFormat.getInstance().format(nRecuperados));

        cajatexto.setText("*Datos actualizados a día "+lista.get(lista.size()-1).getFecha());
        cajaFechaAyer.setText(lista.get(lista.size()-2).getFecha());
        crearGrafico(lista.get(lista.size()-1),recuperados);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GraficoSpainActivity.class);
                intent.putExtra("lista", lista);
                startActivity(intent);
            }
        });

    }

    public void crearGrafico(DatoSpain ds,String recuperados){

        AnyChartView anyChartView = view.findViewById(R.id.graficoPais);

        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));

        Pie pie = AnyChart.pie();

        String colores[] = {"#BF8A26", "#591E3A", "#30728C"};

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Confirmados", Integer.parseInt(ds.getConfirmados())));
        data.add(new ValueDataEntry("Fallecidos", Integer.parseInt(ds.getFallecidos())));
        data.add(new ValueDataEntry("Recuperados", Integer.parseInt(recuperados)));

        pie.data(data);

        pie.labels().enabled(false);
        pie.selected().explode("0%");
        pie.palette(colores);
        pie.tooltip().enabled(false);


        pie.legend()
                .enabled(false);

        anyChartView.setChart(pie);
    }








}