package com.example.miloav.model
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class EventResponse(
    val repertoire: String = "",
    val eventType: String = "",
    val eventDate: Timestamp? = null,
    val responsible: DocumentReference? = null,
    val backs: List<DocumentReference>? = null,
    val players: List<DocumentReference>? = null
)