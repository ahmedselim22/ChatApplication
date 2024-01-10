package com.selim.chatapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.selim.chatapplication.ui.ChatActivity
import com.selim.chatapplication.R
import com.selim.chatapplication.model.User
import com.selim.chatapplication.databinding.UserItemLayoutBinding

class ShowUsersAdapter(private val context: Context,private val usersList: List<User>) :RecyclerView.Adapter<ShowUsersAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View):ViewHolder(itemView){
        val binding = UserItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = usersList[position]
        holder.binding.tvUserItemName.text = currentUser.name
        holder.itemView.setOnClickListener {
            val intent =Intent(context, ChatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        }
    }
}