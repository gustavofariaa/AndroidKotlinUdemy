package com.gustavoamorim.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gustavoamorim.convidados.R
import com.gustavoamorim.convidados.service.constants.GuestConstants
import com.gustavoamorim.convidados.view.adapter.GuestAdapter
import com.gustavoamorim.convidados.view.listener.GuestListener
import com.gustavoamorim.convidados.viewmodel.GuestsViewModel

class AllGuestsFragment : Fragment() {

    private lateinit var mAllGuestsViewModel: GuestsViewModel
    private lateinit var mListener: GuestListener
    private val mAdapter = GuestAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mAllGuestsViewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_all, container, false)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_all_guests)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter

        mListener = object : GuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle = Bundle().apply { putInt(GuestConstants.ID, id) }

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mAllGuestsViewModel.delete(id)
                mAllGuestsViewModel.load(GuestConstants.FILTER.ALL)
            }
        }

        observe()
        return root
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mAllGuestsViewModel.load(GuestConstants.FILTER.ALL)
    }

    private fun observe() {
        mAllGuestsViewModel.guestList.observe(viewLifecycleOwner, {
            mAdapter.updateGuests(it)
        })
    }
}