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

class AbsentsFragment : Fragment() {

    private lateinit var mAbsentsViewModel: GuestsViewModel
    private lateinit var mListener: GuestListener
    private val mAdapter = GuestAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mAbsentsViewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_absents, container, false)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_absents)
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
                mAbsentsViewModel.delete(id)
                mAbsentsViewModel.load(GuestConstants.FILTER.ABSENTS)
            }
        }

        observe()
        return root
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener)
        mAbsentsViewModel.load(GuestConstants.FILTER.ABSENTS)
    }

    private fun observe() {
        mAbsentsViewModel.guestList.observe(viewLifecycleOwner, {
            mAdapter.updateGuests(it)
        })
    }
}