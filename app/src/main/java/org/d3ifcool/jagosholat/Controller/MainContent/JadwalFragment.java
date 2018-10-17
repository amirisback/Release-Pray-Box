package org.d3ifcool.jagosholat.Controller.MainContent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.d3ifcool.jagosholat.Controller.Helper.MethodHelper;
import org.d3ifcool.jagosholat.Controller.Helper.JadwalHelper;
import org.d3ifcool.jagosholat.R;


public class JadwalFragment extends Fragment {

    // Deklarasi Class Helper ----------------------------------------------------------------------
    private JadwalHelper jadwalHelper = new JadwalHelper();
    private MethodHelper methodHelper = new MethodHelper();
    // ---------------------------------------------------------------------------------------------

    // Deklarasi Requirement Variable --------------------------------------------------------------
    private int countTime;
    private final String SHUBUH = "Shalat Shubuh";
    private final String DZUHUR = "Shalat Dzuhur";
    private final String ASHAR = "Shalat Ashar";
    private final String MAGHRIB = "Shalat Maghrib";
    private final String ISYA = "Shalat Isya";
    private TextView txt_sholat_sekarang ;
    private int jumlahDetikSaatIni = methodHelper.getSumWaktuDetik();
    private int miliDetik = methodHelper.getDetikKeMiliDetik();
    // ---------------------------------------------------------------------------------------------

    // Deklarasi Element Layout XML ----------------------------------------------------------------
    private TextView txt_coundown, txt_shalat, txt_waktu_shubuh, txt_waktu_dzuhur, txt_waktu_ashar,
            txt_waktu_maghrib, txt_waktu_isya;
    // ---------------------------------------------------------------------------------------------

    public JadwalFragment() {
        // Required empty public constructor
    }

    public void CekJadwal(){
        if (jadwalHelper.getMJadwalShalat().equals(SHUBUH)){
            txt_shalat.setText(DZUHUR.substring(7));
            countTime = (jadwalHelper.getJmlWaktuDzuhur() - jumlahDetikSaatIni) * miliDetik;
            
        } else if (jadwalHelper.getMJadwalShalat().equals(DZUHUR)){
            txt_shalat.setText(ASHAR.substring(7));
            countTime = (jadwalHelper.getJmlWaktuAshar() - jumlahDetikSaatIni) * miliDetik;
            
        } else if (jadwalHelper.getMJadwalShalat().equals(ASHAR)){
            txt_shalat.setText(MAGHRIB.substring(7));
            countTime = (jadwalHelper.getJmlWaktuMaghrib() - jumlahDetikSaatIni) * miliDetik;
            
        } else if (jadwalHelper.getMJadwalShalat().equals(MAGHRIB)){
            txt_shalat.setText(ISYA.substring(7));
            countTime = (jadwalHelper.getJmlWaktuIsya() - jumlahDetikSaatIni) * miliDetik;
            
        } else if (jadwalHelper.getMJadwalShalat().equals(ISYA)){
            txt_shalat.setText(SHUBUH.substring(7));
            if ((jumlahDetikSaatIni == jadwalHelper.getJmlAftMidnight()) || (jumlahDetikSaatIni < jadwalHelper.getJmlWaktuShubuh())) {
                countTime = (jadwalHelper.getJmlWaktuShubuh() - jumlahDetikSaatIni) * miliDetik;
            } else if ((jumlahDetikSaatIni == jadwalHelper.getJmlWaktuIsya()) || (jumlahDetikSaatIni <= jadwalHelper.getJmlBeMidnight())) {
                countTime =  (jadwalHelper.getJmlWaktuShubuh() + jadwalHelper.getJmlBeMidnight() - jumlahDetikSaatIni) * miliDetik;
            }

        } else {
            txt_shalat.setText(DZUHUR.substring(7));
            countTime = (jadwalHelper.getJmlWaktuDzuhur() - jumlahDetikSaatIni) * miliDetik;
            
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.constraint_jadwal, container, false);

        // -----------------------------------------------------------------------------------------
        // Deklarasi Elemen XML
        txt_coundown = (TextView)rootView.findViewById(R.id.countDown);
        txt_shalat = (TextView)rootView.findViewById(R.id.txt_sholat);
        txt_waktu_shubuh = (TextView)rootView.findViewById(R.id.txt_waktu_shubuh);
        txt_waktu_dzuhur = (TextView)rootView.findViewById(R.id.txt_waktu_dzuhur);
        txt_waktu_ashar = (TextView)rootView.findViewById(R.id.txt_waktu_ashar);
        txt_waktu_maghrib = (TextView)rootView.findViewById(R.id.txt_waktu_maghrib);
        txt_waktu_isya = (TextView)rootView.findViewById(R.id.txt_waktu_isya);
        txt_sholat_sekarang = (TextView) rootView.findViewById(R.id.txt_sholat_sekarang);
        // -----------------------------------------------------------------------------------------
        //set waktu sholat saat ini
        jadwalHelper.setJadwalShalat(txt_sholat_sekarang);
        // -----------------------------------------------------------------------------------------
        CekJadwal();
        jadwalHelper.setTimeOnline(txt_waktu_shubuh, txt_waktu_dzuhur, txt_waktu_ashar, txt_waktu_maghrib, txt_waktu_isya);
        jadwalHelper.CoundownTime(countTime, txt_coundown);
        // -----------------------------------------------------------------------------------------



        return rootView;
    }

}
