package com.covidata.covidata;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class AutodiagnosticoRecomendacionesFragment extends Fragment {
    View view;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1,tab2;
    private PageController pageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_autodiagnostico_recomendaciones, container, false);


        tabLayout=view.findViewById(R.id.tabLayout);
        tab1=view.findViewById(R.id.tab1);
        tab2=view.findViewById(R.id.tab2);
        viewPager=view.findViewById(R.id.viewPager);

        pageAdapter = new PageController(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    pageAdapter.notifyDataSetChanged();
                }
                if(tab.getPosition()==1){
                    pageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        return view;
    }
}
