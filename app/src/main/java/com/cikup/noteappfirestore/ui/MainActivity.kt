package com.cikup.noteappfirestore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cikup.noteappfirestore.R
import com.cikup.noteappfirestore.adapter.NoteAdapter
import com.cikup.noteappfirestore.helper.event.EventDelete
import com.cikup.noteappfirestore.model.NoteModel
import com.cikup.noteappfirestore.helper.navigation.navigateToAddActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

class MainActivity : AppCompatActivity() {


    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private val bus = EventBus.getDefault()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getNotes()

        addNoteFAB.setOnClickListener {
            navigateToAddActivity(this)
        }
    }

    private fun getNotes() {
        progressBarHolder.visibility = View.VISIBLE
        firebaseFirestore.collection("note")
                .orderBy("create_at", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener {
                    if (it.result != null) {
                        progressBarHolder.visibility = View.GONE

                        val data = it.result!!.toObjects<NoteModel>() as ArrayList

                        noteRV.apply {
                            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                            adapter = NoteAdapter(this@MainActivity, data)
                        }

                    }
                }
                .addOnFailureListener {
                    progressBarHolder.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
    }

    @Subscribe
    fun onEventDelete(eventDelete: EventDelete){
        MaterialAlertDialogBuilder(this)
                .setMessage("Are you sure you want to delete this?")
                .setNeutralButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton("Yes") { dialog, which ->
                    dialog.dismiss()
                    progressBarHolder.visibility = View.VISIBLE
                    //call function delete data firestore
                    FirebaseFirestore.getInstance()
                            .collection("note")
                            .document(eventDelete.id)
                            .delete()
                            .addOnCompleteListener {
                                Toast.makeText(this, "Delete Success", Toast.LENGTH_LONG).show()
                                getNotes()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                            }
                }.show()
    }

    override fun onResume() {
        super.onResume()
        bus.register(this)
    }

    override fun onPause() {
        super.onPause()
        bus.unregister(this)
    }
}