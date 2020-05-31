package com.covidata.covidata;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.util.ArrayList;
import java.util.List;

public class GraficoSpainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_spain);

        setTitle("Evolución");

        ArrayList<DatoSpain> lista = (ArrayList<DatoSpain>) getIntent().getSerializableExtra("lista");
        crearGrafico(lista);


    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent2 = new Intent(this,MainActivity.class);
        intent2.putExtra("dato", "1");
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent2);
        finish();
        return true;
    }

    public void crearGrafico(ArrayList<DatoSpain> lista){

        AnyChartView anyChartView = findViewById(R.id.graficoPais);
        anyChartView.addFont("Montserrat", "file:///android_asset/montserratregular.ttf");

        Cartesian cartesian = AnyChart.line();
        cartesian.height("685px");
        cartesian.width("390px");

        cartesian.animation(true);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Evolución Covid-19 en España").margin("10px");
        cartesian.title().fontFamily("Montserrat");

        cartesian.yAxis(0).labels().fontFamily("Montserrat");
        cartesian.xAxis(0).labels().fontFamily("Montserrat").fontSize(11).padding(6d);

        List<DataEntry> seriesData = new ArrayList<>();
        String recuperados="";
        String confirmados="";
        String fallecidos="";
        int contador=0;

        for (DatoSpain ds:lista) {
            if(!ds.getRecuperados().isEmpty()){
                recuperados=ds.getRecuperados();
            }else if(ds.getRecuperados().isEmpty() && contador>23){
                recuperados="150376";
            }else{
                recuperados="0";
            }
            if(ds.getConfirmados().isEmpty()){
                confirmados="0";
            }else{
                confirmados=ds.getConfirmados();
            }
            if(ds.getFallecidos().isEmpty()){
                fallecidos="0";
            }else{
                fallecidos=ds.getFallecidos();
            }

            seriesData.add(new CustomDataEntry(ds.getFecha(), Integer.parseInt(confirmados),
                    Integer.parseInt(fallecidos), Integer.parseInt(recuperados)));
            contador++;
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

        anyChartView.setChart(cartesian);
    }


    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
