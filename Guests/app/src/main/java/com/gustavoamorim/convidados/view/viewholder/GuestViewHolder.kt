package com.gustavoamorim.convidados.view.viewholder

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gustavoamorim.convidados.R
import com.gustavoamorim.convidados.service.model.GuestModel
import com.gustavoamorim.convidados.view.listener.GuestListener

class GuestViewHolder(itemView: View, private val listener: GuestListener) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(guest: GuestModel) {
        val textName = itemView.findViewById<TextView>(R.id.text_name)
        textName.text = guest.name

        textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remove_guest)
                .setMessage(R.string.want_remove)
                .setPositiveButton(R.string.remove) { _, _ ->
                    listener.onDelete(guest.id)
                }.setNeutralButton(R.string.cancel, null)
                .show()
            true
        }
    }

}