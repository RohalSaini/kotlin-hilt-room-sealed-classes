package com.example.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Collection
        val hashMap:HashMap<Int,String> = HashMap<Int,String>() //define empty hashmap
        hashMap.put(1,"Ajay")
        hashMap.put(3,"Vijay")
        hashMap.put(4,"Praveen")
        hashMap.put(2,"Ajay")
        println(".....traversing hashmap.......")
        for(key in hashMap.keys){
            println("Element at key $key = ${hashMap[key]}")
        }
        hashMap.replace(3,"Ashu") // to replace value at no 3
        hashMap.put(2,"Raj") // to add new value at number
        println(hashMap.containsKey(7))
        println(".....traversing hashmap.......")
        for(key in hashMap.keys){
            println("Element at key $key = ${hashMap[key]}")
        }
        hashMap.clear()
        // kotlin basics + advance
    }
}
