package com.example.miloav.network

import com.example.miloav.model.Member
import com.google.firebase.firestore.FirebaseFirestore

interface MemberListener {
    fun onSuccessMemberListListener(memberList: MutableList<Member>)
    fun onFailureListener(isError: Boolean)
}
class MemberService(private val listener: MemberListener) {
    fun fetchMembers() {
        FirebaseFirestore.getInstance().collection(FireBaseServicePath.MAMBERS_PATH.path)
            .get()
            .addOnSuccessListener { result ->
                val listaMenbers = mutableListOf<Member>()
                for (document in result) {
                    val member = document.toObject(Member::class.java)
                    listaMenbers.add(member)
                }
                this.listener.onSuccessMemberListListener(listaMenbers)
            }
            .addOnFailureListener { exception ->
                listener.onFailureListener(true);
            }
    }
}