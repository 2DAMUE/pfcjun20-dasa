package com.covidata.covidata;

import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.appcompat.app.AlertDialog;
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

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombreCCAA=elemento.getNombre();
                String telefonoCCAA=elemento.getTelefono();
                int imagen = elemento.getImagen();

                String edadRecogida=edad.getText().toString();
                int edadEntero=0;

                if(edadRecogida.equals("")){
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Introduzca la edad", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                }else{
                    try {
                        edadEntero=Integer.valueOf(edadRecogida);
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

                        String texto="";
                        int contadorFinal=contadorContacto+contadorSintomas+contadorEnfermedades+contadoredad;
                        if(contadorFinal>=6){
                            texto="Está en riesgo, llame al siguiente teléfono";
                            showAlertDialogButtonClicked(telefonoCCAA,nombreCCAA,R.color.rojo,texto,imagen);
                        }else if(contadorFinal>=4 && contadorFinal<=5){
                            texto="Puede estar en riesgo, llame al siguiente teléfono";
                            showAlertDialogButtonClicked(telefonoCCAA,nombreCCAA,R.color.naranja,texto,imagen);
                        }else if(contadorFinal>=2 && contadorFinal<=3){
                            texto="Vigile sus sintomas y en caso de empeorar llame al siguiente teléfono";
                            showAlertDialogButtonClicked(telefonoCCAA,nombreCCAA,R.color.amarillo,texto,imagen);
                        }else if(contadorFinal<2){
                            texto="Hay un riesgo muy bajo de que tenga la Covid-19";
                            showAlertDialogButtonClicked("",nombreCCAA,R.color.verde,texto,imagen);
                        }
                    } catch (NumberFormatException nfe){
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "El campo edad es incorrecto", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                        toast.show();
                    }


                }

                Log.e("contadorEdad",""+contadoredad);
                Log.e("contadorSintomas",""+contadorSintomas);
                Log.e("contadorEnfermedades",""+contadorEnfermedades);
                Log.e("contadorContacto",""+contadorContacto);

            }
        });

        return view;
    }


    public void showAlertDialogButtonClicked(String telefono, String nombre, int color,String texto,int imagen) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.MyAlertDialogTheme);
        View view2 =  getLayoutInflater().inflate(R.layout.ventana_emergente, null);

        builder.setView(view2);
        builder.setTitle(nombre);

        builder.setPositiveButton("Volver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        contadorContacto=0;
        contadoredad=0;
        contadorEnfermedades=0;
        contadorSintomas=0;

        AlertDialog dialog = builder.create();
        view2.findViewById(R.id.imagen).setBackgroundResource(imagen);
        view2.findViewById(R.id.frame).setBackgroundColor(getResources().getColor(color));
        TextView tvNombre=view2.findViewById(R.id.nombre);
        tvNombre.setText(texto);
        TextView tvTelefono=view2.findViewById(R.id.telefono);
        tvTelefono.setText(telefono);
        dialog.show();
    }




    public static ArrayList<CCAATelefonos> crearListaCCAA(){
        ArrayList<CCAATelefonos> lista=new ArrayList<>();
        lista.add(new CCAATelefonos("Andalucía","900 400 061",R.drawable.andalucia));
        lista.add(new CCAATelefonos("Aragón","976 696 382",R.drawable.aragon));
        lista.add(new CCAATelefonos("Asturias","984 100 400",R.drawable.asturias));
        lista.add(new CCAATelefonos("Canarias","900 112 061",R.drawable.canarias));
        lista.add(new CCAATelefonos("Cantabria","900 612 112",R.drawable.cantabria));
        lista.add(new CCAATelefonos("Castilla-La Mancha","900 112 112",R.drawable.castillalamancha));
        lista.add(new CCAATelefonos("Castilla León","900 222 000",R.drawable.castillaleon));
        lista.add(new CCAATelefonos("Cataluña","061",R.drawable.cataluna));
        lista.add(new CCAATelefonos("Ceuta","900 720 692",R.drawable.ceuta));
        lista.add(new CCAATelefonos("Comunidad de Madrid","900 102 112",R.drawable.madrid));
        lista.add(new CCAATelefonos("Comunidad Valenciana","900 300 555",R.drawable.valencia));
        lista.add(new CCAATelefonos("Extremadura","112",R.drawable.extremadura));
        lista.add(new CCAATelefonos("Galicia","900 400 116",R.drawable.galicia));
        lista.add(new CCAATelefonos("Islas Baleares","061",R.drawable.baleares));
        lista.add(new CCAATelefonos("La Rioja","941 298 333",R.drawable.larioja));
        lista.add(new CCAATelefonos("Melilla","112",R.drawable.melilla));
        lista.add(new CCAATelefonos("Murcia","900 121 212",R.drawable.murcia));
        lista.add(new CCAATelefonos("Navarra","948 290 290",R.drawable.navarra));
        lista.add(new CCAATelefonos("Pais Vasco","900 203 050",R.drawable.paisvasco));



        return lista;
    }

}
