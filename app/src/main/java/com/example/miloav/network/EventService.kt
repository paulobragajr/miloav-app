package com.example.miloav.network

import com.example.miloav.model.Event
import com.example.miloav.model.EventResponse
import com.example.miloav.model.Member
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore

interface EventListener {
    fun onSuccessEventListListener(eventList: MutableList<Event>) {}
    fun onSuccessEventListener(event: Event) {}
    fun onFailureListener(isError: Boolean)
}

class EventService(private val listener: EventListener) {
    fun fetchEvents() {
        FirebaseFirestore.getInstance().collection(FireBaseServicePath.EVENTS_PATH.path)
            .get()
            .addOnSuccessListener { result ->
                val eventTasks = mutableListOf<Task<Event>>()

                for (document in result) {
                    val eventResponse = document.toObject(EventResponse::class.java)
                    val taskEvent = parseEvent(document.id, eventResponse)
                    eventTasks.add(taskEvent)
                }

                Tasks.whenAllSuccess<Event>(eventTasks)
                    .addOnSuccessListener { eventsList ->
                        listener.onSuccessEventListListener(eventsList)
                    }
                    .addOnFailureListener {
                        listener.onFailureListener(true);
                    }
            }
            .addOnFailureListener {
                listener.onFailureListener(true);
            }
    }

    fun fetchEventById(eventId: String) {
        FirebaseFirestore.getInstance().collection(FireBaseServicePath.EVENTS_PATH.path)
            .document(eventId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val eventResponse = document.toObject(EventResponse::class.java)

                    eventResponse?.let { event ->
                        val taskEvent = parseEvent(document.id, event)
                        taskEvent.addOnSuccessListener { eventsList ->
                            listener.onSuccessEventListener(eventsList)
                        }.addOnFailureListener {
                            listener.onFailureListener(true);
                        }
                    }
                } else {
                    listener.onFailureListener(true);
                }
            }
            .addOnFailureListener {
                listener.onFailureListener(true);
            }
    }

    private fun parseEvent(id: String, eventResponse: EventResponse): Task<Event> {
        val responsibleTask = eventResponse.responsible?.get()
            ?.continueWith { task ->
                task.result?.toObject(Member::class.java)
            } ?: Tasks.forResult(null)

        val backsTasks = backs(eventResponse)
        val playersTasks = players(eventResponse)

        val allMemberTasks = mutableListOf<Task<Member?>>()
        allMemberTasks.addAll(backsTasks)
        allMemberTasks.addAll(playersTasks)

        val combinedTask = Tasks.whenAllSuccess<Any>(listOf(responsibleTask) + allMemberTasks)
            .continueWith { task ->
                val results = task.result
                val responsible = results[0] as Member?
                val backs = backsTasks.mapNotNull { it.result }
                val players = playersTasks.mapNotNull { it.result }
                Event(
                    id = id,
                    eventType = eventResponse.eventType,
                    eventDate = eventResponse.eventDate,
                    repertoire = eventResponse.repertoire,
                    responsible = responsible,
                    backs = backs,
                    players = players
                )
            }
        return combinedTask
    }

    private fun backs(eventResponse: EventResponse): List<Task<Member?>> {
        return eventResponse.backs?.map { ref ->
            ref.get().continueWith { it.result?.toObject(Member::class.java) }
        } ?: emptyList()
    }

    private fun players(eventResponse: EventResponse): List<Task<Member?>> {
        return eventResponse.players?.map { ref ->
            ref.get().continueWith { it.result?.toObject(Member::class.java) }
        } ?: emptyList()
    }
}