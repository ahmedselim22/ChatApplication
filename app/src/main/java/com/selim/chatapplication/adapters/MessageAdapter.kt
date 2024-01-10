package com.selim.chatapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.selim.chatapplication.model.Message
import com.selim.chatapplication.R
import com.selim.chatapplication.databinding.MessageRecievedLayoutBinding
import com.selim.chatapplication.databinding.MessageSentLayoutBinding

class MessageAdapter(private val context: Context,private val list: ArrayList<Message>) :RecyclerView.Adapter<MessageAdapter.BaseViewHolder>() {


    abstract class BaseViewHolder(itemView: View) :ViewHolder(itemView)

    class SentMessageViewHolder(itemView: View):BaseViewHolder(itemView){
        val binding = MessageSentLayoutBinding.bind(itemView)
    }
    class RecivedMessageViewHolder(itemView: View):BaseViewHolder(itemView){
        val binding = MessageRecievedLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType){
            TYPE_SENT-> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.message_sent_layout, parent, false)
                return SentMessageViewHolder(view)
            }
            TYPE_RECEIVE->{
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.message_recieved_layout, parent, false)
                return RecivedMessageViewHolder(view)
            }
        }
        return super.createViewHolder(parent,viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val currentMessage = list[position]
        when(holder){
            is SentMessageViewHolder->{
                holder.binding.tvMessageSent.text = currentMessage.message
            }
            is RecivedMessageViewHolder->{
                holder.binding.tvMessageRecived.text = currentMessage.message
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(list[position].senderId)){
            return TYPE_SENT
        }
        else{
            return TYPE_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    companion object{
        const val TYPE_SENT =11
        const val TYPE_RECEIVE =22
    }
}