package com.cikup.noteappfirestore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cikup.noteappfirestore.R
import com.cikup.noteappfirestore.helper.navigation.backToMainActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add.*
import java.util.*

class AddActivity : AppCompatActivity() {

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        saveBTN.setOnClickListener {
            addNote()
        }
    }

    private fun addNote() {
        val title = titleAddActivityEDT.text.toString()
        val description = descriptionAddActivityEDT.text.toString()

        val calendar = Calendar.getInstance()

        val ids = firebaseFirestore.collection("note").document().id

        val data = hashMapOf<String, Any>(
                "id" to ids,
                "title" to title,
                "description" to description,
                "create_at" to calendar.time, //calender.time to get current date or get today
                "status" to false
        )

        firebaseFirestore.collection("note")
                .document(ids)
                .set(data)
                .addOnCompleteListener {
                    Toast.makeText(this, "Success add note", Toast.LENGTH_LONG).show()
                    backToMainActivity(this)
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backToMainActivity(this)
    }
}