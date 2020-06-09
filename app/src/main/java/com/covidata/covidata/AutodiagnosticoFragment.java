package com.covidata.covidata;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class AutodiagnosticoFragment extends Fragment {

    View view;
    ArrayList<CCAATelefonos> listaCCAA;
    Spinner mSpinner;
    CCAATelefonos elemento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_autodiagnostico, container, false);


        listaCCAA=crearListaCCAA();
        ArrayAdapter<CCAATelefonos> adp = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaCCAA);
        mSpinner=view.findViewById(R.id.mSpinner);

        mSpinner.setAdapter(adp);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                elemento = (CCAATelefonos) mSpinner.getAdapter().getItem(position);
                ((TextView) parent.getChildAt(0)).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "montserratregular.ttf"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    public static ArrayList<CCAATelefonos> crearListaCCAA(){
        ArrayList<CCAATelefonos> lista=new ArrayList<>();
        lista.add(new CCAATelefonos("Andalucía","900 400 061"));
        lista.add(new CCAATelefonos("Aragón","976 696 382"));
        lista.add(new CCAATelefonos("Asturias","984 100 400"));
        lista.add(new CCAATelefonos("Cantabria","900 612 112"));
        lista.add(new CCAATelefonos("Castilla-La Mancha","900 112 112"));
        lista.add(new CCAATelefonos("Castilla León","900 222 000"));
        lista.add(new CCAATelefonos("Cataluña","061"));
        lista.add(new CCAATelefonos("Extremadura","112"));
        lista.add(new CCAATelefonos("Galicia","900 400 116"));
        lista.add(new CCAATelefonos("Islas Baleares","061"));
        lista.add(new CCAATelefonos("Canarias","900 112 061"));
        lista.add(new CCAATelefonos("La Rioja","941 298 333"));
        lista.add(new CCAATelefonos("Comunidad de Madrid","900 102 112"));
        lista.add(new CCAATelefonos("Murcia","900 121 212"));
        lista.add(new CCAATelefonos("Navarra","948 290 290"));
        lista.add(new CCAATelefonos("Pais Vasco","900 203 050"));
        lista.add(new CCAATelefonos("Comunidad Valenciana","900 300 555"));
        lista.add(new CCAATelefonos("Ceuta","900 720 692"));
        lista.add(new CCAATelefonos("Melilla","112"));
        return lista;
    }


}
