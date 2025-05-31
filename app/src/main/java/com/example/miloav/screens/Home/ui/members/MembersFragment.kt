package com.example.miloav.screens.Home.ui.members

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miloav.databinding.FragmentMembersBinding
import com.example.miloav.screens.Home.ui.members.adapter.MemberAdapter

class MembersFragment : Fragment() {
    private var _binding: FragmentMembersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMembersBinding.inflate(inflater, container, false)
        val membersViewModel = ViewModelProvider(this).get(MembersViewModel::class.java)
        val recyclerView = binding.memberRecyclerView
        val loading = binding.loading

        membersViewModel.memberList.observe(viewLifecycleOwner) {
            if(!it.isEmpty()) {
                val customAdapter = MemberAdapter(it)
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = customAdapter
                recyclerView.isVisible = true
                loading.isVisible = false
            }
        }

        membersViewModel.isError.observe(viewLifecycleOwner) {
            if(it) {
                loading.isVisible = false
            }
        }

        membersViewModel.members()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}