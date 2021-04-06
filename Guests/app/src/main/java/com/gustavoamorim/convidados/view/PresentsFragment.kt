package com.gustavoamorim.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

class PresentsFragment : Fragment() {

    private lateinit var mPresentsViewModel: GuestsViewModel
    private lateinit var mListener: GuestListener
    private val mAdapter = GuestAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mPresentsViewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_presents, container, false)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_presents)
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
                mPresentsViewModel.delete(id)
                mPresentsViewModel.load(GuestConstants.FILTER.PRESENTS)
            }
        }

        observe()
        return root
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mPresentsViewModel.load(GuestConstants.FILTER.PRESENTS)
    }

    private fun observe() {
        mPresentsViewModel.guestList.observe(viewLifecycleOwner, {
            mAdapter.updateGuests(it)
        })
    }
}