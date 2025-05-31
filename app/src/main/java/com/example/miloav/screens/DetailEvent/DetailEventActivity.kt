package com.example.miloav.screens.DetailEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.example.miloav.R
import com.example.miloav.databinding.ActivityDetailEventBinding
import com.example.miloav.model.Event
import com.example.miloav.model.Member
import com.example.miloav.network.EventListener
import com.example.miloav.network.EventService
import java.text.SimpleDateFormat
import java.util.Locale

class DetailEventActivity : AppCompatActivity(), EventListener {
    private val service = EventService(this)

    private var _binding: ActivityDetailEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val event = intent.getStringExtra("idEvent")
        event?.let { service.fetchEventById(it) }
        setupToolbar()
    }

    fun setupToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSuccessEventListener(event: Event) {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        binding.dataEventDetail.text = simpleDateFormat.format(event.eventDate?.seconds?.times(1000L) ?: 0)
        binding.responsibleDetail.text = event.responsible?.name

        event.players?.let { displayPlayers(it) }
        event.backs?.let { displayBacks(it) }


        if (event.repertoire.isEmpty()){
            binding.repertoire.text = "Repertório não Adicionado"
            return
        }

        binding.repertoire.text = event.repertoire
    }

    fun displayPlayers(players: List<Member>){
        for (payler in players){
            val view = LayoutInflater.from(this).inflate(R.layout.member_item, null, false)
            view.findViewById<TextView>(R.id.name_item).text = payler.name
            view.findViewById<TextView>(R.id.description_function_item).text = payler.descriptionFunction.lowercase()
            binding.layoutPlayers.addView(view)
        }
    }

    fun displayBacks(backs: List<Member>){
        for (back in backs){
            val view = LayoutInflater.from(this).inflate(R.layout.member_item, null, false)
            view.findViewById<TextView>(R.id.name_item).text = back.name
            view.findViewById<TextView>(R.id.description_function_item).text = back.descriptionFunction.lowercase()
            binding.layoutBacks.addView(view)
        }
    }


    override fun onFailureListener(isError: Boolean) {
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}