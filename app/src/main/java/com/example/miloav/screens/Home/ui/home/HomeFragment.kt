package com.example.miloav.screens.Home.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miloav.databinding.FragmentHomeBinding
import com.example.miloav.model.Event
import com.example.miloav.screens.AddEventActivity.AddEventActivity
import com.example.miloav.screens.DetailEvent.DetailEventActivity
import com.example.miloav.screens.Home.ui.home.adapter.EventAdapter
import com.example.miloav.screens.Home.ui.home.adapter.EventAdapterClickListener
import com.example.miloav.screens.Home.ui.members.adapter.MemberAdapter

class HomeFragment : Fragment() , EventAdapterClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.eventsRecyclerView
        val loading = binding.loading

        homeViewModel.eventsList.observe(viewLifecycleOwner) {
            if(!it.isEmpty()) {
                val customAdapter = EventAdapter(it,this )
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = customAdapter
                recyclerView.isVisible = true
                loading.isVisible = false
            }
        }

        homeViewModel.isError.observe(viewLifecycleOwner) {
            if(it) {
                loading.isVisible = false
            }
        }

        homeViewModel.events()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(event: Event) {
        val intent = Intent(context, DetailEventActivity::class.java)
        intent.putExtra("idEvent", event.id)
        startActivity(intent)
    }
}