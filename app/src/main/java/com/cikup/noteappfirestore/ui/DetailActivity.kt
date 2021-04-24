package com.cikup.noteappfirestore.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cikup.noteappfirestore.R
import com.cikup.noteappfirestore.helper.navigation.backToMainActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    private var idNote = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        idNote = intent.getStringExtra("id").toString()

        getNotesById()

        editBTN.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.editBTN ->{
                editNote()
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun getNotesById(){
        progressBarHolder.visibility = View.VISIBLE
        firebaseFirestore.collection("note")
                .document(idNote)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        progressBarHolder.visibility = View.GONE

                        val data = it.result!!

                        titleDetailActivityEDT.setText(data["title"].toString())
                        descriptionDetailActivityEDT.setText(data["description"].toString())

                        val status = data["status"] as Boolean
                        val date = data["create_at"] as Timestamp
                        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

                        if (status){
                            dateTV.text = "Edited ${dateFormat.format(date.toDate())}"
                        }else{
                            dateTV.text = "Created ${dateFormat.format(date.toDate())}"

                        }

                    }
                }
                .addOnFailureListener {
                    progressBarHolder.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
    }

    private fun editNote(){
        progressBarHolder.visibility = View.VISIBLE
        val title = titleDetailActivityEDT.text.toString()
        val description = descriptionDetailActivityEDT.text.toString()

        val calendar = Calendar.getInstance()

        val data = hashMapOf<String, Any>(
                "title" to title,
                "description" to description,
                "create_at" to calendar.time, //calender.time to get current date or get today
                "status" to true
        )
        firebaseFirestore.collection("note")
                .document(idNote)
                .update(data)
                .addOnCompleteListener {
                    progressBarHolder.visibility = View.GONE
                    Toast.makeText(this, "Edit Success", Toast.LENGTH_LONG).show()
                    backToMainActivity(this)

                }
                .addOnFailureListener {
                    progressBarHolder.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        backToMainActivity(this)
    }
}