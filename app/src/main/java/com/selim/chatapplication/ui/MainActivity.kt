 package com.selim.chatapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.selim.chatapplication.R
import com.selim.chatapplication.model.User
import com.selim.chatapplication.adapters.ShowUsersAdapter
import com.selim.chatapplication.databinding.ActivityMainBinding

 class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter: ShowUsersAdapter
    private lateinit var usersList: ArrayList<User>
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference()
        usersList = ArrayList<User>()
        adapter = ShowUsersAdapter(this,usersList)
        binding.recyclerViewMainUsers.setHasFixedSize(true)
        binding.recyclerViewMainUsers.adapter = adapter

        dbRef.child("user").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                usersList.clear()
                for (currentSnapShot in snapshot.children){
                    val currentUser = currentSnapShot.getValue(User::class.java)
                    if (currentUser?.uid !=auth.currentUser?.uid) {
                        usersList.add(currentUser!!)
                    }
                }
                Toast.makeText(this@MainActivity, usersList.toString(), Toast.LENGTH_LONG).show()
                Log.d("here", usersList.toString())
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         MenuInflater(this).inflate(R.menu.manu_logout,menu)
         return super.onCreateOptionsMenu(menu)
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         if (item.itemId== R.id.menu_item_logout){
             auth.signOut()
             startActivity(Intent(this@MainActivity, LoginActivity::class.java))
             finish()
         }
         return true
     }


}