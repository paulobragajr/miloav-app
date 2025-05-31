package com.example.miloav.screens.AddEventActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.miloav.databinding.ActivityAddEventBinding

class AddEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
    }

    fun setupToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}