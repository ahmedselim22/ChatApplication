package com.selim.chatapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.selim.chatapplication.model.Message
import com.selim.chatapplication.adapters.MessageAdapter
import com.selim.chatapplication.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding:ActivityChatBinding
    private lateinit var adapter:MessageAdapter
    lateinit var messageList: ArrayList<Message>
    private var receiverRoom:String?=null
    private var senderRoom:String?=null
    lateinit var dbRef :DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbRef =FirebaseDatabase.getInstance().getReference()
        messageList = ArrayList()

        adapter = MessageAdapter(this,messageList)

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        supportActionBar?.title = name

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        binding.recyclerViewChatMessages.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewChatMessages.adapter = adapter

        dbRef.child("chat").child(senderRoom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (currentSnapShot in snapshot.children){
                        val currentMessage = currentSnapShot.getValue(Message::class.java)
                        messageList.add(currentMessage!!)
                    }
                    Log.d("here sender ,receiver ",senderUid.toString()+" , "+receiverUid.toString())
                    Log.d("here messages ", messageList.size.toString())
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("here database error", error.message.toString())
                }

            })

        binding.ivButtonSend.setOnClickListener {
            val message = binding.etChatMessage.text.toString()
            val messageObject = Message(message,senderUid)
            dbRef.child("chat").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    dbRef.child("chat").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            binding.etChatMessage.setText("")
        }
    }
}










