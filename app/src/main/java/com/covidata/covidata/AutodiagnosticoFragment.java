package com.covidata.covidata;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class AutodiagnosticoFragment extends Fragment{

    View view;
    ArrayList<CCAATelefonos> listaCCAA;
    Spinner mSpinner;
    CCAATelefonos elemento;
    EditText edad;
    CheckBox hipertension, diabetes, obesidad, cancer, enfermedadRenal, cardiopatia, inmunoSupresion, patologiaPulmonar, neoplastias, patologiaEpatica;
    CheckBox fiebre, cansancio, tos, respirar, diarrea, dolorGarganta, vomitos, dolorCabeza;
    RadioButton si, no;
    Button boton;


    int contadorEnfermedades;
    int contadorSintomas;
    int contadoredad;
    int contadorContacto;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_autodiagnostico, container, false);


        listaCCAA=crearListaCCAA();
        ArrayAdapter<CCAATelefonos> adp = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listaCCAA);
        mSpinner=view.findViewById(R.id.mSpinner);

        edad=view.findViewById(R.id.edad);
        hipertension=view.findViewById(R.id.hipertension);
        diabetes=view.findViewById(R.id.diabetes);
        obesidad=view.findViewById(R.id.obesidad);
        cancer=view.findViewById(R.id.cancer);
        enfermedadRenal=view.findViewById(R.id.enfermedadRenal);
        cardiopatia=view.findViewById(R.id.cardiopatia);
        inmunoSupresion=view.findViewById(R.id.inmunosupresion);
        patologiaPulmonar=view.findViewById(R.id.patologiaPulmonar);
        neoplastias=view.findViewById(R.id.neoplatiaActiva);
        patologiaEpatica=view.findViewById(R.id.patologiaHepatica);
        fiebre=view.findViewById(R.id.fiebre);
        cansancio=view.findViewById(R.id.cansancio);
        tos=view.findViewById(R.id.tos);
        respirar=view.findViewById(R.id.respirar);
        diarrea=view.findViewById(R.id.diarrea);
        dolorGarganta=view.findViewById(R.id.dolorGarganta);
        vomitos=view.findViewById(R.id.vomitos);
        dolorCabeza=view.findViewById(R.id.dolorCabeza);
        si=view.findViewById(R.id.radio_si);
        no=view.findViewById(R.id.radio_no);
        boton=view.findViewById(R.id.boton);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String edadRecogida=edad.getText().toString();
                int edadEntero=0;

                if(edadRecogida.equals("")){
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Introduzca la edad", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                }else{
                    edadEntero=Integer.valueOf(edadRecogida);
                }

                if(edadEntero>=45 && edadEntero <=60){
                    contadoredad++;
                }else if(edadEntero>60){
                    contadoredad=+2;
                }

                if(fiebre.isChecked()){
                    contadorSintomas++;
                }
                if(cansancio.isChecked()){
                    contadorSintomas++;
                }
                if(tos.isChecked()){
                    contadorSintomas++;
                }
                if(respirar.isChecked()){
                    contadorSintomas++;
                }
                if(diarrea.isChecked()){
                    contadorSintomas++;
                }
                if(dolorGarganta.isChecked()){
                    contadorSintomas++;
                }
                if(vomitos.isChecked()){
                    contadorSintomas++;
                }
                if(dolorCabeza.isChecked()){
                    contadorSintomas++;
                }


                if (hipertension.isChecked()){
                    contadorEnfermedades++;
                }
                if (diabetes.isChecked()){
                    contadorEnfermedades++;
                }
                if (obesidad.isChecked()){
                    contadorEnfermedades++;
                }
                if (cancer.isChecked()){
                    contadorEnfermedades++;
                }
                if (enfermedadRenal.isChecked()){
                    contadorEnfermedades++;
                }
                if (cardiopatia.isChecked()){
                    contadorEnfermedades++;
                }
                if (inmunoSupresion.isChecked()){
                    contadorEnfermedades++;
                }
                if (patologiaPulmonar.isChecked()){
                    contadorEnfermedades++;
                }
                if (neoplastias.isChecked()){
                    contadorEnfermedades++;
                }
                if (patologiaEpatica.isChecked()){
                    contadorEnfermedades++;
                }

                if(si.isChecked()){
                    contadorContacto++;
                }
                if(no.isChecked()){
                    contadorContacto++;
                }



                Log.e("contadorEdad",""+contadoredad);
                Log.e("contadorSintomas",""+contadorSintomas);
                Log.e("contadorEnfermedades",""+contadorEnfermedades);
                Log.e("contadorContacto",""+contadorContacto);

            }
        });


        mSpinner.setAdapter(adp);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                elemento = (CCAATelefonos) mSpinner.getAdapter().getItem(position);
                elemento.getNombre();
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
