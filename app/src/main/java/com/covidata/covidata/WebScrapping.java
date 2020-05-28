package com.covidata.covidata;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class WebScrapping {

    static String url_base="https://github.com/datadista/datasets/blob/master/COVID%2019/nacional_covid19.csv";


    public static ArrayList<DatoSpain> webScrapping() {
        ArrayList<DatoSpain> lista= new ArrayList();

        try {
            Document d = Jsoup.connect(url_base).get();
            Elements listatr = d.select("tbody>tr.js-file-line");

            for (Element element : listatr) {
                Elements tds = element.select("td");
                String fecha=tds.get(1).text();
                String contagiados=tds.get(3).text();
                String fallecidos=tds.get(6).text();
                String recuperados=tds.get(5).text();
                DatoSpain ds=new DatoSpain(fecha,contagiados,fallecidos,recuperados);
                lista.add(ds);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;

    }
}
