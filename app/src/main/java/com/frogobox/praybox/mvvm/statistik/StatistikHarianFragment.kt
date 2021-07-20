package com.frogobox.praybox.mvvm.statistik

import android.app.AlertDialog
import android.database.Cursor
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frogobox.praybox.R
import com.frogobox.praybox.core.BaseFragment
import com.frogobox.praybox.source.local.DataContract
import com.frogobox.praybox.source.local.DataOperation
import com.frogobox.praybox.util.SingleFunc
import java.util.*

class StatistikHarianFragment : BaseFragment(), LoaderManager.LoaderCallbacks<Cursor> {
    // ---------------------------------------------------------------------------------------------
    // Deklarasi Class helper yang diperlukan
    private val mMethodHelper = SingleFunc.Controller
    private val mDataOperation = DataOperation()
    private var mCursorAdapter: StatistikViewAdapter? = null
    private var mActionMode: ActionMode? = null

    // ---------------------------------------------------------------------------------------------
    private val mActionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onCreateActionMode(actionMode: ActionMode, menu: Menu): Boolean {
            actionMode.menuInflater.inflate(R.menu.menu_action, menu)
            return true
        }

        override fun onPrepareActionMode(actionMode: ActionMode, menu: Menu): Boolean {
            (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
            actionMode.title = mCursorAdapter!!.selectionCount().toString()
            return true
        }

        override fun onActionItemClicked(actionMode: ActionMode, menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.action_delete -> {
                    deleteData()
                    return true
                }
            }
            return false
        }

        override fun onDestroyActionMode(actionMode: ActionMode) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
            mActionMode = null
            mCursorAdapter!!.resetSelection()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_statistik_harian, container, false)
        // -----------------------------------------------------------------------------------------
        val empty_listView = rootView.findViewById<View>(R.id.statistik_view_emptyview)
        val mRecyclerView: RecyclerView = rootView.findViewById(R.id.statistik_list_data)
        val mProgressBar = rootView.findViewById<ProgressBar>(R.id.statistik_progress_bar)
        val mTextViewPercentage = rootView.findViewById<TextView>(R.id.statistik_textview_bar)
        // -----------------------------------------------------------------------------------------
        // Deklarasi Element XML Update View
        val mDialogBuilder = AlertDialog.Builder(context)
        val mDialogView = inflater.inflate(R.layout.content_statistik_update, null)
        // -----------------------------------------------------------------------------------------
        val mDialogForm =
            StatistikDialog(mDialogBuilder, mDialogView, mMethodHelper, context, mDataOperation)
        // -----------------------------------------------------------------------------------------
        val percen = "$progress%"
        mTextViewPercentage.text = percen
        mProgressBar.progress = progress
        if (progress != 0) {
            empty_listView.visibility = View.GONE
        } else {
            empty_listView.visibility = View.VISIBLE
        }
        // -----------------------------------------------------------------------------------------
        val cursor = mDataOperation.getDataToday(context, mMethodHelper.dateToday)
        mCursorAdapter = StatistikViewAdapter(context, cursor)
        val mLayoutManager = LinearLayoutManager(context)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mCursorAdapter
        // -----------------------------------------------------------------------------------------
        return rootView
    }

    // ---------------------------------------------------------------------------------------------
    val progress: Int
        get() {
            val cursor = mDataOperation.getDataToday(context, mMethodHelper.dateToday)
            val countTable = cursor.count
            return countTable * 20
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        loaderManager.initLoader(DATA_LOADER, null, this)
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val selection =
            DataContract.DataEntry.COLUMN_TANGGAL + " = '" + mMethodHelper.dateToday + "'"
        return CursorLoader(
            requireContext(),  // getContext disini menggantikan this
            DataContract.DataEntry.CONTENT_URI,
            mDataOperation.projection,
            selection,
            null,
            null
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        mCursorAdapter!!.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        mCursorAdapter!!.swapCursor(null)
    }

    private fun deleteData() {
        // -----------------------------------------------------------------------------------------
        val messages: String
        val selectedId = mCursorAdapter!!.getmSelectedId()
        val selectedDataId = mCursorAdapter!!.getmSelectedDataId()
        if (selectedId.size == 1) {
            messages = getString(R.string.delete_data)
        } else {
            messages = getString(R.string.deletes_data)
            Collections.sort(selectedId) { integer, t1 -> t1.compareTo(integer) }
        }
        // ---------------------------------------------------------------------------------------------

        // -----------------------------------------------------------------------------------------
        val builder = androidx.appcompat.app.AlertDialog.Builder(
            requireContext()
        )
        builder.setTitle(R.string.delete_data).setMessage(messages)
            .setPositiveButton(R.string.delete) { dialogInterface, i ->
                for (currentPedId in selectedId.indices) {
                    val databaseID = selectedDataId[currentPedId]
                    val delete = mDataOperation.deleteDataId(context, databaseID)
                }
                mCursorAdapter!!.notifyDataSetChanged()
                mActionMode!!.finish()
            }
            .setNegativeButton(R.string.cancel) { dialogInterface, i ->
                dialogInterface.dismiss()
                mActionMode!!.finish()
            }
        builder.create().show()
        // -----------------------------------------------------------------------------------------
    }

    companion object {
        // ---------------------------------------------------------------------------------------------
        // Deklarasi Kebutuhan
        private const val DATA_LOADER = 0
    }
}