package com.frogobox.praybox.view.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.frogobox.praybox.R
import com.frogobox.praybox.base.view.ui.BaseFragment
import com.frogobox.praybox.source.local.DataOperation
import com.frogobox.praybox.util.helper.MethodHelper
import com.frogobox.praybox.util.helper.WaktuShalatHelper
import com.frogobox.praybox.view.ui.dialog.CatatanCustomDialog
import kotlinx.android.synthetic.main.fragment_catatan.*
import java.util.*

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * 40-mobpro-4002-jago-sholat
 * Copyright (C) 21/09/2018.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Line     : bullbee117
 * Phone    : 081357108568
 * Majors   : D3 Teknik Informatika 2016
 * Campus   : Telkom University
 * -----------------------------------------
 * id.amirisback.frogobox
 */
class CatatanFragment : BaseFragment() {

    // Deklarasi Requirement Constants
    private val mHadistArab = intArrayOf(R.string.hadis_arab_0, R.string.hadis_arab_1, R.string.hadis_arab_2,
            R.string.hadis_arab_3, R.string.hadis_arab_4, R.string.hadis_arab_5)
    private val mHadistText = intArrayOf(R.string.hadis_text_0, R.string.hadis_text_1, R.string.hadis_text_2,
            R.string.hadis_text_3, R.string.hadis_text_4, R.string.hadis_text_5)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_catatan, container, false)

        val methodHelper = MethodHelper()
        val waktuShalatHelper = WaktuShalatHelper()
        val crud = DataOperation()

        // Set tampilan tanggal dan waktu
        methodHelper.getSystemTime()
        methodHelper.getSystemRealTime()
        methodHelper.getSumRealTime()
        waktuShalatHelper.setJadwalShalat(jadwal_textview_shalat)
        catatan_textview_tanggal.text = methodHelper.dateToday

        // Set Data Random Hadist untuk XML Layout
        val randomInt = Random()
        val maxRandom = mHadistArab.size - 1
        val minRandom = 0
        val getIndexArrayHadis = randomInt.nextInt(maxRandom - minRandom + 1) + minRandom

        val mResIdHadistArab = mHadistArab[getIndexArrayHadis]
        val mResIdHadistText = mHadistText[getIndexArrayHadis]
        catatan_textview_hadist_arab.setText(mResIdHadistArab)
        catatan_textview_hadist_text.setText(mResIdHadistText)

        // Deklarasi Element XML Update View
        val mDialogBuilder = AlertDialog.Builder(context)
        val mDialogView = inflater.inflate(R.layout.content_catatan_button, null)

        // Deklarasi custom dialog
        val mDialogForm = CatatanCustomDialog(mDialogBuilder, mDialogView,
                methodHelper, context, crud, waktuShalatHelper)

        // Fungsi untuk menampilkan button simpan
        val mShalatNow = jadwal_textview_shalat.text.toString()
        mDialogForm.viewSaveButton(catatan_button_catat_ibadah, mShalatNow)

        // Panggil method untuk mencatat
        catatan_button_catat_ibadah.setOnClickListener { mDialogForm.DialogForm() }

        return rootView
    }
}