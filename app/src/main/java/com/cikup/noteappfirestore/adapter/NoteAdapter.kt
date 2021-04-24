package com.cikup.noteappfirestore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.cikup.noteappfirestore.R
import com.cikup.noteappfirestore.helper.event.EventDelete
import com.cikup.noteappfirestore.model.NoteModel
import com.cikup.noteappfirestore.helper.navigation.navigateToDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item_note.view.*
import org.greenrobot.eventbus.EventBus

class NoteAdapter (
        private val context: Context,
        private val noteModel: ArrayList<NoteModel>
        ) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {


    private val bus = EventBus.getDefault()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = noteModel[position]
        val view = holder.itemView

        view.titleTV.text = data.title
        view.descriptionTV.text = data.description

        view.setOnClickListener {
            val bundle = bundleOf(
                    "id" to data.id
            )
            navigateToDetailActivity(context, bundle)
        }
        view.setOnLongClickListener {
            bus.post(EventDelete(data.id))

            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = noteModel.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}