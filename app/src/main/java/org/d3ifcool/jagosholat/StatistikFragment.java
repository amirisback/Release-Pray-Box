package org.d3ifcool.jagosholat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.d3ifcool.jagosholat.StatistikFragmentContent.StatistikGrafikFragment;
import org.d3ifcool.jagosholat.StatistikFragmentContent.StatistikHarianFragment;
import org.d3ifcool.jagosholat.StatistikFragmentContent.StatistikSemuaFragment;

public class StatistikFragment extends Fragment {

    public StatistikFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_statistik, container, false);

        // -----------------------------------------------------------------------------------------
        // Deklarasi Element XML
        Button btn_statGrafik = rootView.findViewById(R.id.btn_statGrafik);
        Button btn_statHarian = rootView.findViewById(R.id.btn_statHarian);
        Button btn_statSemua = rootView.findViewById(R.id.btn_statSemua);
        // -----------------------------------------------------------------------------------------

        // -----------------------------------------------------------------------------------------
        // Fragment Pertama yang di panggil yaitu Grafik
        StatistikGrafikFragment mStatistikGrafikFragment = new StatistikGrafikFragment();
        exchangeFragment(R.id.fragStatistik, mStatistikGrafikFragment);
        // -----------------------------------------------------------------------------------------

        // -----------------------------------------------------------------------------------------
        // Button pada Statistik Fragment
        btn_statHarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatistikHarianFragment mStatistikHarianFragment = new StatistikHarianFragment();
                exchangeFragment(R.id.fragStatistik, mStatistikHarianFragment);
            }
        });

        btn_statGrafik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatistikGrafikFragment mStatistikGrafikFragment = new StatistikGrafikFragment();
                exchangeFragment(R.id.fragStatistik, mStatistikGrafikFragment);
            }
        });

        btn_statSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatistikSemuaFragment mStatistikSemuaFragment = new StatistikSemuaFragment();
                exchangeFragment(R.id.fragStatistik, mStatistikSemuaFragment);
            }
        });
        // -----------------------------------------------------------------------------------------

        return rootView;
    }


    // Method untuk pindah Fragment ----------------------------------------------------------------
    public void exchangeFragment(int frameLayout, Fragment mFragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(frameLayout, mFragment);
        ft.commit();
    }
    // ---------------------------------------------------------------------------------------------

    public static class MyXAxisValueFormatter implements IAxisValueFormatter {
        private String[] mValues;
        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }
}
