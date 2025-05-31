package com.example.miloav.model

import com.google.firebase.Timestamp
import java.io.Serializable

data class Event(
    var id: String? = "",
    var eventType: String = "",
    var repertoire: String = "",
    var eventDate: Timestamp? = null,
    var responsible: Member? = null,
    val backs: List<Member>?,
    val players: List<Member>?
): Serializable