package com.selim.chatapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.selim.chatapplication.model.Message
import com.selim.chatapplication.R
import com.selim.chatapplication.databinding.MessageRecievedLayoutBinding
import com.selim.chatapplication.databinding.MessageSentLayoutBinding

class MessageTestAdapter(private val context: Context,private val messageList:ArrayList<Message>) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val ITEM_RECEIVE =1
    private val ITEM_SENT =2
    class SentViewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding = MessageSentLayoutBinding.bind(itemView)
    }
    class ReceivedViewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding = MessageRecievedLayoutBinding.bind(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage =messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==1){
            val view = LayoutInflater.from(context).inflate(R.layout.message_recieved_layout,parent,false)
            return ReceivedViewholder(view)
        }
        else{
            val view = LayoutInflater.from(context).inflate(R.layout.message_sent_layout,parent,false)
            return SentViewholder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewholder::class.java){
            val viewHolder = holder as SentViewholder
            viewHolder.binding.tvMessageSent.text =currentMessage.message
        }else{
            val viewholder =holder as ReceivedViewholder
            viewholder.binding.tvMessageRecived.text =currentMessage.message
        }
    }

}