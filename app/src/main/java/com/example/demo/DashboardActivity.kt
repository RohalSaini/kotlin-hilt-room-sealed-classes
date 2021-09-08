package com.example.demo

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.demo.Adapter.RecylerViewAdpater
import com.example.demo.modal.User
import com.google.android.material.navigation.NavigationView
import com.example.demo.databinding.ActivityDashboardBinding
import com.example.demo.modal.Todo
import java.lang.Exception
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener


class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val TAG = "DASHBOARD ACTIVITY"
    lateinit var binding:ActivityDashboardBinding
    lateinit var user:User
    var todoList:MutableList<Todo> = mutableListOf()
    lateinit var adapter:RecylerViewAdpater

     @SuppressLint("WrongConstant", "NotifyDataSetChanged")
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = ActivityDashboardBinding.inflate(layoutInflater)
         val view = binding.root
         setContentView(view)

         // Getting data from parser
         user = intent.extras?.getParcelable<User>("obj")!!

         binding.navView.setNavigationItemSelectedListener(this)
         binding.floatAddTodo.setOnClickListener {
             var intent = Intent(this,TodoAddActivity::class.java)
                 intent.putExtra("obj",user)
                 startActivity(intent)
         }
         try {
             var db = DbHelper(this)
             todoList =db.getTodoByEmail(user.email,"0")
         } catch (error:Exception){
            Log.e(TAG,error.message.toString())
         }
         binding.swiperefreshLayout.setOnRefreshListener(OnRefreshListener {
             var db = DbHelper(this)
             var temp = db.getTodoByEmail(user.email,todoList.size.toString())
             for(todo in temp) {
                 todoList.add(todo)
             }
             //todoList = db.getTodoByEmail(user.email)
             adapter.notifyDataSetChanged()
             binding.swiperefreshLayout.isRefreshing = false
         })
         binding.layputToolbar.settings.setOnClickListener {
             logout(this)
         }
//         binding.layputToolbar.expandedMenu.setOnClickListener {
//             Log.d("One Id is", "Home button clicked")
//         }
//         binding.layputToolbar.appName.setOnClickListener {
//             Log.d("Two Id is", "app named clicked")
//         }
//         binding.layputToolbar.settings.setOnClickListener {
//             Log.d("three Id is", "setting clicked")
//         }
//         binding.navView.getHeaderView(0).setOnClickListener {
//             Log.d("Header view", " header view clicked")
//         }
         if(todoList.isNotEmpty()) {
             val staggeredGridLayoutManager = StaggeredGridLayoutManager(1,LinearLayoutManager.VERTICAL)
             binding.emptyRecylerView.visibility = View.INVISIBLE
             adapter = RecylerViewAdpater(this,todoList)
             binding.baseAdpater.layoutManager = staggeredGridLayoutManager
             binding.baseAdpater.adapter = adapter
         }
     }

    private fun clickMenu(view: View) {
        openDrawer(binding.drawerLayout)
    }
    private fun Logout(view: View) {
       closeDrawer(binding.drawerLayout)
    }

    private fun closeDrawer(drawerLayout: DrawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        }
    }

    private fun openDrawer(drawerLayout: DrawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START)
    }
    private fun logout(mainActivity: DashboardActivity) {
                        AlertDialog.Builder(mainActivity)
                            .setTitle("Logout")
                            .setMessage("Are you sure you want to LogOut !!")
                            .setPositiveButton("Yes",DialogInterface.OnClickListener {
                                    dialog, i ->
                                            mainActivity.finish()
                                            var session = Session(this)
                                                session.setLoggedin(false,"","","","")
                                                session.removeAll()
                                                finish()
                             })
                            .setNegativeButton("No",DialogInterface.OnClickListener {
                                    dialogInterface, i ->
                                dialogInterface.dismiss()
                            }).show()

    }

    override fun onPause() {
        super.onPause()
        closeDrawer(binding.drawerLayout)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            binding.navView.menu.getItem(0).itemId -> Toast.makeText(this, "Home Clicked", Toast.LENGTH_LONG).show()
            binding.navView.menu.getItem(1).itemId -> Toast.makeText(this, "Dashboard Clicked", Toast.LENGTH_LONG).show()
            binding.navView.menu.getItem(2).itemId -> logout(this)
        }
        return true
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Do you want to Exit?")
        builder.setPositiveButton("Yes") { dialog, which -> //if user pressed "yes", then he is allowed to exit from application
            finishAffinity()
        }
        builder.setNegativeButton("No") { dialog, which -> //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        }.create().show()
    }
}
