package com.covidata.covidata;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class SpainFragment extends Fragment implements TareaAsincrona.rellenarArrayList{

    View view;
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_spain, container, false);

        //Obtengo la fecha actual
        Date objDate = new Date();
        System.out.println(objDate);
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
        String fecha=objSDF.format(objDate);
        tv=view.findViewById(R.id.confirmados);
        TareaAsincrona simpleTask= new TareaAsincrona(this);
        simpleTask.execute();

        return view;
    }



    @Override
    public void rellenaRecycler(ArrayList<DatoSpain> lista) {
        String conf= lista.get(lista.size()-1).getConfirmados();
        String recuperados="";
        for (DatoSpain ds:lista) {
            if(!ds.getRecuperados().isEmpty()){
                recuperados=ds.getRecuperados();
            }
        }

        tv.setText(recuperados);
    }
}