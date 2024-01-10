package com.selim.chatapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.selim.chatapplication.model.User
import com.selim.chatapplication.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        dbRef =FirebaseDatabase.getInstance().getReference()
        binding.tvRegisterTextLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegister.setOnClickListener {
                if (validateInputs()){
                    val name = binding.etRegisterName.text!!.trim().toString()
                    val email = binding.etRegisterEmail.text!!.trim().toString()
                    val password = binding.etRegisterPassword.text!!.trim().toString()
                    register(name,email, password)
                }
                else{
                    Toast.makeText(this, "password not matches", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun register(name:String,email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            task->
            if (task.isSuccessful){
                addUserToDatabase(name,email,auth.currentUser!!.uid)
                Toast.makeText(this, "registered successfully", Toast.LENGTH_SHORT).show()
                val intent =Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "failed to register", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        dbRef.child("user").child(uid).setValue(User(name,email,uid))
    }

    private fun validateInputs(): Boolean {
        if (binding.etRegisterName.text?.isNotEmpty()!! && binding.etRegisterEmail.text?.isNotEmpty()!! &&
            binding.etRegisterPassword.text?.isNotEmpty()!!&& binding.etRegisterConfirmPassword.text?.isNotEmpty()!!){
            return true
        }
        return false
    }
}