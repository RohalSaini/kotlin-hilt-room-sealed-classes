package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.databinding.ActivityLoginBinding
import com.example.demo.modal.User
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    val TAG = "LOGIN ACTIVITY"
    lateinit var db :DbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        settingOnClickListener()
        var session = Session(this)
        if(session.loggedin()) {
           var obj = User(id = session.getId().toString(),username = session.getName().toString(),pasword = session.getPassword().toString(),email = session.getEmail().toString())
           callToActivity(obj)
        }
    }

    private fun settingOnClickListener() {
        binding.editextEmailAddress.setOnClickListener {
            binding.editextPasssword.requestFocus()
        }
        binding.editextPasssword.setOnClickListener {
            hideKeyboard(it)
            binding.btnLogin.requestFocus()
        }
        binding.btnLogin.setOnClickListener {
            hideKeyboard(it)
            if(ValidationChecking()) {
                var email = binding.editextEmailAddress.text.trim()
                var password = binding.editextPasssword.text.trim()
                callDbToCheckUser(email = email,password= password,it)
            }
        }

        binding.registerHere.setOnClickListener {
            var intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun callDbToCheckUser(email: CharSequence, password: CharSequence, view: View) {
        try {
            val obj = DbHelper(this).getUser(email = email.toString())
            when {
                obj == null -> {
                    Snackbar
                        .make(view,"Wrong email address", Snackbar.LENGTH_LONG)
                        .show()
                }
                obj.pasword != password.toString() -> {
                    Snackbar
                        .make(view,"Wrong password filled, please fill correct one", Snackbar.LENGTH_LONG)
                        .show(); // Don’t forget to show!
                }
                else -> {

                    var session = Session(this)
                    session.setLoggedin(true,obj.email,obj.pasword,obj.username,obj.id)

                    callToActivity(obj= obj)
                    Log.d(TAG, "Data is $obj")
                }
            }
        }catch (exception:Exception) {
            Log.e(TAG,exception.message.toString())
            Snackbar
                .make(view,"Email  is not correct ,Please fill valid email address", Snackbar.LENGTH_LONG)
                .show(); // Don’t forget to show!

        }
    }

    private fun callToActivity(obj: User) {
        var intent = Intent(this,DashboardActivity::class.java)
        intent.putExtra("obj",obj)
        startActivity(intent)
    }

    private fun ValidationChecking() :Boolean{
        if (binding.editextEmailAddress.text.trim().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(binding.editextEmailAddress.text.trim()).matches()) {
            Toast.makeText(this,"Enter valid email address",Toast.LENGTH_LONG).show()
            return  false;
        }
        if(binding.editextPasssword.text.trim().isBlank() || binding.editextPasssword.text.isNullOrEmpty() ) {
            Toast.makeText(this,"Enter Password ",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    private fun hideKeyboard(v: View) {
        val inputMethodManager:InputMethodManager= getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }

    override fun onRestart() {
        super.onRestart()
        binding.editextEmailAddress.text.clear()
        binding.editextPasssword.text.clear()
    }
}