package com.cikup.noteappfirestore.model

import com.google.firebase.Timestamp
import java.util.*

data class NoteModel(
        val id: String = "",
        val title: String = "",
        val description: String = "",
        val create_at: Timestamp = Timestamp(Date()),
        val status: Boolean = false
)