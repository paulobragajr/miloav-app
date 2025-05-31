package com.example.miloav.screens.Home.ui.members

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.miloav.model.Member
import com.example.miloav.network.MemberListener
import com.example.miloav.network.MemberService

class MembersViewModel : ViewModel(), MemberListener {
    private val service = MemberService(this)
    private val _memberList = MutableLiveData<MutableList<Member>>().apply {
        value = mutableListOf<Member>()
    }
    val memberList: LiveData<MutableList<Member>> = _memberList

    private val _isError = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isError: LiveData<Boolean> = _isError

    fun members(){
        service.fetchMembers()
    }

    override fun onSuccessMemberListListener(memberList: MutableList<Member>) {
        _memberList.value = memberList
    }

    override fun onFailureListener(isError: Boolean) {
        _isError.value = isError
    }
}