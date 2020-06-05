package com.covidata.covidata;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.anychart.graphics.vector.Stroke;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ComunidadAutonomaFragment extends Fragment {

    View view;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_comunidad_autonoma, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        ArrayList<String> lista=crearListaCCAA();
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(lista);
        recyclerView.setAdapter((recyclerAdapter));

        return view;
    }

    public static ArrayList<String> crearListaCCAA(){
        ArrayList<String> lista=new ArrayList<>();
        lista.add("Andalucía");
        lista.add("Aragón");
        lista.add("Asturias");
        lista.add("Cantabria");
        lista.add("Castilla-La Mancha");
        lista.add("Castilla León");
        lista.add("Cataluña");
        lista.add("Extremadura");
        lista.add("Galicia");
        lista.add("Islas Baleares");
        lista.add("Canarias");
        lista.add("La Rioja");
        lista.add("Comunidad de Madrid");
        lista.add("Murcia");
        lista.add("Navarra");
        lista.add("Pais Vasco");
        lista.add("Comunidad Valenciana");
        lista.add("Ceuta");
        lista.add("Melilla");
        return lista;
    }


}
