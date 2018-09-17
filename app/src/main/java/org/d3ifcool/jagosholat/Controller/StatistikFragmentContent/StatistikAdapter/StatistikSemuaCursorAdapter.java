package org.d3ifcool.jagosholat.Controller.StatistikFragmentContent.StatistikAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.d3ifcool.jagosholat.R;
import org.d3ifcool.jagosholat.Model.DataContract.DataEntry;

public class StatistikSemuaCursorAdapter extends CursorAdapter {

    public StatistikSemuaCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Mereturn layout content dari listview yang akan di pakai
        // disini mereturn content_statistik_semua
        return LayoutInflater.from(context).inflate(R.layout.content_statistik_semua, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // -----------------------------------------------------------------------------------------
        // Deklarasi Element XML
        TextView stat_tanggal = view.findViewById(R.id.txt_stat_semua_tanggal);
        TextView stat_waktu = view.findViewById(R.id.txt_stat_semua_waktu);
        TextView stat_shalat = view.findViewById(R.id.txt_stat_semua_shalat);
        // -----------------------------------------------------------------------------------------

        // -----------------------------------------------------------------------------------------
        // Mencari index dalam database
        int waktuColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_WAKTU);
        int shalatColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_SHALAT);
        int tanggalColumnIndex = cursor.getColumnIndex(DataEntry.COLUMN_TANGGAL);
        // -----------------------------------------------------------------------------------------

        // -----------------------------------------------------------------------------------------
        // Mendapat data dari database berdasarkan index
        String waktu = cursor.getString(waktuColumnIndex);
        String shalat = cursor.getString(shalatColumnIndex);
        String tanggal = cursor.getString(tanggalColumnIndex);
        // -----------------------------------------------------------------------------------------

        // -----------------------------------------------------------------------------------------
        // Untuk Merubah Shalat Dzuhur menjadi -> DZUHUR
        String subStringShalat = shalat.substring(7, shalat.length());
        String upperCaseShalat = subStringShalat.toUpperCase();
        // -----------------------------------------------------------------------------------------

        // -----------------------------------------------------------------------------------------
        // Menset Text berdasarkan String yang sudah di dapat
        stat_shalat.setText(upperCaseShalat);
        stat_waktu.setText(waktu);
        stat_tanggal.setText(tanggal);
        // -----------------------------------------------------------------------------------------
    }


}
